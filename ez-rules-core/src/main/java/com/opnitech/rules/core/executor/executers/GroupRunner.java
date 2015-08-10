package com.opnitech.rules.core.executor.executers;

import com.opnitech.rules.core.executor.util.PriorityList;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public interface GroupRunner {

    PriorityList<Runner> getExecutors();
}
