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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Historic data point that is passed to the rule when a rule requires past values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class HistoricDataPoint {
    /**
     * Timestamp (based on UTC timezone) of the previous readout.
     */
    private Instant timestampUtc;

    /**
     * Local date time of the previous readout (when the time zone is not important).
     */
    private LocalDateTime localDatetime;

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
        this.timestampUtc = timestampUtc;
        this.localDatetime = localDatetime;
        this.backPeriodsIndex = backPeriodsIndex;
        this.sensorReadout = sensorReadout;
    }

    /**
     * Absolute (UTC based) timestamp of the previous sensor readout.
     * @return Absolute timestamp of the previous sensor readout.
     */
    public Instant getTimestampUtc() {
        return timestampUtc;
    }

    /**
     * Sets the timestamp of the previous readout.
     * @param timestampUtc Timestamp of the previous readout.
     */
    public void setTimestampUtc(Instant timestampUtc) {
        this.timestampUtc = timestampUtc;
    }

    /**
     * Returns a local date time of the previous readout.
     * @return Local date time of the readout.
     */
    public LocalDateTime getLocalDatetime() {
        return localDatetime;
    }

    /**
     * Sets the local date time of the readout.
     * @param localDatetime Local date time of the readout.
     */
    public void setLocalDatetime(LocalDateTime localDatetime) {
        this.localDatetime = localDatetime;
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
