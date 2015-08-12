package com.opnitech.rules.core.test.engine.test_validators;

import org.junit.Test;

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
public class GroupWithWhenAnnotationValidatorTest {

    private static final int RULE_GROUP_KEY_INDEX = 0;
    private static final int RULE_EXECUTE_WHEN_INDEX = 1;
    private static final int RULE_EXECUTE_THEN_INDEX = 2;

    private static final int GROUP_GROUP_KEY_INDEX = 0;
    private static final int GROUP_WHEN_ENUM_INDEX = 1;
    private static final int GROUP_EXECUTE_WHEN_INDEX = 2;

    @Test
    public void testValidWhenGroup() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.ALL, new Object[][]
            {
                {
                    "TEST_GROUP_KEY",
                    WhenEnum.ACCEPT,
                    true
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY",
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY",
                    true,
                    true
                        }
            });
    }

    private void testGroupWithWhen(ExecutionStrategyEnum executionStrategy, Object[][] groupDataArray, Object[][] ruleDataArray)
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
            groups[i] = new ValidGroupDefinitionWithWhenAnnotation(groupDataArray[i][GROUP_GROUP_KEY_INDEX].toString(),
                    (WhenEnum) groupDataArray[i][GROUP_WHEN_ENUM_INDEX]);
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
            Assert.assertEquals(((Boolean) ruleDataArray[i][RULE_EXECUTE_WHEN_INDEX]).booleanValue(), rules[i].isExcuteWhen());
            Assert.assertEquals(((Boolean) ruleDataArray[i][RULE_EXECUTE_THEN_INDEX]).booleanValue(),
                    !rules[i].getExecuteThens().isEmpty());
        }
    }

    private void assertGroupsAfterExecution(Object[][] groupDataArray, ValidGroupDefinitionWithWhenAnnotation[] groups) {

        for (int i = 0; i < groups.length; i++) {
            Assert.assertEquals(((Boolean) groupDataArray[i][GROUP_EXECUTE_WHEN_INDEX]).booleanValue(),
                    groups[i].isExecuteWhen());
        }
    }

    private ValidGroupRuleWithValidGroupKey[] createRules(Object[][] ruleDataArray, RulesEngine rulesEngine)
            throws EngineException {

        ValidGroupRuleWithValidGroupKey[] rules = new ValidGroupRuleWithValidGroupKey[ruleDataArray.length];
        for (int i = 0; i < rules.length; i++) {
            rules[i] = new ValidGroupRuleWithValidGroupKey(ruleDataArray[i][RULE_GROUP_KEY_INDEX].toString());
            rulesEngine.registerExecutable(rules[i]);
        }

        return rules;
    }
}
