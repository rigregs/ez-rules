package com.opnitech.rules.core.test.utils.annotation;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class CorrectAnnotatedRule {

    @When
    public WhenEnum when() {

        return WhenEnum.ACCEPT;
    }

    @Then
    public void then() {

        // NOP
    }
}
