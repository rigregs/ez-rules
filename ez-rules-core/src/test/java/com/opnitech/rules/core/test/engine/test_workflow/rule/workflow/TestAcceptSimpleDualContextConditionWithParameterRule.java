package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context1;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context2;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestAcceptSimpleDualContextConditionWithParameterRule {

    private Context1 context1;

    private Context2 context2;

    @When
    public WhenEnum condition(Context1 contextParam1, Context2 contextParam2) {

        this.context1 = contextParam1;
        this.context2 = contextParam2;

        return WhenEnum.ACCEPT;
    }

    @Then
    public void action() {

        // NOP
    }

    public Context1 getContext1() {

        return this.context1;
    }

    public void setContext1(Context1 context1) {

        this.context1 = context1;
    }

    public Context2 getContext2() {

        return this.context2;
    }

    public void setContext2(Context2 context2) {

        this.context2 = context2;
    }
}
