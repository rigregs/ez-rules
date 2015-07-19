package com.opnitech.rules.core.executor.flow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.ExchangeManager;
import com.opnitech.rules.core.utils.ClassUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
final class ExchangeManagerFlow implements ExchangeManager {

    private final List<Object> exchanges = new ArrayList<>();

    public ExchangeManagerFlow(Object... exchanges) {

        for (Object exchange : exchanges) {
            this.exchanges.add(exchange);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#resolveExchange(java.lang.Class)
     */
    @Override
    public <ExchangeType> ExchangeType resolveExchange(Class<ExchangeType> exchangeClass) {

        return ClassUtil.<ExchangeType> resolveEntity(exchangeClass, this.exchanges);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#addExchange(java.lang.Object)
     */
    @Override
    public <ExchangeType> void addExchange(ExchangeType exchange) {

        Validate.notNull(exchange);

        this.exchanges.add(exchange);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#removeExchange(java.lang.Object)
     */
    @Override
    public <ExchangeType> void removeExchange(ExchangeType exchange) {

        Validate.notNull(exchange);

        this.exchanges.remove(exchange);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.opnitech.rules.core.ExchangeManager#replaceExchange(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public <ExchangeType> void replaceExchange(ExchangeType oldExchange, ExchangeType newExchange) {

        removeExchange(oldExchange);
        addExchange(newExchange);
    }

    public List<Object> getExchanges() {

        return this.exchanges;
    }
}
