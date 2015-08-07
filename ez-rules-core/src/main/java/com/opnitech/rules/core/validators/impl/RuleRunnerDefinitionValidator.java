package com.opnitech.rules.core.validators.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.opnitech.rules.core.annotations.group.Group;
import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.annotations.rule.RulePriority;
import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;
import com.opnitech.rules.core.validators.RunnerDefinitionValidator;

/**
 * Allow to validate all the constrains that a Rule must have
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class RuleRunnerDefinitionValidator implements RunnerDefinitionConditionValidator, RunnerDefinitionValidator {

    public RuleRunnerDefinitionValidator() {
        // Default constructor
    }

    /*
     * (non-Javadoc)
     * @see ca.cn.servicedelivery.carshipment.business.rules.validators.
     * ExecutableDefinitionConditionValidator#accept(java.lang.Object)
     */
    @Override
    public boolean acceptRunner(Object executable) throws Exception {

        return AnnotationUtil.isAnnotationPresent(executable, Rule.class);
    }

    /*
     * (non-Javadoc)
     * @see ca.cn.servicedelivery.carshipment.business.rules.validators.
     * ExecutableDefinitionValidator#validate(java.lang.Object)
     */
    @Override
    public void validate(Object executable) throws Exception {

        checkValidWhenMethod(executable);
        checkValidThenMethod(executable);
        checkValidPriorityMethod(executable);
        checkValidGroupMethod(executable);
    }

    private void checkValidGroupMethod(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, Group.class);

        checkValidMethodCountWithQualifierAnnotation(Group.class, executable, methods);

        if (CollectionUtils.isNotEmpty(methods)) {
            Method groupMethod = methods.get(0);

            checkValidMethodResultValue(executable, groupMethod, Class.class);
            checkValidMethodWithZeroParameters(executable, groupMethod);
            checkValidGroupAnnotation(executable, groupMethod);
        }
    }

    private void checkValidGroupAnnotation(Object executable, Method groupMethod) throws Exception {

        Group group = AnnotationUtil.resolveAnnotation(groupMethod, Group.class);
        if (group.groupKey() != null) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid group definition method ''{0}'' in rule {1}. A group method should not define the group property",
                    groupMethod.getName(), executable);
        }
    }

    private void checkValidPriorityMethod(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, RulePriority.class);

        checkValidMethodCountWithQualifierAnnotation(RulePriority.class, executable, methods);

        if (CollectionUtils.isNotEmpty(methods)) {
            Method priorityMethod = methods.get(0);
            checkValidMethodResultValue(executable, priorityMethod, Integer.TYPE);
            checkValidMethodWithZeroParameters(executable, priorityMethod);
        }
    }

    private void checkValidMethodWithZeroParameters(Object executable, Method priorityMethod) {

        if (ArrayUtils.isNotEmpty(priorityMethod.getParameterTypes())) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A priority method cannot have parameters, please check the method signature. Rule: {0}", executable);
        }
    }

    private void checkValidMethodCountWithQualifierAnnotation(Class<? extends Annotation> annotationClass, Object executable,
            List<Method> methods) {

        if (CollectionUtils.isNotEmpty(methods) && methods.size() != 1) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A rule can have 0 or 1 method annotated with a ''{0}'' annotation, please check the method signature. Rule: {1}",
                    annotationClass, executable);
        }
    }

    private void checkValidThenMethod(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, Then.class);

        checkValidThenMethodCount(executable, methods);
        checkValidMethodResultValue(executable, methods.get(0), Void.TYPE);
    }

    private void checkValidThenMethodCount(Object executable, List<Method> methods) {

        if (CollectionUtils.isEmpty(methods)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A rule must have at least one method annotated with a 'Then' annotation, please check the method signature. Rule: {0}",
                    executable);
        }
    }

    private void checkValidWhenMethod(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, When.class);

        checkValidWhenMethodCount(executable, methods);
        checkValidMethodResultValue(executable, methods.get(0), WhenEnum.class, Boolean.class, boolean.class);
    }

    private void checkValidMethodResultValue(Object executable, Method method, Class<?>... expectedResultValues) {

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

    private void checkValidWhenMethodCount(Object executable, List<Method> methods) {

        if (CollectionUtils.isEmpty(methods) || methods.size() > 1) {
            ExceptionUtil.throwIllegalArgumentException(
                    "A rule must have one method annotated with a 'When' annotation, please check the method signature. Rule: {0}",
                    executable);
        }
    }
}
