package com.opnitech.rules.samples.exchanges;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.samples.exchanges.rules.EvaluateWithExchangeRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class EvaluateWithExchangeMain {

    public static void main(String[] args) throws EngineException {

        sayNothing();
        saySomething();
    }

    private static void sayNothing() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();

        rulesEngine.registerExecutable(EvaluateWithExchangeRule.class);

        System.out.println("This execution should not evaluate");
        System.out.println("****");
        rulesEngine.execute(Boolean.FALSE, "Hello world!");
        System.out.println("****");
    }

    private static void saySomething() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();

        rulesEngine.registerExecutable(EvaluateWithExchangeRule.class);

        System.out.println("This should print from the callback");
        System.out.println("****");
        rulesEngine.execute(Boolean.TRUE, "Hello world!");
        System.out.println("****");
    }
}
