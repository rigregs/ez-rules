package com.opnitech.rules.core.validators.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.rule.Priority;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractValidator {

    public AbstractValidator() {
        // Default constructor
    }

    protected void checkUniqueExecutor(List<Object> candidateExecutables, Class<? extends Annotation> annotationClass,
            Object executable) {

        if (candidateExecutables.contains(executable)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid ''{0}''. Yuo are trying to register double executer. Executer: ''{1}''", annotationClass,
                    executable);
        }
    }

    protected void checkValidMethodWithZeroParameters(Object executable, Method method) {

        if (ArrayUtils.isNotEmpty(method.getParameterTypes())) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A priority method cannot have parameters, please check the method signature. Rule: ''{0}''", executable);
        }
    }

    protected void checkValidMethodCountWithQualifierAnnotation(Class<? extends Annotation> annotationClass, Object executable,
            List<Method> methods) {

        if (CollectionUtils.isNotEmpty(methods) && methods.size() != 1) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A rule can have 0 or 1 method annotated with a ''{0}'' annotation, please check the method signature. Rule: ''{1}''",
                    annotationClass, executable);
        }
    }

    protected void checkValidMethodResultValue(Object executable, Method method, Class<?>... expectedResultValues) {

        Class<?> returnType = method.getReturnType();

        Class<?> assignable = findAssignable(returnType, expectedResultValues);

        if (assignable == null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Return type of a condition method inside a rule should be one of those types: ''{0}''. Rule: ''{1}''. Current result type: ''{2}''. Method: ''{3}''",
                    generateExpectedResultValues(expectedResultValues), executable, returnType, method.getName());
        }
    }

    protected void checkGroupKeyMethods(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, GroupKey.class);

        checkValidMethodCountWithQualifierAnnotation(GroupKey.class, executable, methods);
        checkGroupKeyMethod(executable, methods);
    }

    protected void checkValidPriorityMethod(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, Priority.class);

        checkValidMethodCountWithQualifierAnnotation(Priority.class, executable, methods);

        if (CollectionUtils.isNotEmpty(methods)) {
            Method priorityMethod = methods.get(0);
            checkValidMethodResultValue(executable, priorityMethod, Integer.TYPE);
            checkValidMethodWithZeroParameters(executable, priorityMethod);
            checkExecutableType(executable, priorityMethod, Priority.class);
        }
    }

    private void checkGroupKeyMethod(Object executable, List<Method> methods) {

        if (CollectionUtils.isNotEmpty(methods)) {
            Method groupKeyMethod = methods.get(0);

            checkValidMethodResultValue(executable, groupKeyMethod, String.class);
            checkValidMethodWithZeroParameters(executable, groupKeyMethod);

            checkExecutableType(executable, groupKeyMethod, GroupKey.class);
        }
    }

    private void checkExecutableType(Object executable, Method method, Class<? extends Annotation> annotationClass) {

        if (Class.class.isAssignableFrom(executable.getClass())) {
            ExceptionUtil.throwIllegalArgumentException(
                    "You cannot register a 'Executable' as a class that contain a method with the ''{0}'' annotation. You need to register the definition as instance executable. 'Executable': ''{1}'', Method: ''{2}''",
                    annotationClass, executable, method.getName());
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