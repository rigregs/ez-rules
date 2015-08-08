package com.opnitech.rules.core.test.engine.test_priority.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.rule.Priority;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition
public class InvalidGroupPriorityMultiplePriorityRule {

    public InvalidGroupPriorityMultiplePriorityRule() {
        // Default constructor
    }

    @Priority
    public int priority() {

        return 0;
    }

    @Priority
    public int priority1() {

        return 0;
    }
}
