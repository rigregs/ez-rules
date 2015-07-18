package com.opnitech.rules.core.executor.executers;

import org.slf4j.Logger;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.executor.flow.WorkflowState;
import com.opnitech.rules.core.executor.util.PriorityOrdered;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface RuleRunner extends PriorityOrdered {

    WhenEnum execute(WorkflowState workflowState) throws Exception;

    void logRuleMetadata(Logger logger, Object producer, int level);
}
