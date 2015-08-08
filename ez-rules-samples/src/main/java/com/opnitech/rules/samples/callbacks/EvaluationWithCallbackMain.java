package com.opnitech.rules.samples.callbacks;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RulesEngine;
import com.opnitech.rules.samples.callbacks.callback.CanIEvaluateCallback;
import com.opnitech.rules.samples.callbacks.callback.EvaluateCallback;
import com.opnitech.rules.samples.callbacks.rules.EvaluateWithCallbackRule;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class EvaluationWithCallbackMain {

    public static void main(String[] args) throws EngineException {

        evaluteNothing();
        evaluteSomething();
    }

    private static void evaluteNothing() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(EvaluateWithCallbackRule.class, new CanIEvaluateCallback(false), EvaluateCallback.class);
        System.out.println("This should not evaluate the rule");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }

    private static void evaluteSomething() throws EngineException {

        RulesEngine rulesEngine = new RulesEngine();
        rulesEngine.registerExecutable(EvaluateWithCallbackRule.class, new CanIEvaluateCallback(true),
                new EvaluateCallback("Evaluation from the callback"));

        System.out.println("This execution should print the callback parameter");
        System.out.println("****");
        rulesEngine.execute();
        System.out.println("****");
    }
}
