package com.opnitech.rules.core.executor.executers.impl.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.reflection.ParameterMetadata;
import com.opnitech.rules.core.executor.reflection.ParameterResolver;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class SingleRuleParameterResolver implements ParameterResolver {

    private static final List<RunnerParameterResolver> PARAMETER_RESOLVERS = new ArrayList<>();

    static {
        SingleRuleParameterResolver.PARAMETER_RESOLVERS.add(new CallbackRunnerParameterResolver());
        SingleRuleParameterResolver.PARAMETER_RESOLVERS.add(new ExchangeManagerParameterResolver());
        SingleRuleParameterResolver.PARAMETER_RESOLVERS.add(new ExchangeParameterResolver());
    }

    private final WorkflowState<?> workflowState;

    public SingleRuleParameterResolver(WorkflowState<?> workflowState) {

        this.workflowState = workflowState;
    }

    @Override
    public Object resolveParameter(ParameterMetadata methodParameterMetadata) throws Exception {

        for (RunnerParameterResolver executerParameterResolver : SingleRuleParameterResolver.PARAMETER_RESOLVERS) {
            if (executerParameterResolver.acceptParameter(this.workflowState, methodParameterMetadata)) {
                return executerParameterResolver.resolveParameter(this.workflowState, methodParameterMetadata);
            }
        }

        return null;
    }
}
