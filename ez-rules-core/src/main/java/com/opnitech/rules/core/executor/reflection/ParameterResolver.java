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
     * @return
     * @throws Exception
     */
    Object resolveParameter(ParameterMetadata methodParameterMetadata) throws Exception;
}
