package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule(priority = 3)
@Group(group = TestStopFirstGroupDefinition.class)
public class StopFirstGroupRule3 extends AbstractGroupRule3 {

    public StopFirstGroupRule3() {
        // Default constructor
    }
}
