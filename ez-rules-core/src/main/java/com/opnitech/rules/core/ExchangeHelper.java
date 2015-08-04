package com.opnitech.rules.core;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class ExchangeHelper {

    private ExchangeHelper() {
        // Default constructor
    }

    public static NamedExchange exchange(Object name, Object value) {

        return new NamedExchange(name, value);
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
