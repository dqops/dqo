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
    private int samplesLimit = 100;

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
     * Returns the default limit of column value samples that are collected.
     * @return The default limit of column value samples.
     */
    public int getSamplesLimit() {
        return samplesLimit;
    }

    /**
     * Sets the default limit of column value samples.
     * @param samplesLimit The default limit of column value samples.
     */
    public void setSamplesLimit(int samplesLimit) {
        this.samplesLimit = samplesLimit;
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
