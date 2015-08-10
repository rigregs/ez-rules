package com.opnitech.rules.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class AnnotationValidatorUtil {

    private AnnotationValidatorUtil() {
        // Default constructor
    }

    /**
     * Validate that all elements in the list have the same annotation type
     * 
     * @param possibleAnnotatedList
     *            List of possible annotated instances to validate
     * @param annotationClass
     *            Annotation to validate
     * @throws Exception
     *             An exception if the validation fail
     */
    public static void validateAnnotationPresent(List<? extends Object> possibleAnnotatedList,
            Class<? extends Annotation> annotationClass) throws Exception {

        if (CollectionUtils.isNotEmpty(possibleAnnotatedList)) {
            for (Object possibleAnnotated : possibleAnnotatedList) {
                AnnotationValidatorUtil.validateAnnotationPresent(possibleAnnotated, annotationClass);
            }
        }
    }

    /**
     * Validate that the element have an specific annotation
     * 
     * @param possibleAnnotated
     *            Possible annotate instance
     * @param annotationClass
     *            Annotation to validate
     * @throws Exception
     *             An exception if the validation fail
     */
    public static void validateAnnotationPresent(Object possibleAnnotated, Class<? extends Annotation> annotationClass)
            throws Exception {

        Validate.isTrue(AnnotationUtil.isAnnotationPresent(possibleAnnotated, annotationClass));
    }

    /**
     * @param possibleAnnotated
     *            Possible annotate instance
     * @param annotationClass
     *            Annotation to validate
     * @param minExpectedMethodCount
     *            Minimum expected number of occurrences of the method
     * @param maxExpectedMethodCount
     *            Maximum expected number of occurrences of the method
     * @param possibleReturnTypes
     *            Possible return types allowed for the method
     * @throws Exception
     *             An exception if the validation fail
     */
    public static void validateAnnotatedMethods(Object possibleAnnotated, Class<? extends Annotation> annotationClass,
            int minExpectedMethodCount, int maxExpectedMethodCount, Class<?>... possibleReturnTypes) throws Exception {

        Validate.notNull(possibleAnnotated);
        Validate.notNull(annotationClass);
        Validate.isTrue(ArrayUtils.isNotEmpty(possibleReturnTypes));
        Validate.isTrue(minExpectedMethodCount >= 0);
        Validate.isTrue(maxExpectedMethodCount >= 0);
        Validate.isTrue(
                minExpectedMethodCount == Integer.MAX_VALUE || maxExpectedMethodCount == Integer.MAX_VALUE
                        || minExpectedMethodCount <= maxExpectedMethodCount,
                buildMessage(possibleAnnotated, annotationClass, "minExpectedMethodCount<=maxExpectedMethodCount",
                        new StringBuffer(" (").append(minExpectedMethodCount).append("<=").append(maxExpectedMethodCount)
                                .append("").toString()));

        List<Method> resolveMethodsWithAnnotation = AnnotationUtil.resolveMethodsWithAnnotation(possibleAnnotated,
                annotationClass);

        validateMethosCount(possibleAnnotated, annotationClass, minExpectedMethodCount, maxExpectedMethodCount,
                resolveMethodsWithAnnotation);
    }

    private static void validateMethosCount(Object possibleAnnotated, Class<? extends Annotation> annotationClass,
            int minExpectedMethodCount, int maxExpectedMethodCount, List<Method> resolveMethodsWithAnnotation) {

        Validate.isTrue(
                minExpectedMethodCount == Integer.MAX_VALUE || resolveMethodsWithAnnotation.size() >= minExpectedMethodCount,
                buildMessage(possibleAnnotated, annotationClass, "minCount>=", Integer.toString(minExpectedMethodCount)));

        Validate.isTrue(
                maxExpectedMethodCount == Integer.MAX_VALUE || resolveMethodsWithAnnotation.size() <= maxExpectedMethodCount,
                buildMessage(possibleAnnotated, annotationClass, "maxCount<=", Integer.toString(maxExpectedMethodCount)));
    }

    private static String buildMessage(Object possibleAnnotated, Class<? extends Annotation> annotationClass, String criteria,
            String criteriaValue) {

        return MessageFormat.format(
                "Annotated Method Validation Fail. Criteria:''{0}{1}'', Annotated Class:''{2}'', Annotation:''{3}''", criteria,
                criteriaValue, possibleAnnotated, annotationClass);
    }
}
