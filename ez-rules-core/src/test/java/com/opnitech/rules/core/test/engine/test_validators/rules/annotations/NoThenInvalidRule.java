package com.opnitech.rules.core.test.engine.test_validators.rules.annotations;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class NoThenInvalidRule extends AbstractRule<WhenEnum> {

    public NoThenInvalidRule() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when() {

        return doExecuteWhen();
    }

    public void then() {

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
