package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule(priority = 1)
@Group(ExecuteAllGroupDefinition.class)
public class ExecuteAllFirstGroupRule1 extends AbstractGroupRule1 {

    public ExecuteAllFirstGroupRule1() {
        // Default constructor
    }
}
