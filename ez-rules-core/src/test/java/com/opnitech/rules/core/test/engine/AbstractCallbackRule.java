package com.opnitech.rules.core.test.engine;

import java.util.HashSet;
import java.util.Set;

import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AbstractCallbackRule extends AbstractRule<WhenEnum> {

    private Set<AbstractCallback> whenCallbacks = new HashSet<>();
    private Set<AbstractCallback> thenCallbacks = new HashSet<>();

    public AbstractCallbackRule(WhenEnum whenResult) {
        super(whenResult);
    }

    protected void registerWhenCallbacks(AbstractCallback... callbacks) {

        registerCallbacks(this.whenCallbacks, callbacks);
    }

    protected void registerThenCallbacks(AbstractCallback... callbacks) {

        registerCallbacks(this.thenCallbacks, callbacks);
    }

    private void registerCallbacks(Set<AbstractCallback> callbackSet, AbstractCallback... callbacks) {

        for (AbstractCallback callback : callbacks) {
            if (callback != null) {
                callback.setCalled(true);
                callbackSet.add(callback);
            }
        }
    }

    public Set<AbstractCallback> getWhenCallbacks() {

        return this.whenCallbacks;
    }

    public Set<AbstractCallback> getThenCallbacks() {

        return this.thenCallbacks;
    }
}
