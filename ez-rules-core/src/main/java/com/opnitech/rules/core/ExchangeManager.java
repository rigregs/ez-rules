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
     * @param <ResultType>
     *            Allow the client to define the overall result type of the rule
     *            execution. There is two ways to define the rule result, using
     *            the exchange manager injected in the rule or using the return
     *            value of the {@Then} Annotate method
     * @return ResultType Type of the result that will be returned
     */
    <ResultType> ResultType resolveResult();

    /**
     * Allow to add a new exchange
     * 
     * @param <ResultType>
     *            Allow the client to define the overall result type of the rule
     *            execution. There is two ways to define the rule result, using
     *            the exchange manager injected in the rule or using the return
     *            value of the {@Then} Annotate method
     * @param result
     *            Register a result of the overall rule execution
     */
    <ResultType> void registerResult(ResultType result);

    /**
     * Allow to resolve a exchange from a type
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param exchangeClass
     *            Class of the exchange that will be resolved
     * @return ExchangeType Type of the exchange
     */
    <ExchangeType> ExchangeType resolveExchangeByClass(Class<ExchangeType> exchangeClass);

    /**
     * Allow to resolve a exchange from a name
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param name
     *            Name of the exchange that will be resolved
     * @return ExchangeType Type of the exchange
     */
    <ExchangeType> ExchangeType resolveExchangeByName(Object name);

    /**
     * Allow to add a new exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param exchange
     *            Exchange to be added
     */
    <ExchangeType> void registerExchange(ExchangeType exchange);

    /**
     * Allow to add a new exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param name
     *            Name of the Exchange. This is use to inject named exchanges or
     *            to resolve collection types like Maps, List
     * @param exchange
     *            Exchange to be added
     */
    <ExchangeType> void registerExchange(Object name, ExchangeType exchange);

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
     * Allow to replace one exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param name
     *            Name of the Exchange. This is use to inject named exchanges or
     *            to resolve collection types like Maps, List
     * @param oldExchange
     *            Existent Exchange
     * @param newExchange
     *            Exchange to replace the existent exchange
     */
    <ExchangeType> void replaceExchange(Object name, ExchangeType oldExchange, ExchangeType newExchange);

    /**
     * Allow top remove a exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param exchange
     *            Exchange to be removed
     */
    <ExchangeType> void removeExchangeByValue(ExchangeType exchange);

    /**
     * Allow top remove a exchange
     * 
     * @param name
     *            Exchange name to be removed
     */
    void removeExchangeByName(Object name);
}
