package com.opnitech.rules.core.annotations.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

/**
 * Annotation to mark a Rule. A rule should have one method annotate with the
 * condition Annotation (see the {@link When} class) and need to have at least
 * one method annotated with the Then annotation (see {@link Then} class )
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Rule {

    String ruleId() default StringUtils.EMPTY;

    String description() default StringUtils.EMPTY;

    int priority() default 0;
}
