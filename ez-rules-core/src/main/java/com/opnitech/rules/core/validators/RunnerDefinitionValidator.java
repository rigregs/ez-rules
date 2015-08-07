package com.opnitech.rules.core.validators;

import java.util.List;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface RunnerDefinitionValidator {

    /**
     * Allow to define a common interface for a executable validation
     * 
     * @param candidateExecutables
     *            List of executables already registered
     * @param executable
     *            Executable to validate
     * @throws Exception
     *             Throw an exception if the executable isn't valid
     */
    void validate(List<Object> candidateExecutables, Object executable) throws Exception;
}
