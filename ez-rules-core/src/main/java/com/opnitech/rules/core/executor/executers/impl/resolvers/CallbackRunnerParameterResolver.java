package com.opnitech.rules.core.executor.executers.impl.resolvers;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.MethodMetadata;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;
import com.opnitech.rules.core.utils.ClassUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
class CallbackRunnerParameterResolver implements RunnerParameterResolver {

    public CallbackRunnerParameterResolver() {
        // Default constructor
    }

    @Override
    public boolean acceptParameter(WorkflowState<?> workflowState, ParameterMetadata methodParameterMetadata) {

        return methodParameterMetadata.isAnnotationPresent(Callback.class);
    }

    @Override
    public Object resolveParameter(WorkflowState<?> workflowState, ParameterMetadata methodParameterMetadata) {

        Object callback = ClassUtil.resolveEntity(methodParameterMetadata.getParameterType(), workflowState.getCallbacks());

        if (callback == null) {
            MethodMetadata methodMetadata = methodParameterMetadata.getMethodMetadata();

            ExceptionUtil.throwIllegalArgumentException("Callback can not be found. Class: ''{0}'' , Method: ''{1}''",
                    methodMetadata.getOwnerClass().getName(), methodMetadata.getMethod().getName());
        }

        return callback;
    }
}
