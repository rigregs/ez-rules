package com.opnitech.rules.core.test.engine.test_injection;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_injection.rules.RuleWithProxyAsExchange;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxy;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyFactory;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyInterface1;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyInterface2;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleInjectionTest extends AbstractRuleEngineExecutorTest {

    private static final String TEST = "TEST";

    @Test
    public void testProxyInjection() {

        ParamToBeProxy paramToBeProxy = new ParamToBeProxy(TEST);

        Object proxy = ParamToBeProxyFactory.createProxy(paramToBeProxy);

        RuleWithProxyAsExchange ruleWithProxyAsExchange = new RuleWithProxyAsExchange();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(ruleWithProxyAsExchange);

        rulesEngine.execute(proxy);

        validateExecutables(true, true, ruleWithProxyAsExchange);

        ParamToBeProxyInterface1 paramToBeProxyInterface1 = ruleWithProxyAsExchange.getParamToBeProxyInterface1();
        Assert.assertNotNull(paramToBeProxyInterface1);
        Assert.assertEquals(TEST, ruleWithProxyAsExchange.getParamToBeProxyInterface1().getValue());

        ParamToBeProxyInterface2 paramToBeProxyInterface2 = ruleWithProxyAsExchange.getParamToBeProxyInterface2();
        Assert.assertNotNull(paramToBeProxyInterface2);
        Assert.assertEquals(TEST, ruleWithProxyAsExchange.getParamToBeProxyInterface2().getValue());
    }
}
