package com.opnitech.rules.samples.exchanges;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.samples.exchanges.rules.SaySomethingWithExchangeRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class SaySomethingWithExchangeMain {

    public static void main(String[] args) throws EngineException {

        sayNothing();
        saySomething();
    }

    private static void sayNothing() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();

        rulesEngine.registerExecutable(SaySomethingWithExchangeRule.class);

        System.out.println("This execution shouldn't say anything");
        System.out.println("****");
        rulesEngine.execute(Boolean.FALSE);
        System.out.println("****");
    }

    private static void saySomething() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();

        rulesEngine.registerExecutable(SaySomethingWithExchangeRule.class);

        System.out.println("Let's actually say something");
        System.out.println("****");
        rulesEngine.execute(Boolean.TRUE, "Finally I'm allowed to talk!!!");
        System.out.println("****");
    }
}
