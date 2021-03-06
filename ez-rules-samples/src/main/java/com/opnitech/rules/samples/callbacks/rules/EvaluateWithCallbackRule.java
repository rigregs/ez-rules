package com.opnitech.rules.samples.callbacks.rules;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.samples.callbacks.callback.CanIEvaluateCallback;
import com.opnitech.rules.samples.callbacks.callback.EvaluateCallback;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class EvaluateWithCallbackRule {

    @When
    public boolean when(@Callback CanIEvaluateCallback callback) {

        return callback.isAnswer();
    }

    @Then
    public void then(@Callback EvaluateCallback callback) {

        System.out.println(callback.getEvaluation());
    }
}
