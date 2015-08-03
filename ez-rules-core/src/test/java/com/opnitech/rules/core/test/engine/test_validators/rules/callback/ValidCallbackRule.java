package com.opnitech.rules.core.test.engine.test_validators.rules.callback;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractCallbackRule;
import com.opnitech.rules.core.test.engine.test_validators.callback.ValidCallback;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class ValidCallbackRule extends AbstractCallbackRule {

    public ValidCallbackRule() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when(@Callback ValidCallback validCallback) {

        registerWhenCallbacks(validCallback);

        return doExecuteWhen();
    }

    @Then
    public void then(@Callback ValidCallback validCallback) {

        registerThenCallbacks(validCallback);

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
