package com.opnitech.rules.core.executor.executers.impl.strategy;

import java.util.Objects;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.Runner;
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
    public WhenEnum doExecution(WorkflowState<?> workflowState, PriorityList<Runner> executors) throws Throwable {

        for (Runner runner : executors) {

            WhenEnum executeWhen = runner.executeWhen(workflowState);

            if (executeWhen.getPriority() < 0) {
                return executeWhen;
            }

            if (Objects.equals(WhenEnum.ACCEPT, executeWhen)) {
                WhenEnum whenExecuteResult = runner.execute(workflowState);

                return whenExecuteResult;
            }
        }

        return WhenEnum.REJECT;
    }
}
