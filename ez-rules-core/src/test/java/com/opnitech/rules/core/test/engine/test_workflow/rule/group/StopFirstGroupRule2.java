package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule(priority = 2)
@Group(groupKey = TestStopFirstGroupDefinition.class)
public class StopFirstGroupRule2 extends AbstractGroupRule2 {

    public StopFirstGroupRule2() {
        // Default constructor
    }
}
