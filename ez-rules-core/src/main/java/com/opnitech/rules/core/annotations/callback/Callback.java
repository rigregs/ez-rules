package com.opnitech.rules.core.annotations.callback;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.opnitech.rules.core.annotations.rule.Then;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;

/**
 * Annotate a Callback with this annotation in order be able to inject it in the
 * Rule Then (see {@link Then} Depending of the strategy of the group you can
 * define the way the rules will be handle (see {@link ExecutionStrategyEnum})
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
    {
        ElementType.TYPE,
        ElementType.PARAMETER
    })
public @interface Callback {
    // NOP
}
