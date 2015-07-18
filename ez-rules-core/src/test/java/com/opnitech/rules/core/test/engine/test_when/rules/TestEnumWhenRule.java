package com.opnitech.rules.core.test.engine.test_when.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestEnumWhenRule extends AbstractWhenRule<WhenEnum> {

    public TestEnumWhenRule(WhenEnum whenResult) {
        super(whenResult);
    }

    @When
    public WhenEnum when() {

        return doExecuteWhen();
    }
}
