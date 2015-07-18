package com.opnitech.rules.core.executor.flow;

import java.util.List;

import com.opnitech.rules.core.ContextManager;

/**
 * Represent the global data that is needed for the rule execution, all rule
 * executors exchange this data in order to keep the rules stateless.
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class WorkflowState {

    private final ContextManager contextManager;

    private final List<Object> callbacks;

    private Exception exception;

    public WorkflowState(List<Object> callbacks, Object... contexts) {

        this.callbacks = callbacks;
        this.contextManager = new ContextManagerFlow(contexts);
    }

    /**
     * Get the context manage
     * 
     * @return
     */
    public ContextManager getContextManager() {

        return this.contextManager;
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
     * Get the lasted exception happen in the rule execution
     * 
     * @return
     */
    public Exception getException() {

        return this.exception;
    }

    /**
     * @param exception
     */
    public void setException(Exception exception) {

        this.exception = exception;
    }
}
