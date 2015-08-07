package com.opnitech.rules.samples.exchanges.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class SaySomethingWithExchangeRule {

    @When
    public boolean canISaySomething(Boolean answer) {

        return answer != null
                ? answer
                : false;
    }

    @Then
    public void saySomething(String something) {

        System.out.println(something);
    }
}
