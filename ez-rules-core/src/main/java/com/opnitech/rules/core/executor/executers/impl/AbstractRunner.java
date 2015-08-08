package com.opnitech.rules.core.executor.executers.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.opnitech.rules.core.annotations.rule.Priority;
import com.opnitech.rules.core.executor.executers.Runner;
import com.opnitech.rules.core.utils.AnnotationUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractRunner implements Runner {

    public AbstractRunner() {
        // Default constructor
    }

    protected int resolvePriority(Object executable, int annotationPriority) throws Exception {

        int finalPriority = annotationPriority;

        Method methodWithRulePriotityAnnotation = AnnotationUtil.resolveMethodWithAnnotation(executable, Priority.class);
        if (methodWithRulePriotityAnnotation != null) {
            finalPriority += calculatePriority(executable, methodWithRulePriotityAnnotation);
        }

        return finalPriority;
    }

    private int calculatePriority(Object executable, Method methodWithRulePriotityAnnotation)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        return (int) methodWithRulePriotityAnnotation.invoke(executable);
    }
}
