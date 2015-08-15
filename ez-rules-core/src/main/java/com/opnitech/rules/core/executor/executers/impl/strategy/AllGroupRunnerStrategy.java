package com.opnitech.rules.core.executor.executers.impl.strategy;

import org.apache.commons.lang3.ObjectUtils;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.executor.executers.WhenResult;
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
    public WhenResult doExecution(WorkflowState<?> workflowState, PriorityList<Runner> executors) throws Throwable {

        WhenResult allRunnerWhen = checkAllRunnerWhen(workflowState, executors);
        if (ObjectUtils.notEqual(WhenEnum.ACCEPT, allRunnerWhen.getWhenEnum())) {
            return allRunnerWhen;
        }

        executeActions(workflowState, executors);

        return new WhenResult(WhenEnum.ACCEPT);
    }

    private void executeActions(WorkflowState<?> workflowState, PriorityList<Runner> executors) throws Throwable {

        for (Runner groupRuleExecuter : executors) {
            groupRuleExecuter.execute(workflowState);
        }
    }

    private WhenResult checkAllRunnerWhen(WorkflowState<?> workflowState, PriorityList<Runner> executors) throws Throwable {

        for (Runner runner : executors) {

            WhenResult executeWhen = runner.executeWhen(workflowState);

            if (ObjectUtils.notEqual(WhenEnum.ACCEPT, executeWhen.getWhenEnum())) {
                return executeWhen;
            }
        }

        return new WhenResult(WhenEnum.ACCEPT);
    }
}
