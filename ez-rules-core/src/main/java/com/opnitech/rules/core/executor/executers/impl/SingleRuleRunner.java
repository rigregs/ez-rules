package com.opnitech.rules.core.executor.executers.impl;

import java.util.Objects;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.flow.WorkflowState;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class SingleRuleRunner extends AbstractRuleRunner {

    public SingleRuleRunner(Object rule) throws Exception {

        super(rule);
    }

    @Override
    public WhenEnum execute(WorkflowState workflowState) throws Throwable {

        WhenEnum when = doExecuteWhen(workflowState);

        if (Objects.equals(WhenEnum.ACCEPT, when)) {
            doExecuteThen(workflowState);
        }

        return when;
    }
}
