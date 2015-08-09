package com.opnitech.rules.core.enums;

/**
 * Define the strategy for a group of rules
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public enum ExecutionStrategyEnum {

    /**
     * The execution of the rules in the group will stop as soon a rule is
     * executed, the rest of the rules won't be evaluated inside this group
     */
    STOP_FIRST,

    /**
     * Will execute all possible rules that accept the criteria, if at least one
     * rule is accepted then the whole group return accepted
     */
    ALL_POSSIBLE,

    /**
     * All the rules need to be accepted so all the rules will ran, if one rule
     * is rejected the whole group won't be executed
     */
    ALL
}
