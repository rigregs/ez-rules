package com.opnitech.rules.core.utils;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;

/**
 * A exception helper to easy the process the same exception triggering pattern
 * in the project
 * 
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class ExceptionUtil {

    public ExceptionUtil() {
        // Default constructor
    }

    /**
     * Helper to throw a {@link IllegalArgumentException} exception.
     * 
     * @param message
     * @param arguments
     * @throws IllegalArgumentException
     */
    public static void throwIllegalArgumentException(String message, Object... arguments) throws IllegalArgumentException {

        if (ArrayUtils.isNotEmpty(arguments)) {
            throw new IllegalArgumentException(MessageFormat.format(message, arguments));
        }

        throw new IllegalArgumentException(message);
    }
}
