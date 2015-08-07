package com.opnitech.rules.core;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class NamedExchange {
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
