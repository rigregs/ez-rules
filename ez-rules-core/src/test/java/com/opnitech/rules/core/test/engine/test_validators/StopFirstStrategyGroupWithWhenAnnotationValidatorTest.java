package com.opnitech.rules.core.test.engine.test_validators;

import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class StopFirstStrategyGroupWithWhenAnnotationValidatorTest extends AbstractGroupWithWhenValidatorTest {

    @Test
    public void testOneGroup() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.STOP_FIRST, new Object[][]
            {
                {
                    ExecutionStrategyEnum.STOP_FIRST,
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
                    false,
                    false
                        }
            });
    }

    @Test
    public void testTwoGroupFirstReject() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.STOP_FIRST, new Object[][]
            {
                {
                    ExecutionStrategyEnum.STOP_FIRST,
                    "TEST_GROUP_KEY_1",
                    WhenEnum.REJECT,
                    true
                        },
                {
                    ExecutionStrategyEnum.STOP_FIRST,
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY_1",
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_1",
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2",
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2",
                    false,
                    false
                        }
            });
    }

    @Test
    public void testTwoGroupFirstAccept() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.STOP_FIRST, new Object[][]
            {
                {
                    ExecutionStrategyEnum.STOP_FIRST,
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.STOP_FIRST,
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    false
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY_1",
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_1",
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2",
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2",
                    false,
                    false
                        }
            });
    }

}
