package com.opnitech.rules.core.test.engine.test_when;

import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanPrimitiveWhenRule;
import com.opnitech.rules.core.test.engine.test_when.rules.TestBooleanWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class BooleanWhenRuleEngineExecutorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testBooleanPrimitiveWhenRule() throws Exception {

        validateRule(new TestBooleanPrimitiveWhenRule(true));
    }

    @Test
    public void testBooleanPrimitiveWhenFalseRule() throws Exception {

        validateWithPreconditionsRule(true, false, new TestBooleanPrimitiveWhenRule(false));
    }

    @Test
    public void testBooleanWhenRule() throws Exception {

        validateRule(new TestBooleanWhenRule(true));
    }

    @Test
    public void testBooleanWhenFalseRule() throws Exception {

        validateWithPreconditionsRule(true, false, new TestBooleanWhenRule(false));
    }
}
