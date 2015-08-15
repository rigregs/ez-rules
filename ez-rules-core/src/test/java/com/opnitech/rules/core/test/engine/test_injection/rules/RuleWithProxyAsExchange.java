package com.opnitech.rules.core.test.engine.test_injection.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyInterface1;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParamToBeProxyInterface2;
import com.opnitech.rules.core.test.engine.test_priority.rules.TestBooleanPriorityRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class RuleWithProxyAsExchange extends AbstractRule<WhenEnum> {

    private ParamToBeProxyInterface1 paramToBeProxyInterface1;
    private ParamToBeProxyInterface2 paramToBeProxyInterface2;

    public RuleWithProxyAsExchange() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when(ParamToBeProxyInterface1 paramToBeProxyInterfaceExchange) {

        this.paramToBeProxyInterface1 = paramToBeProxyInterfaceExchange;
        return doExecuteWhen();
    }

    @Then
    public void then(ParamToBeProxyInterface2 paramToBeProxyInterfaceExchange) {

        doExecuteThen(TestBooleanPriorityRule.class.getEnclosingMethod());
        this.paramToBeProxyInterface2 = paramToBeProxyInterfaceExchange;
    }

    public ParamToBeProxyInterface1 getParamToBeProxyInterface1() {

        return this.paramToBeProxyInterface1;
    }

    public ParamToBeProxyInterface2 getParamToBeProxyInterface2() {

        return this.paramToBeProxyInterface2;
    }
}
