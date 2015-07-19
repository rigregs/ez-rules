package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange1;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestAcceptSimpleConditionWithParameterRule {

    private Exchange1 exchangeCondition1;

    private Exchange1 exchangeAction1;

    public TestAcceptSimpleConditionWithParameterRule() {
        // Default constructor
    }

    @When
    public WhenEnum condition(Exchange1 exchange1) {

        this.exchangeCondition1 = exchange1;

        return WhenEnum.ACCEPT;
    }

    @Then
    public void action(Exchange1 exchange1) {

        this.exchangeAction1 = exchange1;

    }

    public Exchange1 getExchangeCondition1() {

        return this.exchangeCondition1;
    }

    public Exchange1 getExchangeAction1() {

        return this.exchangeAction1;
    }
}
