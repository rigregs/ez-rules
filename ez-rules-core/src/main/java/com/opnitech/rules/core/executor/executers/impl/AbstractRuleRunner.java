package com.opnitech.rules.core.executor.executers.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.RuleRunner;
import com.opnitech.rules.core.executor.executers.impl.resolvers.SingleRuleRunnerParameterResolver;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.MethodMetadata;
import com.opnitech.rules.core.executor.reflection.MethodRunnerResult;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;
import com.opnitech.rules.core.executor.util.PriorityList;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.LoggerUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractRuleRunner implements RuleRunner {

    private static final Map<Class<?>, WhenExecutor> WHEN_EXECUTORS = new HashMap<>();

    static {
        AbstractRuleRunner.WHEN_EXECUTORS.put(WhenEnum.class, new EnumWhenExecutor());
        AbstractRuleRunner.WHEN_EXECUTORS.put(Boolean.class, new BooleanWhenExecutor());
        AbstractRuleRunner.WHEN_EXECUTORS.put(boolean.class, new BooleanWhenExecutor());
    }

    private final int priority;

    private final Object rule;

    private final MethodMetadata acceptExecutionMethodMetadata;

    private final PriorityList<MethodMetadata> actionMethodMetadatas = new PriorityList<>();

    private final Rule ruleAnnotation;

    public AbstractRuleRunner(Object rule) throws Exception {

        this.rule = rule;

        AnnotationUtil.validateAnnotationPresent(this.rule, Rule.class);

        this.acceptExecutionMethodMetadata = createAcceptExecutionMethodMetadata();

        createActionMethodMetadatas();

        this.ruleAnnotation = AnnotationUtil.resolveAnnotation(rule, Rule.class);

        this.priority = this.ruleAnnotation.priority();
    }

    @Override
    public void logRuleMetadata(Logger logger, Object producer, int level) {

        LoggerUtil.info(logger, level, producer, null, "Simple Rule Executer. Rule class: {0}, Description: {1}, Priority: {2}",
                this.rule.getClass(), this.ruleAnnotation.description(), this.ruleAnnotation.priority());

        logRuleMethodMetadata(logger, producer, level + 1);
    }

    private void logRuleMethodMetadata(Logger logger, Object producer, int level) {

        LoggerUtil.info(logger, level, producer, null, "Accept method...");
        logMethodsState(logger, producer, level + 1, this.acceptExecutionMethodMetadata);

        LoggerUtil.info(logger, level, producer, null, "Then methods...");
        for (MethodMetadata methodMetadata : this.actionMethodMetadatas) {
            logMethodsState(logger, producer, level + 1, methodMetadata);
        }
    }

    private void logMethodsState(Logger logger, Object producer, int level, MethodMetadata methodMetadata) {

        LoggerUtil.info(logger, level, producer, null, "Method. Name: {0}, Priority: {1}", methodMetadata.getMethod().getName(),
                methodMetadata.getPriority());

        logMethodParametersState(logger, producer, level + 1, methodMetadata);
    }

    private void logMethodParametersState(Logger logger, Object producer, int level, MethodMetadata methodMetadata) {

        ParameterMetadata[] parametersMetadata = methodMetadata.getParametersMetadata();

        if (ArrayUtils.isNotEmpty(parametersMetadata)) {
            for (ParameterMetadata methodParameterMetadata : parametersMetadata) {
                LoggerUtil.info(logger, level, producer, null, "Method Param. Type: {0}",
                        methodParameterMetadata.getParameterType());
            }
        }
    }

    private void createActionMethodMetadatas() throws Exception {

        List<Method> actionMethods = AnnotationUtil.resolveMethodsWithAnnotation(this.rule, Then.class);
        for (Method method : actionMethods) {
            Then actionAnnotation = AnnotationUtil.resolveAnnotation(method, Then.class);

            this.actionMethodMetadatas.add(new MethodMetadata(this.rule.getClass(), method, actionAnnotation.priority()));
        }
    }

    private MethodMetadata createAcceptExecutionMethodMetadata() throws Exception {

        return new MethodMetadata(this.rule.getClass(), AnnotationUtil.resolveMethodWithAnnotation(this.rule, When.class));
    }

    protected WhenEnum doExecuteWhen(WorkflowState workflowState) throws Exception {

        WhenExecutor whenExecutor = AbstractRuleRunner.WHEN_EXECUTORS.get(this.acceptExecutionMethodMetadata.getReturnType());
        Validate.notNull(whenExecutor);

        return whenExecutor.executeWhen(workflowState, this.acceptExecutionMethodMetadata, this.rule);
    }

    protected void doExecuteThen(WorkflowState workflowState) throws Exception {

        for (MethodMetadata methodMetadata : this.actionMethodMetadatas) {

            methodMetadata.execute(new MethodRunnerResult<Void>(this.rule), new SingleRuleRunnerParameterResolver(workflowState));
        }
    }

    @Override
    public int getPriority() {

        return this.priority;
    }

    protected Object getRule() {

        return this.rule;
    }

    public Rule getRuleAnnotation() {

        return this.ruleAnnotation;
    }

    private static interface WhenExecutor {
        WhenEnum executeWhen(WorkflowState workflowState, MethodMetadata acceptExecutionMethodMetadata, Object rule)
                throws Exception;
    }

    private static class EnumWhenExecutor implements WhenExecutor {

        public EnumWhenExecutor() {
            // Default constructor
        }

        @Override
        public WhenEnum executeWhen(WorkflowState workflowState, MethodMetadata acceptExecutionMethodMetadata, Object rule)
                throws Exception {

            MethodRunnerResult<WhenEnum> methodExecutionResult = new MethodRunnerResult<>(rule);

            acceptExecutionMethodMetadata.execute(methodExecutionResult, new SingleRuleRunnerParameterResolver(workflowState));

            return methodExecutionResult.getResult();
        }

    }

    private static class BooleanWhenExecutor implements WhenExecutor {

        public BooleanWhenExecutor() {
            // Default constructor
        }

        @Override
        public WhenEnum executeWhen(WorkflowState workflowState, MethodMetadata acceptExecutionMethodMetadata, Object rule)
                throws Exception {

            MethodRunnerResult<Boolean> methodExecutionResult = new MethodRunnerResult<>(rule);

            acceptExecutionMethodMetadata.execute(methodExecutionResult, new SingleRuleRunnerParameterResolver(workflowState));

            return methodExecutionResult.getResult()
                    ? WhenEnum.ACCEPT
                    : WhenEnum.REJECT;
        }

    }
}
