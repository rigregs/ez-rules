package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractGroupRule3 extends AbstractGroupRule {

    public AbstractGroupRule3() {
        // Default constructor
    }

    @Override
    protected WhenEnum doCondition() {

        return WhenEnum.ACCEPT;
    }
}
