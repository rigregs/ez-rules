package com.opnitech.rules.core.executor.executers.impl.resolvers;

import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
class ContextRunnerParameterResolver implements RunnerParameterResolver {

    public ContextRunnerParameterResolver() {
        // Default constructor
    }

    @Override
    public boolean acceptParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata) {

        return true;
    }

    @Override
    public Object resolveParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata) {

        return workflowState.getContextManager().resolveContext(methodParameterMetadata.getParameterType());
    }
}