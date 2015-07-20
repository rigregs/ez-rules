package com.opnitech.rules.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opnitech.rules.core.annotations.callback.Callback;
import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.executor.RuleEngineExecuter;
import com.opnitech.rules.core.executor.flow.WorkflowState;
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
     * @return
     * @throws Throwable
     */
    public RuleEngineExecutionResult execute(Object... exchanges) throws EngineException {

        return execute(true, exchanges);
    }

    public RuleEngineExecutionResult execute(boolean throwException, Object... exchanges) throws EngineException {

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
     * @throws Exception
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

        if (!registered) {
            this.validated = false;
            ExceptionUtil.throwIllegalArgumentException("Invalid argument trying to register a Rule Engine Element: {0}", value);
        }
    }

    private boolean internalRegisterExecutable(List<Object> candidateExecutable, Object executable,
            Class<? extends Annotation> annotationClass) throws Exception {

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
     * @return
     */
    public List<Object> getExecutables() {

        return this.executables;
    }

    /**
     * Allow to define the executables that will form part of the rule engine.
     * Executable can be a Rule, Callback or GroupDefinition.
     * 
     * @param executables
     * @throws Exception
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