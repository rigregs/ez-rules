package com.opnitech.rules.core.test.engine.test_executables.rules.instantiation;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractInstantiationRule extends AbstractRule<WhenEnum> {

    public AbstractInstantiationRule() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when() {

        return doExecuteWhen();
    }

    @Then
    public void then() {

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
