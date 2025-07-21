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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Historic data point that is passed to the rule when a rule requires past values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class HistoricDataPoint {
    /**
     * Timestamp (based on UTC timezone) of the previous readout as a Linux epoch time (seconds).
     */
    private long timestampUtcEpoch;

    /**
     * Local date time of the previous readout (when the time zone is not important) as a Linux epoch time (seconds), but with time zone shift applied.
     */
    private long localDatetimeEpoch;

    /**
     * Negative index (like -1, -2...) of this value. This value says that the historic data point is -1, -2 or more time periods (e.q. days, hours) before the time window of the current sensor readout that is evaluated by a rule.
     */
    private int backPeriodsIndex;

    /**
     * Sensor readout from the past.
     */
    private Double sensorReadout;

    /**
     * Previously predicted expected value.
     */
    private Double expectedValue;

    /**
     * Default (empty) constructor.
     */
    public HistoricDataPoint() {
    }

    /**
     * Creates a historic data point given the timestamp and the value of a historic sensor readout.
     * @param timestampUtc Timestamp of the readout (absolute UTC timestamp)
     * @param localDatetime Local date time of the readout.
     * @param backPeriodsIndex Index of the time period related to the current time period.
     * @param sensorReadout Sensor readout from the past.
     * @param expectedValue Previously predicted expected value.
     */
    public HistoricDataPoint(Instant timestampUtc, LocalDateTime localDatetime, int backPeriodsIndex, Double sensorReadout, Double expectedValue) {
        if (timestampUtc != null) {
            this.timestampUtcEpoch = timestampUtc.toEpochMilli() / 1000L;
        }
        if (localDatetime != null) {
            this.localDatetimeEpoch = localDatetime.toEpochSecond(ZoneOffset.UTC);
        }

        this.backPeriodsIndex = backPeriodsIndex;
        this.sensorReadout = sensorReadout;
        this.expectedValue = expectedValue;
    }

    /**
     * Returns the time period as an UTC timestamp, in a form of an epoch.
     * @return Time series.
     */
    public long getTimestampUtcEpoch() {
        return timestampUtcEpoch;
    }

    /**
     * Sets the time as an UTC timestamp (epoch).
     * @param timestampUtcEpoch Timestamp as an UTC epoch.
     */
    public void setTimestampUtcEpoch(long timestampUtcEpoch) {
        this.timestampUtcEpoch = timestampUtcEpoch;
    }

    /**
     * Returns the time period as a Linux epoch time, but shifted by a time zone.
     * @return Time period shifted by a time period, as an epoch (seconds).
     */
    public long getLocalDatetimeEpoch() {
        return localDatetimeEpoch;
    }

    /**
     * Sets the time period as an epoch time, but shifted by the time zone.
     * @param localDatetimeEpoch Time period local time, as an epoch (seconds).
     */
    public void setLocalDatetimeEpoch(long localDatetimeEpoch) {
        this.localDatetimeEpoch = localDatetimeEpoch;
    }

    /**
     * Returns the back index (-1, -2, -3, ..) of the historic time period before the current sensor readout.
     * @return Back period index.
     */
    public int getBackPeriodsIndex() {
        return backPeriodsIndex;
    }

    /**
     * Sets the past period negative index.
     * @param backPeriodsIndex Back period index.
     */
    public void setBackPeriodsIndex(int backPeriodsIndex) {
        this.backPeriodsIndex = backPeriodsIndex;
    }

    /**
     * Gets the value of the past sensor readout.
     * @return Past sensor readout.
     */
    public Double getSensorReadout() {
        return sensorReadout;
    }

    /**
     * Sets the past sensor readout.
     * @param sensorReadout Sensor readout.
     */
    public void setSensorReadout(Double sensorReadout) {
        this.sensorReadout = sensorReadout;
    }

    /**
     * Returns the previously predicted expected value.
     * @return Previously predicted expected value.
     */
    public Double getExpectedValue() {
        return expectedValue;
    }

    /**
     * Stores a previously predicted expected value.
     * @param expectedValue Previously predicted expected value.
     */
    public void setExpectedValue(Double expectedValue) {
        this.expectedValue = expectedValue;
    }
}
