package com.opnitech.rules.core.test.engine.test_executables;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_executables.rules.instantiation.AbstractHierarchyInstantiationRule;
import com.opnitech.rules.core.test.engine.test_executables.rules.instantiation.SimpleHierarchyInstantiationRule;
import com.opnitech.rules.core.test.engine.test_executables.rules.instantiation.SimpleInstantiationRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleInstantiationEngineExecutorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testSimpleInstantiationRuleByClass() throws Exception {

        validateRule(SimpleInstantiationRule.class);
    }

    @Test
    public void testSimpleInstantiationRuleByClassName() throws Exception {

        validateRule(SimpleInstantiationRule.class.getName());
    }

    @Test
    public void testSimpleInstantiationRuleByInstance() throws Exception {

        validateRule(new SimpleInstantiationRule());
    }

    @Test
    public void testSimpleHierarchyInstantiationRuleByClass() throws Exception {

        // The class need to be annotated as Rule, we don;t search for the
        // annotation in the parents
        validateExceptionRule(SimpleHierarchyInstantiationRule.class);
    }

    @Test
    public void testAbstractHierarchyInstantiationRule() throws Exception {

        try {
            validateRule(AbstractHierarchyInstantiationRule.class);
            Assert.fail();
        }
        catch (Exception exception) {
            // We should be ok
        }
    }
}
