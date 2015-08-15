package com.opnitech.rules.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opnitech.rules.core.ExchangeBuilder.NamedExchange;
import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
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

    private Lock ruleEngineExecuterLock = new ReentrantLock();

    private final String description;

    private final ExecutionStrategyEnum executionStrategy;

    public RulesEngine() {

        this(ExecutionStrategyEnum.ALL, RulesEngine.class.getSimpleName());
    }

    public RulesEngine(ExecutionStrategyEnum executionStrategy) {

        this(executionStrategy, RulesEngine.class.getSimpleName());
    }

    public RulesEngine(ExecutionStrategyEnum executionStrategy, String description) {

        Validate.notNull(executionStrategy);

        this.executionStrategy = executionStrategy;
        this.description = description;
    }

    /**
     * Execute the rule engine, you can pass as many exchange you need for the
     * process
     * 
     * @param <ResultType>
     *            The result of the rule execution, it populate with the last
     *            result of a {@link When} annotated method of the lasted
     *            register of a result using the {ExchangeManager}
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
    public <ResultType> ExecutionResult<ResultType> execute(Object... exchanges) throws EngineException {

        return internalExecute(true, exchanges);
    }

    /**
     * Execute without trigger internal exceptions in the rules
     * 
     * @param <ResultType>
     *            The result of the rule execution, it populate with the last
     *            result of a {@link When} annotated method of the lasted
     *            register of a result using the {ExchangeManager}
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
    public <ResultType> ExecutionResult<ResultType> executeSilent(Object... exchanges) throws EngineException {

        return internalExecute(false, exchanges);
    }

    private <ResultType> ExecutionResult<ResultType> internalExecute(boolean throwException, Object... exchanges)
            throws EngineException {

        try {
            validateNullableExchanges(exchanges);

            WorkflowState<ResultType> workflowState = internalExecute(exchanges);
            if (throwException && workflowState.getThrowable() != null) {
                throw wrapToEngineException(workflowState.getThrowable());
            }

            Throwable throwable = workflowState.getThrowable();

            @SuppressWarnings(
                {
                    "unchecked",
                    "unused"
                })
            ExecutionResult<ResultType> ruleExecutionResult = new ExecutionResult<ResultType>(throwable == null, throwable != null
                    ? wrapToEngineException(throwable)
                    : null, (ResultType) workflowState.getExchangeManager().resolveResult());

            return ruleExecutionResult;
        }
        catch (Exception e) {
            if (throwException) {
                throw wrapToEngineException(e);
            }

            @SuppressWarnings("unused")
            ExecutionResult<ResultType> ruleExecutionResult = new ExecutionResult<ResultType>(false, wrapToEngineException(e),
                    null);

            return ruleExecutionResult;
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

    private <ResultType> WorkflowState<ResultType> internalExecute(Object... exchanges) throws EngineException {

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

        try {
            this.ruleEngineExecuterLock.lock();

            if (this.ruleEngineExecuter == null) {

                this.ruleEngineExecuter = new RuleEngineExecuter(this.executionStrategy);
                try {
                    this.ruleEngineExecuter.initialize(this.callbacks, this.rules, this.groupDefinitions);
                }
                catch (Exception e) {
                    throw new EngineException(e);
                }
            }
        }
        finally {
            this.ruleEngineExecuterLock.unlock();
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
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid executer trying to register a Rule Engine Element: ''{0}'', the element doesn''t match any of the posssible executers...",
                    value);
        }
    }

    private Object findGroupDefinitionFromRule(Object executable) throws Exception {

        if (!AnnotationUtil.isAnnotationPresent(executable, Rule.class)) {
            return null;
        }

        Group group = AnnotationUtil.resolveAnnotation(executable, Group.class);

        Class<?> groupDefinitionClass = group != null
                ? group.value()
                : null;

        return this.groupDefinitions.contains(groupDefinitionClass)
                ? null
                : groupDefinitionClass;
    }

    private boolean internalRegisterExecutable(List<Object> candidateExecutables, Object executable,
            Class<? extends Annotation> annotationClass) throws Exception {

        if (executable == null) {
            return false;
        }

        RunnerDefinitionConditionValidator executableDefinitionConditionValidator = resolveExecutableDefinitionConditionValidator(
                annotationClass);

        if (executableDefinitionConditionValidator.acceptRunner(executable)) {
            if (executableDefinitionConditionValidator instanceof RunnerDefinitionValidator) {
                ((RunnerDefinitionValidator) executableDefinitionConditionValidator).validate(candidateExecutables, executable);
            }

            candidateExecutables.add(executable);

            if (RulesEngine.LOGGER.isInfoEnabled()) {

                LoggerUtil.info(RulesEngine.LOGGER, this, this.description,
                        "Register Executable. Type: ''{0}'', Executable:''{1}''", annotationClass, executable);
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

        try {
            this.ruleEngineExecuterLock.lock();
            this.ruleEngineExecuter = null;
        }
        finally {
            this.ruleEngineExecuterLock.unlock();
        }
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
