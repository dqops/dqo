/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks;

import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of time scale of monitoring and partitioned data quality checks (daily, monthly, etc.)
 */
public enum CheckTimeScale {
    @JsonProperty("daily")
    daily,  // lowercase name to make swagger work

    @JsonProperty("monthly")
    monthly; // lowercase name to make swagger work

    /**
     * Converts the time series to a matching time gradient.
     * @return Time gradient.
     */
    public TimePeriodGradient toTimeSeriesGradient() {
        if (this == daily) {
            return TimePeriodGradient.day;
        }
        else if (this == monthly) {
            return TimePeriodGradient.month;
        }
        else {
            throw new UnsupportedOperationException("Time scale " + this + " mapping missing, add it here.");
        }
    }

    public static class CheckTimeScaleSampleFactory implements SampleValueFactory<CheckTimeScale> {
        @Override
        public CheckTimeScale createSample() {
            return daily;
        }
    }
}
