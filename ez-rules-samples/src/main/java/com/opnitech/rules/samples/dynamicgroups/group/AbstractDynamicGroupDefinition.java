package com.opnitech.rules.samples.dynamicgroups.group;

import com.opnitech.rules.core.annotations.group.GroupKey;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class AbstractDynamicGroupDefinition {
    private String groupKey;

    public AbstractDynamicGroupDefinition(String groupKey) {
        this.groupKey = groupKey;
    }

    @GroupKey
    public String retrieveGroupKey() {

        return this.groupKey;
    }
}
