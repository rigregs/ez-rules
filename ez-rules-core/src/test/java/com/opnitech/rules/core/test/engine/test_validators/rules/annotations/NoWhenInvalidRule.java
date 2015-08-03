package com.opnitech.rules.core.test.engine.test_validators.rules.annotations;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class NoWhenInvalidRule extends AbstractRule<WhenEnum> {

    public NoWhenInvalidRule() {
        super(WhenEnum.ACCEPT);
    }

    public WhenEnum when() {

        return doExecuteWhen();
    }

    @Then
    public void then() {

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }

}
