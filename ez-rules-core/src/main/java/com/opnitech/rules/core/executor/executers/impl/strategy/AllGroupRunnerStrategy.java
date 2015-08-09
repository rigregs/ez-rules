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
    public WhenEnum doExecution(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors) throws Throwable {

        WhenEnum allRunnerWhen = checkAllRunnerWhen(workflowState, executors);
        if (ObjectUtils.notEqual(WhenEnum.ACCEPT, allRunnerWhen)) {
            return allRunnerWhen;
        }

        executeActions(workflowState, executors);

        return WhenEnum.ACCEPT;
    }

    private void executeActions(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors) throws Throwable {

        for (GroupRuleRunner groupRuleExecuter : executors) {
            groupRuleExecuter.execute(workflowState);
        }
    }

    private WhenEnum checkAllRunnerWhen(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors) throws Throwable {

        for (GroupRuleRunner groupRuleExecuter : executors) {

            WhenEnum executeWhen = groupRuleExecuter.executeWhen(workflowState);
            if (ObjectUtils.notEqual(WhenEnum.ACCEPT, executeWhen)) {
                return executeWhen;
            }
        }

        return WhenEnum.ACCEPT;
    }
}
