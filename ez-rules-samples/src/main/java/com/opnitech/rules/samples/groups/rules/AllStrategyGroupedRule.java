package com.opnitech.rules.samples.groups.rules;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.samples.groups.group.AllGroupDefinition;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
@Group(groupDefinitionClass = AllGroupDefinition.class)
public class AllStrategyGroupedRule {

    private boolean canExecute;
    private String textToPrint;

    public AllStrategyGroupedRule(boolean canExecute, String textToPrint) {
        this.canExecute = canExecute;
        this.textToPrint = textToPrint;
    }

    @When
    public boolean when() {

        return this.canExecute;
    }

    @Then
    public void execute() {

        System.out.println(this.textToPrint);
    }
}
