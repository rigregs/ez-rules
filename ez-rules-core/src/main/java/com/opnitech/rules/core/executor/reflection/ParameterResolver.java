package com.opnitech.rules.core.executor.reflection;

/**
 * Allow to resolve a parameter value for a method execution
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface ParameterResolver {

    /**
     * Allow to resolve a parameter value for a method execution
     * 
     * @param methodParameterMetadata
     *            Metadata of the parameter that need to be resolved
     * @return The value of the parameter
     * @throws Exception
     *             An error happen resolving the parameter
     */
    Object resolveParameter(ParameterMetadata methodParameterMetadata) throws Exception;
}
