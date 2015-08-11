package com.opnitech.rules.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/**
 * Helper class to manipulate Annotation
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class AnnotationUtil {

    private AnnotationUtil() {
        // Default constructor
    }

    /**
     * Check if an annotation is presented in the element
     * 
     * @param possibleAnnotated
     *            Possible annotate instance
     * @param annotationClass
     *            Annotation to check
     * @return True if the annotation exists, otherwise False.
     * @throws Exception
     *             Throw an exception if something happen validating the
     *             annotation
     */
    public static boolean isAnnotationPresent(Object possibleAnnotated, Class<? extends Annotation> annotationClass)
            throws Exception {

        Validate.notNull(possibleAnnotated);
        Validate.notNull(annotationClass);

        Class<?> possibleAnnotatedClass = ClassUtil.createClass(possibleAnnotated);

        return possibleAnnotatedClass.isAnnotationPresent(annotationClass);
    }

    /**
     * Retrieve the annotation from the element
     * 
     * @param <AnnotationType>
     *            Annotation Type to resolve
     * @param possibleAnnotated
     *            Possible annotate instance
     * @param annotationClass
     *            Annotation to check
     * @return The annotation
     * @throws Exception
     *             Throw an exception if something happen validating the
     *             annotation
     */
    public static <AnnotationType extends Annotation> AnnotationType resolveAnnotation(Object possibleAnnotated,
            Class<AnnotationType> annotationClass) throws Exception {

        Validate.notNull(possibleAnnotated);
        Validate.notNull(annotationClass);

        return Method.class.isAssignableFrom(possibleAnnotated.getClass())
                ? ((Method) possibleAnnotated).getAnnotation(annotationClass)
                : ClassUtil.<Object> createClass(possibleAnnotated).getAnnotation(annotationClass);
    }

    /**
     * Retrieve a method in the class that have one specific annotation, if more
     * than one method have the annotation will trigger a validation exception
     * 
     * @param <AnnotationType>
     *            Annotation Type to resolve
     * @param value
     *            Instance of the class with annotated methods
     * @param annotationClass
     *            Annotation to check
     * @return Requested annotation method
     * @throws Exception
     *             Throw an exception if something happen trying to retrieve the
     *             annotation or if more than one method contain the annotation
     */
    public static <AnnotationType extends Annotation> Method resolveMethodWithAnnotation(Object value,
            Class<AnnotationType> annotationClass) throws Exception {

        List<Method> methods = AnnotationUtil.resolveMethodsWithAnnotation(value, annotationClass);

        if (CollectionUtils.isNotEmpty(methods)) {
            Validate.isTrue(methods.size() == 1);
            return methods.get(0);
        }

        return null;
    }

    /**
     * Retrieve all the methods in the class that have one specific annotation
     * 
     * @param <AnnotationType>
     *            Annotation Type to resolve
     * @param value
     *            Instance of the class with annotated methods
     * @param annotationClass
     *            Annotation to check
     * @return Requested annotation methods
     * @throws Exception
     *             Throw an exception if something happen trying to retrieve the
     *             annotation
     */
    public static <AnnotationType extends Annotation> List<Method> resolveMethodsWithAnnotation(Object value,
            Class<AnnotationType> annotationClass) throws Exception {

        Validate.notNull(value);
        Validate.notNull(annotationClass);

        Class<?> clazz = ClassUtil.createClass(value);

        List<Method> methods = new ArrayList<>();

        Method[] classMethods = clazz.getMethods();
        if (ArrayUtils.isNotEmpty(classMethods)) {
            for (Method method : classMethods) {
                if (method.isAnnotationPresent(annotationClass)) {
                    methods.add(method);
                }
            }
        }

        return methods;
    }
}
