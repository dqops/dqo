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

import com.dqops.core.configuration.DqoLoggingExecutionConfigurationProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.parquet.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Logger used for logging check, sensor and rule execution issues, selecting the logger name and severity.
 */
@Component
public class UserErrorLoggerImpl implements UserErrorLogger {
    /**
     * Logger name for issues from sensors.
     */
    public static final String LOGGER_NAME_SENSORS = "com.dqops.user-errors.sensors";

    /**
     * Logger name for issues from rules.
     */
    public static final String LOGGER_NAME_RULES = "com.dqops.user-errors.rules";

    /**
     * Logger name for issues from running checks.
     */
    public static final String LOGGER_NAME_CHECKS = "com.dqops.user-errors.checks";

    /**
     * Logger name for issues from statistics.
     */
    public static final String LOGGER_NAME_STATISTICS = "com.dqops.user-errors.statistics";

    /**
     * Logger name for issues from parsing YAML files.
     */
    public static final String LOGGER_NAME_YAML = "com.dqops.user-errors.yaml";

    private final Logger sensorsLogger = LoggerFactory.getLogger(LOGGER_NAME_SENSORS);
    private final Logger rulesLogger = LoggerFactory.getLogger(LOGGER_NAME_RULES);
    private final Logger checksLogger = LoggerFactory.getLogger(LOGGER_NAME_CHECKS);
    private final Logger statisticsLogger = LoggerFactory.getLogger(LOGGER_NAME_STATISTICS);
    private final Logger yamlLogger = LoggerFactory.getLogger(LOGGER_NAME_YAML);
    private final DqoLoggingExecutionConfigurationProperties configurationProperties;

    /**
     * Dependency injection constructor.
     * @param configurationProperties Logging configuration properties.
     */
    @Autowired
    public UserErrorLoggerImpl(DqoLoggingExecutionConfigurationProperties configurationProperties) {

        this.configurationProperties = configurationProperties;
    }

    /**
     * Logs a sensor issue.
     * @param message Message to log.
     * @param cause Exception to log (optional).
     */
    @Override
    public void logSensor(String message, Throwable cause) {
        logInternal(message, cause, this.sensorsLogger, this.configurationProperties.getSensorsLogLevel(),
                this.configurationProperties.getSensorsAdditionalKeyValuePairs());
    }

    /**
     * Logs a rule issue.
     * @param message Message to log.
     * @param cause Exception to log (optional).
     */
    @Override
    public void logRule(String message, Throwable cause) {
        logInternal(message, cause, this.rulesLogger, this.configurationProperties.getRulesLogLevel(),
                this.configurationProperties.getRulesAdditionalKeyValuePairs());
    }

    /**
     * Logs a check issue.
     * @param message Message to log.
     * @param cause Exception to log (optional).
     */
    @Override
    public void logCheck(String message, Throwable cause) {
        logInternal(message, cause, this.checksLogger, this.configurationProperties.getChecksLogLevel(),
                this.configurationProperties.getChecksAdditionalKeyValuePairs());
    }

    /**
     * Logs a statistics collection issue.
     * @param message Message to log.
     * @param cause Exception to log (optional).
     */
    @Override
    public void logStatistics(String message, Throwable cause) {
        logInternal(message, cause, this.statisticsLogger, this.configurationProperties.getStatisticsLogLevel(),
                this.configurationProperties.getStatisticsAdditionalKeyValuePairs());
    }

    /**
     * Logs a yaml schema issues (invalid YAML files).
     *
     * @param message Message to log.
     * @param cause   Exception to log (optional).
     */
    @Override
    public void logYaml(String message, Throwable cause) {
        logInternal(message, cause, this.yamlLogger, this.configurationProperties.getYamlLogLevel(),
                this.configurationProperties.getYamlAdditionalKeyValuePairs());
    }

    /**
     * Internal method that logs the issue to a proper logger.
     * @param message Message to log.
     * @param cause Optional exception.
     * @param targetLogger Target logger.
     * @param logLevel Target log level.
     * @param additionalKeyValuePairs Optional string with key/value pairs in the form: atr1=1,atr2=other
     */
    protected void logInternal(String message,
                               Throwable cause,
                               Logger targetLogger,
                               Level logLevel,
                               String additionalKeyValuePairs) {
        if (logLevel == null || !targetLogger.isEnabledForLevel(logLevel)) {
            return;
        }

        LoggingEventBuilder loggingEventBuilder = targetLogger.atLevel(logLevel);
        loggingEventBuilder.setMessage(message);
        if (cause != null) {
            loggingEventBuilder.setCause(cause);
        }

        if (!Strings.isNullOrEmpty(additionalKeyValuePairs)) {
            String[] keyValuePairs = StringUtils.split(additionalKeyValuePairs, ',');

            for (String keyValuePair : keyValuePairs) {
                String[] keyValuePairElements = StringUtils.split(keyValuePair, '=');
                loggingEventBuilder.addKeyValue(keyValuePairElements[0], keyValuePairElements[1]);
            }
        }

        loggingEventBuilder.log();
    }
}
