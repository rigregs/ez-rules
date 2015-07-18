package com.opnitech.rules.core.executor.executers.impl.strategy;

import org.apache.commons.lang3.ObjectUtils;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.impl.GroupRuleRunner;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AllGroupRunnerStrategy implements GroupRunnerStrategy {

    public AllGroupRunnerStrategy() {
        // Default constructor

    }

    @Override
    public WhenEnum doExecution(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors) throws Exception {

        WhenEnum checkAllExecutorsConditions = checkAllExecutorsConditions(workflowState, executors);
        if (ObjectUtils.notEqual(WhenEnum.ACCEPT, checkAllExecutorsConditions)) {
            return WhenEnum.REJECT;
        }

        executeActions(workflowState, executors);

        return WhenEnum.ACCEPT;
    }

    private void executeActions(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors) throws Exception {

        for (GroupRuleRunner groupRuleExecuter : executors) {
            groupRuleExecuter.execute(workflowState);
        }
    }

    private WhenEnum checkAllExecutorsConditions(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors)
            throws Exception {

        for (GroupRuleRunner groupRuleExecuter : executors) {

            if (ObjectUtils.notEqual(WhenEnum.ACCEPT, groupRuleExecuter.executeWhen(workflowState))) {
                return WhenEnum.REJECT;
            }
        }

        return WhenEnum.ACCEPT;
    }
}
