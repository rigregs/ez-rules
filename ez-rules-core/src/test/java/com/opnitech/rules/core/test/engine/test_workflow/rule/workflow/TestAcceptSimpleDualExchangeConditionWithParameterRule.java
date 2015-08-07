package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange1;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange2;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestAcceptSimpleDualExchangeConditionWithParameterRule {

    private Exchange1 exchange1;

    private Exchange2 exchange2;

    @When
    public WhenEnum condition(Exchange1 exchangeParam1, Exchange2 exchangeParam2) {

        this.exchange1 = exchangeParam1;
        this.exchange2 = exchangeParam2;

        return WhenEnum.ACCEPT;
    }

    @Then
    public void action() {

        // NOP
    }

    public Exchange1 getExchange1() {

        return this.exchange1;
    }

    public void setExchange1(Exchange1 exchange1) {

        this.exchange1 = exchange1;
    }

    public Exchange2 getExchange2() {

        return this.exchange2;
    }

    public void setExchange2(Exchange2 exchange2) {

        this.exchange2 = exchange2;
    }
}
