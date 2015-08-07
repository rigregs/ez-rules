package com.opnitech.rules.core.test.engine;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AbstractRule<WhenResultType> {

    private boolean excuteWhen;

    private Set<Method> executeThens = new HashSet<>();

    private WhenResultType whenResult;

    public AbstractRule(WhenResultType whenResult) {
        this.whenResult = whenResult;
    }

    protected WhenResultType doExecuteWhen() {

        this.excuteWhen = true;

        return this.whenResult;
    }

    protected void doExecuteThen(Method method) {

        this.executeThens.add(method);
    }

    public boolean isExcuteWhen() {

        return this.excuteWhen;
    }

    public Set<Method> getExecuteThens() {

        return this.executeThens;
    }
}
