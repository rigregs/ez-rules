package com.opnitech.rules.core.test.utils;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.utils.annotation.CorrectAnnotatedRule;
import com.opnitech.rules.core.utils.AnnotationValidatorUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ValidateAnnotatedMethodsAnnotationValidatorTest {

    @Test
    public void testCorrectWhenAnnotationCount() throws Exception {

        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, 0, 1, true, WhenEnum.class,
                Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, 1, 1, true, WhenEnum.class,
                Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, Integer.MIN_VALUE, 1, true,
                WhenEnum.class, Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, 1, Integer.MAX_VALUE, true,
                WhenEnum.class, Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, Integer.MIN_VALUE,
                Integer.MAX_VALUE, true, WhenEnum.class, Boolean.class, boolean.class);
    }

    @Test
    public void testRangeCorrectAnnotationInvalidMin() throws Exception {

        validateException("minCount>=2", CorrectAnnotatedRule.class, When.class, 2, 3, true, WhenEnum.class, Boolean.class,
                boolean.class);
    }

    @Test
    public void testRangeCorrectAnnotationInvalidMax() throws Exception {

        validateException("maxCount<=0", CorrectAnnotatedRule.class, When.class, 0, 0, true, WhenEnum.class, Boolean.class,
                boolean.class);
    }

    @Test
    public void testRangeCorrectAnnotationInvalidInterval() throws Exception {

        validateException("minExpectedMethodCount<=maxExpectedMethodCount", CorrectAnnotatedRule.class, When.class, 1, 0, true,
                WhenEnum.class, Boolean.class, boolean.class);
    }

    @Test
    public void testParameterCorrectAnnotationValidIntervalParamCount() throws Exception {

        validateException("minExpectedMethodCount<=maxExpectedMethodCount", CorrectAnnotatedRule.class, When.class, 1, 0, true,
                WhenEnum.class, Boolean.class, boolean.class);
        validateException("minExpectedMethodCount<=maxExpectedMethodCount", CorrectAnnotatedRule.class, Then.class, 1, 0, false,
                WhenEnum.class, Boolean.class, boolean.class);
    }

    @Test
    public void testParameterCorrectAnnotationInvalidIntervalParamCount() throws Exception {

        validateException("parameterCount=0", CorrectAnnotatedRule.class, When.class, 0, 1, false, WhenEnum.class, Boolean.class,
                boolean.class);
    }

    @Test
    public void testResultTypeCorrectAnnotationInvalidResult() throws Exception {

        validateException("returnType not found", CorrectAnnotatedRule.class, When.class, 0, 1, true, Boolean.class,
                boolean.class);
    }

    private void validateException(String expectedExceptionContent, Object possibleAnnotated,
            Class<? extends Annotation> annotationClass, int minExpectedMethodCount, int maxExpectedMethodCount,
            boolean allowParameters, Class<?>... possibleReturnTypes) throws Exception {

        try {
            AnnotationValidatorUtil.validateAnnotatedMethods(possibleAnnotated, annotationClass, minExpectedMethodCount,
                    maxExpectedMethodCount, allowParameters, possibleReturnTypes);
            Assert.fail();
        }
        catch (IllegalArgumentException exception) {
            Assert.assertTrue(MessageFormat.format("Cannot find ''{0}'' in the error message ''{1}''.", expectedExceptionContent,
                    exception.getMessage()), exception.getMessage().contains(expectedExceptionContent));
        }
    }
}
