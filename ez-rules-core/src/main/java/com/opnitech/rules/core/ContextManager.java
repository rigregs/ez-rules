package com.opnitech.rules.core;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface ContextManager {

    /**
     * Allow to resolve a context from a type
     * 
     * @param contextClass
     * @return
     */
    <ContextType> ContextType resolveContext(Class<ContextType> contextClass);

    /**
     * Allow to add a new context
     * 
     * @param context
     */
    <ContextType> void addContext(ContextType context);

    /**
     * Allow to replace one context
     * 
     * @param oldContext
     * @param newContext
     */
    <ContextType> void replaceContext(ContextType oldContext, ContextType newContext);

    /**
     * Allow top remove a context
     * 
     * @param context
     */
    <ContextType> void removeContext(ContextType context);
}
