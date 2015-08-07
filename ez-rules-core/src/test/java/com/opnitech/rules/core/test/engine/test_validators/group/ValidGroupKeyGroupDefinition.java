package com.opnitech.rules.core.test.engine.test_validators.group;

import com.opnitech.rules.core.annotations.group.GroupDefinition;
import com.opnitech.rules.core.annotations.group.GroupKey;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
@GroupDefinition
public class ValidGroupKeyGroupDefinition {

    private String result;

    public ValidGroupKeyGroupDefinition(String result) {
        this.result = result;
    }

    @GroupKey
    public String groupKey() {

        return this.result;
    }
}
