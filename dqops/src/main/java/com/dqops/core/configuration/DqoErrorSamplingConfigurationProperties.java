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
 * Configuration POJO with the configuration for the dqo.error-sampling that configures how the error sampler works.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.error-sampling")
@EqualsAndHashCode(callSuper = false)
public class DqoErrorSamplingConfigurationProperties implements Cloneable {
    private int truncatedStringsLength = 100;
    private int samplesLimit = 50;
    private int totalSamplesLimit = 1000;

    /**
     * Returns the length of text samples. Longer texts are truncated.
     * @return Maximum string length that is stored in the error samples results.
     */
    public int getTruncatedStringsLength() {
        return truncatedStringsLength;
    }

    /**
     * Sets the length of text samples. Longer texts are truncated.
     * @param truncatedStringsLength Maximum string length.
     */
    public void setTruncatedStringsLength(int truncatedStringsLength) {
        this.truncatedStringsLength = truncatedStringsLength;
    }

    /**
     * Returns the limit of error samples that are collected for each data grouping. If no grouping is configured on the table, it is the actual limit of samples collected.
     * @return The sample limt.
     */
    public int getSamplesLimit() {
        return samplesLimit;
    }

    /**
     * Sets the limit of error samples collected for each data grouping.
     * @param samplesLimit Samples limit per data grouping.
     */
    public void setSamplesLimit(int samplesLimit) {
        this.samplesLimit = samplesLimit;
    }

    /**
     * Returns the total result limit for all samples collected for multiple data groupings.
     * @return Total samples limit.
     */
    public int getTotalSamplesLimit() {
        return totalSamplesLimit;
    }

    /**
     * Sets a total samples limit when a table has a data grouping configuration.
     * @param totalSamplesLimit Total samples limit for grouped tables.
     */
    public void setTotalSamplesLimit(int totalSamplesLimit) {
        this.totalSamplesLimit = totalSamplesLimit;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoErrorSamplingConfigurationProperties clone() {
        try {
            DqoErrorSamplingConfigurationProperties cloned = (DqoErrorSamplingConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
