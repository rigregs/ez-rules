package com.opnitech.rules.core.test.engine.test_priority.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Priority;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.test.engine.AbstractRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestBooleanPriorityRule extends AbstractRule<Boolean> {

    public TestBooleanPriorityRule() {
        super(true);
    }

    @Priority
    public int priority() {

        return 5;
    }

    @When
    public boolean when() {

        return doExecuteWhen();
    }

    @Then
    public void then() {

        doExecuteThen(TestBooleanPriorityRule.class.getEnclosingMethod());    }
}
