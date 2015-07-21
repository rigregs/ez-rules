package com.opnitech.rules.samples.callbacks.callback;

import com.opnitech.rules.core.annotations.callback.Callback;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Callback
public class CanISaySomethingCallback {

    private boolean answer;

    public CanISaySomethingCallback(boolean answer) {
        this.answer = answer;
    }

    public boolean isAnswer() {

        return this.answer;
    }
}
