package com.opnitech.rules.core.annotations.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

/**
 * Annotation to mark a method as action, must be a public method with the
 * following signature You can have more than one action method in one rule
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Then {

    String description() default StringUtils.EMPTY;

    int priority() default 0;
}
