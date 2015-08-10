package com.opnitech.rules.core.test.utils;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.test.engine.test_workflow.callback.TestValidCallback;
import com.opnitech.rules.core.test.utils.callback.TestInvalidCallback;
import com.opnitech.rules.core.utils.AnnotationValidatorUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AnnotationValidatorUtilTest {

    @Test
    public void testWithListValid() throws Exception {

        AnnotationValidatorUtil.validateAnnotationPresent(Arrays.asList(new Class<?>[]
            {
                TestValidCallback.class
            }), Callback.class);
    }

    @Test
    public void testWithListInvalid() throws Exception {

        try {
            AnnotationValidatorUtil.validateAnnotationPresent(Arrays.asList(new Class<?>[]
                {
                    TestInvalidCallback.class
                }), Callback.class);

            Assert.fail();
        }
        catch (@SuppressWarnings("unused")
        IllegalArgumentException exception) {
            // We're good
        }
    }

    @Test
    public void testWithListCombined() throws Exception {

        try {
            AnnotationValidatorUtil.validateAnnotationPresent(Arrays.asList(new Class<?>[]
                {
                    TestValidCallback.class,
                    TestInvalidCallback.class
                }), Callback.class);
            Assert.fail();
        }
        catch (@SuppressWarnings("unused")
        IllegalArgumentException exception) {
            // We're good
        }
    }
}
