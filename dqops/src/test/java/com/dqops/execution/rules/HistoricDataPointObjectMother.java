/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
