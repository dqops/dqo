/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;

/**
 * Wrapper over a throwable (exception) formatter that changes double quotes " with a quoted text \".
 * It is required for logging inside a docker container.
 */
public class EncodingShortenedThrowableConverter extends ShortenedThrowableConverter {
    /**
     * Fixes stack traces that have a double quote, because the docker logging engine does not enclose them with proper quotes.
     *
     * @param event Event to convert.
     * @return Encoded message.
     */
    @Override
    public String convert(ILoggingEvent event) {
        String convertedMessage = super.convert(event);
        if (convertedMessage == null) {
            return null;
        }

        if (convertedMessage.indexOf('\\') >= 0) {
            convertedMessage = convertedMessage.replace("\\", "\\\\");
        }
        
        if (convertedMessage.indexOf('"') >= 0) {
            convertedMessage = convertedMessage.replace("\"", "\\\"");
        }

        return convertedMessage;
    }
}
