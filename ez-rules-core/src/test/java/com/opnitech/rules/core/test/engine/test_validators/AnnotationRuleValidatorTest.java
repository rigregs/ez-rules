package com.opnitech.rules.core.test.engine.test_validators;

import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_validators.rules.annotations.NoAnnotationInvalidRule;
import com.opnitech.rules.core.test.engine.test_validators.rules.annotations.NoThenInvalidRule;
import com.opnitech.rules.core.test.engine.test_validators.rules.annotations.NoWhenInvalidRule;
import com.opnitech.rules.core.test.engine.test_validators.rules.annotations.ValidRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AnnotationRuleValidatorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testValidRule() throws Exception {

        validateRule(new ValidRule());
    }

    @Test
    public void testNoAnnotationInvalidRule() throws Exception {

        validateExceptionRule(new NoAnnotationInvalidRule());
    }

    @Test
    public void testNoWhenInvalidRule() throws Exception {

        validateExceptionRule(new NoWhenInvalidRule());
    }

    @Test
    public void testNoThenInvalidRule() throws Exception {

        validateExceptionRule(new NoThenInvalidRule());
    }
}
