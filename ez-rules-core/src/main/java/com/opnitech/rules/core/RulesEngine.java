package com.opnitech.rules.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.executor.RuleEngineExecuter;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;
import com.opnitech.rules.core.utils.LoggerUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;
import com.opnitech.rules.core.validators.RunnerDefinitionValidator;
import com.opnitech.rules.core.validators.impl.CallbackRunnerDefinitionValidator;
import com.opnitech.rules.core.validators.impl.GroupDefinitionRunnerDefinitionValidator;
import com.opnitech.rules.core.validators.impl.RuleRunnerDefinitionValidator;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class RulesEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(RulesEngine.class);

    private static final Map<Class<?>, RunnerDefinitionConditionValidator> VALIDATORS = new HashMap<>();

    static {
        RulesEngine.VALIDATORS.put(Callback.class, new CallbackRunnerDefinitionValidator());
        RulesEngine.VALIDATORS.put(Rule.class, new RuleRunnerDefinitionValidator());
        RulesEngine.VALIDATORS.put(GroupDefinition.class, new GroupDefinitionRunnerDefinitionValidator());
    }

    private boolean validated = true;

    private List<Object> executables = new ArrayList<>();

    private List<Object> callbacks = new ArrayList<>();

    private List<Object> rules = new ArrayList<>();

    private List<Object> groupDefinitions = new ArrayList<>();

    private RuleEngineExecuter ruleEngineExecuter;

    private final String description;

    public RulesEngine() {

        this(RulesEngine.class.getSimpleName());
    }

    public RulesEngine(String description) {

        this.description = description;
    }

    /**
     * Execute the rule engine, you can pass as many exchange you need for the
     * process
     * 
     * @param exchanges
     *            Initial list of exchanges that the rules will use. The engine
     *            will try to match the parameters to the rules method with the
     *            exchanges you pass here, if match the value will be passed to
     *            the rule execution
     * @return The overall status of the rule execution. You can find if the
     *         execution is success, the exception (if happen) and many other
     *         information.
     * @throws EngineException
     *             If any exception occurs it get wrapped in a EngineException
     */
    public RuleEngineExecutionResult execute(Object... exchanges) throws EngineException {

        return internalExecute(true, exchanges);
    }

    /**
     * Execute without trigger internal exceptions in the rules
     * 
     * @param exchanges
     *            Initial list of exchanges that the rules will use. The engine
     *            will try to match the parameters to the rules method with the
     *            exchanges you pass here, if match the value will be passed to
     *            the rule execution
     * @return The overall status of the rule execution. You can find if the
     *         execution is success, the exception (if happen) and many other
     *         information.
     * @throws EngineException
     *             If any exception occurs it get wrapped in a EngineException
     */
    public RuleEngineExecutionResult executeSilent(Object... exchanges) throws EngineException {

        return internalExecute(false, exchanges);
    }

    /**
     * Allow to create a named Exchange
     * 
     * @param name
     *            Exchange name
     * @param value
     *            Exchange value
     * @return
     */
    public static NamedExchange namedExchange(Object name, Object value) {

        return new NamedExchange(name, value);
    }

    public RuleEngineExecutionResult internalExecute(boolean throwException, Object... exchanges) throws EngineException {

        try {
            validateNullableExchanges(exchanges);

            WorkflowState workflowState = internalExecute(exchanges);
            if (throwException && workflowState.getThrowable() != null) {
                throw wrapToEngineException(workflowState.getThrowable());
            }

            Throwable throwable = workflowState.getThrowable();

            return new RuleEngineExecutionResult(throwable == null, throwable != null
                    ? wrapToEngineException(throwable)
                    : null, workflowState.getExchangeManager());
        }
        catch (Exception e) {
            if (throwException) {
                throw wrapToEngineException(e);
            }

            return new RuleEngineExecutionResult(false, wrapToEngineException(e), null);
        }
    }

    private EngineException wrapToEngineException(Throwable throwable) {

        return throwable instanceof EngineException
                ? (EngineException) throwable
                : new EngineException(throwable);
    }

    private void validateNullableExchanges(Object... exchanges) {

        for (Object exchange : exchanges) {
            Validate.notNull(exchange);
            if (NamedExchange.class.isAssignableFrom(exchange.getClass())) {
                NamedExchange namedExchange = (NamedExchange) exchange;
                Validate.notNull(namedExchange);
            }
        }
    }

    private WorkflowState internalExecute(Object... exchanges) throws EngineException {

        if (!this.validated) {
            ExceptionUtil.throwIllegalArgumentException(
                    "The state of the executer isn't valid, check all executables injected in the engine.");
        }

        if (this.ruleEngineExecuter == null) {
            createRuleEngineExecuter();
        }

        return this.ruleEngineExecuter.execute(exchanges);
    }

    private void createRuleEngineExecuter() throws EngineException {

        this.ruleEngineExecuter = new RuleEngineExecuter();
        try {
            this.ruleEngineExecuter.initialize(this.callbacks, this.rules, this.groupDefinitions);
        }
        catch (Exception e) {
            throw new EngineException(e);
        }
    }

    /**
     * Allow to register an executable
     * 
     * @param executablesToRegister
     *            Array of the executables to be registered. An executable can
     *            be a Rule or Callback.
     * @throws EngineException
     *             Throw an exception if the executable isn't valid
     */
    public void registerExecutable(Object... executablesToRegister) throws EngineException {

        try {
            clearExecuter();

            for (Object executable : executablesToRegister) {
                internalRegisterExecutable(executable);
            }
        }
        catch (Exception e) {
            throw new EngineException(e);
        }
    }

    private void internalRegisterExecutable(Object value) throws Exception {

        Validate.notNull(value);

        boolean registered = false;

        registered |= internalRegisterExecutable(this.callbacks, value, Callback.class);
        registered |= internalRegisterExecutable(this.rules, value, Rule.class);
        registered |= internalRegisterExecutable(this.groupDefinitions, value, GroupDefinition.class);
        registered |= internalRegisterExecutable(this.groupDefinitions, findGroupDefinitionFromRule(value),
                GroupDefinition.class);

        if (!registered) {
            this.validated = false;
            ExceptionUtil.throwIllegalArgumentException("Invalid argument trying to register a Rule Engine Element: {0}", value);
        }
    }

    private Object findGroupDefinitionFromRule(Object executable) throws Exception {

        Group group = AnnotationUtil.resolveAnnotation(executable, Group.class);

        return group != null
                ? group.groupKey()
                : null;
    }

    private boolean internalRegisterExecutable(List<Object> candidateExecutable, Object executable,
            Class<? extends Annotation> annotationClass) throws Exception {

        if (executable == null) {
            return false;
        }

        RunnerDefinitionConditionValidator executableDefinitionConditionValidator = resolveExecutableDefinitionConditionValidator(
                annotationClass);

        if (executableDefinitionConditionValidator.acceptRunner(executable)) {
            if (executableDefinitionConditionValidator instanceof RunnerDefinitionValidator) {
                ((RunnerDefinitionValidator) executableDefinitionConditionValidator).validate(executable);
            }

            candidateExecutable.add(executable);

            if (RulesEngine.LOGGER.isInfoEnabled()) {

                LoggerUtil.info(RulesEngine.LOGGER, this, this.description, "Register Executable. Type: {0}, Executable:{1}",
                        annotationClass, executable);
            }

            return true;
        }

        return false;
    }

    private RunnerDefinitionConditionValidator resolveExecutableDefinitionConditionValidator(
            Class<? extends Annotation> annotationClass) {

        RunnerDefinitionConditionValidator executableDefinitionConditionValidator = RulesEngine.VALIDATORS.get(annotationClass);
        Validate.notNull(executableDefinitionConditionValidator);

        return executableDefinitionConditionValidator;
    }

    private void clearExecuter() {

        this.ruleEngineExecuter = null;
    }

    private void clearExecutables() {

        this.executables.clear();
        this.callbacks.clear();
        this.rules.clear();
        this.groupDefinitions.clear();
    }

    /**
     * Allow to get all defined executables
     * 
     * @return Return the list of available executables
     */
    public List<Object> getExecutables() {

        return this.executables;
    }

    /**
     * Allow to define the executables that will form part of the rule engine.
     * Executable can be a Rule, Callback or GroupDefinition.
     * 
     * @param executables
     *            List of executables
     * @throws EngineException
     *             Throw an exception if at least one of the executables aren't
     *             valid
     */
    public void setExecutables(List<Object> executables) throws EngineException {

        try {
            clearExecuter();
            clearExecutables();

            for (Object executable : executables) {

                internalRegisterExecutable(executable);
                this.executables.add(executable);
            }
        }
        catch (Exception e) {
            throw new EngineException(e);
        }
    }
}
