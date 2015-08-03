package com.opnitech.rules.core.test.engine.test_priority.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.RulePriority;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.test.engine.AbstractRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestInvalidResultBooleanPriorityRule extends AbstractRule<Boolean> {

    public TestInvalidResultBooleanPriorityRule() {
        super(true);
    }

    @RulePriority
    public long priority() {

        return 5;
    }

    @When
    public boolean when() {

        return doExecuteWhen();
    }

    @Then
    public void then() {

        doExecuteThen(TestInvalidResultBooleanPriorityRule.class.getEnclosingMethod());    }
}
