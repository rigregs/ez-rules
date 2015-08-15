package com.opnitech.rules.core.executor.executers.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.annotations.rule.Priority;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.executor.executers.WhenResult;
import com.opnitech.rules.core.executor.executers.impl.resolvers.SingleRuleParameterResolver;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.MethodMetadata;
import com.opnitech.rules.core.executor.reflection.MethodRunnerResult;
import com.opnitech.rules.core.utils.AnnotationUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractRunner implements Runner {

    private static final Map<Class<?>, WhenExecutor> WHEN_EXECUTORS = new HashMap<>();

    static {
        AbstractRunner.WHEN_EXECUTORS.put(WhenEnum.class, new EnumWhenExecutor());
        AbstractRunner.WHEN_EXECUTORS.put(Boolean.class, new BooleanWhenExecutor());
        AbstractRunner.WHEN_EXECUTORS.put(boolean.class, new BooleanWhenExecutor());
    }

    private final MethodMetadata acceptExecutionMethodMetadata;

    private final Object executable;

    public AbstractRunner(Object executable) throws Exception {
        this.executable = executable;
        this.acceptExecutionMethodMetadata = createAcceptExecutionMethodMetadata();
    }

    protected abstract WhenResult createWhenResult(WhenEnum whenEnum);

    private MethodMetadata createAcceptExecutionMethodMetadata() throws Exception {

        if (this.executable == null) {
            return null;
        }

        Method methodWithWhenAnnotation = AnnotationUtil.resolveMethodWithAnnotation(this.executable, When.class);
        return methodWithWhenAnnotation != null
                ? new MethodMetadata(this.executable.getClass(), methodWithWhenAnnotation)
                : null;
    }

    @Override
    public WhenResult executeWhen(WorkflowState<?> workflowState) throws Throwable {

        if (this.acceptExecutionMethodMetadata != null) {
            WhenExecutor whenExecutor = AbstractRunner.WHEN_EXECUTORS.get(this.acceptExecutionMethodMetadata.getReturnType());
            Validate.notNull(whenExecutor);

            return createWhenResult(whenExecutor.executeWhen(workflowState, this.acceptExecutionMethodMetadata, this.executable));
        }

        return createWhenResult(WhenEnum.ACCEPT);
    }

    protected int resolvePriority(int annotationPriority) throws Exception {

        int finalPriority = annotationPriority;

        Method methodWithRulePriotityAnnotation = AnnotationUtil.resolveMethodWithAnnotation(this.executable, Priority.class);
        if (methodWithRulePriotityAnnotation != null) {
            finalPriority += calculatePriority(methodWithRulePriotityAnnotation);
        }

        return finalPriority;
    }

    private int calculatePriority(Method methodWithRulePriotityAnnotation)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        return (int) methodWithRulePriotityAnnotation.invoke(this.executable);
    }

    public MethodMetadata getAcceptExecutionMethodMetadata() {

        return this.acceptExecutionMethodMetadata;
    }

    public Object getExecutable() {

        return this.executable;
    }

    private static interface WhenExecutor {
        WhenEnum executeWhen(WorkflowState<?> workflowState, MethodMetadata acceptExecutionMethodMetadata, Object rule)
                throws Throwable;
    }

    private static class EnumWhenExecutor implements WhenExecutor {

        public EnumWhenExecutor() {
            // Default constructor
        }

        @Override
        public WhenEnum executeWhen(WorkflowState<?> workflowState, MethodMetadata acceptExecutionMethodMetadata, Object rule)
                throws Throwable {

            MethodRunnerResult<WhenEnum> methodExecutionResult = new MethodRunnerResult<>(rule);

            acceptExecutionMethodMetadata.execute(methodExecutionResult, new SingleRuleParameterResolver(workflowState));

            return methodExecutionResult.getResult();
        }

    }

    private static class BooleanWhenExecutor implements WhenExecutor {

        public BooleanWhenExecutor() {
            // Default constructor
        }

        @Override
        public WhenEnum executeWhen(WorkflowState<?> workflowState, MethodMetadata acceptExecutionMethodMetadata, Object rule)
                throws Throwable {

            MethodRunnerResult<Boolean> methodExecutionResult = new MethodRunnerResult<>(rule);

            acceptExecutionMethodMetadata.execute(methodExecutionResult, new SingleRuleParameterResolver(workflowState));

            return methodExecutionResult.getResult()
                    ? WhenEnum.ACCEPT
                    : WhenEnum.REJECT;
        }
    }
}
