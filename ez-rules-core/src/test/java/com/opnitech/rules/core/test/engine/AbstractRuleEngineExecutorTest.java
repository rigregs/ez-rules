package com.opnitech.rules.core.test.engine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;

import com.opnitech.rules.core.EngineException;
import com.opnitech.rules.core.RuleEngineExecutionResult;
import com.opnitech.rules.core.RulesEngine;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AbstractRuleEngineExecutorTest {

    protected Exception validateExceptionRule(Object... executables) {

        try {
            validateRule(executables);
            Assert.fail();

            return null;
        }
        catch (Exception exception) {
            return exception;
        }
    }

    protected void validateRule(Object... executables) throws EngineException {

        validateWithPreconditionsRule(true, true, executables);
    }

    protected void validateWithPreconditionsRule(boolean executeWhen, boolean executeAtLeastOneThen, Object... executables)
            throws EngineException {

        RulesEngine engine = new RulesEngine();
        engine.setExecutables(Arrays.asList(executables));

        RuleEngineExecutionResult execute = engine.execute();
        if (!execute.isSuccess()) {
            throw execute.getException();
        }

        for (Object executable : executables) {
            if (AbstractRule.class.isAssignableFrom(executable.getClass())) {
                AbstractRule<?> rule = (AbstractRule<?>) executable;

                Assert.assertEquals(executeWhen, rule.isExcuteWhen());
                Assert.assertEquals(executeAtLeastOneThen, !rule.getExecuteThens().isEmpty());
            }
        }
    }

    protected void validateCallback(AbstractCallbackRule callbackRule, Class<?>[] expectedWhenCallbacks,
            Class<?>[] expectedThenCallbacks) throws Exception {

        validateRule(buildAllExecutables(callbackRule, expectedWhenCallbacks, expectedThenCallbacks));

        validateWhenCallBack(callbackRule, expectedWhenCallbacks);
        validateThenCallBack(callbackRule, expectedThenCallbacks);
    }

    private Object[] buildAllExecutables(AbstractCallbackRule callbackRule, Class<?>[] expectedWhenCallbacks,
            Class<?>[] expectedThenCallbacks) {

        Set<Object> allExecutables = new HashSet<>();
        allExecutables.add(callbackRule);

        Class<?>[] allCallbacks = ArrayUtils.addAll(expectedWhenCallbacks, expectedThenCallbacks);
        if (allCallbacks != null) {
            for (Class<?> callback : allCallbacks) {
                if (!allExecutables.contains(callback)) {
                    allExecutables.add(callback);
                }
            }
        }

        return allExecutables.toArray();
    }

    private void validateWhenCallBack(AbstractCallbackRule callbackRule, Class<?>... callbacksClasses) {

        validateCallbacks(callbackRule.getWhenCallbacks(), callbacksClasses);
    }

    private void validateThenCallBack(AbstractCallbackRule callbackRule, Class<?>... callbacksClasses) {

        validateCallbacks(callbackRule.getThenCallbacks(), callbacksClasses);
    }

    private void validateCallbacks(Set<AbstractCallback> resolvedCallbacks, Class<?>... callbacksClasses) {

        if (callbacksClasses == null) {
            Assert.assertEquals(0, resolvedCallbacks.size());
        }
        else {
            Assert.assertEquals(callbacksClasses.length, resolvedCallbacks.size());

            for (AbstractCallback callback : resolvedCallbacks) {
                Assert.assertTrue(ArrayUtils.contains(callbacksClasses, callback.getClass()));
            }
        }
    }
}
