package com.opnitech.rules.core.executor.executers.impl.strategy;

import java.util.Objects;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.executor.executers.WhenResult;
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
    public WhenResult doExecution(WorkflowState<?> workflowState, PriorityList<Runner> executors) throws Throwable {

        for (Runner runner : executors) {

            WhenResult executeWhen = runner.executeWhen(workflowState);

            if (executeWhen.getWhenEnum().getPriority() < 0) {
                return executeWhen;
            }

            if (Objects.equals(WhenEnum.ACCEPT, executeWhen.getWhenEnum())) {
                WhenResult whenExecuteResult = runner.execute(workflowState);

                return whenExecuteResult;
            }
        }

        return new WhenResult(WhenEnum.REJECT);
    }
}
