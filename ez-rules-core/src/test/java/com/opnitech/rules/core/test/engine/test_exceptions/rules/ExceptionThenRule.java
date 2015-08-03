package com.opnitech.rules.core.test.engine.test_exceptions.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class ExceptionThenRule {

    @When
    public boolean when() {

        return true;
    }

    @Then
    public void then() throws Exception {

        throw new Exception("Then");
    }
}
