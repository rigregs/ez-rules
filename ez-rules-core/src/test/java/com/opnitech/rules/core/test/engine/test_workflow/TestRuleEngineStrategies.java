package com.opnitech.rules.core.test.engine.test_workflow;

import org.junit.Test;

import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class TestRuleEngineStrategies extends AbstractTestRuleEngineStrategies {

    @Test
    public void testDefaultExecutionStrategy() throws Exception {

        testMultiRule(null, new WhenEnum[]
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
}
