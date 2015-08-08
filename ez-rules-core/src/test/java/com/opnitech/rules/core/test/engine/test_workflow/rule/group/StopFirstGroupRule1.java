package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule(priority = 1, description = "STOPFIRSTGROUPRULE1")
@Group(TestStopFirstGroupDefinition.class)
public class StopFirstGroupRule1 extends AbstractGroupRule1 {

    public StopFirstGroupRule1() {
        // Default constructor
    }

    @Override
    protected WhenEnum doCondition() {

        return WhenEnum.REJECT;
    }
}
