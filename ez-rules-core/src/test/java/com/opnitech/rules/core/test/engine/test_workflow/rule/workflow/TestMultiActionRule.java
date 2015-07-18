package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context1;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context2;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context3;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestMultiActionRule {

    public static final int ACTION_COUNT = 6;

    private final List<Integer> actionExecutions = new ArrayList<>();

    public TestMultiActionRule() {
        // Default constructor
    }

    @When
    public WhenEnum condition() {

        return WhenEnum.ACCEPT;
    }

    @Then(priority = 2)
    public void action2() {

        this.actionExecutions.add(2);
    }

    @Then(priority = 1)
    public void action1() {

        this.actionExecutions.add(1);
    }

    @Then(priority = 4)
    public void action4(Context2 context2) {

        Validate.notNull(context2);
        this.actionExecutions.add(4);
    }

    @Then(priority = 3)
    public void action3(Context1 context1) {

        Validate.notNull(context1);
        this.actionExecutions.add(3);
    }

    @Then(priority = 6)
    public void action6(Context1 context1, Context2 context2, Context3 context3) {

        Validate.notNull(context1);
        Validate.notNull(context2);
        Validate.isTrue(context3 == null);

        this.actionExecutions.add(6);
    }

    @Then(priority = 5)
    public void action5(Context1 context1, Context2 context2) {

        Validate.notNull(context1);
        Validate.notNull(context2);

        this.actionExecutions.add(5);
    }

    public List<Integer> getActionExecutions() {

        return this.actionExecutions;
    }
}
