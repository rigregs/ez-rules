package com.opnitech.rules.core.executor.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opnitech.rules.core.executor.util.PriorityOrdered;

/**
 * Manage the metadata of a method and it's parameters
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class MethodMetadata implements PriorityOrdered {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodMetadata.class);

    private final Method method;

    private final ParameterMetadata[] parametersMetadata;

    private final int priority;

    private final Class<?> ownerClass;

    private Class<?> returnType;

    public MethodMetadata(Class<?> ownerClass, Method method) {

        this(ownerClass, method, 0);
    }

    public MethodMetadata(Class<?> ownerClass, Method method, int priority) {

        this.ownerClass = ownerClass;
        this.method = method;
        this.priority = priority;

        Class<?>[] parameterTypes = this.method.getParameterTypes();
        Annotation[][] parameterAnnotations = this.method.getParameterAnnotations();

        this.setReturnType(this.method.getReturnType());

        this.parametersMetadata = ArrayUtils.isNotEmpty(parameterTypes)
                ? new ParameterMetadata[parameterTypes.length]
                : null;

        populateParameters(parameterTypes, parameterAnnotations);
    }

    private void populateParameters(Class<?>[] parameterTypes, Annotation[][] parameterAnnotations) {

        if (this.parametersMetadata != null) {

            for (int i = 0; i < parameterTypes.length; i++) {
                ParameterMetadata parameterMetadata = new ParameterMetadata(this, parameterTypes[i]);

                this.parametersMetadata[i] = parameterMetadata;

                if (ArrayUtils.isNotEmpty(parameterAnnotations[i])) {
                    for (int j = 0; j < parameterAnnotations[i].length; j++) {
                        parameterMetadata.registerAnnotation(parameterAnnotations[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Allow to execute the method represented by this methos metadata
     * 
     * @param methodExecutionResult
     * @param parameterResolver
     * @throws Exception
     */
    public <ResultType> void execute(MethodRunnerResult<ResultType> methodExecutionResult, ParameterResolver parameterResolver)
            throws Throwable {

        try {
            @SuppressWarnings("unchecked")
            ResultType result = (ResultType) (ArrayUtils.isNotEmpty(this.parametersMetadata)
                    ? result = executeWithParameters(methodExecutionResult, parameterResolver)
                    : this.method.invoke(methodExecutionResult.getInstance()));

            methodExecutionResult.setResult(result);
        }
        catch (InvocationTargetException e) {
            throw extractInternalException(e);
        }
        catch (Exception e) {
            throw e;
        }
    }

    private Throwable extractInternalException(InvocationTargetException invocationTargetException) {

        MethodMetadata.LOGGER.error("Error executing internal rule engine...", invocationTargetException);

        return invocationTargetException.getTargetException();
    }

    private <ResultType> ResultType executeWithParameters(MethodRunnerResult<ResultType> methodExecutionResult,
            ParameterResolver parameterResolver) throws Throwable {

        try {
            Object[] arguments = new Object[this.parametersMetadata.length];

            for (int i = 0; i < this.parametersMetadata.length; i++) {
                arguments[i] = parameterResolver.resolveParameter(this.parametersMetadata[i]);
            }

            @SuppressWarnings("unchecked")
            ResultType result = (ResultType) this.method.invoke(methodExecutionResult.getInstance(), arguments);

            return result;
        }
        catch (InvocationTargetException e) {
            throw extractInternalException(e);
        }
        catch (Exception e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see ca.cn.servicedelivery.carshipment.business.rules.core.util.
     * PriorityOrdered#getPriority()
     */
    @Override
    public int getPriority() {

        return this.priority;
    }

    /**
     * Allow to get the original method definition
     * 
     * @return
     */
    public Method getMethod() {

        return this.method;
    }

    /**
     * Allow to get the class that implement this method
     * 
     * @return
     */
    public Class<?> getOwnerClass() {

        return this.ownerClass;
    }

    public ParameterMetadata[] getParametersMetadata() {

        return this.parametersMetadata;
    }

    public Class<?> getReturnType() {

        return this.returnType;
    }

    public void setReturnType(Class<?> returnType) {

        this.returnType = returnType;
    }
}
