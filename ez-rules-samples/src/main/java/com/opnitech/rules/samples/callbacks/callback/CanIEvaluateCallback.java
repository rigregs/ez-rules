package com.opnitech.rules.samples.callbacks.callback;

import com.opnitech.rules.core.annotations.rule.Callback;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Callback
public class CanIEvaluateCallback {

    private boolean answer;

    public CanIEvaluateCallback(boolean answer) {
        this.answer = answer;
    }

    public boolean isAnswer() {

        return this.answer;
    }
}
