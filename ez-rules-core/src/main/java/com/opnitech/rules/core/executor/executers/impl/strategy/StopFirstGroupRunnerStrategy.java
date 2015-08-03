package com.opnitech.rules.core.executor.executers.impl.strategy;

import java.util.Objects;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.impl.GroupRuleRunner;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class StopFirstGroupRunnerStrategy implements GroupRunnerStrategy {

    public StopFirstGroupRunnerStrategy() {
        // Default constructor
    }

    @Override
    public WhenEnum doExecution(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors) throws Throwable {

        for (GroupRuleRunner groupRuleExecuter : executors) {

            if (Objects.equals(WhenEnum.ACCEPT, groupRuleExecuter.executeWhen(workflowState))) {
                return groupRuleExecuter.execute(workflowState);
            }
        }

        return WhenEnum.REJECT;
    }
}
