package com.opnitech.rules.core.executor.executers.impl.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;
import com.opnitech.rules.core.executor.reflection.ParameterResolver;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class SingleRuleRunnerParameterResolver implements ParameterResolver {

    private static final List<RunnerParameterResolver> PARAMETER_RESOLVERS = new ArrayList<>();

    static {
        SingleRuleRunnerParameterResolver.PARAMETER_RESOLVERS.add(new CallbackRunnerParameterResolver());
        SingleRuleRunnerParameterResolver.PARAMETER_RESOLVERS.add(new ContextManagerRunnerParameterResolver());
        SingleRuleRunnerParameterResolver.PARAMETER_RESOLVERS.add(new ContextRunnerParameterResolver());
    }

    private final WorkflowState workflowState;

    public SingleRuleRunnerParameterResolver(WorkflowState workflowState) {

        this.workflowState = workflowState;
    }

    @Override
    public Object resolveParameter(ParameterMetadata methodParameterMetadata) throws Exception {

        for (RunnerParameterResolver executerParameterResolver : SingleRuleRunnerParameterResolver.PARAMETER_RESOLVERS) {
            if (executerParameterResolver.acceptParameter(this.workflowState, methodParameterMetadata)) {
                return executerParameterResolver.resolveParameter(this.workflowState, methodParameterMetadata);
            }
        }

        return null;
    }
}
