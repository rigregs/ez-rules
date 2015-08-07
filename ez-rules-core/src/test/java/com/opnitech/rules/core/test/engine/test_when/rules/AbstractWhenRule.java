package com.opnitech.rules.core.test.engine.test_when.rules;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.test.engine.AbstractRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AbstractWhenRule<WhenResultType> extends AbstractRule<WhenResultType> {

    public AbstractWhenRule(WhenResultType whenResult) {
        super(whenResult);
    }

    @Then
    public void then() {

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
