package com.opnitech.rules.core.enums;

/**
 * Allow to define if the rule is able to run or not
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public enum WhenEnum {
    /**
     * Return this value if you want the rule to be executed
     */
    ACCEPT,

    /**
     * Return this value if you don't want the rule to be executed
     */
    REJECT;
}
