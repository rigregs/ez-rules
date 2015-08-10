package com.opnitech.rules.core.executor.executers;

import com.opnitech.rules.core.enums.WhenEnum;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public class WhenResult {

    private final WhenEnum whenEnum;

    public WhenResult(WhenEnum whenEnum) {
        this.whenEnum = whenEnum;
    }

    public WhenEnum getWhenEnum() {

        return this.whenEnum;
    }
}
