package com.opnitech.rules.samples.dynamicgroups.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition(ruleExecutionStrategy = ExecutionStrategyEnum.STOP_FIRST)
public class DynamicStopFirstGroupDefinition extends AbstractDynamicGroupDefinition {

    public DynamicStopFirstGroupDefinition(String groupKey) {
        super(groupKey);
    }
}
