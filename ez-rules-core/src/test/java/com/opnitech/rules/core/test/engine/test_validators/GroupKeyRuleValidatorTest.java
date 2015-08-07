package com.opnitech.rules.core.test.engine.test_validators;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_validators.group.InvalidKeyResultGroupKeyGroupDefinition;
import com.opnitech.rules.core.test.engine.test_validators.group.InvalidKeyWithParameterGroupKeyGroupDefinition;
import com.opnitech.rules.core.test.engine.test_validators.group.ValidGroupDefinition;
import com.opnitech.rules.core.test.engine.test_validators.group.ValidGroupKeyGroupDefinition;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupKeyRuleValidatorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testValidGroupDefinition() throws EngineException {

        validateRule(ValidGroupDefinition.class);
    }

    @Test
    public void testDuplicateValidGroupDefinition() throws EngineException {

        validateExceptionRule(ValidGroupDefinition.class, ValidGroupDefinition.class);
    }

    @Test
    public void testValidGroupKeyGroupDefinition() throws Exception {

        validateRule(new ValidGroupKeyGroupDefinition("TEST"));
    }

    @Test
    public void testInvalidGroupKeyGroupDefinitionBlankResult() throws Exception {

        validateExceptionRule(new ValidGroupKeyGroupDefinition(" "));
    }

    @Test
    public void testInvalidGroupKeyGroupDefinitionEmptyResult() throws Exception {

        validateExceptionRule(new ValidGroupKeyGroupDefinition(StringUtils.EMPTY));
    }

    @Test
    public void testInvalidGroupKeyGroupDefinitionDuplicateResult() throws Exception {

        validateExceptionRule(new ValidGroupKeyGroupDefinition("TEST"), new ValidGroupKeyGroupDefinition("TEST"));
    }

    @Test
    public void testInvalidGroupKeyGroupDefinitionNullResult() throws Exception {

        validateExceptionRule(new ValidGroupKeyGroupDefinition(null));
    }

    @Test
    public void testInvalidCallToValidGroupKeyGroupDefinition() throws Exception {

        validateExceptionRule(ValidGroupKeyGroupDefinition.class);
    }

    @Test
    public void testInvalidKeyWithParameterGroupKeyGroupDefinition() throws Exception {

        validateExceptionRule(new InvalidKeyWithParameterGroupKeyGroupDefinition());
    }

    @Test
    public void testInvalidKeyResultGroupKeyGroupDefinition() throws Exception {

        validateExceptionRule(new InvalidKeyResultGroupKeyGroupDefinition());
    }
}
