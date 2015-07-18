package com.opnitech.rules.core.executor.flow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.opnitech.rules.core.ContextManager;
import com.opnitech.rules.core.utils.ClassUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
final class ContextManagerFlow implements ContextManager {

    private final List<Object> contexts = new ArrayList<>();

    public ContextManagerFlow(Object... contexts) {

        for (Object context : contexts) {
            this.contexts.add(context);
        }
    }

    @Override
    public <ContextType> ContextType resolveContext(Class<ContextType> contextClass) {

        return ClassUtil.<ContextType> resolveEntity(contextClass, this.contexts);
    }

    @Override
    public <ContextType> void addContext(ContextType context) {

        Validate.notNull(context);

        this.contexts.add(context);
    }

    @Override
    public <ContextType> void removeContext(ContextType context) {

        Validate.notNull(context);

        this.contexts.remove(context);
    }

    @Override
    public <ContextType> void replaceContext(ContextType oldContext, ContextType newContext) {

        removeContext(oldContext);
        addContext(newContext);
    }

    public List<Object> getContexts() {

        return this.contexts;
    }
}
