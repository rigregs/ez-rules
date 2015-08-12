package com.opnitech.rules.core.test.engine.test_validators.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition
public class InvalidGroupKeyDuplicateGroupDefinition {

    @GroupKey
    public String groupKey() {

        return "TEST";
    }

    @GroupKey
    public String groupKey1() {

        return "TEST";
    }
}
