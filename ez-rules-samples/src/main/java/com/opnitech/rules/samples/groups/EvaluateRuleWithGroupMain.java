package com.opnitech.rules.samples.groups;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.samples.groups.group.AllGroupDefinition;
import com.opnitech.rules.samples.groups.group.StopFirstGroupDefinition;
import com.opnitech.rules.samples.groups.rules.AllStrategyGroupedRule;
import com.opnitech.rules.samples.groups.rules.StopFirstStrategyGroupedRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class EvaluateRuleWithGroupMain {

    public static void main(String[] args) throws EngineException {

        evaluateAllStrategyGroupWithOneRuleFailingPrecondition();
        evaluateAllStrategyGroupWithAllRuleSuccessPrecondition();

        evaluateStopFirstStrategyGroupWithOneRuleFailingPrecondition();
    }

    private static void evaluateAllStrategyGroupWithOneRuleFailingPrecondition() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(AllGroupDefinition.class, new AllStrategyGroupedRule(false, "Rule 1"),
                new AllStrategyGroupedRule(true, "Rule 2"));

        System.out.println("No Rule action should be executed...");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }

    private static void evaluateAllStrategyGroupWithAllRuleSuccessPrecondition() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(AllGroupDefinition.class, new AllStrategyGroupedRule(true, "Rule 1"),
                new AllStrategyGroupedRule(true, "Rule 2"));

        System.out.println("Both rules should be evaluate");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }

    private static void evaluateStopFirstStrategyGroupWithOneRuleFailingPrecondition() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(StopFirstGroupDefinition.class, new StopFirstStrategyGroupedRule(false, "Rule 1"),
                new StopFirstStrategyGroupedRule(true, "Rule 2"));

        System.out.println("Rule 2 should be executed");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }
}
