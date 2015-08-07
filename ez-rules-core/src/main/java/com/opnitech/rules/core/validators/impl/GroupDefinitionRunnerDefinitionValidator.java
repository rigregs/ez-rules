package com.opnitech.rules.core.validators.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;
import com.opnitech.rules.core.validators.RunnerDefinitionValidator;

/**
 * Allow to validate all the constrains that a Group Definition must have
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupDefinitionRunnerDefinitionValidator extends AbstractValidator
        implements RunnerDefinitionConditionValidator, RunnerDefinitionValidator {

    public GroupDefinitionRunnerDefinitionValidator() {
        // Default constructor
    }

    /*
     * (non-Javadoc)
     * @see ca.cn.servicedelivery.carshipment.business.rules.validators.
     * ExecutableDefinitionConditionValidator#accept(java.lang.Object)
     */
    @Override
    public boolean acceptRunner(Object executable) throws Exception {

        return AnnotationUtil.isAnnotationPresent(executable, GroupDefinition.class);
    }

    @Override
    public void validate(List<Object> candidateExecutables, Object executable) throws Exception {

        validateUniqueGroupDefinition(candidateExecutables, executable);
        validateGroupKeyMethods(executable);
    }

    private void validateUniqueGroupDefinition(List<Object> candidateExecutables, Object executable) {

        if (candidateExecutables.contains(executable)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid Group Definition. Yuo are trying to register double a group defintion. Group Definition: {0}, Method: {1}",
                    executable);
        }
    }

    private void validateGroupKeyMethods(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, GroupKey.class);

        checkValidMethodCountWithQualifierAnnotation(GroupKey.class, executable, methods);
        validateGroupKeyMethod(executable, methods);
    }

    private void validateGroupKeyMethod(Object executable, List<Method> methods) {

        if (CollectionUtils.isNotEmpty(methods)) {
            Method groupKeyMethod = methods.get(0);

            checkValidMethodResultValue(executable, groupKeyMethod, String.class);
            checkValidMethodWithZeroParameters(executable, groupKeyMethod);

            if (Class.class.isAssignableFrom(executable.getClass())) {
                ExceptionUtil.throwIllegalArgumentException(
                        "Invalid Group Definition. You cannot register a Group Definition as a class that contain a method with the GroupKey annotation. You need to register the definition as instance executable. Group Definition: {0}, Method: {1}",
                        executable, groupKeyMethod.getName());
            }
        }
    }
}
