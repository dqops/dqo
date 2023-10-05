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
package com.dqops.core.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.logging.execution that configures how user errors of running checks are reported.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.logging.execution")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoLoggingExecutionConfigurationProperties implements Cloneable {
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
    public DqoLoggingExecutionConfigurationProperties clone() {
        try {
            DqoLoggingExecutionConfigurationProperties cloned = (DqoLoggingExecutionConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
