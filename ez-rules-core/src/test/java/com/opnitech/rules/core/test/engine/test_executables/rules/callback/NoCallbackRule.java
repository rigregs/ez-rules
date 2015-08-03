package com.opnitech.rules.core.test.engine.test_executables.rules.callback;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractCallbackRule;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class NoCallbackRule extends AbstractCallbackRule {

    public NoCallbackRule() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when() {

        return doExecuteWhen();
    }

    @Then
    public void then() {

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
