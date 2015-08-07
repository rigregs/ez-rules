package com.opnitech.rules.core.executor.executers.impl.resolvers;

import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
interface RunnerParameterResolver {

    boolean acceptParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata);

    Object resolveParameter(WorkflowState workflowState, ParameterMetadata methodParameterMetadata);
}
