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

import lombok.EqualsAndHashCode;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.statistics that configures how the data statistics collector (basic profiler) works.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.statistics")
@EqualsAndHashCode(callSuper = false)
public class DqoStatisticsCollectorConfigurationProperties implements Cloneable {
    private int truncatedStringsLength = 50;
    private int viewedStatisticsAgeMonths = 3;
    private Level logLevel = Level.WARN;

    /**
     * Returns the length of a the results returned by a statistics collector (for min, max operations) on string columns.
     * @return Maximum string length that is stored in the statistics results.
     */
    public int getTruncatedStringsLength() {
        return truncatedStringsLength;
    }

    /**
     * Sets the length of strings that are stored in the result_string column in the statistics table.
     * @param truncatedStringsLength Maximum string length.
     */
    public void setTruncatedStringsLength(int truncatedStringsLength) {
        this.truncatedStringsLength = truncatedStringsLength;
    }

    /**
     * Returns the number of full months with statistics results that are scanned and shown. Older statistics results are not shown in UI.
     * @return Showed oldest statistics results.
     */
    public int getViewedStatisticsAgeMonths() {
        return viewedStatisticsAgeMonths;
    }

    /**
     * Sets the number of months (monthly partitions) that are loaded to be shown on the statistics tabs.
     * @param viewedStatisticsAgeMonths Number of monthly partitions that are scanned.
     */
    public void setViewedStatisticsAgeMonths(int viewedStatisticsAgeMonths) {
        this.viewedStatisticsAgeMonths = viewedStatisticsAgeMonths;
    }

    /**
     * Returns the logging level for reporting any errors encountered during statistics collection.
     * @return The logging error level for statistics collection errors.
     */
    public Level getLogLevel() {
        return logLevel;
    }

    /**
     * Sets the logging level for statistics collection errors.
     * @param logLevel Logging level for statistics collection errors.
     */
    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoStatisticsCollectorConfigurationProperties clone() {
        try {
            DqoStatisticsCollectorConfigurationProperties cloned = (DqoStatisticsCollectorConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
