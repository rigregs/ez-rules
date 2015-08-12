package com.opnitech.rules.core.test.engine.test_validators.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition
public class ValidGroupDefinitionWithWhenAnnotation {

    private String groupKey;
    private WhenEnum whenEnum;

    private boolean executeWhen;

    public ValidGroupDefinitionWithWhenAnnotation(String groupKey, WhenEnum whenEnum) {
        this.groupKey = groupKey;
        this.whenEnum = whenEnum;
    }

    @When
    public WhenEnum when() {

        this.executeWhen = true;

        return this.whenEnum;
    }

    @GroupKey
    public String groupKey() {

        return this.groupKey;
    }

    public boolean isExecuteWhen() {

        return this.executeWhen;
    }
}
