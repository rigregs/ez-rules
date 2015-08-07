package com.opnitech.rules.core.test.engine.test_workflow.rule.group;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractGroupRule {

    private boolean executeCondition;

    private boolean executeAction;

    public AbstractGroupRule() {
        // Default constructor
    }

    protected abstract WhenEnum doCondition();

    @When
    public final WhenEnum condition() {

        this.executeCondition = true;

        return doCondition();
    }

    @Then
    public final void action() {

        this.executeAction = true;
    }

    public boolean isExecuteCondition() {

        return this.executeCondition;
    }

    public boolean isExecuteAction() {

        return this.executeAction;
    }
}
