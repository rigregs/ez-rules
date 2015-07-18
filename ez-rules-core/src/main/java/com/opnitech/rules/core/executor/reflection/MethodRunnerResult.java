package com.opnitech.rules.core.executor.reflection;

/**
 * Represent the result of one method execution
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class MethodRunnerResult<ResultType> {

    private final Object instance;

    private ResultType result;

    public MethodRunnerResult(Object instance) {

        this.instance = instance;
    }

    /**
     * Return of the executed method
     * 
     * @return
     */
    public ResultType getResult() {

        return this.result;
    }

    /**
     * Set result of the implemented method
     * 
     * @param result
     */
    public void setResult(ResultType result) {

        this.result = result;
    }

    /**
     * Original Object instance
     * 
     * @return
     */
    public Object getInstance() {

        return this.instance;
    }
}
