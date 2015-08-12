package com.opnitech.rules.core.test.engine.test_validators;

import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ComplexStrategyGroupWithWhenAnnotationValidatorTest extends AbstractGroupWithWhenValidatorTest {

    @Test
    public void testComplexNestedGroups() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.ALL, new Object[][]
            {
                {
                    ExecutionStrategyEnum.STOP_FIRST,
                    "",
                    "TEST_GROUP_KEY_1_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.ALL,
                    "",
                    "TEST_GROUP_KEY_2_ALL",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_2_ALL",
                    "TEST_GROUP_KEY_2_ALL_1_ALL",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.ALL_POSSIBLE,
                    "TEST_GROUP_KEY_2_ALL",
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.STOP_FIRST,
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE",
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE_1_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.STOP_FIRST,
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE",
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE_2_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.ALL_POSSIBLE,
                    "",
                    "TEST_GROUP_KEY_3_ALL_POSSIBLE",
                    WhenEnum.ACCEPT,
                    true
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY_1_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_1_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2_ALL",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2_ALL",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_1_ALL",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_1_ALL",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE",
                    WhenEnum.REJECT,
                    true,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE_1_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE_1_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE_2_STOP_FIRST",
                    WhenEnum.REJECT,
                    true,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2_ALL_2_ALL_POSSIBLE_2_STOP_FIRST",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_3_ALL_POSSIBLE",
                    WhenEnum.REJECT,
                    true,
                    false
                        },
                {
                    "TEST_GROUP_KEY_3_ALL_POSSIBLE",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        }
            });
    }
}
