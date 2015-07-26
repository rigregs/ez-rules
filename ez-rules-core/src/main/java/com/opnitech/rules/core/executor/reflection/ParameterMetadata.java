package com.opnitech.rules.core.executor.reflection;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Describe the metadata information of a method parameter
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class ParameterMetadata {

    private final Class<?> parameterType;

    private final Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<>();

    private final MethodMetadata methodMetadata;

    public ParameterMetadata(MethodMetadata methodMetadata, Class<?> parameterType) {

        this.methodMetadata = methodMetadata;
        this.parameterType = parameterType;
    }

    /**
     * Allow to check if an annotation is present in the parameter
     * 
     * @param annotationClass
     *            Annotation to check in the parameter
     * @return True if the annotation is present, false otherwise
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {

        return this.annotations.containsKey(annotationClass);
    }

    /**
     * Allow to register a parameter for the method parameter
     * 
     * @param annotation
     *            Annotation to register as part of the parameter metatada
     */
    public void registerAnnotation(Annotation annotation) {

        this.annotations.put(annotation.annotationType(), annotation);
    }

    /**
     * Allow to get the parameter type
     * 
     * @return Class type of the parameter
     */
    public Class<?> getParameterType() {

        return this.parameterType;
    }

    /**
     * Allow to get the method metadata
     * 
     * @return Reference to the container method metadata
     */
    public MethodMetadata getMethodMetadata() {

        return this.methodMetadata;
    }
}
