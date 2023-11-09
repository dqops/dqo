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
package com.dqops.checks;

import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.docs.SampleValueFactory;
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
