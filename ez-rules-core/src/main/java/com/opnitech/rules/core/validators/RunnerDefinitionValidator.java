package com.opnitech.rules.core.validators;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface RunnerDefinitionValidator {

    /**
     * Allow to define a common interface for a executable validation
     * 
     * @param executable
     *            Executable to validate
     * @throws Exception
     *             Throw an exception if the executable isn't valid
     */
    void validate(Object executable) throws Exception;
}
