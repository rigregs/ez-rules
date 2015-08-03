package com.opnitech.rules.core.test.utils;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.annotations.callback.Callback;
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

    @Test
    public void testWithListValid() throws Exception {

        AnnotationUtil.validateAnnotationPresent(Arrays.asList(new Class<?>[]
            {
                TestValidCallback.class
            }), Callback.class);
    }

    @Test
    public void testWithListInvalid() throws Exception {

        try {
            AnnotationUtil.validateAnnotationPresent(Arrays.asList(new Class<?>[]
                {
                    TestInvalidCallback.class
                }), Callback.class);

            Assert.fail();
        }
        catch (@SuppressWarnings("unused")
        IllegalArgumentException e) {
            // We're good
        }
    }

    @Test
    public void testWithListCombined() throws Exception {

        try {
            AnnotationUtil.validateAnnotationPresent(Arrays.asList(new Class<?>[]
                {
                    TestValidCallback.class,
                    TestInvalidCallback.class
                }), Callback.class);
            Assert.fail();
        }
        catch (@SuppressWarnings("unused")
        IllegalArgumentException e) {
            // We're good
        }
    }
}
