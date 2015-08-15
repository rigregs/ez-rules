package com.opnitech.rules.core.executor.executers.impl.resolvers;

import com.opnitech.rules.core.annotations.rule.Exchange;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
class ExchangeParameterResolver implements RunnerParameterResolver {

    public ExchangeParameterResolver() {
        // Default constructor
    }

    @Override
    public boolean acceptParameter(WorkflowState<?> workflowState, ParameterMetadata methodParameterMetadata) {

        return true;
    }

    @Override
    public Object resolveParameter(WorkflowState<?> workflowState, ParameterMetadata methodParameterMetadata) {

        return methodParameterMetadata.isAnnotationPresent(Exchange.class)
                ? workflowState.getExchangeManager()
                        .resolveExchangeByName(methodParameterMetadata.resolveAnnotation(Exchange.class).value())
                : workflowState.getExchangeManager().resolveExchangeByClass(methodParameterMetadata.getParameterType());
    }
}
