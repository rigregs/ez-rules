package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.ContextManager;
import com.opnitech.rules.core.annotations.callback.Callback;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.callback.TestValidCallback;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context1;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestCallbackRule {

    @When
    public WhenEnum condition() {

        return WhenEnum.ACCEPT;
    }

    @Then
    public void action(Context1 context1, ContextManager contextManager, @Callback TestValidCallback testValidCallback) {

        Validate.notNull(context1);
        Validate.notNull(testValidCallback);
        Validate.notNull(contextManager);
    }
}
