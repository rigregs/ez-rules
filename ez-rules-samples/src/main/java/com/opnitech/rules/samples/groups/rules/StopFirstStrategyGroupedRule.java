package com.opnitech.rules.samples.groups.rules;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.samples.groups.group.StopFirstGroupDefinition;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
@Group(groupDefinitionClass = StopFirstGroupDefinition.class)
public class StopFirstStrategyGroupedRule {

    private boolean canExecute;
    private String textToPrint;

    public StopFirstStrategyGroupedRule(boolean canExecute, String textToPrint) {
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
