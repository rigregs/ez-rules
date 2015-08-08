package com.opnitech.rules.samples.exchanges.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class EvaluateWithExchangeRule {

    @When
    public boolean canIEvaluate(Boolean answer) {

        return answer != null
                ? answer
                : false;
    }

    @Then
    public void evaluate(String something) {

        System.out.println(something);
    }
}
