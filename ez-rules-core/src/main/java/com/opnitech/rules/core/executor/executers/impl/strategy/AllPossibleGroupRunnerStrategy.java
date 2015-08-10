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
public class AllPossibleGroupRunnerStrategy implements GroupRunnerStrategy {

    public AllPossibleGroupRunnerStrategy() {
        // Default constructor
    }

    @Override
    public WhenResult doExecution(WorkflowState workflowState, PriorityList<Runner> executors) throws Throwable {

        WhenEnum finalResult = WhenEnum.REJECT;

        for (Runner runner : executors) {

            WhenResult executeWhen = runner.executeWhen(workflowState);

            if (!Objects.equals(executeWhen, finalResult)) {
                finalResult = WhenEnum.fromPriority(Math.min(finalResult.getPriority(), executeWhen.getWhenEnum().getPriority()));
            }

            if (finalResult.getPriority() < 0) {
                return new WhenResult(finalResult);
            }

            if (Objects.equals(WhenEnum.ACCEPT, executeWhen.getWhenEnum())) {
                runner.execute(workflowState);
            }
        }

        return new WhenResult(finalResult);
    }
}
