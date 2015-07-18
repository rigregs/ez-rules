package com.opnitech.rules.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
     * Validate that all elements in the list have the same annotation type
     * 
     * @param possibleAnnotatedList
     * @param annotationClass
     * @throws Exception
     */
    public static void validateAnnotationPresent(List<? extends Object> possibleAnnotatedList,
            Class<? extends Annotation> annotationClass) throws Exception {

        if (CollectionUtils.isNotEmpty(possibleAnnotatedList)) {
            for (Object possibleAnnotated : possibleAnnotatedList) {
                AnnotationUtil.validateAnnotationPresent(possibleAnnotated, annotationClass);
            }
        }
    }

    /**
     * Validate that the element have an specific annotation
     * 
     * @param possibleAnnotated
     * @param annotationClass
     * @throws Exception
     */
    public static void validateAnnotationPresent(Object possibleAnnotated, Class<? extends Annotation> annotationClass)
            throws Exception {

        Validate.isTrue(AnnotationUtil.isAnnotationPresent(possibleAnnotated, annotationClass));
    }

    /**
     * Check if an annotation is presented in the element
     * 
     * @param value
     * @param annotationClass
     * @return
     * @throws Exception
     */
    public static boolean isAnnotationPresent(Object value, Class<? extends Annotation> annotationClass) throws Exception {

        Validate.notNull(value);
        Validate.notNull(annotationClass);

        Class<?> possibleAnnotatedClass = ClassUtil.createClass(value);

        return possibleAnnotatedClass.isAnnotationPresent(annotationClass);
    }

    /**
     * Retrieve the annotation from the element
     * 
     * @param value
     * @param annotationClass
     * @return
     * @throws Exception
     */
    public static <AnnotationType extends Annotation> AnnotationType resolveAnnotation(Object value,
            Class<AnnotationType> annotationClass) throws Exception {

        Validate.notNull(value);
        Validate.notNull(annotationClass);

        return Method.class.isAssignableFrom(value.getClass())
                ? ((Method) value).getAnnotation(annotationClass)
                : ClassUtil.<Object> createClass(value).getAnnotation(annotationClass);
    }

    /**
     * Retrieve a method in the class that have one specific annotation, if more
     * than one method have the annotation will trigger a validation exception
     * 
     * @param value
     * @param annotationClass
     * @return
     * @throws Exception
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
     * @param value
     * @param annotationClass
     * @return
     * @throws Exception
     */
    public static <AnnotationType extends Annotation> List<Method> resolveMethodsWithAnnotation(Object value,
            Class<AnnotationType> annotationClass) throws Exception {

        Validate.notNull(value);
        Validate.notNull(annotationClass);

        Class<?> clazz = ClassUtil.createClass(value);

        List<Method> methods = new ArrayList<>();

        Method[] classMethods = clazz.getMethods();
        for (Method method : classMethods) {
            if (method.isAnnotationPresent(annotationClass)) {
                methods.add(method);
            }
        }

        return methods;
    }
}
