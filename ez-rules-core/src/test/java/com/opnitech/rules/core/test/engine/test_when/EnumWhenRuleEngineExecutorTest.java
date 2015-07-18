package com.opnitech.rules.core.test.engine.test_when;

import org.junit.Test;

import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_when.rules.TestEnumWhenRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class EnumWhenRuleEngineExecutorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testBooleanPrimitiveWhenRule() throws Exception {

        validateRule(new TestEnumWhenRule(WhenEnum.ACCEPT));
    }

    @Test
    public void testBooleanPrimitiveWhenFalseRule() throws Exception {

        validateWithPreconditionsRule(true, false, new TestEnumWhenRule(WhenEnum.REJECT));
    }
}
