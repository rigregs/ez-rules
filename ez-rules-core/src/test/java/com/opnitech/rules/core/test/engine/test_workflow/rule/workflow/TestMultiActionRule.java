package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange1;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange2;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange3;

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
    public void action4(Exchange2 exchange2) {

        Validate.notNull(exchange2);
        this.actionExecutions.add(4);
    }

    @Then(priority = 3)
    public void action3(Exchange1 exchange1) {

        Validate.notNull(exchange1);
        this.actionExecutions.add(3);
    }

    @Then(priority = 6)
    public void action6(Exchange1 exchange1, Exchange2 exchange2, Exchange3 exchange3) {

        Validate.notNull(exchange1);
        Validate.notNull(exchange2);
        Validate.isTrue(exchange3 == null);

        this.actionExecutions.add(6);
    }

    @Then(priority = 5)
    public void action5(Exchange1 exchange1, Exchange2 exchange2) {

        Validate.notNull(exchange1);
        Validate.notNull(exchange2);

        this.actionExecutions.add(5);
    }

    public List<Integer> getActionExecutions() {

        return this.actionExecutions;
    }
}
