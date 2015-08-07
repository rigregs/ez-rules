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
public class SaySomethingWithGroupMain {

    public static void main(String[] args) throws EngineException {

        sayNothingWithAllGroup();
        saySomethignWithAllGroup();

        sayText2WithStopFirstGroup();
        sayText1WithStopFirstGroup();
    }

    private static void sayNothingWithAllGroup() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(AllGroupDefinition.class, new AllStrategyGroupedRule(false, "Text 1"),
                new AllStrategyGroupedRule(true, "Text 2"));

        System.out.println("This execution shouldn't say anything");
        System.out.println("****");
        rulesEngine.execute(Boolean.FALSE);
        System.out.println("****");
    }

    private static void saySomethignWithAllGroup() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(AllGroupDefinition.class, new AllStrategyGroupedRule(true, "All Text 1"),
                new AllStrategyGroupedRule(true, "Text 2"));

        System.out.println("This execution shouldn't say anything");
        System.out.println("****");
        rulesEngine.execute(Boolean.FALSE);
        System.out.println("****");
    }

    private static void sayText2WithStopFirstGroup() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(StopFirstGroupDefinition.class, new StopFirstStrategyGroupedRule(false, "All Text 1"),
                new StopFirstStrategyGroupedRule(true, "All  Text 2"));

        System.out.println("This execution should say 'Stop First Text 2'");
        System.out.println("****");
        rulesEngine.execute(Boolean.FALSE);
        System.out.println("****");
    }

    private static void sayText1WithStopFirstGroup() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(StopFirstGroupDefinition.class,
                new StopFirstStrategyGroupedRule(true, "Stop First Text 1"),
                new StopFirstStrategyGroupedRule(false, "Stop First Text 2"));

        System.out.println("This execution should say 'Stop First Text 2'");
        System.out.println("****");
        rulesEngine.execute(Boolean.FALSE);
        System.out.println("****");
    }
}
