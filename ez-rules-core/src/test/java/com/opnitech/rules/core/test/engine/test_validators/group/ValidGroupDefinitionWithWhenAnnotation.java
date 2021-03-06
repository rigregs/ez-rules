package com.opnitech.rules.core.test.engine.test_validators.group;

import org.apache.commons.lang3.StringUtils;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupDefinitionExecutionStrategy;
import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.group.GroupParentKey;
import com.opnitech.rules.core.annotations.rule.Priority;
import com.opnitech.rules.core.annotations.rule.When;
import com.opnitech.rules.core.enums.ExecutionStrategyEnum;
import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition
public class ValidGroupDefinitionWithWhenAnnotation {

    private String groupKey;
    private WhenEnum whenEnum;

    private boolean executeWhen;
    private ExecutionStrategyEnum executionStrategy;
    private int priority;
    private String parentGroupKey;

    public ValidGroupDefinitionWithWhenAnnotation(int priority, String parentGroupKey, String groupKey, WhenEnum whenEnum,
            ExecutionStrategyEnum executionStrategy) {
        
        this.priority = priority;
        this.parentGroupKey = parentGroupKey;
        this.groupKey = groupKey;
        this.whenEnum = whenEnum;
        this.executionStrategy = executionStrategy;
    }

    @Priority
    public int retrievePriority() {

        return this.priority;
    }

    @GroupParentKey
    public String retrieveGroupParentKey() {

        return StringUtils.trimToNull(this.parentGroupKey);
    }

    @GroupDefinitionExecutionStrategy
    public ExecutionStrategyEnum executionStrategy() {

        return this.executionStrategy;
    }

    @When
    public WhenEnum when() {

        this.executeWhen = true;

        return this.whenEnum;
    }

    @GroupKey
    public String groupKey() {

        return this.groupKey;
    }

    public boolean isExecuteWhen() {

        return this.executeWhen;
    }
}
