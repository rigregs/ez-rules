package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.ExchangeManager;
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
public class TestExchangeManagerRule {

    public static final int ACTION_COUNT = 7;

    private int actionExecuted = 0;

    @When
    public WhenEnum condition() {

        return WhenEnum.ACCEPT;
    }

    @Then(priority = 1)
    public void action1(Exchange1 exchange1, Exchange2 exchange2, ExchangeManager exchangeManager) {

        Validate.notNull(exchangeManager);

        Validate.notNull(exchange1);
        Validate.isTrue(exchange2 == null);

        this.actionExecuted++;
    }

    @Then(priority = 2)
    public void actionAddExchange(Exchange1 exchange1, ExchangeManager exchangeManager) {

        Validate.notNull(exchangeManager);

        Validate.notNull(exchange1);

        exchangeManager.addExchange(new Exchange2("TEST"));

        this.actionExecuted++;
    }

    @Then(priority = 3)
    public void actionValidate3(Exchange1 exchange1, Exchange2 exchange2, ExchangeManager exchangeManager) {

        Validate.notNull(exchangeManager);

        Validate.notNull(exchange1);
        Validate.notNull(exchange2);
        Validate.isTrue(Objects.equals("TEST", exchange2.getValue()));

        this.actionExecuted++;
    }

    @Then(priority = 4)
    public void actionReplace4(Exchange1 exchange1, Exchange2 exchange2, ExchangeManager exchangeManager) {

        Validate.notNull(exchangeManager);

        Validate.notNull(exchange1);

        exchangeManager.replaceExchange(exchange2, new Exchange2("TEST1"));

        this.actionExecuted++;
    }

    @Then(priority = 5)
    public void actionCheckNewExchange4(Exchange1 exchange1, Exchange2 exchange2, ExchangeManager exchangeManager) {

        Validate.notNull(exchangeManager);

        Validate.notNull(exchange1);
        Validate.notNull(exchange2);
        Validate.isTrue(Objects.equals("TEST1", exchange2.getValue()));

        this.actionExecuted++;
    }

    @Then(priority = 6)
    public void actionRemoveExchange6(Exchange1 exchange1, Exchange2 exchange2, ExchangeManager exchangeManager) {

        Validate.notNull(exchangeManager);

        Validate.notNull(exchange1);
        Validate.notNull(exchange2);

        exchangeManager.removeExchange(exchange2);

        this.actionExecuted++;
    }

    @Then(priority = 7)
    public void actionCheckRemoveExchange7(Exchange1 exchange1, Exchange2 exchange2, ExchangeManager exchangeManager) {

        Validate.notNull(exchangeManager);

        Validate.notNull(exchange1);
        Validate.isTrue(exchange2 == null);

        this.actionExecuted++;
    }

    public int getActionExecuted() {

        return this.actionExecuted;
    }
}
