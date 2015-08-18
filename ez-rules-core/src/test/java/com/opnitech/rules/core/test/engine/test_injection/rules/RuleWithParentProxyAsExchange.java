package com.opnitech.rules.core.test.engine.test_injection.rules;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_injection.rules.proxy.ParentParamToBeProxyInterface;
import com.opnitech.rules.core.test.engine.test_priority.rules.TestBooleanPriorityRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class RuleWithParentProxyAsExchange extends AbstractRule<WhenEnum> {

    private ParentParamToBeProxyInterface parentParamToBeProxyInterfaceWhen;
    private ParentParamToBeProxyInterface parentParamToBeProxyInterfaceThen;

    public RuleWithParentProxyAsExchange() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when(ParentParamToBeProxyInterface parentParamToBeProxyInterfaceWhenParam) {

        this.parentParamToBeProxyInterfaceWhen = parentParamToBeProxyInterfaceWhenParam;
        return doExecuteWhen();
    }

    @Then
    public void then(ParentParamToBeProxyInterface parentParamToBeProxyInterfaceThenParam) {

        doExecuteThen(TestBooleanPriorityRule.class.getEnclosingMethod());
        this.parentParamToBeProxyInterfaceThen = parentParamToBeProxyInterfaceThenParam;
    }

    public ParentParamToBeProxyInterface getParentParamToBeProxyInterfaceWhen() {

        return this.parentParamToBeProxyInterfaceWhen;
    }

    public ParentParamToBeProxyInterface getParentParamToBeProxyInterfaceThen() {

        return this.parentParamToBeProxyInterfaceThen;
    }
}
