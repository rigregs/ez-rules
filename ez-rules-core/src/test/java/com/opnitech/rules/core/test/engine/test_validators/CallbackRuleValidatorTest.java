package com.opnitech.rules.core.test.engine.test_validators;

import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_validators.callback.ValidCallback;
import com.opnitech.rules.core.test.engine.test_validators.rules.callback.FullInvalidCallbackRule;
import com.opnitech.rules.core.test.engine.test_validators.rules.callback.PartialThenInvalidCallbackRule;
import com.opnitech.rules.core.test.engine.test_validators.rules.callback.PartialWhenInvalidCallbackRule;
import com.opnitech.rules.core.test.engine.test_validators.rules.callback.ValidCallbackRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class CallbackRuleValidatorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testValidCallbackRule() throws Exception {

        validateCallback(new ValidCallbackRule(), new Class<?>[]
            {
                ValidCallback.class
            }, new Class<?>[]
            {
                ValidCallback.class
            });
    }

    @Test
    public void testFullInvalidCallbackRule() throws Exception {

        validateExceptionRule(FullInvalidCallbackRule.class);
    }

    @Test
    public void testPartialWhenInvalidCallbackRule() throws Exception {

        validateExceptionRule(PartialWhenInvalidCallbackRule.class);
    }

    @Test
    public void testPartialThenInvalidCallbackRule() throws Exception {

        validateExceptionRule(PartialThenInvalidCallbackRule.class);
    }
}
