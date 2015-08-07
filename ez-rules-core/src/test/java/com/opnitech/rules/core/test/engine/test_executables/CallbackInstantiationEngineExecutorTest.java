package com.opnitech.rules.core.test.engine.test_executables;

import org.junit.Test;

import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_executables.callback.SimpleCallback1;
import com.opnitech.rules.core.test.engine.test_executables.callback.SimpleCallback2;
import com.opnitech.rules.core.test.engine.test_executables.rules.callback.FullCallbackRule;
import com.opnitech.rules.core.test.engine.test_executables.rules.callback.FullCallbackWithMissingAnnotationRule;
import com.opnitech.rules.core.test.engine.test_executables.rules.callback.FullCallbackWithPartialAnnotationRule;
import com.opnitech.rules.core.test.engine.test_executables.rules.callback.NoCallbackRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class CallbackInstantiationEngineExecutorTest extends AbstractRuleEngineExecutorTest {

    @Test
    public void testNoCallbackRule() throws Exception {

        validateCallback(new NoCallbackRule(), null, null);
    }

    @Test
    public void testFullCallbackRule() throws Exception {

        validateCallback(new FullCallbackRule(), new Class<?>[]
            {
                SimpleCallback1.class,
                SimpleCallback2.class
            }, new Class<?>[]
            {
                SimpleCallback1.class,
                SimpleCallback2.class
            });
    }

    @Test
    public void testFullCallbackWithMissingAnnotationRule() throws Exception {

        validateCallback(new FullCallbackWithMissingAnnotationRule(), null, null);
    }

    @Test
    public void testFullCallbackWithPartialAnnotationRule() throws Exception {

        validateCallback(new FullCallbackWithPartialAnnotationRule(), new Class<?>[]
            {
                SimpleCallback1.class
            }, new Class<?>[]
            {
                SimpleCallback2.class
            });
    }
}
