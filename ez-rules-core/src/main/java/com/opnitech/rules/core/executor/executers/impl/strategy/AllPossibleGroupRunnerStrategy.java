package com.opnitech.rules.core.executor.executers.impl.strategy;

import java.util.Objects;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.impl.GroupRuleRunner;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AllPossibleGroupRunnerStrategy implements GroupRunnerStrategy {

    public AllPossibleGroupRunnerStrategy() {
        // Default constructor
    }

    @Override
    public WhenEnum doExecution(WorkflowState workflowState, PriorityList<GroupRuleRunner> executors) throws Throwable {

        WhenEnum finalResult = WhenEnum.REJECT;

        for (GroupRuleRunner groupRuleExecuter : executors) {

            WhenEnum executeWhen = groupRuleExecuter.executeWhen(workflowState);

            if (!Objects.equals(executeWhen, finalResult)) {
                finalResult = WhenEnum.fromPriority(Math.min(finalResult.getPriority(), executeWhen.getPriority()));
            }

            if (finalResult.getPriority() < 0) {
                return finalResult;
            }

            if (Objects.equals(WhenEnum.ACCEPT, executeWhen)) {
                groupRuleExecuter.execute(workflowState);
            }
        }

        return finalResult;
    }
}
