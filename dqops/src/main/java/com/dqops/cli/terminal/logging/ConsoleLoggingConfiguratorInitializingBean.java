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
import ch.qos.logback.classic.encoder.JsonEncoder;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.dqops.core.configuration.DqoLoggingConfigurationProperties;
import net.logstash.logback.encoder.LogstashEncoder;
import net.logstash.logback.fieldnames.LogstashFieldNames;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

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
        ConsoleAppender<ILoggingEvent> consoleAppenderError = new ConsoleAppender<>();
        ConsoleAppender<ILoggingEvent> consoleAppenderNonError = new ConsoleAppender<>();
        Encoder<ILoggingEvent> encoderError = null;
        Encoder<ILoggingEvent> encoderNonError = null;

        switch (loggingConfigurationProperties.getConsole()) {
            case OFF:
                return;

            case PATTERN:
                PatternLayoutEncoder lineEncoderError = new PatternLayoutEncoder();
                lineEncoderError.setContext(loggerContext);
                lineEncoderError.setPattern(this.loggingConfigurationProperties.getPattern());
                lineEncoderError.start();
                encoderError = lineEncoderError;

                PatternLayoutEncoder lineEncoderNonError = new PatternLayoutEncoder();
                lineEncoderNonError.setContext(loggerContext);
                lineEncoderNonError.setPattern(this.loggingConfigurationProperties.getPattern());
                lineEncoderNonError.start();
                encoderNonError = lineEncoderNonError;


                consoleAppenderError.setWithJansi(this.loggingConfigurationProperties.isConsoleWithJansi());
                consoleAppenderNonError.setWithJansi(this.loggingConfigurationProperties.isConsoleWithJansi());
                break;

            case JSON:
                LogstashEncoder jsonEncoderError = new LogstashEncoder();
                jsonEncoderError.setContext(loggerContext);
                jsonEncoderError.setTimestampPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
                ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
                throwableConverter.setRootCauseFirst(true);
                jsonEncoderError.setThrowableConverter(throwableConverter);
                LogstashFieldNames fieldNamesError = new LogstashFieldNames();
                fieldNamesError.setTimestamp("time");
                fieldNamesError.setMessage("log");
//                fieldNamesError.setLevel("severity_key");
//                fieldNamesError.setLogger("labels_key");
                jsonEncoderError.setFieldNames(fieldNamesError);
                jsonEncoderError.start();
                encoderError = jsonEncoderError;

                LogstashEncoder jsonEncoderNonError = new LogstashEncoder();
                jsonEncoderNonError.setContext(loggerContext);
                jsonEncoderNonError.setTimestampPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
                LogstashFieldNames fieldNamesNonError = new LogstashFieldNames();
                fieldNamesNonError.setTimestamp("time");
                fieldNamesNonError.setMessage("log");
//                fieldNamesNonError.setLevel("severity_key");
//                fieldNamesNonError.setLogger("labels_key");
                jsonEncoderNonError.setFieldNames(fieldNamesNonError);
                jsonEncoderNonError.start();
                encoderNonError = jsonEncoderNonError;

                consoleAppenderError.setWithJansi(false);
                consoleAppenderNonError.setWithJansi(false);
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
        consoleAppenderError.setOutputStream(System.err);
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
}
