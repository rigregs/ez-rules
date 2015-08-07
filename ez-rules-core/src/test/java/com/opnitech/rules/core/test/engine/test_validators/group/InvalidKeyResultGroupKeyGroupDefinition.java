package com.opnitech.rules.core.test.engine.test_validators.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition
public class InvalidKeyResultGroupKeyGroupDefinition {

    @GroupKey
    public Object groupKey() {

        return "TEST";
    }
}
