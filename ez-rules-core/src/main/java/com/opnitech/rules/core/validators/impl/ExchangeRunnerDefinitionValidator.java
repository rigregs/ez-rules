package com.opnitech.rules.core.validators.impl;

import com.opnitech.rules.core.annotations.rule.Exchange;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.validators.RunnerDefinitionConditionValidator;

/**
 * Allow to validate all the constrains that a Exchange must have
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ExchangeRunnerDefinitionValidator implements RunnerDefinitionConditionValidator {

    public ExchangeRunnerDefinitionValidator() {
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

        return AnnotationUtil.isAnnotationPresent(executable, Exchange.class);
    }
}
