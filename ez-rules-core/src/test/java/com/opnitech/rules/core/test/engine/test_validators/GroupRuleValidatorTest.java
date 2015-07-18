package com.opnitech.rules.core.test.engine.test_validators;

import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_validators.group.InvalidGroupDefinition;
import com.opnitech.rules.core.test.engine.test_validators.group.ValidGroupDefinition;
import com.opnitech.rules.core.test.engine.test_validators.rules.group.InvalidGroupRule;
import com.opnitech.rules.core.test.engine.test_validators.rules.group.ValidGroupRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupRuleValidatorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testValidRule() throws Exception {

        // Group need to be defined
        validateRule(ValidGroupDefinition.class, new ValidGroupRule());
    }

    @Test
    public void testValidRuleButNoGroupDefinition() throws Exception {

        // Group need to be defined
        validateExceptionRule(new ValidGroupRule());
    }

    @Test
    public void testInvalidRuleButNoGroupDefinition() throws Exception {

        validateExceptionRule(new InvalidGroupRule());
    }

    @Test
    public void testInvalidRule() throws Exception {

        validateExceptionRule(InvalidGroupDefinition.class, new InvalidGroupRule());
    }
}
