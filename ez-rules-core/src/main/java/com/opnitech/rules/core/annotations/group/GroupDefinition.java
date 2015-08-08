package com.opnitech.rules.core.annotations.group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

import com.opnitech.rules.core.enums.ExecutionStrategyEnum;

/**
 * Annotate a group marker type with this, after you can use the type as ID for
 * the group in the Group (see {@link Group})
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GroupDefinition {

    String description() default StringUtils.EMPTY;

    int priority() default 0;

    ExecutionStrategyEnum value() default ExecutionStrategyEnum.STOP_FIRST;
}
