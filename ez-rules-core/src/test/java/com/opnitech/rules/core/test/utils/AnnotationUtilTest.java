package com.opnitech.rules.core.test.utils;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.test.engine.test_workflow.callback.TestValidCallback;
import com.opnitech.rules.core.test.utils.callback.TestInvalidCallback;
import com.opnitech.rules.core.utils.AnnotationUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AnnotationUtilTest {

    @Test
    public void testWithValueValid() throws Exception {

        Assert.assertTrue(AnnotationUtil.isAnnotationPresent(new TestValidCallback(), Callback.class));
        Assert.assertTrue(AnnotationUtil.isAnnotationPresent(TestValidCallback.class, Callback.class));
    }

    @Test
    public void testWithValueInvalid() throws Exception {

        Assert.assertFalse(AnnotationUtil.isAnnotationPresent(new TestInvalidCallback(), Callback.class));
        Assert.assertFalse(AnnotationUtil.isAnnotationPresent(TestInvalidCallback.class, Callback.class));
    }
}
