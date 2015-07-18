package com.opnitech.rules.core.test.engine;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractCallback {

    private boolean called;

    public AbstractCallback() {
        // Default constructor
    }

    public boolean isCalled() {

        return this.called;
    }

    public void setCalled(boolean called) {

        this.called = called;
    }
}
