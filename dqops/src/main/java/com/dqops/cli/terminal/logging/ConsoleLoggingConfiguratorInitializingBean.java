/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.cli.terminal.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import ch.qos.logback.core.spi.FilterReply;
import com.dqops.core.configuration.DqoLoggingConfigurationProperties;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.fieldnames.LogstashFieldNames;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.apache.parquet.Strings;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Bootstrap component that configures console logging during boot.
 */
@Component
@Lazy(false)
public class ConsoleLoggingConfiguratorInitializingBean implements InitializingBean {
    private DqoLoggingConfigurationProperties loggingConfigurationProperties;

    /**
     * Dependency injection constructor.
     * @param loggingConfigurationProperties Configuration parameters with the logging settings.
     */
    @Autowired
    public ConsoleLoggingConfiguratorInitializingBean(DqoLoggingConfigurationProperties loggingConfigurationProperties) {
        this.loggingConfigurationProperties = loggingConfigurationProperties;
    }

    /**
     * Called by Spring Boot when the application starts.
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.loggingConfigurationProperties.getConsole() == null ||
                this.loggingConfigurationProperties.getConsole() == DqoConsoleLoggingMode.OFF) {
            return;
        }

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        EncodingConsoleAppender<ILoggingEvent> consoleAppenderError = new EncodingConsoleAppender<>();
        EncodingConsoleAppender<ILoggingEvent> consoleAppenderNonError = new EncodingConsoleAppender<>();
        Encoder<ILoggingEvent> encoderError = null;
        Encoder<ILoggingEvent> encoderNonError = null;

        switch (loggingConfigurationProperties.getConsole()) {
            case OFF:
                return;

            case PATTERN:
                encoderError = createPatternLayoutEncoder(loggerContext);
                encoderNonError = createPatternLayoutEncoder(loggerContext);
                consoleAppenderError.setWithJansi(this.loggingConfigurationProperties.isConsoleWithJansi());
                consoleAppenderNonError.setWithJansi(this.loggingConfigurationProperties.isConsoleWithJansi());
                break;

            case JSON:
                encoderError = createJsonEncoder(loggerContext);
                encoderNonError = createJsonEncoder(loggerContext);
                consoleAppenderError.setWithJansi(false);
                consoleAppenderNonError.setWithJansi(false);
                consoleAppenderError.setEncodeCharacters(true);
                consoleAppenderNonError.setEncodeCharacters(true);
                break;
        }

        consoleAppenderError.setEncoder(encoderError);
        consoleAppenderError.setImmediateFlush(this.loggingConfigurationProperties.isConsoleImmediateFlush());
        LevelFilter errorLevelFilter = new LevelFilter();
        errorLevelFilter.setLevel(Level.ERROR);
        errorLevelFilter.setOnMatch(FilterReply.NEUTRAL);
        errorLevelFilter.setOnMismatch(FilterReply.DENY);
        errorLevelFilter.start();
        consoleAppenderError.addFilter(errorLevelFilter);
        if (this.loggingConfigurationProperties.isLogErrorsToStderr()) {
            consoleAppenderError.setTarget(ConsoleTarget.SystemErr.getName());
        }
        consoleAppenderError.start();
        rootLogger.addAppender(consoleAppenderError);

        consoleAppenderNonError.setEncoder(encoderNonError);
        consoleAppenderNonError.setImmediateFlush(this.loggingConfigurationProperties.isConsoleImmediateFlush());
        LevelFilter notErrorLevelFilter = new LevelFilter();
        notErrorLevelFilter.setLevel(Level.ERROR);
        notErrorLevelFilter.setOnMatch(FilterReply.DENY);
        notErrorLevelFilter.setOnMismatch(FilterReply.NEUTRAL);
        notErrorLevelFilter.start();
        consoleAppenderNonError.addFilter(notErrorLevelFilter);
        consoleAppenderNonError.start();
        rootLogger.addAppender(consoleAppenderNonError);
    }

    /**
     * Creates a pattern layout formatter.
     * @param loggerContext Logger context.
     * @return Patter layout formatter encoder.
     */
    private PatternLayoutEncoder createPatternLayoutEncoder(LoggerContext loggerContext) {
        PatternLayoutEncoder lineEncoder = new PatternLayoutEncoder();
        lineEncoder.setContext(loggerContext);
        lineEncoder.setPattern(this.loggingConfigurationProperties.getPattern());
        lineEncoder.setCharset(StandardCharsets.UTF_8);
        lineEncoder.start();
        return lineEncoder;
    }

    /**
     * Creates a json encoder that returns messages as json objects.
     * @param loggerContext Logger context.
     * @return Json encoder.
     */
    private LogstashEncoder createJsonEncoder(LoggerContext loggerContext) {
        LogstashEncoder jsonEncoder = new LogstashEncoder();
        jsonEncoder.setContext(loggerContext);
        jsonEncoder.setTimestampPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        jsonEncoder.setThrowableConverter(throwableConverter);
        LogstashFieldNames fieldNames = new LogstashFieldNames();
        if (!Strings.isNullOrEmpty(this.loggingConfigurationProperties.getJsonLogFieldLevel())) {
            fieldNames.setLevel(this.loggingConfigurationProperties.getJsonLogFieldLevel());
        }
        jsonEncoder.setFieldNames(fieldNames);
        jsonEncoder.start();
        return jsonEncoder;
    }
}
