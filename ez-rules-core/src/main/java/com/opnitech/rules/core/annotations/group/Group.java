package com.opnitech.rules.core.annotations.group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.opnitech.rules.core.annotations.rule.Rule;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;

/**
 * Annotate a Rule (see {@link Rule}) class) with this annotation in order to
 * specify the group where the rule belong. Depending of the strategy of the
 * group you can define the way the rules will be handle (see
 * {@link ExecutionStrategyEnum})
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Group {
    Class<?>value();
}
