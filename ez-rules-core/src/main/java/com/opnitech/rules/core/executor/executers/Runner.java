package com.opnitech.rules.core.executor.executers;

import org.slf4j.Logger;

import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityOrdered;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface Runner extends PriorityOrdered {

    WhenResult execute(WorkflowState workflowState) throws Throwable;

    WhenResult executeWhen(WorkflowState workflowState) throws Throwable;

    void logRuleMetadata(Logger logger, Object producer, int level);
}
