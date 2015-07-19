package com.opnitech.rules.core.executor.flow;

import java.util.List;

import com.opnitech.rules.core.ExchangeManager;

/**
 * Represent the global data that is needed for the rule execution, all rule
 * executors exchange this data in order to keep the rules stateless.
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class WorkflowState {

    private final ExchangeManager exchangeManager;

    private final List<Object> callbacks;

    private Throwable throwable;

    public WorkflowState(List<Object> callbacks, Object... exchanges) {

        this.callbacks = callbacks;
        this.exchangeManager = new ExchangeManagerFlow(exchanges);
    }

    /**
     * Get the Exchange Manager
     * 
     * @return
     */
    public ExchangeManager getExchangeManager() {

        return this.exchangeManager;
    }

    /**
     * Get all defined callbacks
     * 
     * @return
     */
    public List<Object> getCallbacks() {

        return this.callbacks;
    }

    /**
     * Get the lasted Throwable happen in the rule execution
     * 
     * @return
     */
    public Throwable getThrowable() {

        return this.throwable;
    }

    /**
     * @param Throwable
     */
    public void setThrowable(Throwable throwable) {

        this.throwable = throwable;
    }
}
