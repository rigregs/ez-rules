package com.opnitech.rules.core.executor.executers.impl;

import org.apache.commons.lang3.ObjectUtils;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.utils.ExceptionUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupRuleRunner extends AbstractRuleRunner {

    public GroupRuleRunner(Object rule) throws Exception {

        super(rule);
    }

    public WhenEnum executeWhen(WorkflowState workflowState) throws Exception {

        WhenEnum when = doExecuteWhen(workflowState);
        if (ObjectUtils.notEqual(WhenEnum.ACCEPT, when) && ObjectUtils.notEqual(WhenEnum.REJECT, when)) {

            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid condition result for a group fule. Expected: {0} or {1}, but was {2} Rule: {3}", WhenEnum.ACCEPT,
                    WhenEnum.REJECT, when, getRule());
        }

        return when;
    }

    @Override
    public WhenEnum execute(WorkflowState workflowState) throws Exception {

        doExecuteThen(workflowState);

        return WhenEnum.ACCEPT;
    }
}
