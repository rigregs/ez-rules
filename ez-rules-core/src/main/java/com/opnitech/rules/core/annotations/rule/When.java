package com.opnitech.rules.core.annotations.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark the method of the rule that decide if the rule need to be
 * execute, only one method on the rule should be annotate with this annotation.
 * The method should have the following signature:
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface When {
    // NOP
}
