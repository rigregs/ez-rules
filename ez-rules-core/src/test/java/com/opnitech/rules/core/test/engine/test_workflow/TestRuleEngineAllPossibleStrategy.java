package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Test;

import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class TestRuleEngineAllPossibleStrategy extends AbstractTestRuleEngineStrategies {

    @Test
    public void testAllExecutionStrategy() throws Exception {

        testMultiRule(ExecutionStrategyEnum.ALL_POSSIBLE, new WhenEnum[]
            {
                WhenEnum.ACCEPT,
                WhenEnum.ACCEPT,
                WhenEnum.ACCEPT
            }, new boolean[][]
            {
                {
                    true,
                    true
                        },
                {
                    true,
                    true
                        },
                {
                    true,
                    true
                        }
            });
    }

    @Test
    public void testAllExecutionStrategyFirstReject() throws Exception {

        testMultiRule(ExecutionStrategyEnum.ALL_POSSIBLE, new WhenEnum[]
            {
                WhenEnum.REJECT,
                WhenEnum.ACCEPT,
                WhenEnum.ACCEPT
            }, new boolean[][]
            {
                {
                    true,
                    false
                        },
                {
                    true,
                    true
                        },
                {
                    true,
                    true
                        }
            });
    }

    @Test
    public void testAllExecutionStrategyMiddleReject() throws Exception {

        testMultiRule(ExecutionStrategyEnum.ALL_POSSIBLE, new WhenEnum[]
            {
                WhenEnum.ACCEPT,
                WhenEnum.REJECT,
                WhenEnum.ACCEPT
            }, new boolean[][]
            {
                {
                    true,
                    true
                        },
                {
                    true,
                    false
                        },
                {
                    true,
                    true
                        }
            });
    }

    @Test
    public void testAllExecutionStrategyLastReject() throws Exception {

        testMultiRule(ExecutionStrategyEnum.ALL_POSSIBLE, new WhenEnum[]
            {
                WhenEnum.ACCEPT,
                WhenEnum.ACCEPT,
                WhenEnum.REJECT
            }, new boolean[][]
            {
                {
                    true,
                    true
                        },
                {
                    true,
                    true
                        },
                {
                    true,
                    false
                        }
            });
    }
}
