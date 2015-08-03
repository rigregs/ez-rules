package com.opnitech.rules.core.validators.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.opnitech.rules.core.annotations.rule.Rule;
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
