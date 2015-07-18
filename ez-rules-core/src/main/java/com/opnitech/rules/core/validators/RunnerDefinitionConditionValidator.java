package com.opnitech.rules.core.validators;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface RunnerDefinitionConditionValidator {

    /**
     * Define a common interface for executable validator, if true the validator
     * will be execute for an specific executable
     * 
     * @param executable
     * @return
     * @throws Exception
     */
    boolean acceptRunner(Object executable) throws Exception;
}
