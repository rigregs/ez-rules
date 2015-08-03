package com.opnitech.rules.core.validators.impl;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;

/**
 * Allow to validate all the constrains that a Group Definition must have
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class GroupDefinitionRunnerDefinitionValidator implements RunnerDefinitionConditionValidator {

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
}
