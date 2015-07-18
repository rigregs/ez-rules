package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context1;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestAcceptSimpleConditionWithParameterRule {

    private Context1 contextCondition1;

    private Context1 contextAction1;

    public TestAcceptSimpleConditionWithParameterRule() {
        // Default constructor
    }

    @When
    public WhenEnum condition(Context1 context1) {

        this.contextCondition1 = context1;

        return WhenEnum.ACCEPT;
    }

    @Then
    public void action(Context1 context1) {

        this.contextAction1 = context1;

    }

    public Context1 getContextCondition1() {

        return this.contextCondition1;
    }

    public Context1 getContextAction1() {
        return this.contextAction1;
    }
}
