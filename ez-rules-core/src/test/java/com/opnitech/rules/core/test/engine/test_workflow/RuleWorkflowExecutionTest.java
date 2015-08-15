package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.ExecutionResult;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.test.engine.test_workflow.callback.TestValidCallback;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange1;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange2;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestAcceptSimpleConditionWithParameterRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestAcceptSimpleDualExchangeConditionWithParameterRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestAcceptSimpleRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestCallbackRule;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestExchangeManagerRule;
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

        Exchange1 exchange1 = new Exchange1("TEST");

        ExecutionResult<?> execute = rulesEngine.execute(exchange1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(exchange1, rule.getExchangeCondition1());
        Assert.assertEquals(exchange1, rule.getExchangeAction1());
    }

    @Test
    public void testAcceptSimpleDualExchangeConditionWithParameterRule() throws Exception {

        TestAcceptSimpleDualExchangeConditionWithParameterRule rule = new TestAcceptSimpleDualExchangeConditionWithParameterRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(rule);

        Exchange1 exchange1 = new Exchange1("TEST");
        Exchange2 exchange2 = new Exchange2("TEST");

        ExecutionResult<?> execute = rulesEngine.execute(exchange1, exchange2);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(exchange1, rule.getExchange1());
        Assert.assertEquals(exchange2, rule.getExchange2());

        rule.setExchange1(null);
        rule.setExchange2(null);

        execute = rulesEngine.execute(exchange2, exchange1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(exchange1, rule.getExchange1());
        Assert.assertEquals(exchange2, rule.getExchange2());
    }

    @Test
    public void testMultiActionRule() throws Exception {

        Exchange1 exchange1 = new Exchange1("TEST");
        Exchange2 exchange2 = new Exchange2("TEST");

        TestMultiActionRule rule = new TestMultiActionRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(rule);

        ExecutionResult<?> execute = rulesEngine.execute(exchange1, exchange2);
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

        Exchange1 exchange1 = new Exchange1("TEST");

        TestCallbackRule rule = new TestCallbackRule();

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(TestValidCallback.class);
        rulesEngine.registerExecutable(rule);

        ExecutionResult<?> execute = rulesEngine.execute(exchange1);
        if (execute.getException() != null) {
            throw execute.getException();
        }
    }

    @Test
    public void testExchangeManagerRule() throws Exception {

        Exchange1 exchange1 = new Exchange1("TEST");

        TestExchangeManagerRule rule = new TestExchangeManagerRule();
        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(rule);

        ExecutionResult<?> execute = rulesEngine.execute(exchange1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        Assert.assertEquals(TestExchangeManagerRule.ACTION_COUNT, rule.getActionExecuted());
    }
}
