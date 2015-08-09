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
    ACCEPT(0),

    /**
     * Return this value if you don't want the rule to be executed
     */
    REJECT(1),

    /**
     * If a rule return this value the whole group will stop no matter the
     * strategy
     */
    REJECT_GROUP(-1),

    /**
     * If a rule return this value the whole rule engine will stop
     */
    REJECT_ALL(-2);

    private final int priority;

    private WhenEnum(int priority) {
        this.priority = priority;
    }

    public static WhenEnum fromPriority(int priority) {

        for (WhenEnum whenEnum : WhenEnum.values()) {
            if (priority == whenEnum.getPriority()) {
                return whenEnum;
            }
        }

        return null;
    }

    public int getPriority() {

        return this.priority;
    }
}
