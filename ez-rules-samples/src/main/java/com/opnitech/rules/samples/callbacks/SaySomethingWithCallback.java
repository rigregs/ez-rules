package com.opnitech.rules.samples.callbacks;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.samples.callbacks.callback.CanISaySomethingCallback;
import com.opnitech.rules.samples.callbacks.callback.SaySomethingCallback;
import com.opnitech.rules.samples.callbacks.rules.SaySomethingWithCallbackRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class SaySomethingWithCallback {

    public static void main(String[] args) throws EngineException {

        sayNothing();
        saySomething();
    }

    private static void sayNothing() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(SaySomethingWithCallbackRule.class, new CanISaySomethingCallback(false),
                SaySomethingCallback.class);
        System.out.println("This execution shouldn't say anything");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }

    private static void saySomething() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(SaySomethingWithCallbackRule.class, new CanISaySomethingCallback(true),
                new SaySomethingCallback("Something to say from the callback"));

        System.out.println("This execution should say something");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }
}
