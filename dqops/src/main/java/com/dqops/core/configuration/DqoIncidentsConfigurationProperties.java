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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo.incidents that configures how the incidents are loaded and managed.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.incidents")
@EqualsAndHashCode(callSuper = false)
public class DqoIncidentsConfigurationProperties implements Cloneable {
    private int countOpenIncidentsDays = 60;
    private int topIncidentsDays = 30;
    private int columnHistogramSize = 10;
    private int checkHistogramSize = 10;
    private int partitionedChecksTimeWindowDays = 45;

    /**
     * Returns the number of days back that DQOps counts open incidents per day.
     * @return Number of days back to count open incidents.
     */
    public int getCountOpenIncidentsDays() {
        return countOpenIncidentsDays;
    }

    /**
     * Sets the number of days back to count the open incidents.
     * @param countOpenIncidentsDays Number of days back to count open incidents.
     */
    public void setCountOpenIncidentsDays(int countOpenIncidentsDays) {
        this.countOpenIncidentsDays = countOpenIncidentsDays;
    }

    /**
     * Returns the number of days shown on the summary of the top incidents.
     * @return The number of days for the summary of the top incidents.
     */
    public int getTopIncidentsDays() {
        return topIncidentsDays;
    }

    /**
     * Sets the filter for the number of days for the summary of the top incidents per grouping.
     * @param topIncidentsDays Days to scan for top incidents.
     */
    public void setTopIncidentsDays(int topIncidentsDays) {
        this.topIncidentsDays = topIncidentsDays;
    }

    /**
     * Returns the size of a column histogram.
     * @return Column histogram size.
     */
    public int getColumnHistogramSize() {
        return columnHistogramSize;
    }

    /**
     * Sets the size of a column histogram.
     * @param columnHistogramSize Column histogram size.
     */
    public void setColumnHistogramSize(int columnHistogramSize) {
        this.columnHistogramSize = columnHistogramSize;
    }

    /**
     * Returns the size of the check histogram.
     * @return The size of the check histogram.
     */
    public int getCheckHistogramSize() {
        return checkHistogramSize;
    }

    /**
     * Sets the size of the check histogram.
     * @param checkHistogramSize The size of the check histogram.
     */
    public void setCheckHistogramSize(int checkHistogramSize) {
        this.checkHistogramSize = checkHistogramSize;
    }

    /**
     * Returns the time window (in days) for partitioned checks that should be not included in new data quality incidents.
     * When a data quality issue is detected for a daily partition that is older than that number of days, it is not included in the incident and will not trigger creating new incidents.
     * @return The age of a check result for a partitioned check that should not trigger creating new incidents and is not counted.
     */
    public int getPartitionedChecksTimeWindowDays() {
        return partitionedChecksTimeWindowDays;
    }

    /**
     * Sets the time window for the maximum age of a daily or monthly partition whose data quality issues are included in new data quality incidents when an issue is detected.
     * @param partitionedChecksTimeWindowDays Time window in days.
     */
    public void setPartitionedChecksTimeWindowDays(int partitionedChecksTimeWindowDays) {
        this.partitionedChecksTimeWindowDays = partitionedChecksTimeWindowDays;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoIncidentsConfigurationProperties clone() {
        try {
            DqoIncidentsConfigurationProperties cloned = (DqoIncidentsConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
