package com.opnitech.rules.core.executor.executers.impl.strategy;

import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.executor.executers.WhenResult;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface GroupRunnerStrategy {

    WhenResult doExecution(WorkflowState workflowState, PriorityList<Runner> executors) throws Throwable;
}
