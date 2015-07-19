package com.opnitech.rules.core.test.engine.test_exceptions.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class ExceptionWhenRule {

    @When
    public boolean when() throws Exception {

        throw new Exception("When");
    }

    @Then
    public void then() {

        // NOP
    }
}
