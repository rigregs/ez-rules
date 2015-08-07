package com.opnitech.rules.core.validators.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.utils.AnnotationUtil;
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
    public void validate(Object executable) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, GroupKey.class);

        checkValidMethodCountWithQualifierAnnotation(GroupKey.class, executable, methods);

        if (CollectionUtils.isNotEmpty(methods)) {
            Method groupMethod = methods.get(0);

            checkValidMethodResultValue(executable, groupMethod, String.class);
            checkValidMethodWithZeroParameters(executable, groupMethod);
        }
    }
}
