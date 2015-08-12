package com.opnitech.rules.core.test.engine.test_validators;

import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AllStrategyGroupWithWhenAnnotationValidatorTest extends AbstractGroupWithWhenValidatorTest {

    @Test
    public void testOneGroup() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.ALL, new Object[][]
            {
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY",
                    WhenEnum.ACCEPT,
                    true
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY",
                    WhenEnum.ACCEPT,
                    true,
                    false
                        },
                {
                    "TEST_GROUP_KEY",
                    WhenEnum.REJECT,
                    true,
                    false
                        }
            });
    }

    @Test
    public void testTwoGroupFirstReject() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.ALL, new Object[][]
            {
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_1",
                    WhenEnum.REJECT,
                    true
                        },
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    false
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.REJECT,
                    false,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    false,
                    false
                        }
            });
    }

    @Test
    public void testTwoGroupFirstRejectCombined() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.ALL, new Object[][]
            {
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
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
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.REJECT,
                    true,
                    false
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    false,
                    false
                        }
            });
    }

    @Test
    public void testTwoGroupFirstAccept() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.ALL, new Object[][]
            {
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        }
            });
    }

    @Test
    public void testTwoGroupFirstAcceptCombined() throws EngineException {

        testGroupWithWhen(ExecutionStrategyEnum.ALL, new Object[][]
            {
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true
                        },
                {
                    ExecutionStrategyEnum.ALL,
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true
                        }
            }, new Object[][]
            {
                {
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_1",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY_2",
                    WhenEnum.ACCEPT,
                    true,
                    true
                        }
            });
    }
}
