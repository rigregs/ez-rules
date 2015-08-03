package com.opnitech.rules.core.test.engine.test_exceptions;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_exceptions.rules.ExceptionThenRule;
import com.opnitech.rules.core.test.engine.test_exceptions.rules.ExceptionWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ExceptionEngineExecutorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testExceptionWhenRule() {

        validate(ExceptionWhenRule.class, "When");
    }

    @Test
    public void testExceptionThenRule() {

        validate(ExceptionThenRule.class, "Then");
    }

    private void validate(Class<?> ruleClass, String expectedMessage) {

        Exception validateExceptionRule = validateExceptionRule(ruleClass);
        Assert.assertNotNull(validateExceptionRule);
        Assert.assertEquals(expectedMessage, validateExceptionRule.getCause().getMessage());
    }
}
