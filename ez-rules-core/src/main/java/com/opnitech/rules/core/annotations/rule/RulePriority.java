package com.opnitech.rules.core.annotations.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow to define the method that determine the rule priority allowing dynamic
 * priority rule calculation. This priority method is only evaluate one time
 * when the rule is build.
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RulePriority {

}
