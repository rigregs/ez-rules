package com.opnitech.rules.core.validators.impl;

import com.opnitech.rules.core.annotations.rule.Callback;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;

/**
 * Allow to validate all the constrains that a Callback must have
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class CallbackRunnerDefinitionValidator implements RunnerDefinitionConditionValidator {

    public CallbackRunnerDefinitionValidator() {
        // Default constructor
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator#
     * acceptRunner(java.lang.Object)
     */
    @Override
    public boolean acceptRunner(Object executable) throws Exception {

        return AnnotationUtil.isAnnotationPresent(executable, Callback.class);
    }
}
