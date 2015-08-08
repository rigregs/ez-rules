package com.opnitech.rules.core.test.engine.test_priority;

import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_priority.rules.TestBooleanPriorityRule;
import com.opnitech.rules.core.test.engine.test_priority.rules.TestInvalidMultipleBooleanPriorityRule;
import com.opnitech.rules.core.test.engine.test_priority.rules.TestInvalidParameterBooleanPriorityRule;
import com.opnitech.rules.core.test.engine.test_priority.rules.TestInvalidResultBooleanPriorityRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RulePriorityTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testTestBooleanPriorityRule() throws EngineException {

        validateRule(new TestBooleanPriorityRule());
    }
    @Test
    public void testTestBooleanPriorityRuleAsClass() throws EngineException {

        validateExceptionRule(TestBooleanPriorityRule.class);
    }

    @Test
    public void testTestInvalidResultBooleanPriorityRule() throws EngineException {

        validateExceptionRule(new TestInvalidResultBooleanPriorityRule());
    }

    @Test
    public void testTestInvalidParameterBooleanPriorityRule() throws EngineException {

        validateExceptionRule(new TestInvalidParameterBooleanPriorityRule());
    }

    @Test
    public void testTestInvalidMultipleBooleanPriorityRule() throws EngineException {

        validateExceptionRule(new TestInvalidMultipleBooleanPriorityRule());
    }
}
