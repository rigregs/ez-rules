package com.opnitech.rules.core;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class EngineException extends Exception {

    private static final long serialVersionUID = -6002982537821208698L;

    /**
     * Default constructor
     * 
     * @param cause
     *            Internal error triggered by the engine
     */
    public EngineException(Throwable cause) {
        super(cause);
    }
}
