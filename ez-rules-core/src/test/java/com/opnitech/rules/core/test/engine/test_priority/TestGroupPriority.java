package com.opnitech.rules.core.test.engine.test_priority;

import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_priority.group.InvalidGroupPriorityMultiplePriorityRule;
import com.opnitech.rules.core.test.engine.test_priority.group.InvalidGroupPriorityResult;
import com.opnitech.rules.core.test.engine.test_priority.group.InvalidGroupPriorityWithParameter;
import com.opnitech.rules.core.test.engine.test_priority.group.ValidGroupPriority;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class TestGroupPriority extends AbstractRuleEngineExecutorTest {

    @Test
    public void testValidGroupPriority() throws EngineException {

        validateRule(new ValidGroupPriority());
    }

    @Test
    public void testValidGroupPriorityAsClass() {

        validateExceptionRule(ValidGroupPriority.class);
    }

    @Test
    public void testInvalidGroupPriorityMultiplePriorityRule() {

        validateExceptionRule(new InvalidGroupPriorityMultiplePriorityRule());
    }

    @Test
    public void testInvalidGroupPriorityWithParameter() {

        validateExceptionRule(new InvalidGroupPriorityWithParameter());
    }

    @Test
    public void testInvalidGroupPriorityResult() {

        validateExceptionRule(new InvalidGroupPriorityResult());
    }
}
