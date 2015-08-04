package com.opnitech.rules.core.test.engine.test_workflow.rule.exchange;

import com.opnitech.rules.core.annotations.rule.Exchange;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange1;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestNamedExchangeRule extends AbstractRule<Boolean> {

    public TestNamedExchangeRule() {
        super(true);
    }

    @When
    public boolean when(@Exchange("when1") Exchange1 exchangeWhen1, @Exchange("when2") Exchange1 exchangeWhen2) {

        exchangeWhen1.setValue("when1");
        exchangeWhen2.setValue("when2");

        return true;
    }

    @Then
    public void then(@Exchange("then1") Exchange1 exchangeThen1, @Exchange("then2") Exchange1 exchangeThen2) {

        exchangeThen1.setValue("then1");
        exchangeThen2.setValue("then2");
    }
}
