package com.opnitech.rules.core.executor.executers.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.WhenResult;
import com.opnitech.rules.core.executor.executers.impl.resolvers.SingleRuleParameterResolver;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.MethodMetadata;
import com.opnitech.rules.core.executor.reflection.MethodRunnerResult;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;
import com.opnitech.rules.core.executor.util.PriorityList;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.AnnotationValidatorUtil;
import com.opnitech.rules.core.utils.LoggerUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractRuleRunner extends AbstractRunner {

    private final int priority;

    private final PriorityList<MethodMetadata> actionMethodMetadatas = new PriorityList<>();

    private final Rule ruleAnnotation;

    public AbstractRuleRunner(Object rule) throws Exception {

        super(rule);

        AnnotationValidatorUtil.validateAnnotationPresent(rule, Rule.class);

        createActionMethodMetadatas();

        this.ruleAnnotation = AnnotationUtil.resolveAnnotation(rule, Rule.class);
        this.priority = resolvePriority(this.ruleAnnotation.priority());
    }

    @Override
    public WhenResult execute(WorkflowState workflowState) throws Throwable {

        doExecuteThen(workflowState);

        return new WhenResult(WhenEnum.ACCEPT);
    }

    @Override
    protected WhenResult createWhenResult(WhenEnum whenEnum) {

        return new WhenResult(whenEnum);
    }

    @Override
    public void logRuleMetadata(Logger logger, Object producer, int level) {

        LoggerUtil.info(logger, level, producer, null,
                "Simple Rule Executer. Rule class: ''{0}'', Description: ''{1}'', Priority: ''{2}''", getExecutable().getClass(),
                this.ruleAnnotation.description(), this.priority);

        logRuleMethodMetadata(logger, producer, level + 1);
    }

    private void logRuleMethodMetadata(Logger logger, Object producer, int level) {

        LoggerUtil.info(logger, level, producer, null, "Accept method...");
        logMethodsState(logger, producer, level + 1, getAcceptExecutionMethodMetadata());

        LoggerUtil.info(logger, level, producer, null, "Then methods...");
        for (MethodMetadata methodMetadata : this.actionMethodMetadatas) {
            logMethodsState(logger, producer, level + 1, methodMetadata);
        }
    }

    private void logMethodsState(Logger logger, Object producer, int level, MethodMetadata methodMetadata) {

        LoggerUtil.info(logger, level, producer, null, "Method. Name: ''{0}'', Priority: ''{1}''",
                methodMetadata.getMethod().getName(), methodMetadata.getPriority());

        logMethodParametersState(logger, producer, level + 1, methodMetadata);
    }

    private void logMethodParametersState(Logger logger, Object producer, int level, MethodMetadata methodMetadata) {

        ParameterMetadata[] parametersMetadata = methodMetadata.getParametersMetadata();

        if (ArrayUtils.isNotEmpty(parametersMetadata)) {
            for (ParameterMetadata methodParameterMetadata : parametersMetadata) {
                LoggerUtil.info(logger, level, producer, null, "Method Param. Type: ''{0}''",
                        methodParameterMetadata.getParameterType());
            }
        }
    }

    private void createActionMethodMetadatas() throws Exception {

        Object executable = getExecutable();

        List<Method> actionMethods = AnnotationUtil.resolveMethodsWithAnnotation(executable, Then.class);
        for (Method method : actionMethods) {
            Then actionAnnotation = AnnotationUtil.resolveAnnotation(method, Then.class);

            this.actionMethodMetadatas.add(new MethodMetadata(executable.getClass(), method, actionAnnotation.priority()));
        }
    }

    protected void doExecuteThen(WorkflowState workflowState) throws Throwable {

        for (MethodMetadata methodMetadata : this.actionMethodMetadatas) {

            methodMetadata.execute(new MethodRunnerResult<Void>(getExecutable()), new SingleRuleParameterResolver(workflowState));
        }
    }

    @Override
    public int getPriority() {

        return this.priority;
    }

    public Rule getRuleAnnotation() {

        return this.ruleAnnotation;
    }
}
