package com.opnitech.rules.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class ExchangeBuilder {

    private final List<Object> exchanges = new ArrayList<>();

    private ExchangeBuilder() {
        // Default constructor
    }

    public static ExchangeBuilder create() {

        return new ExchangeBuilder();
    }

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
     * @return The exchange builder
     */
    public <ExchangeType> ExchangeBuilder add(Object name, ExchangeType exchange) {

        this.exchanges.add(new NamedExchange(name, exchange));

        return this;
    }

    /**
     * Allow to add a new exchange
     * 
     * @param <ExchangeType>
     *            Type of the exchange to be resolved
     * @param exchange
     *            Exchange to be added
     * @return The exchange builder
     */
    public <ExchangeType> ExchangeBuilder add(ExchangeType exchange) {

        this.exchanges.add(exchange);

        return this;
    }

    /**
     * @return Return the added exchanges
     */
    public List<Object> getExchanges() {

        return this.exchanges;
    }

    public static class NamedExchange {
        private final Object name;
        private final Object value;

        public NamedExchange(Object name, Object value) {
            this.name = name;
            this.value = value;
        }

        public Object getName() {

            return this.name;
        }

        public Object getValue() {

            return this.value;
        }
    }

}
