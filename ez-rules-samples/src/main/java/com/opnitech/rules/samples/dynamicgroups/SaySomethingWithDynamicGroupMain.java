package com.opnitech.rules.samples.dynamicgroups;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.samples.dynamicgroups.group.DynamicAllGroupDefinition;
import com.opnitech.rules.samples.dynamicgroups.group.DynamicStopFirstGroupDefinition;
import com.opnitech.rules.samples.dynamicgroups.rules.DynamicGroupedRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class SaySomethingWithDynamicGroupMain {

    public static void main(String[] args) throws EngineException {

        evaluateAllStrategyGroupWithOneRuleFailingPrecondition();
        evaluateAllStrategyGroupWithAllRuleSuccessPrecondition();

        evaluateStopFirstStrategyGroupWithOneRuleFailingPrecondition();
    }

    private static void evaluateAllStrategyGroupWithOneRuleFailingPrecondition() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(new DynamicAllGroupDefinition("evaluateAllStrategyGroupWithOneRuleFailingPrecondition"),
                new DynamicGroupedRule("evaluateAllStrategyGroupWithOneRuleFailingPrecondition", false, "Rule 1"),
                new DynamicGroupedRule("evaluateAllStrategyGroupWithOneRuleFailingPrecondition", true, "Rule 2"));

        System.out.println("No Rule action should be executed...");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }

    private static void evaluateAllStrategyGroupWithAllRuleSuccessPrecondition() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(new DynamicAllGroupDefinition("evaluateAllStrategyGroupWithAllRuleSuccessPrecondition"),
                new DynamicGroupedRule("evaluateAllStrategyGroupWithAllRuleSuccessPrecondition", true, "Rule 1"),
                new DynamicGroupedRule("evaluateAllStrategyGroupWithAllRuleSuccessPrecondition", true, "Rule 2"));

        System.out.println("Both rules should be evaluate");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }

    private static void evaluateStopFirstStrategyGroupWithOneRuleFailingPrecondition() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(
                new DynamicStopFirstGroupDefinition("evaluateStopFirstStrategyGroupWithOneRuleFailingPrecondition"),
                new DynamicGroupedRule("evaluateStopFirstStrategyGroupWithOneRuleFailingPrecondition", false, "Rule 1"),
                new DynamicGroupedRule("evaluateStopFirstStrategyGroupWithOneRuleFailingPrecondition", true, "Rule 2"));

        System.out.println("Rule 2 should be executed");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }
}
