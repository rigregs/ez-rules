package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Assert;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.ExecutionResult;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.test_workflow.exchanges.Exchange1;
import com.opnitech.rules.core.test.engine.test_workflow.rule.workflow.TestAcceptSimpleRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AbstractTestRuleEngineStrategies {

    protected void testMultiRule(ExecutionStrategyEnum executionStrategy, WhenEnum[] whenEnums, boolean[][] acceptConditions)
            throws EngineException {

        Exchange1 exchange1 = new Exchange1("TEST");

        RulesEngine rulesEngine = executionStrategy != null
                ? new RulesEngine(executionStrategy)
                : new RulesEngine();

        TestAcceptSimpleRule[] rules = new TestAcceptSimpleRule[whenEnums.length];
        for (int i = 0; i < whenEnums.length; i++) {
            rules[i] = new TestAcceptSimpleRule(whenEnums[i]);
            rulesEngine.registerExecutable(rules[i]);
        }

        ExecutionResult<?> execute = rulesEngine.execute(exchange1);
        if (execute.getException() != null) {
            throw execute.getException();
        }

        for (int i = 0; i < acceptConditions.length; i++) {
            Assert.assertEquals(acceptConditions[i][0], rules[i].isExecuteCondition());
            Assert.assertEquals(acceptConditions[i][1], rules[i].isExecuteAction());
        }
    }
}
