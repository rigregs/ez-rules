package com.opnitech.rules.core.test.engine.test_when_result;

import org.junit.Assert;
import org.junit.Test;

import com.opnitech.rules.core.ExecutionResult;
import com.opnitech.rules.core.enums.WhenEnum;
import com.opnitech.rules.core.test.engine.AbstractRuleEngineExecutorTest;
import com.opnitech.rules.core.test.engine.test_when_result.rules.TestValidWhenResult;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class TestWhenResultTest extends AbstractRuleEngineExecutorTest {

    private static final String TEST = "TEST";

    @Test
    public void testTestValidWhenResult() {

        TestValidWhenResult testValidWhenResult = new TestValidWhenResult(WhenEnum.ACCEPT, TEST, 1);

        ExecutionResult<String> executionResult = validateRule(testValidWhenResult);
        Assert.assertEquals(TEST, executionResult.getResult());
    }

    @Test
    public void testTestValidWhenResultWithPriority() {

        TestValidWhenResult testValidWhenResult1 = new TestValidWhenResult(WhenEnum.ACCEPT, TEST, 2);
        TestValidWhenResult testValidWhenResult2 = new TestValidWhenResult(WhenEnum.ACCEPT, "TEST!", 1);

        ExecutionResult<String> executionResult = validateRule(testValidWhenResult1, testValidWhenResult2);
        Assert.assertEquals(TEST, executionResult.getResult());

        executionResult = validateRule(testValidWhenResult2, testValidWhenResult1);
        Assert.assertEquals(TEST, executionResult.getResult());
    }
}
