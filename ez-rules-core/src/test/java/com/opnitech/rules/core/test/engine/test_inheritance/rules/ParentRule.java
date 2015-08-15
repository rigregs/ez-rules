package com.opnitech.rules.core.test.engine.test_inheritance.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class ParentRule extends AbstractRule<WhenEnum> {

    public ParentRule() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when() {

        return doExecuteWhen();
    }

    @Then
    public void then() {

        doExecuteWhen();
    }
}
