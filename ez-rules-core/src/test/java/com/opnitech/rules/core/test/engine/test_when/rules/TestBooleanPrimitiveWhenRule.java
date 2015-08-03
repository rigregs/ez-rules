package com.opnitech.rules.core.test.engine.test_when.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.When;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestBooleanPrimitiveWhenRule extends AbstractWhenRule<Boolean> {

    public TestBooleanPrimitiveWhenRule(boolean executeThen) {
        super(executeThen);
    }

    @When
    public boolean when() {

        return doExecuteWhen();
    }
}
