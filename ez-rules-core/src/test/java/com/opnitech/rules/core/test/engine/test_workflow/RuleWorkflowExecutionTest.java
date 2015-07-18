package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.RuleEngineExecutionResult;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.callback.TestValidCallback;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context1;
import com.opnitech.rules.core.test.engine.test_workflow.context.Context2;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestAcceptSimpleConditionWithParameterRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestAcceptSimpleDualContextConditionWithParameterRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestAcceptSimpleRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestCallbackRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestContextManagerRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestMultiActionRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleWorkflowExecutionTest {

    @Test
    public void testSimpleRules() throws Exception {

        TestAcceptSimpleRule testAcceptSimpleRule = new TestAcceptSimpleRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(testAcceptSimpleRule);

        rulesEngine.execute();

        Assert.assertTrue(testAcceptSimpleRule.isExecuteCondition());
        Assert.assertTrue(testAcceptSimpleRule.isExecuteAction());
    }

    @Test
    public void testAcceptSimpleConditionWithParameterRule() throws Exception {

        TestAcceptSimpleConditionWithParameterRule rule = new TestAcceptSimpleConditionWithParameterRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(rule);

        Context1 context1 = new Context1("TEST");

        RuleEngineExecutionResult execute = rulesEngine.execute(context1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(context1, rule.getContextCondition1());
        Assert.assertEquals(context1, rule.getContextAction1());
    }

    @Test
    public void testAcceptSimpleDualContextConditionWithParameterRule() throws Exception {

        TestAcceptSimpleDualContextConditionWithParameterRule rule = new TestAcceptSimpleDualContextConditionWithParameterRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(rule);

        Context1 context1 = new Context1("TEST");
        Context2 context2 = new Context2("TEST");

        RuleEngineExecutionResult execute = rulesEngine.execute(context1, context2);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(context1, rule.getContext1());
        Assert.assertEquals(context2, rule.getContext2());

        rule.setContext1(null);
        rule.setContext2(null);

        execute = rulesEngine.execute(context2, context1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(context1, rule.getContext1());
        Assert.assertEquals(context2, rule.getContext2());
    }

    @Test
    public void testMultiActionRule() throws Exception {

        Context1 context1 = new Context1("TEST");
        Context2 context2 = new Context2("TEST");

        TestMultiActionRule rule = new TestMultiActionRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(rule);

        RuleEngineExecutionResult execute = rulesEngine.execute(context1, context2);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Integer[] actions = rule.getActionExecutions().toArray(new Integer[0]);

        Assert.assertEquals(TestMultiActionRule.ACTION_COUNT, actions.length);

        for (int i = 0; i < actions.length; i++) {
            Assert.assertEquals(Integer.valueOf(i + 1), actions[i]);
        }
    }

    @Test
    public void testCallbackRule() throws Exception {

        Context1 context1 = new Context1("TEST");

        TestCallbackRule rule = new TestCallbackRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(TestValidCallback.class);
        rulesEngine.registerExecutable(rule);

        RuleEngineExecutionResult execute = rulesEngine.execute(context1);
        if (execute.getException() != null) {
            throw execute.getException();
        }
    }

    @Test
    public void testContextManagerRule() throws Exception {

        Context1 context1 = new Context1("TEST");

        TestContextManagerRule rule = new TestContextManagerRule();
        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(rule);

        RuleEngineExecutionResult execute = rulesEngine.execute(context1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(TestContextManagerRule.ACTION_COUNT, rule.getActionExecuted());
    }

    @Test
    public void testMultiRule() throws Exception {

        Context1 context1 = new Context1("TEST");

        TestAcceptSimpleRule acceptRule1 = new TestAcceptSimpleRule(WhenEnum.ACCEPT);
        TestAcceptSimpleRule acceptRule2 = new TestAcceptSimpleRule(WhenEnum.ACCEPT);

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(acceptRule1);
        rulesEngine.registerExecutable(acceptRule2);

        RuleEngineExecutionResult execute = rulesEngine.execute(context1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertTrue(acceptRule1.isExecuteCondition());
        Assert.assertTrue(acceptRule1.isExecuteAction());

        Assert.assertTrue(acceptRule2.isExecuteCondition());
        Assert.assertTrue(acceptRule2.isExecuteAction());
    }
}
