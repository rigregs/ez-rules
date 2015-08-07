package com.opnitech.rules.core.test.engine.test_when.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.When;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestBooleanWhenRule extends AbstractWhenRule<Boolean> {

    public TestBooleanWhenRule(boolean executeThen) {
        super(executeThen);
    }

    @When
    public Boolean when() {

        return doExecuteWhen();
    }
}
