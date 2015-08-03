package com.opnitech.rules.core.test.engine.test_workflow.rule.workflow;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class TestAcceptSimpleRule {

    private boolean executeCondition;

    private boolean executeAction;

    private final WhenEnum ruleCondition;

    public TestAcceptSimpleRule() {

        this(WhenEnum.ACCEPT);
    }

    public TestAcceptSimpleRule(WhenEnum ruleCondition) {

        this.ruleCondition = ruleCondition;
    }

    @When
    public WhenEnum condition() {

        this.executeCondition = true;
        return this.ruleCondition;
    }

    @Then
    public void action() {

        this.executeAction = true;
    }

    public boolean isExecuteCondition() {

        return this.executeCondition;
    }

    public boolean isExecuteAction() {

        return this.executeAction;
    }
}
