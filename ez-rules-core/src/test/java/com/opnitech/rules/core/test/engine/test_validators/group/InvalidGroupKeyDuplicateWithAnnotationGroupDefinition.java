package com.opnitech.rules.core.test.engine.test_validators.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition(groupParentKey = InvalidGroupKeyDuplicateWithAnnotationGroupDefinition.class)
public class InvalidGroupKeyDuplicateWithAnnotationGroupDefinition {

    @GroupKey
    public String groupKey() {

        return "TEST";
    }
}
