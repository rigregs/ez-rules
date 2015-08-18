package com.opnitech.rules.core.test.engine.test_injection;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_injection.rules.RuleWithParentProxyAsExchange;
import com.opnitech.rules.core.test.engine.test_injection.rules.RuleWithProxyAsExchange;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxy;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyFactory;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyInterface1;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyInterface2;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParentParamToBeProxyInterface;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleInjectionTest extends AbstractRuleEngineExecutorTest {

    private static final String TEST = "TEST";

    @Test
    public void testProxyInjectionWithFinalInterface() {

        ParamToBeProxy paramToBeProxy = new ParamToBeProxy(TEST);

        Object proxy = ParamToBeProxyFactory.createProxy(paramToBeProxy, new Class[]
            {
                ParamToBeProxyInterface1.class,
                ParamToBeProxyInterface2.class
            });

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

    @Test
    public void testProxyInjectionWithParentInterface() {

        ParamToBeProxy paramToBeProxy = new ParamToBeProxy(TEST);

        Object proxy = ParamToBeProxyFactory.createProxy(paramToBeProxy, new Class[]
            {
                ParentParamToBeProxyInterface.class,
            });

        RuleWithProxyAsExchange ruleWithProxyAsExchange = new RuleWithProxyAsExchange();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(ruleWithProxyAsExchange);

        rulesEngine.execute(proxy);

        validateExecutables(true, true, ruleWithProxyAsExchange);

        ParamToBeProxyInterface1 paramToBeProxyInterface1 = ruleWithProxyAsExchange.getParamToBeProxyInterface1();
        Assert.assertNull(paramToBeProxyInterface1);

        ParamToBeProxyInterface2 paramToBeProxyInterface2 = ruleWithProxyAsExchange.getParamToBeProxyInterface2();
        Assert.assertNull(paramToBeProxyInterface2);
    }

    @Test
    public void testProxyInjectionWithParentInterfaceInParentSupportedRule() {

        testWithParent(ParentParamToBeProxyInterface.class);
    }

    @Test
    public void testProxyInjectionWithChildInterfaceInParentSupportedRule() {

        testWithParent(ParamToBeProxyInterface1.class);
    }

    private void testWithParent(Class<?> interfaceClass) {

        ParamToBeProxy paramToBeProxy = new ParamToBeProxy(TEST);

        Object proxy = ParamToBeProxyFactory.createProxy(paramToBeProxy, new Class[]
            {
                interfaceClass
            });

        RuleWithParentProxyAsExchange ruleWithParentProxyAsExchange = new RuleWithParentProxyAsExchange();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(ruleWithParentProxyAsExchange);

        rulesEngine.execute(proxy);

        validateExecutables(true, true, ruleWithParentProxyAsExchange);

        ParentParamToBeProxyInterface paramToBeProxyInterfaceWhen = ruleWithParentProxyAsExchange
                .getParentParamToBeProxyInterfaceWhen();
        Assert.assertNotNull(paramToBeProxyInterfaceWhen);
        Assert.assertEquals(TEST, paramToBeProxyInterfaceWhen.getValue());

        ParentParamToBeProxyInterface paramToBeProxyInterfaceThen = ruleWithParentProxyAsExchange
                .getParentParamToBeProxyInterfaceThen();
        Assert.assertNotNull(paramToBeProxyInterfaceThen);
        Assert.assertEquals(TEST, paramToBeProxyInterfaceThen.getValue());
    }
}
