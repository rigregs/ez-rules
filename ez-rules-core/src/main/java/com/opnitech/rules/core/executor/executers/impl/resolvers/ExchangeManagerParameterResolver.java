package com.opnitech.rules.core.executor.executers.impl.resolvers;

import com.opnitech.rules.core.ExchangeManager;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
class ExchangeManagerParameterResolver implements RunnerParameterResolver {

    public ExchangeManagerParameterResolver() {
        // Default constructor
    }

    @Override
    public boolean acceptParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata) {

        return ExchangeManager.class.isAssignableFrom(methodParameterMetadata.getParameterType());
    }

    @Override
    public Object resolveParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata) {

        return workflowState.getExchangeManager();
    }
}
