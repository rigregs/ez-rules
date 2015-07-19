package com.opnitech.rules.core;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface ExchangeManager {

    /**
     * Allow to resolve a exchange from a type
     * 
     * @param exchangeClass
     * @return
     */
    <ExchangeType> ExchangeType resolveExchange(Class<ExchangeType> exchangeClass);

    /**
     * Allow to add a new exchange
     * 
     * @param exchange
     */
    <ExchangeType> void addExchange(ExchangeType exchange);

    /**
     * Allow to replace one exchange
     * 
     * @param oldExchange
     * @param newExchange
     */
    <ExchangeType> void replaceExchange(ExchangeType oldExchange, ExchangeType newExchange);

    /**
     * Allow top remove a exchange
     * 
     * @param exchange
     */
    <ExchangeType> void removeExchange(ExchangeType exchange);
}
