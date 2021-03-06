package com.opnitech.rules.core.executor.executers.impl.strategy;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityList;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface GroupRunnerStrategy {

    WhenEnum doExecution(WorkflowState<?> workflowState, PriorityList<Runner> executors) throws Throwable;
}
