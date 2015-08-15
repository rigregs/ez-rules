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
     * @param allowParameters
     *            Check if the method can have parameters
     * @param possibleReturnTypes
     *            Possible return types allowed for the method
     * @throws Exception
     *             An exception if the validation fail
     */
    public static void validateAnnotatedMethods(Object possibleAnnotated, Class<? extends Annotation> annotationClass,
            int minExpectedMethodCount, int maxExpectedMethodCount, boolean allowParameters, Class<?>... possibleReturnTypes)
                    throws Exception {

        Validate.notNull(possibleAnnotated);
        Validate.notNull(annotationClass);
        Validate.isTrue(minExpectedMethodCount == Integer.MIN_VALUE || minExpectedMethodCount >= 0);
        Validate.isTrue(maxExpectedMethodCount == Integer.MAX_VALUE || maxExpectedMethodCount >= 0);

        validateMinMaxValues(possibleAnnotated, annotationClass, minExpectedMethodCount, maxExpectedMethodCount);

        List<Method> methodsWithAnnotation = AnnotationUtil.resolveMethodsWithAnnotation(possibleAnnotated, annotationClass);

        validateMethodsCount(possibleAnnotated, annotationClass, minExpectedMethodCount, maxExpectedMethodCount,
                methodsWithAnnotation);

        if (CollectionUtils.isNotEmpty(methodsWithAnnotation)) {
            validateMethodsParameters(possibleAnnotated, annotationClass, methodsWithAnnotation, allowParameters);
            validateMethodResultValue(possibleAnnotated, annotationClass, methodsWithAnnotation, possibleReturnTypes);
        }
    }

    private static void validateMethodResultValue(Object possibleAnnotated, Class<? extends Annotation> annotationClass,
            List<Method> methodsWithAnnotation, Class<?>... possibleReturnTypes) {

        if (ArrayUtils.isNotEmpty(possibleReturnTypes)) {
            for (Method method : methodsWithAnnotation) {
                Class<?> returnType = method.getReturnType();

                Class<?> assignable = findAssignableClass(returnType, possibleReturnTypes);
                if (assignable == null) {
                    ExceptionUtil.throwIllegalArgumentException(buildMessage(possibleAnnotated, annotationClass,
                            "returnType not found in ", ArrayUtils.toString(possibleReturnTypes)));
                }
            }
        }
    }

    private static Class<?> findAssignableClass(Class<?> returnType, Class<?>... expectedResultValues) {

        for (Class<?> expectedResultValue : expectedResultValues) {
            if (expectedResultValue.isAssignableFrom(returnType)) {
                return expectedResultValue;
            }
        }

        return null;
    }

    private static void validateMethodsParameters(Object possibleAnnotated, Class<? extends Annotation> annotationClass,
            List<Method> methodsWithAnnotation, boolean allowParameters) {

        if (!allowParameters) {
            for (Method method : methodsWithAnnotation) {

                Class<?>[] parameterTypes = method.getParameterTypes();
                if (ArrayUtils.isNotEmpty(parameterTypes)) {
                    ExceptionUtil.throwIllegalArgumentException(buildMessage(possibleAnnotated, annotationClass,
                            "parameterCount=0", buildCondition(0, parameterTypes.length, "=")));
                }
            }
        }
    }

    private static void validateMethodsCount(Object possibleAnnotated, Class<? extends Annotation> annotationClass,
            int minExpectedMethodCount, int maxExpectedMethodCount, List<Method> methodsWithAnnotation) {

        if (!(minExpectedMethodCount == Integer.MIN_VALUE || methodsWithAnnotation.size() >= minExpectedMethodCount)) {
            ExceptionUtil.throwIllegalArgumentException(
                    buildMessage(possibleAnnotated, annotationClass, "minCount>=", Integer.toString(minExpectedMethodCount)));
        }

        if (!(maxExpectedMethodCount == Integer.MAX_VALUE || methodsWithAnnotation.size() <= maxExpectedMethodCount)) {
            ExceptionUtil.throwIllegalArgumentException(
                    buildMessage(possibleAnnotated, annotationClass, "maxCount<=", Integer.toString(maxExpectedMethodCount)));
        }
    }

    private static void validateMinMaxValues(Object possibleAnnotated, Class<? extends Annotation> annotationClass,
            int minExpectedMethodCount, int maxExpectedMethodCount) {

        if (!(minExpectedMethodCount == Integer.MIN_VALUE || maxExpectedMethodCount == Integer.MAX_VALUE
                || minExpectedMethodCount <= maxExpectedMethodCount)) {

            ExceptionUtil.throwIllegalArgumentException(
                    buildMessage(possibleAnnotated, annotationClass, "minExpectedMethodCount<=maxExpectedMethodCount",
                            buildCondition(minExpectedMethodCount, maxExpectedMethodCount, "<=")));
        }
    }

    private static String buildCondition(Object expectedValue, Object realValue, String operation) {

        return new StringBuffer(" (").append(expectedValue).append(operation).append(realValue).append(") ").toString();
    }

    private static String buildMessage(Object possibleAnnotated, Class<? extends Annotation> annotationClass, String criteria,
            String criteriaValue) {

        return MessageFormat.format(
                "Annotated Method Validation Fail. Criteria:''{0}{1}'', Annotated Class:''{2}'', Annotation:''{3}''", criteria,
                criteriaValue, possibleAnnotated, annotationClass);
    }
}
