package com.opnitech.rules.core.executor.executers.impl.resolvers;

import com.opnitech.rules.core.ContextManager;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
class ContextManagerRunnerParameterResolver implements RunnerParameterResolver {

    public ContextManagerRunnerParameterResolver() {
        // Default constructor
    }

    @Override
    public boolean acceptParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata) {

        return ContextManager.class.isAssignableFrom(methodParameterMetadata.getParameterType());
    }

    @Override
    public Object resolveParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata) {

        return workflowState.getContextManager();
    }
}
