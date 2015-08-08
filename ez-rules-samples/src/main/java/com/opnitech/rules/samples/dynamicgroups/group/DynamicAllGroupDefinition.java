package com.opnitech.rules.samples.dynamicgroups.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition(ruleExecutionStrategy = ExecutionStrategyEnum.ALL)
public class DynamicAllGroupDefinition extends AbstractDynamicGroupDefinition {

    public DynamicAllGroupDefinition(String groupKey) {
        super(groupKey);
    }
}
