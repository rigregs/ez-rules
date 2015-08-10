package com.opnitech.rules.core.test.utils;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.utils.annotation.CorrectAnnotatedRule;
import com.opnitech.rules.core.utils.AnnotationValidatorUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ValidateAnnotatedMethodsAnnotationValidatorTest {

    @Test
    public void testCorrectWhenAnnotation() throws Exception {

        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, 0, 1, WhenEnum.class,
                Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, 1, 1, WhenEnum.class,
                Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, Integer.MAX_VALUE, 1,
                WhenEnum.class, Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, 1, Integer.MAX_VALUE,
                WhenEnum.class, Boolean.class, boolean.class);
        AnnotationValidatorUtil.validateAnnotatedMethods(CorrectAnnotatedRule.class, When.class, Integer.MAX_VALUE,
                Integer.MAX_VALUE, WhenEnum.class, Boolean.class, boolean.class);
    }

    @Test
    public void testCorrectAnnotationInvalidMin() throws Exception {

        validfateException("minCount>=2", CorrectAnnotatedRule.class, When.class, 2, 3, WhenEnum.class, Boolean.class,
                boolean.class);
    }

    @Test
    public void testCorrectAnnotationInvalidMax() throws Exception {

        validfateException("maxCount<=0", CorrectAnnotatedRule.class, When.class, 0, 0, WhenEnum.class, Boolean.class,
                boolean.class);
    }

    @Test
    public void testCorrectAnnotationInvalidRange() throws Exception {

        validfateException("minExpectedMethodCount<=maxExpectedMethodCount", CorrectAnnotatedRule.class, When.class, 1, 0,
                WhenEnum.class, Boolean.class, boolean.class);
    }

    private void validfateException(String expectedExceptionContent, Object possibleAnnotated,
            Class<? extends Annotation> annotationClass, int minExpectedMethodCount, int maxExpectedMethodCount,
            Class<?>... possibleReturnTypes) throws Exception {

        try {
            AnnotationValidatorUtil.validateAnnotatedMethods(possibleAnnotated, annotationClass, minExpectedMethodCount,
                    maxExpectedMethodCount, possibleReturnTypes);
        }
        catch (IllegalArgumentException exception) {
            Assert.assertTrue(MessageFormat.format("Cannot find ''{0}'' in the error message ''{1}''.", expectedExceptionContent,
                    exception.getMessage()), exception.getMessage().contains(expectedExceptionContent));
        }
    }
}
