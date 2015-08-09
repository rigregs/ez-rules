package com.opnitech.rules.core.test.engine.test_priority.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.rule.Priority;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition
public class InvalidGroupPriorityWithParameter {

    public InvalidGroupPriorityWithParameter() {
        // Default constructor
    }

    @Priority
    public int priority(@SuppressWarnings("unused") boolean somethig) {

        return 0;
    }
}
