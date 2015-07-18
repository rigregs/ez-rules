package com.opnitech.rules.core.executor.util;

/**
 * Implement this interface to manage a list ordered elements in the system
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface PriorityOrdered {

    /**
     * Return the priority of the element
     * 
     * @return
     */
    int getPriority();
}
