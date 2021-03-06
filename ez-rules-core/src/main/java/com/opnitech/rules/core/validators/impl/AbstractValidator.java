package com.opnitech.rules.core.validators.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.opnitech.rules.core.annotations.group.GroupKey;
import com.opnitech.rules.core.annotations.rule.Priority;
import com.opnitech.rules.core.utils.AnnotationUtil;
import com.opnitech.rules.core.utils.AnnotationValidatorUtil;
import com.opnitech.rules.core.utils.ExceptionUtil;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public abstract class AbstractValidator {

    public AbstractValidator() {
        // Default constructor
    }

    protected void validateUniqueExecutor(List<Object> candidateExecutables, Class<? extends Annotation> annotationClass,
            Object executable) {

        if (candidateExecutables.contains(executable)) {
            ExceptionUtil.throwIllegalArgumentException(
                    "Invalid ''{0}''. Yuo are trying to register double executer. Executer: ''{1}''", annotationClass,
                    executable);
        }
    }

    protected void validateGroupKeyMethod(Object executable) throws Exception {

        AnnotationValidatorUtil.validateAnnotatedMethods(executable, GroupKey.class, 0, 1, false, String.class);
        validateExecutableIsInstance(executable, GroupKey.class);
    }

    protected void validateValidPriorityMethod(Object executable) throws Exception {

        AnnotationValidatorUtil.validateAnnotatedMethods(executable, Priority.class, 0, 1, false, Integer.TYPE);
        validateExecutableIsInstance(executable, Priority.class);
    }

    protected void validateExecutableIsInstance(Object executable, Class<? extends Annotation> annotationClass) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(executable, annotationClass);

        if (CollectionUtils.isNotEmpty(methods) && Class.class.isAssignableFrom(executable.getClass())) {
            ExceptionUtil.throwIllegalArgumentException(
                    "You cannot register a 'Executable' as a class that contain a method with the ''{0}'' annotation. You need to register the definition as instance executable. 'Executable': ''{1}'', Method: ''{2}''",
                    annotationClass, executable, methods.iterator().next().getName());
        }
    }
}
