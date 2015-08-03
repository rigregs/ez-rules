package com.opnitech.rules.core.test.engine.test_executables.rules.callback;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractCallbackRule;
import com.opnitech.rules.core.test.engine.test_executables.callback.SimpleCallback1;
import com.opnitech.rules.core.test.engine.test_executables.callback.SimpleCallback2;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class FullCallbackWithPartialAnnotationRule extends AbstractCallbackRule {

    public FullCallbackWithPartialAnnotationRule() {
        super(WhenEnum.ACCEPT);
    }

    @When
    public WhenEnum when(@Callback SimpleCallback1 simpleCallback1, SimpleCallback2 simpleCallback2) {

        registerWhenCallbacks(simpleCallback1, simpleCallback2);

        return doExecuteWhen();
    }

    @Then
    public void then(SimpleCallback1 simpleCallback1, @Callback SimpleCallback2 simpleCallback2) {

        registerThenCallbacks(simpleCallback1, simpleCallback2);

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
