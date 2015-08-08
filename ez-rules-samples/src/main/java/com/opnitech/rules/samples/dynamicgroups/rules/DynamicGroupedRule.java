package com.opnitech.rules.samples.dynamicgroups.rules;

import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class DynamicGroupedRule {

    private boolean canExecute;
    private String textToPrint;
    private String groupKey;

    public DynamicGroupedRule(String groupKey, boolean canExecute, String textToPrint) {
        this.groupKey = groupKey;
        this.canExecute = canExecute;
        this.textToPrint = textToPrint;
    }

    @GroupKey
    public String retrieveGroupKey() {

        return this.groupKey;
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
