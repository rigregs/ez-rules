package com.opnitech.rules.core.test.engine.test_validators;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_validators.group.ValidGroupDefinition;
import com.opnitech.rules.core.test.engine.test_validators.group.ValidGroupKeyGroupDefinition;
import com.opnitech.rules.core.test.engine.test_validators.rules.group.ValidGroupRuleWithValidGroupKey;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupKeyRuleValidatorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testValidGroupKeyGroupDefinition() throws EngineException {

        validateRule(new ValidGroupKeyGroupDefinition("TEST"), new ValidGroupRuleWithValidGroupKey("TEST"));
    }

    @Test
    public void testValidGroupKeyGroupDefinitionException() {

        validateExceptionRule(new ValidGroupKeyGroupDefinition("TEST"), new ValidGroupRuleWithValidGroupKey("TEST1"));
    }

    @Test
    public void testValidGroupRuleWithValidGroupKeyUsingTheGroupDefinitionClass() throws EngineException {

        validateRule(ValidGroupDefinition.class, new ValidGroupRuleWithValidGroupKey(ValidGroupDefinition.class.getName()));
    }

    @Test
    public void testValidGroupRuleWithValidGroupKey() {

        validateExceptionRule(new ValidGroupRuleWithValidGroupKey("TEST"));
    }

    @Test
    public void testValidGroupRuleWithValidGroupKeyButNullResult() {

        validateRule(new ValidGroupRuleWithValidGroupKey(null));
    }

    @Test
    public void testValidGroupRuleWithValidGroupKeyButBlankResult() {

        validateExceptionRule(new ValidGroupRuleWithValidGroupKey(" "));
    }

    @Test
    public void testValidGroupRuleWithValidGroupKeyButEmptyResult() {

        validateExceptionRule(new ValidGroupRuleWithValidGroupKey(StringUtils.EMPTY));
    }

    @Test
    public void testInvalidClassValidGroupRuleWithValidGroupKey() {

        validateExceptionRule(ValidGroupRuleWithValidGroupKey.class);
    }
}
