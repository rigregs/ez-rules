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
    public void testAllStrategyValidWhenGroupWith() throws EngineException {

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
                    true,
                    true
                        },
                {
                    "TEST_GROUP_KEY",
                    true,
                    true
                        }
            });
    }
}
