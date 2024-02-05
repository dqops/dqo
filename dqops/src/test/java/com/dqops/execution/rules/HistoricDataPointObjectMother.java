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
package com.dqops.execution.rules;

import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.utils.datetime.LocalDateTimePeriodUtility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Object mother for {@link HistoricDataPoint}.
 */
public class HistoricDataPointObjectMother {
    /**
     * Creates an array of historic data points for a given time window.
     * @param timeWindowSettingsSpec Time window settings, the default is 14 days.
     * @param gradient Time gradient, for example a DAY.
     * @param readoutTimestamp Timestamp of the current sensor readout. The historic values will be before it.
     * @param values Array of sensor readouts to fill as historic readouts. Nulls are accepted and will generate a missing readouts.
     * @return Array of historic data points.
     */
    public static HistoricDataPoint[] fillHistoricReadouts(RuleTimeWindowSettingsSpec timeWindowSettingsSpec,
                                                           TimePeriodGradient gradient,
                                                           LocalDateTime readoutTimestamp,
                                                           Double[] values,
                                                           Double[] expectedValues) {
        HistoricDataPoint[] historicDataPoints = new HistoricDataPoint[timeWindowSettingsSpec.getPredictionTimeWindow()];

        for( int i = values.length - 1; i >= 0; i--) {
            if (values[i] == null) {
                continue;
            }

            int dataPointBackIndex = -(values.length - i);
            LocalDateTime dataPointLocalDateTime = LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                    readoutTimestamp, dataPointBackIndex, gradient);
            Instant dataPointInstant = dataPointLocalDateTime.toInstant(ZoneOffset.UTC);
            Double expectedValue = expectedValues != null ? expectedValues[i] : null;
            HistoricDataPoint dataPoint = new HistoricDataPoint(dataPointInstant, dataPointLocalDateTime, dataPointBackIndex, values[i], expectedValue);
            historicDataPoints[historicDataPoints.length - values.length + i] = dataPoint;
        }

        return historicDataPoints;
    }
}
