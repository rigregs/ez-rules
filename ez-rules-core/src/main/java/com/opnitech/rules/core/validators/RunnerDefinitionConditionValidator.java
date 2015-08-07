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
     *            Executable to be checked
     * @return Return if the executable has been accepted
     * @throws Exception
     *             Exception could happen during the validation
     */
    boolean acceptRunner(Object executable) throws Exception;
}
