package com.opnitech.rules.core.annotations.group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a group marker type with this, after you can use the type as ID for
 * the group in the Group (see {@link Group})
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GroupKey {

    String groupKey() default "";
}
