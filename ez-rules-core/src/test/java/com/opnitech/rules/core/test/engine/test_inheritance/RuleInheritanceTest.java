package com.opnitech.rules.core.test.engine.test_inheritance;

import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_inheritance.rules.InvalidChildRuleWithoutAnnotation;
import com.opnitech.rules.core.test.engine.test_inheritance.rules.ParentRule;
import com.opnitech.rules.core.test.engine.test_inheritance.rules.ValidChildRuleWithAnnotation;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleInheritanceTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testParentRule() {

        validateRule(ParentRule.class);
    }

    @Test
    public void testInvalidChildRuleWithoutAnnotation() {

        validateExceptionRule(InvalidChildRuleWithoutAnnotation.class);
    }

    @Test
    public void testValidChildRuleWithAnnotation() {

        validateRule(ValidChildRuleWithAnnotation.class);
    }
}
