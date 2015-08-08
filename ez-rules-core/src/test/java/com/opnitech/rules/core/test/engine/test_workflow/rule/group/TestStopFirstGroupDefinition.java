package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition(ExecutionStrategyEnum.STOP_FIRST)
public class TestStopFirstGroupDefinition {

    public TestStopFirstGroupDefinition() {
        // Default constructor
    }
}
