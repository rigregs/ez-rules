package com.opnitech.rules.samples.callbacks.callback;

import com.opnitech.rules.core.annotations.rule.Callback;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Callback
public class EvaluateCallback {

    private String evaluation;

    public EvaluateCallback() {
        // Default constructor
    }

    public EvaluateCallback(String something) {
        this.evaluation = something;
    }

    public String getEvaluation() {

        return this.evaluation;
    }
}
