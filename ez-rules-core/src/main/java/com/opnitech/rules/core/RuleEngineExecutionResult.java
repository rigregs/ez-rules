package com.opnitech.rules.core;

import java.io.Serializable;

/**
 * Return the final status execution of the rule engine
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class RuleEngineExecutionResult implements Serializable {

    private static final long serialVersionUID = 3201692196289859060L;

    private final boolean success;

    private final EngineException exception;

    private ExchangeManager exchangeManager;

    public RuleEngineExecutionResult(boolean success, EngineException exception, ExchangeManager exchangeManager) {

        this.success = success;
        this.exception = exception;
        this.exchangeManager = exchangeManager;
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

    public ExchangeManager getExchangeManager() {

        return this.exchangeManager;
    }
}
