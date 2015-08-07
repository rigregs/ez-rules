package com.opnitech.rules.core.validators.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.opnitech.rules.core.utils.ExceptionUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractValidator {

    public AbstractValidator() {
        // Default constructor
    }

    protected void checkValidMethodWithZeroParameters(Object executable, Method method) {

        if (ArrayUtils.isNotEmpty(method.getParameterTypes())) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A priority method cannot have parameters, please check the method signature. Rule: {0}", executable);
        }
    }

    protected void checkValidMethodCountWithQualifierAnnotation(Class<? extends Annotation> annotationClass, Object executable,
            List<Method> methods) {

        if (CollectionUtils.isNotEmpty(methods) && methods.size() != 1) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A rule can have 0 or 1 method annotated with a ''{0}'' annotation, please check the method signature. Rule: {1}",
                    annotationClass, executable);
        }
    }

    protected void checkValidMethodResultValue(Object executable, Method method, Class<?>... expectedResultValues) {

        Class<?> returnType = method.getReturnType();

        Class<?> assignable = findAssignable(returnType, expectedResultValues);

        if (assignable == null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Return type of a condition method inside a rule should be one of those types: ''{0}''. Rule: {1}. Current result type: {2}. Method: {3}",
                    generateExpectedResultValues(expectedResultValues), executable, returnType, method.getName());
        }
    }

    private String generateExpectedResultValues(Class<?>[] expectedResultValues) {

        StringBuffer buffer = new StringBuffer();

        boolean firstTime = true;
        for (Class<?> clazz : expectedResultValues) {
            if (!firstTime) {
                buffer.append(", ");
            }

            buffer.append(clazz.getName());

            firstTime = false;
        }

        return buffer.toString();
    }

    private Class<?> findAssignable(Class<?> returnType, Class<?>[] expectedResultValues) {

        for (Class<?> expectedResultValue : expectedResultValues) {
            if (expectedResultValue.isAssignableFrom(returnType)) {
                return expectedResultValue;
            }
        }

        return null;
    }
}
