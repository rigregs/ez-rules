package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.ExchangeBuilder;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange1;
import com.opnitech.rules.core.test.engine.test_workflow.rule.exchange.TestNamedExchangeRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleNamedExchangeExecutorTest {

    @Test
    public void testSimpleRules() throws Exception {

        TestNamedExchangeRule testNamedExchangeRule = new TestNamedExchangeRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(testNamedExchangeRule);

        Exchange1 exchangeWhen1 = new Exchange1(null);
        Exchange1 exchangeWhen2 = new Exchange1(null);

        Exchange1 exchangeThen1 = new Exchange1(null);
        Exchange1 exchangeThen2 = new Exchange1(null);

        rulesEngine.execute(ExchangeBuilder.create().add("when1", exchangeWhen1).add("when2", exchangeWhen2)
                .add("then1", exchangeThen1).add("then2", exchangeThen2));

        Assert.assertEquals("when1", exchangeWhen1.getValue());
        Assert.assertEquals("when2", exchangeWhen2.getValue());

        Assert.assertEquals("then1", exchangeThen1.getValue());
        Assert.assertEquals("then2", exchangeThen2.getValue());
    }
}
