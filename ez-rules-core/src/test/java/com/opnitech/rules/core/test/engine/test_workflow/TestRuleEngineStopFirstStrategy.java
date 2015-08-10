package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Test;

import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class TestRuleEngineStopFirstStrategy extends AbstractTestRuleEngineStrategies {
    @Test
    public void testStopFirstExecutionStrategyWithAllRuleAccepting() throws Exception {

        testMultiRule(ExecutionStrategyEnum.STOP_FIRST, new WhenEnum[]
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
    public void testStopFirstExecutionStrategyWithMiddleRuleAccepting() throws Exception {

        testMultiRule(ExecutionStrategyEnum.STOP_FIRST, new WhenEnum[]
            {
                WhenEnum.REJECT,
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
                    true
                        },
                {
                    false,
                    false
                        }
            });
    }

    @Test
    public void testStopFirstExecutionStrategyWithLastRuleAccepting() throws Exception {

        testMultiRule(ExecutionStrategyEnum.STOP_FIRST, new WhenEnum[]
            {
                WhenEnum.REJECT,
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
                    true,
                    true
                        }
            });
    }

    @Test
    public void testStopFirstExecutionStrategyWithAnyRuleAccepting() throws Exception {

        testMultiRule(ExecutionStrategyEnum.STOP_FIRST, new WhenEnum[]
            {
                WhenEnum.REJECT,
                WhenEnum.REJECT,
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
