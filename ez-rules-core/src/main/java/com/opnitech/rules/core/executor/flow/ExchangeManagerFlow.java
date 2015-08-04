package com.opnitech.rules.core.executor.flow;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.ExchangeManager;
import com.opnitech.rules.core.utils.ClassUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
final class ExchangeManagerFlow implements ExchangeManager {

    private final Map<Object, Object> exchanges = new HashMap<>();

    public ExchangeManagerFlow(Object... exchanges) {

        for (Object exchange : exchanges) {
            addExchange(exchange);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#resolveExchange(java.lang.Class)
     */
    @Override
    public <ExchangeType> ExchangeType resolveExchange(Class<ExchangeType> exchangeClass) {

        return ClassUtil.<ExchangeType> resolveEntity(exchangeClass, this.exchanges.values());
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#addExchange(java.lang.Object)
     */
    @Override
    public <ExchangeType> void addExchange(ExchangeType exchange) {

        Validate.notNull(exchange);

        addExchange(exchange.getClass(), exchange);

    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#addExchange(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public <ExchangeType> void addExchange(Object name, ExchangeType exchange) {

        Validate.notNull(exchange);

        this.exchanges.put(name, exchange);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#removeExchange(java.lang.Object)
     */
    @Override
    public <ExchangeType> void removeExchangeByValue(ExchangeType exchange) {

        Validate.notNull(exchange);

        this.exchanges.remove(exchange.getClass());
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#removeExchangeByName(java.lang.
     * Object)
     */
    @Override
    public void removeExchangeByName(Object name) {

        this.exchanges.remove(name);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#replaceExchange(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public <ExchangeType> void replaceExchange(ExchangeType oldExchange, ExchangeType newExchange) {

        removeExchangeByValue(oldExchange);
        addExchange(newExchange);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#replaceExchange(java.lang.Object,
     * java.lang.Object, java.lang.Object)
     */
    @Override
    public <ExchangeType> void replaceExchange(Object name, ExchangeType oldExchange, ExchangeType newExchange) {

        removeExchangeByValue(oldExchange);
        addExchange(name, newExchange);
    }

    public Map<Object, Object> getExchanges() {

        return this.exchanges;
    }
}
