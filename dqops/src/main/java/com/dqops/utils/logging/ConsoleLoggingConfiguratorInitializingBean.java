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

package com.dqops.utils.logging;

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
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
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
    private final DqoLoggingConfigurationProperties loggingConfigurationProperties;
    private final DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private final DqoUserPrincipalProvider userPrincipalProvider;

    /**
     * Dependency injection constructor.
     * @param loggingConfigurationProperties Configuration parameters with the logging settings.
     * @param dqoCloudApiKeyProvider DQOps Cloud api key provider.
     * @param userPrincipalProvider Local user principal provider.
     */
    @Autowired
    public ConsoleLoggingConfiguratorInitializingBean(
            DqoLoggingConfigurationProperties loggingConfigurationProperties,
            DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
            DqoUserPrincipalProvider userPrincipalProvider) {
        this.loggingConfigurationProperties = loggingConfigurationProperties;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.userPrincipalProvider = userPrincipalProvider;
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
        ConsoleAppender<ILoggingEvent> consoleAppenderError = createConsoleAppender();
        ConsoleAppender<ILoggingEvent> consoleAppenderNonError = createConsoleAppender();
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
     * Creates a valid console appender. When JSON logging is enabled, creates a special appender.
     * @return Console appender.
     */
    private ConsoleAppender<ILoggingEvent> createConsoleAppender() {
        DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey(userPrincipal.getDomainIdentity());
        DqoCloudApiKeyPayload apiKeyPayload = apiKey != null ? apiKey.getApiKeyPayload() : null;

        ConsoleAppender<ILoggingEvent> consoleAppender =
                this.loggingConfigurationProperties.getConsole() == DqoConsoleLoggingMode.JSON
                        ? new AugmentingConsoleAppender(this.loggingConfigurationProperties.isEncodeMessage(), apiKeyPayload,
                                                        this.loggingConfigurationProperties.getJsonMessageMaxLength()) :
                        new ConsoleAppender<>();
        return consoleAppender;
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
        String timestampPattern = !Strings.isNullOrEmpty(this.loggingConfigurationProperties.getJsonTimestampPattern())
                ? this.loggingConfigurationProperties.getJsonTimestampPattern()
                : "[ISO_INSTANT]";
        jsonEncoder.setTimestampPattern(timestampPattern);

        ShortenedThrowableConverter throwableConverter =
                this.loggingConfigurationProperties.isEncodeMessage() ?
                new EncodingShortenedThrowableConverter() : new ShortenedThrowableConverter();
        throwableConverter.setRootCauseFirst(true);
        jsonEncoder.setThrowableConverter(throwableConverter);

        LogstashFieldNames fieldNames = new LogstashFieldNames();
        if (!Strings.isNullOrEmpty(this.loggingConfigurationProperties.getJsonLogFieldLevel())) {
            fieldNames.setLevel(this.loggingConfigurationProperties.getJsonLogFieldLevel());
        }
        if (!Strings.isNullOrEmpty(this.loggingConfigurationProperties.getJsonLogFieldMessage())) {
            fieldNames.setMessage(this.loggingConfigurationProperties.getJsonLogFieldMessage());
        }
        if (!Strings.isNullOrEmpty(this.loggingConfigurationProperties.getJsonLogFieldTimestamp())) {
            fieldNames.setTimestamp(this.loggingConfigurationProperties.getJsonLogFieldTimestamp());
        }
        if (!Strings.isNullOrEmpty(this.loggingConfigurationProperties.getJsonLogFieldArguments())) {
            fieldNames.setArguments(this.loggingConfigurationProperties.getJsonLogFieldArguments());
        }
        jsonEncoder.setFieldNames(fieldNames);

        jsonEncoder.setIncludeKeyValuePairs(true);
        jsonEncoder.setIncludeStructuredArguments(true);
        jsonEncoder.setIncludeNonStructuredArguments(true);

        jsonEncoder.start();
        return jsonEncoder;
    }
}
