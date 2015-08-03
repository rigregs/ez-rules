package com.opnitech.rules.core;

/**
 * Allow to manage the exchange parameters between all the rules in the engine
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface ExchangeManager {

    /**
     * Allow to resolve a exchange from a type
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param exchangeClass
     *            Class of the exchange that will be resolved
     * @return ExchangeType Type of the exchange
     */
    <ExchangeType> ExchangeType resolveExchange(Class<ExchangeType> exchangeClass);

    /**
     * Allow to add a new exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param exchange
     *            Exchange to be added
     */
    <ExchangeType> void addExchange(ExchangeType exchange);

    /**
     * Allow to replace one exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param oldExchange
     *            Existent Exchange
     * @param newExchange
     *            Exchange to replace the existent exchange
     */
    <ExchangeType> void replaceExchange(ExchangeType oldExchange, ExchangeType newExchange);

    /**
     * Allow top remove a exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param exchange
     *            Exchange to be removed
     */
    <ExchangeType> void removeExchange(ExchangeType exchange);
}
