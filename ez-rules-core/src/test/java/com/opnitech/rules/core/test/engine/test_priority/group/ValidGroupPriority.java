package com.opnitech.rules.core.test.engine.test_priority.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.rule.Priority;

/**
 * @author Rigre Gregorio Garciandia Sonora
 *
 */
@GroupDefinition
public class ValidGroupPriority {
 
    public ValidGroupPriority() {
        // Default constructor
    }
    
    @Priority
    public int priority() {
        return 0;
    }
}
