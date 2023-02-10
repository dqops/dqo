/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.logging that configures how file logging works inside the DQO user home .log folder.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.logging")
@EqualsAndHashCode(callSuper = false)
public class DqoLoggingConfigurationProperties implements Cloneable {
    /**
     * The default logging pattern used in logs inside the user home's .logs folder.
     */
    public static final String DEFAULT_PATTERN = "%-12date{YYYY-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} -%kvp- %msg%rootException%n";

    /**
     * Default maximum number of old files that are stored.
     */
    public static final int DEFAULT_MAX_HISTORY = 7;

    /**
     * Default total file size cap for log files before files are deleted to save space.
     */
    public static final String DEFAULT_TOTAL_SIZE_CAP = "10mb";

    private boolean enableUserHomeLogging = true;
    private Integer maxHistory = DEFAULT_MAX_HISTORY;
    private String pattern = DEFAULT_PATTERN;
    private String totalSizeCap = DEFAULT_TOTAL_SIZE_CAP;

    /**
     * Returns the flag if file logging inside the user home's .log folder should be enabled.
     * @return True when logging should be enabled.
     */
    public boolean isEnableUserHomeLogging() {
        return enableUserHomeLogging;
    }

    /**
     * Sets the flag too enable logging inside the user home folder.
     * @param enableUserHomeLogging Enable logging in the user home folder.
     */
    public void setEnableUserHomeLogging(boolean enableUserHomeLogging) {
        this.enableUserHomeLogging = enableUserHomeLogging;
    }

    /**
     * Returns the number of historic log files to preserve.
     * @return Number of historic log files to preserve.
     */
    public Integer getMaxHistory() {
        return maxHistory;
    }

    /**
     * Sets the number of historic log files to store.
     * @param maxHistory Number of historic log files.
     */
    public void setMaxHistory(Integer maxHistory) {
        this.maxHistory = maxHistory;
    }

    /**
     * Returns the logging pattern for logback used for file logging.
     * @return Logback log entry pattern.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Sets the pattern used for log entries in the file logs.
     * @param pattern Logging pattern.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Returns the total log file size cap. For example: 10mb. See {@link ch.qos.logback.core.util.FileSize} for more patterns.
     * @return Total log file size cap.
     */
    public String getTotalSizeCap() {
        return totalSizeCap;
    }

    /**
     * Sets the total log size cap, for example "10mb".
     * @param totalSizeCap Total log file size cap.
     */
    public void setTotalSizeCap(String totalSizeCap) {
        this.totalSizeCap = totalSizeCap;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoLoggingConfigurationProperties clone() {
        try {
            DqoLoggingConfigurationProperties cloned = (DqoLoggingConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
