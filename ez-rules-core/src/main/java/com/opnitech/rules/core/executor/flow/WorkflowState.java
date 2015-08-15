package com.opnitech.rules.core.executor.flow;

import java.util.List;

import com.opnitech.rules.core.ExchangeManager;

/**
 * Represent the global data that is needed for the rule execution, all rule
 * executors exchange this data in order to keep the rules stateless.
 * 
 * @author Rigre Gregorio Garciandia Sonora
 * @param <ResultType>
 *            Allow the client to define the overall result type of the rule
 *            execution. There is two ways to define the rule result, using the
 *            exchange manager injected in the rule or using the return value of
 *            the {@Then} Annotate method
 */
public final class WorkflowState<ResultType> {

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
     * @return Return the exchange Manager allowing to change the state of the
     *         rule Exchanger inside any rule.
     */
    public ExchangeManager getExchangeManager() {

        return this.exchangeManager;
    }

    /**
     * Get all defined callbacks
     * 
     * @return Return the list of callbacks
     */
    public List<Object> getCallbacks() {

        return this.callbacks;
    }

    /**
     * Get the lasted Throwable happen in the rule execution
     * 
     * @return Return the current exception happen inside the rule execution
     */
    public Throwable getThrowable() {

        return this.throwable;
    }

    /**
     * @param throwable
     *            The exception happen inside the rule engine execution
     */
    public void setThrowable(Throwable throwable) {

        this.throwable = throwable;
    }
}
