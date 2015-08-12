package com.opnitech.rules.core.test.engine.test_validators.rules.group;

import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRule;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Rule
public class ValidGroupRuleWithValidGroupKey extends AbstractRule<WhenEnum> {

    private String groupKey;

    public ValidGroupRuleWithValidGroupKey(String groupKey) {
        this(groupKey, WhenEnum.ACCEPT);
    }

    public ValidGroupRuleWithValidGroupKey(String groupKey, WhenEnum whenEnum) {
        super(whenEnum);

        this.groupKey = groupKey;
    }

    @GroupKey
    public String groupKey() {

        return this.groupKey;
    }

    @When
    public WhenEnum when() {

        return doExecuteWhen();
    }

    @Then
    public void then() {

        doExecuteThen(TestBooleanPrimitiveWhenRule.class.getEnclosingMethod());
    }
}
