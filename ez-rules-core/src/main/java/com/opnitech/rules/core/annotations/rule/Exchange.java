package com.opnitech.rules.core.annotations.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Exchange {
    String name() default StringUtils.EMPTY;
}
