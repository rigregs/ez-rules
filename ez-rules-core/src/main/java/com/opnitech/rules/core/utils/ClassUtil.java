package com.opnitech.rules.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 * Helper class to deal with some of classes manipulation in the system
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class ClassUtil {

    private ClassUtil() {
        // Default constructor
    }

    /**
     * Return the list of instances base in a mix list of classes, instances and
     * class names
     * 
     * @param <ObjectType>
     *            Generic type of the instances
     * @param values
     *            Mix list of classes, instances and class names
     * @return List of instances
     * @throws Exception
     *             Throw an exception if the instances cannot be created
     */
    public static <ObjectType> List<ObjectType> createInstances(List<ObjectType> values) throws Exception {

        List<ObjectType> instances = new ArrayList<>();

        for (ObjectType value : values) {
            instances.add(ClassUtil.<ObjectType> createInstance(value));
        }

        return instances;
    }

    /**
     * Will create the instances of the objects, if the value is a string will
     * try to convert it to a class and create the instance. If the value is a
     * class will create the instance. If the value is already an instance it
     * will just return the same value.
     * 
     * @param <ObjectType>
     *            Generic type of the instances
     * @param value
     *            Instance, class name or class
     * @return The instance
     * @throws Exception
     *             Throw an exception if the instance cannot be created
     */
    @SuppressWarnings("unchecked")
    public static <ObjectType> ObjectType createInstance(Object value) throws Exception {

        Validate.notNull(value);

        return (ObjectType) (value.getClass().isAssignableFrom(Class.class)
                ? ClassUtil.createInstance((Class<ObjectType>) value)
                : value.getClass().isAssignableFrom(String.class)
                        ? ClassUtil.createInstance((String) value)
                        : value);
    }

    /**
     * If you pass a class value will return the class, otherwise will try to
     * get the class from the string re[presentation of the value
     * 
     * @param <ClassType>
     *            The type of the returned class
     * @param value
     *            Value that represent the class, can be the class or the class
     *            name
     * @return The class
     * @throws Exception
     *             Throw an exception if the class cannot be created
     */
    @SuppressWarnings("unchecked")
    public static <ClassType> Class<ClassType> createClass(Object value) throws Exception {

        Validate.notNull(value);

        return (Class<ClassType>) (value.getClass().isAssignableFrom(Class.class)
                ? value
                : value.getClass().isAssignableFrom(String.class)
                        ? Class.forName((String) value)
                        : value.getClass());
    }

    /**
     * Create an instance from the class name
     * 
     * @param <ObjectType>
     *            Generic type of the instances
     * @param className
     *            Class name of the instance will be created
     * @return The created instance
     * @throws Exception
     *             Throw an exception if something happen trying creating the
     *             instance
     */
    @SuppressWarnings("unchecked")
    public static <ObjectType> ObjectType createInstance(String className) throws Exception {

        Validate.notNull(className);

        return (ObjectType) ClassUtil.createInstance(Class.forName(className));
    }

    /**
     * Create an instance from the class
     * 
     * @param <ObjectType>
     *            Generic type of the instances
     * @param clazz
     *            The instance class
     * @return The created instance
     * @throws Exception
     *             Throw an exception if something happen trying creating the
     *             instance
     */
    public static <ObjectType> ObjectType createInstance(Class<ObjectType> clazz) throws Exception {

        Validate.notNull(clazz);

        return clazz.newInstance();
    }

    /**
     * Resolve the first element of a class that match with the specified class
     * 
     * @param <EntityType>
     *            Entity generic type
     * @param entities
     *            List of entities
     * @param entityClass
     *            Class of the entity to be resolved
     * @return The resolved entity
     */
    @SuppressWarnings("unchecked")
    public static <EntityType> EntityType resolveEntity(Class<?> entityClass, Collection<Object> entities) {

        for (Object entity : entities) {
            if (entity.getClass().isAssignableFrom(entityClass)) {
                return (EntityType) entity;
            }
        }

        return null;
    }

    /**
     * Resolve all elements that match with the specified class
     * 
     * @param <EntityType>
     *            Entity generic type
     * @param entities
     *            The list of entities to be resolved
     * @param entityClass
     *            Class of the entity to be resolved
     * @return List of the resolved entities
     */
    @SuppressWarnings("unchecked")
    public static <EntityType> List<EntityType> resolveEntities(Class<?> entityClass, List<Object> entities) {

        List<EntityType> matchEntities = new ArrayList<>();

        for (Object entity : entities) {
            if (entity.getClass().isAssignableFrom(entityClass)) {
                matchEntities.add((EntityType) entity);
            }
        }

        return matchEntities;
    }
}
