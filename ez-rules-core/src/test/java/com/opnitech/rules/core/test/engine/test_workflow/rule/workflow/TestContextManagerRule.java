package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.ContextManager;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context1;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context2;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestContextManagerRule {

    public static final int ACTION_COUNT = 7;

    private int actionExecuted = 0;

    @When
    public WhenEnum condition() {

        return WhenEnum.ACCEPT;
    }

    @Then(priority = 1)
    public void action1(Context1 context1, Context2 context2, ContextManager contextManager) {

        Validate.notNull(contextManager);

        Validate.notNull(context1);
        Validate.isTrue(context2 == null);

        this.actionExecuted++;
    }

    @Then(priority = 2)
    public void actionAddContext2(Context1 context1, ContextManager contextManager) {

        Validate.notNull(contextManager);

        Validate.notNull(context1);

        contextManager.addContext(new Context2("TEST"));

        this.actionExecuted++;
    }

    @Then(priority = 3)
    public void actionValidate3(Context1 context1, Context2 context2, ContextManager contextManager) {

        Validate.notNull(contextManager);

        Validate.notNull(context1);
        Validate.notNull(context2);
        Validate.isTrue(Objects.equals("TEST", context2.getValue()));

        this.actionExecuted++;
    }

    @Then(priority = 4)
    public void actionReplace4(Context1 context1, Context2 context2, ContextManager contextManager) {

        Validate.notNull(contextManager);

        Validate.notNull(context1);

        contextManager.replaceContext(context2, new Context2("TEST1"));

        this.actionExecuted++;
    }

    @Then(priority = 5)
    public void actionCheckNewContext4(Context1 context1, Context2 context2, ContextManager contextManager) {

        Validate.notNull(contextManager);

        Validate.notNull(context1);
        Validate.notNull(context2);
        Validate.isTrue(Objects.equals("TEST1", context2.getValue()));

        this.actionExecuted++;
    }

    @Then(priority = 6)
    public void actionRemoveContext6(Context1 context1, Context2 context2, ContextManager contextManager) {

        Validate.notNull(contextManager);

        Validate.notNull(context1);
        Validate.notNull(context2);

        contextManager.removeContext(context2);

        this.actionExecuted++;
    }

    @Then(priority = 7)
    public void actionCheckRemoveContext7(Context1 context1, Context2 context2, ContextManager contextManager) {

        Validate.notNull(contextManager);

        Validate.notNull(context1);
        Validate.isTrue(context2 == null);

        this.actionExecuted++;
    }

    public int getActionExecuted() {

        return this.actionExecuted;
    }
}
