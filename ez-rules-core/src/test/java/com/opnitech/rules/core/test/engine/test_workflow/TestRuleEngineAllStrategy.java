package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Test;

import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class TestRuleEngineAllStrategy extends AbstractTestRuleEngineStrategies {
    
    @Test
    public void testAllExecutionStrategy() throws Exception {

        testMultiRule(ExecutionStrategyEnum.ALL, new WhenEnum[]
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

        testMultiRule(ExecutionStrategyEnum.ALL, new WhenEnum[]
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
                    false,
                    false
                        },
                {
                    false,
                    false
                        }
            });
    }

    @Test
    public void testAllExecutionStrategyMiddleReject() throws Exception {

        testMultiRule(ExecutionStrategyEnum.ALL, new WhenEnum[]
            {
                WhenEnum.ACCEPT,
                WhenEnum.REJECT,
                WhenEnum.ACCEPT
            }, new boolean[][]
            {
                {
                    true,
                    false
                        },
                {
                    true,
                    false
                        },
                {
                    false,
                    false
                        }
            });
    }

    @Test
    public void testAllExecutionStrategyLastReject() throws Exception {

        testMultiRule(ExecutionStrategyEnum.ALL, new WhenEnum[]
            {
                WhenEnum.ACCEPT,
                WhenEnum.ACCEPT,
                WhenEnum.REJECT
            }, new boolean[][]
            {
                {
                    true,
                    false
                        },
                {
                    true,
                    false
                        },
                {
                    true,
                    false
                        }
            });
    }
}
