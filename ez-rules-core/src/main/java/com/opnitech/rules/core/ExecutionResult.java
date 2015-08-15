package com.opnitech.rules.core;

import java.io.Serializable;

/**
 * Return the final status execution of the rule engine
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class ExecutionResult<ResultType> implements Serializable {

    private static final long serialVersionUID = 3201692196289859060L;

    private final boolean success;

    private final EngineException exception;

    private final ResultType result;

    public ExecutionResult(boolean success, EngineException exception, ResultType result) {

        this.success = success;
        this.exception = exception;
        this.result = result;
    }

    /**
     * Return the final result of the operation, if no exception occur will
     * return true
     * 
     * @return Return the status of the rule execution
     */
    public boolean isSuccess() {

        return this.success;
    }

    /**
     * Return the exception cause the process to stop.
     * 
     * @return The exception occur during the execution
     */
    public EngineException getException() {

        return this.exception;
    }

    /**
     * @return The result of the rule engine
     */
    public ResultType getResult() {

        return this.result;
    }
}
