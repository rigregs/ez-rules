package com.opnitech.rules.core.utils;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

/**
 * @author Rigre Gregorio Garciandia Sonora
 */
public final class LoggerUtil {

    private static final String LEVEL_PATTERN = "{0}{1}";

    private static final String MESSAGE_PATTERN = "[{0} -> {1}]: {2}";

    private LoggerUtil() {
        // Default constructor
    }

    public static void info(Logger logger, Object producer, String context, String message, Object... arguments) {

        info(logger, 0, producer, context, message, arguments);
    }

    public static void info(Logger logger, int level, Object producer, String context, String message, Object... arguments) {

        if (logger.isInfoEnabled()) {
            logger.info(createMessage(level, producer, context, message, arguments));
        }
    }

    private static String createMessage(int level, Object producer, String context, String message, Object... arguments) {

        Validate.notNull(producer);
        Validate.notNull(message);

        String levelMessage = level > 0
                ? MessageFormat.format(LoggerUtil.LEVEL_PATTERN, StringUtils.repeat(" ", level * 4), message)
                : message;

        return MessageFormat.format(LoggerUtil.MESSAGE_PATTERN, producer, StringUtils.isNotBlank(context)
                ? context
                : producer.getClass().getSimpleName(),
                ArrayUtils.isNotEmpty(arguments)
                        ? MessageFormat.format(levelMessage, arguments)
                        : levelMessage);
    }
}
