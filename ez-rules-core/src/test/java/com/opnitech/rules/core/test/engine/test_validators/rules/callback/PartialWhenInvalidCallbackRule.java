package com.opnitech.rules.core.test.engine.test_validators.rules.callback;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractCallbackRule;
import com.opnitech.rules.core.test.engine.test_validators.callback.InvalidCallback;
import com.opnitech.rules.core.test.engine.test_validators.callback.ValidCallback;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class PartialWhenInvalidCallbackRule extends AbstractCallbackRule {

    public PartialWhenInvalidCallbackRule() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when(@Callback InvalidCallback callback) {

        registerWhenCallbacks(callback);

        return doExecuteWhen();
    }

    @Then
    public void then(@Callback ValidCallback callback) {

        registerThenCallbacks(callback);

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
