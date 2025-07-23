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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A wrapper over a logging event that encodes double quotes in messages.
 */
public class EncodingLoggingEvent implements ILoggingEvent {
    private ILoggingEvent wrappedEvent;
    private Object[] augmentedArgumentArray;
    private boolean quoteMessage;
    private List<KeyValuePair> keyValuePairs;
    private Integer jsonMessageMaxLength;

    /**
     * Creates a wrapper over an original logging event.
     *
     * @param wrappedEvent             The original logging event.
     * @param augmentedArgumentArray   An alternate array of arguments, added to the "attrs" (or "attributes") dictionary.
     * @param quoteMessage             Encodes all double quotes and backslashes the message field again.
     * @param keyValuePairs            Alternative list of key-value pairs.
     * @param jsonMessageMaxLength     Maximum length of a message field, the rest is truncated.
     */
    public EncodingLoggingEvent(ILoggingEvent wrappedEvent,
                                Object[] augmentedArgumentArray,
                                boolean quoteMessage,
                                List<KeyValuePair> keyValuePairs,
                                Integer jsonMessageMaxLength) {
        this.wrappedEvent = wrappedEvent;
        this.augmentedArgumentArray = augmentedArgumentArray;
        this.quoteMessage = quoteMessage;
        this.keyValuePairs = keyValuePairs;
        this.jsonMessageMaxLength = jsonMessageMaxLength;
    }

    @Override
    public String getThreadName() {
        return wrappedEvent.getThreadName();
    }

    @Override
    public Level getLevel() {
        return wrappedEvent.getLevel();
    }

    @Override
    public String getMessage() {
        String message = wrappedEvent.getMessage();
        if (message != null && this.jsonMessageMaxLength != null) {
            if (message.length() > this.jsonMessageMaxLength) {
                message = message.substring(0, this.jsonMessageMaxLength);
            }
        }
        return message;
    }

    /**
     * Returns an augmented argument array.
     * @return Augmented argument array.
     */
    @Override
    public Object[] getArgumentArray() {
        return this.augmentedArgumentArray;
    }

    /**
     * Encodes the message with extra quotes, to avoid issues in logging to docker.
     *
     * @return Extra encoded message with all double quotes that are quoted again.
     */
    @Override
    public String getFormattedMessage() {
        String formattedMessage = wrappedEvent.getFormattedMessage();
        if (formattedMessage != null && this.jsonMessageMaxLength != null) {
            if (formattedMessage.length() > this.jsonMessageMaxLength) {
                formattedMessage = formattedMessage.substring(0, this.jsonMessageMaxLength);
            }
        }

        if (this.quoteMessage && formattedMessage != null && formattedMessage.indexOf('\\') >= 0) {
            formattedMessage = formattedMessage.replace("\\", "\\\\");
        }

        if (this.quoteMessage && formattedMessage != null && formattedMessage.indexOf('"') >= 0) {
            formattedMessage = formattedMessage.replace("\"", "\\\"");
        }

        return formattedMessage;
    }

    @Override
    public String getLoggerName() {
        return wrappedEvent.getLoggerName();
    }

    @Override
    public LoggerContextVO getLoggerContextVO() {
        return wrappedEvent.getLoggerContextVO();
    }

    @Override
    public IThrowableProxy getThrowableProxy() {
        return wrappedEvent.getThrowableProxy();
    }

    @Override
    public StackTraceElement[] getCallerData() {
        return wrappedEvent.getCallerData();
    }

    @Override
    public boolean hasCallerData() {
        return wrappedEvent.hasCallerData();
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public Marker getMarker() {
        return wrappedEvent.getMarker();
    }

    @Override
    public List<Marker> getMarkerList() {
        return wrappedEvent.getMarkerList();
    }

    @Override
    public Map<String, String> getMDCPropertyMap() {
        return wrappedEvent.getMDCPropertyMap();
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public Map<String, String> getMdc() {
        return wrappedEvent.getMdc();
    }

    @Override
    public long getTimeStamp() {
        return wrappedEvent.getTimeStamp();
    }

    @Override
    public int getNanoseconds() {
        return wrappedEvent.getNanoseconds();
    }

    @Override
    public Instant getInstant() {
        return wrappedEvent.getInstant();
    }

    @Override
    public long getSequenceNumber() {
        return wrappedEvent.getSequenceNumber();
    }

    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        if (this.keyValuePairs != null) {
            return this.keyValuePairs;
        }
        return wrappedEvent.getKeyValuePairs();
    }

    @Override
    public void prepareForDeferredProcessing() {
        wrappedEvent.prepareForDeferredProcessing();
    }
}
