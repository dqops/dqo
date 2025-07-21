/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.logging.user-errors that configures how user errors of running checks or parsing YAML files are reported.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.logging.user-errors")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoLoggingUserErrorsConfigurationProperties implements Cloneable {
    /**
     * Log level for reporting issues captured by sensors.
     */
    private Level sensorsLogLevel = Level.WARN;

    /**
     * Log level for reporting issues captured by rules.
     */
    private Level rulesLogLevel = Level.WARN;

    /**
     * Log level for reporting issues captured by checks.
     */
    private Level checksLogLevel = Level.WARN;

    /**
     * Log level for reporting issues captured by statistics collection.
     */
    private Level statisticsLogLevel = Level.WARN;

    /**
     * Log level for reporting issues captured when parsing YAML files.
     */
    private Level yamlLogLevel = Level.WARN;

    /**
     * Comma separated list of key-value pairs added to log messages with issues from sensors.
     */
    private String sensorsAdditionalKeyValuePairs;

    /**
     * Comma separated list of key-value pairs added to log messages with issues from rules.
     */
    private String rulesAdditionalKeyValuePairs;

    /**
     * Comma separated list of key-value pairs added to log messages with issues from checks.
     */
    private String checksAdditionalKeyValuePairs;

    /**
     * Comma separated list of key-value pairs added to log messages with issues from statistics collection.
     */
    private String statisticsAdditionalKeyValuePairs;

    /**
     * Comma separated list of key-value pairs added to log messages with issues from parsing invalid YAML files.
     */
    private String yamlAdditionalKeyValuePairs;

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoLoggingUserErrorsConfigurationProperties clone() {
        try {
            DqoLoggingUserErrorsConfigurationProperties cloned = (DqoLoggingUserErrorsConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
