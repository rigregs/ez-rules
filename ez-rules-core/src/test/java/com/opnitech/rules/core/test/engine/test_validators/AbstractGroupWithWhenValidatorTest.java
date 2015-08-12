package com.opnitech.rules.core.test.engine.test_validators;

import java.text.MessageFormat;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_validators.group.ValidGroupDefinitionWithWhenAnnotation;
import com.opnitech.rules.core.test.engine.test_validators.rules.group.ValidGroupRuleWithValidGroupKey;

import junit.framework.Assert;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AbstractGroupWithWhenValidatorTest {

    private static final int RULE_GROUP_KEY_INDEX = 0;
    private static final int RULE_WHEN_ENUM_INDEX = 1;
    private static final int RULE_EXECUTE_WHEN_INDEX = 2;
    private static final int RULE_EXECUTE_THEN_INDEX = 3;

    private static final int GROUP_EXECUTION_STRATEGY_INDEX = 0;
    private static final int GROUP_PARENT_GROUP_KEY_INDEX = 1;
    private static final int GROUP_GROUP_KEY_INDEX = 2;
    private static final int GROUP_WHEN_ENUM_INDEX = 3;
    private static final int GROUP_EXECUTE_WHEN_INDEX = 4;

    protected void testGroupWithWhen(ExecutionStrategyEnum executionStrategy, Object[][] groupDataArray, Object[][] ruleDataArray)
            throws EngineException {

        RulesEngine rulesEngine = new RulesEngine(executionStrategy);

        ValidGroupDefinitionWithWhenAnnotation[] groups = createGroups(groupDataArray, rulesEngine);
        ValidGroupRuleWithValidGroupKey[] rules = createRules(ruleDataArray, rulesEngine);

        rulesEngine.execute();

        assertAfterExecution(groupDataArray, ruleDataArray, groups, rules);
    }

    private ValidGroupDefinitionWithWhenAnnotation[] createGroups(Object[][] groupDataArray, RulesEngine rulesEngine)
            throws EngineException {

        ValidGroupDefinitionWithWhenAnnotation[] groups = new ValidGroupDefinitionWithWhenAnnotation[groupDataArray.length];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new ValidGroupDefinitionWithWhenAnnotation(i, groupDataArray[i][GROUP_PARENT_GROUP_KEY_INDEX].toString(),
                    groupDataArray[i][GROUP_GROUP_KEY_INDEX].toString(), (WhenEnum) groupDataArray[i][GROUP_WHEN_ENUM_INDEX],
                    (ExecutionStrategyEnum) groupDataArray[i][GROUP_EXECUTION_STRATEGY_INDEX]);
            rulesEngine.registerExecutable(groups[i]);
        }

        return groups;
    }

    private void assertAfterExecution(Object[][] groupDataArray, Object[][] ruleDataArray,
            ValidGroupDefinitionWithWhenAnnotation[] groups, ValidGroupRuleWithValidGroupKey[] rules) {

        assertGroupsAfterExecution(groupDataArray, groups);
        assertRulesAfterExecution(ruleDataArray, rules);
    }

    private void assertRulesAfterExecution(Object[][] ruleDataArray, ValidGroupRuleWithValidGroupKey[] rules) {

        for (int i = 0; i < rules.length; i++) {
            String assertId = MessageFormat.format("Rule: {0}, Group Key: {1}", i, rules[i].groupKey());
            Assert.assertEquals(assertId, ((Boolean) ruleDataArray[i][RULE_EXECUTE_WHEN_INDEX]).booleanValue(),
                    rules[i].isExcuteWhen());
            Assert.assertEquals(assertId, ((Boolean) ruleDataArray[i][RULE_EXECUTE_THEN_INDEX]).booleanValue(),
                    !rules[i].getExecuteThens().isEmpty());
        }
    }

    private void assertGroupsAfterExecution(Object[][] groupDataArray, ValidGroupDefinitionWithWhenAnnotation[] groups) {

        for (int i = 0; i < groups.length; i++) {
            Assert.assertEquals(new StringBuffer().append("Group: ").append(i).toString(),
                    ((Boolean) groupDataArray[i][GROUP_EXECUTE_WHEN_INDEX]).booleanValue(), groups[i].isExecuteWhen());
        }
    }

    private ValidGroupRuleWithValidGroupKey[] createRules(Object[][] ruleDataArray, RulesEngine rulesEngine)
            throws EngineException {

        ValidGroupRuleWithValidGroupKey[] rules = new ValidGroupRuleWithValidGroupKey[ruleDataArray.length];
        for (int i = 0; i < rules.length; i++) {
            rules[i] = new ValidGroupRuleWithValidGroupKey(i, ruleDataArray[i][RULE_GROUP_KEY_INDEX].toString(),
                    (WhenEnum) ruleDataArray[i][RULE_WHEN_ENUM_INDEX]);
            rulesEngine.registerExecutable(rules[i]);
        }

        return rules;
    }
}
