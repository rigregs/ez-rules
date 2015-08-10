package com.opnitech.rules.core.executor.executers.impl;

import com.opnitech.rules.core.enums.ExecutionStrategyEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class MainGroupRunner extends AbstractGroupRunner {

    public MainGroupRunner(ExecutionStrategyEnum executionStrategyEnum) throws Exception {
        super(null);
        initialize(0, executionStrategyEnum);
    }

    @Override
    protected String retrieveGroupDescription() {

        return "Main Group Executor";
    }

    @Override
    protected Object retrieveGroupDefinitionType() {

        return "Main Group";
    }
}
