package com.opnitech.rules.core.test.engine.test_when_result.rules;

import com.opnitech.rules.core.annotations.rule.Priority;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_priority.rules.TestBooleanPriorityRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestValidWhenResult extends AbstractRule<WhenEnum> {

    private Object result;
    private int priotity;

    public TestValidWhenResult(WhenEnum whenResult, Object result, int priotity) {
        super(whenResult);
        this.result = result;
        this.priotity = priotity;
    }

    @Priority
    public int retrievePriority() {

        return this.priotity;
    }

    @When
    public WhenEnum when() {

        return doExecuteWhen();
    }

    @Then
    public Object then() {

        doExecuteThen(TestBooleanPriorityRule.class.getEnclosingMethod());

        return this.result;
    }
}
