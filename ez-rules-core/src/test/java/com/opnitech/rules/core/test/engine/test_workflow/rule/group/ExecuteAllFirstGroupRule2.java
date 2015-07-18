package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule(priority = 2)
@Group(group = ExecuteAllGroupDefinition.class)
public class ExecuteAllFirstGroupRule2 extends AbstractGroupRule2 {

    public ExecuteAllFirstGroupRule2() {
        // Default constructor
    }
}
