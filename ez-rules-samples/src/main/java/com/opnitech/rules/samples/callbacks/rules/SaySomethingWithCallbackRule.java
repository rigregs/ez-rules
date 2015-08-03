package com.opnitech.rules.samples.callbacks.rules;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.samples.callbacks.callback.CanISaySomethingCallback;
import com.opnitech.rules.samples.callbacks.callback.SaySomethingCallback;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class SaySomethingWithCallbackRule {

    @When
    public boolean when(@Callback CanISaySomethingCallback callback) {

        return callback.isAnswer();
    }

    @Then
    public void then(@Callback SaySomethingCallback callback) {

        System.out.println(callback.getSomething());
    }
}
