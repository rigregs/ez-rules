package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RuleEngineExecutionResult;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.AbstractGroupRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.ExecuteAllFirstGroupRule1;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.ExecuteAllFirstGroupRule2;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.ExecuteAllFirstGroupRule3;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.ExecuteAllGroupDefinition;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.StopFirstGroupRule1;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.StopFirstGroupRule2;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.StopFirstGroupRule3;
import com.opnitech.rules.core.test.engine.test_workflow.rule.group.TestStopFirstGroupDefinition;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupRuleWorkflowExecutionTest {

    public GroupRuleWorkflowExecutionTest() {
        // Default constructor
    }

    @Test
    public void testRuleEngineWithoutGroup() {

        StopFirstGroupRule1 rule1 = new StopFirstGroupRule1();

        try {
            RulesEngine rulesEngine = new RulesEngine();
            rulesEngine.registerExecutable(rule1);
            rulesEngine.execute();
        }
        catch (EngineException e) {
            Assert.assertEquals(IllegalArgumentException.class, e.getCause().getClass());
        }
        catch (@SuppressWarnings("unused")
        Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testTestStopFirstGroupDefinition() throws Throwable {

        final StopFirstGroupRule1 rule1 = new StopFirstGroupRule1();
        final StopFirstGroupRule2 rule2 = new StopFirstGroupRule2();
        final StopFirstGroupRule3 rule3 = new StopFirstGroupRule3();

        testExecuteAllGroupDefinition(new RegisterexecutableCallback() {

            @Override
            public void registerRules(RulesEngine rulesEngine) throws Exception {

                rulesEngine.registerExecutable(TestStopFirstGroupDefinition.class);

                rulesEngine.registerExecutable(rule3);
                rulesEngine.registerExecutable(rule2);
                rulesEngine.registerExecutable(rule1);
            }
        });

        assertGroupRule(rule1, true, false);
        assertGroupRule(rule2, true, true);
        assertGroupRule(rule3, false, false);
    }

    @Test
    public void testExecuteAllNotExecutedGroupDefinition() throws Throwable {

        final ExecuteAllFirstGroupRule1 rule1 = new ExecuteAllFirstGroupRule1();
        final ExecuteAllFirstGroupRule2 rule2 = new ExecuteAllFirstGroupRule2();
        final ExecuteAllFirstGroupRule3 rule3 = new ExecuteAllFirstGroupRule3();

        testExecuteAllGroupDefinition(new RegisterexecutableCallback() {

            @Override
            public void registerRules(RulesEngine rulesEngine) throws Exception {

                rulesEngine.registerExecutable(ExecuteAllGroupDefinition.class);

                rulesEngine.registerExecutable(rule3);
                rulesEngine.registerExecutable(rule2);
                rulesEngine.registerExecutable(rule1);
            }
        });

        assertGroupRule(rule1, true, false);
        assertGroupRule(rule2, false, false);
        assertGroupRule(rule3, false, false);
    }

    @Test
    public void testExecuteAllExecutedGroupDefinition() throws Throwable {

        final ExecuteAllFirstGroupRule2 rule2 = new ExecuteAllFirstGroupRule2();
        final ExecuteAllFirstGroupRule3 rule3 = new ExecuteAllFirstGroupRule3();

        testExecuteAllGroupDefinition(new RegisterexecutableCallback() {

            @Override
            public void registerRules(RulesEngine rulesEngine) throws Exception {

                rulesEngine.registerExecutable(ExecuteAllGroupDefinition.class);

                rulesEngine.registerExecutable(rule3);
                rulesEngine.registerExecutable(rule2);
            }
        });

        assertGroupRule(rule2, true, true);
        assertGroupRule(rule3, true, true);
    }

    private void testExecuteAllGroupDefinition(RegisterexecutableCallback callback) throws Throwable {

        RulesEngine rulesEngine = new RulesEngine();

        callback.registerRules(rulesEngine);

        RuleEngineExecutionResult execute = rulesEngine.execute();
        if (execute.getException() != null) {
            throw execute.getException();
        }
    }

    private void assertGroupRule(AbstractGroupRule rule, boolean executeCondition, boolean executeAction) {

        Assert.assertEquals(executeCondition, rule.isExecuteCondition());
        Assert.assertEquals(executeAction, rule.isExecuteAction());
    }

    private interface RegisterexecutableCallback {

        void registerRules(RulesEngine rulesEngine) throws Exception;
    }
}
