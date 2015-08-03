package com.opnitech.rules.samples.callbacks.callback;

import com.opnitech.rules.core.annotations.rule.Callback;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Callback
public class SaySomethingCallback {

    private String something;

    public SaySomethingCallback() {
        // Default constructor
    }

    public SaySomethingCallback(String something) {
        this.something = something;
    }

    public String getSomething() {

        return this.something;
    }
}
