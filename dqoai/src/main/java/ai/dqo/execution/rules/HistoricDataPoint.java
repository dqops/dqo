/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.rules;

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
     * Timestamp (based on UTC timezone) of the previous reading.
     */
    private Instant timestampUtc;

    /**
     * Local date time of the previous reading (when the time zone is not important).
     */
    private LocalDateTime localDatetime;

    /**
     * Negative index (like -1, -2...) of this value. This value says that the historic data point is -1, -2 or more time periods (e.q. days, hours) before the time window of the current sensor reading that is evaluated by a rule.
     */
    private int backPeriodsIndex;

    /**
     * Sensor reading from the past.
     */
    private Double sensorReading;

    /**
     * Default (empty) constructor.
     */
    public HistoricDataPoint() {
    }

    /**
     * Creates a historic data point given the timestamp and the value of a historic sensor reading.
     * @param timestampUtc Timestamp of the reading (absolute UTC timestamp)
     * @param localDatetime Local date time of the reading.
     * @param backPeriodsIndex Index of the time period related to the current time period.
     * @param sensorReading Sensor reading from the past.
     */
    public HistoricDataPoint(Instant timestampUtc, LocalDateTime localDatetime, int backPeriodsIndex, Double sensorReading) {
        this.timestampUtc = timestampUtc;
        this.localDatetime = localDatetime;
        this.backPeriodsIndex = backPeriodsIndex;
        this.sensorReading = sensorReading;
    }

    /**
     * Absolute (UTC based) timestamp of the previous sensor reading.
     * @return Absolute timestamp of the previous sensor reading.
     */
    public Instant getTimestampUtc() {
        return timestampUtc;
    }

    /**
     * Sets the timestamp of the previous reading.
     * @param timestampUtc Timestamp of the previous reading.
     */
    public void setTimestampUtc(Instant timestampUtc) {
        this.timestampUtc = timestampUtc;
    }

    /**
     * Returns a local date time of the previous reading.
     * @return Local date time of the reading.
     */
    public LocalDateTime getLocalDatetime() {
        return localDatetime;
    }

    /**
     * Sets the local date time of the reading.
     * @param localDatetime Local date time of the reading.
     */
    public void setLocalDatetime(LocalDateTime localDatetime) {
        this.localDatetime = localDatetime;
    }

    /**
     * Returns the back index (-1, -2, -3, ..) of the historic time period before the current sensor reading.
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
     * Gets the value of the past sensor reading.
     * @return Past sensor reading.
     */
    public Double getSensorReading() {
        return sensorReading;
    }

    /**
     * Sets the past sensor reading.
     * @param sensorReading Sensor reading.
     */
    public void setSensorReading(Double sensorReading) {
        this.sensorReading = sensorReading;
    }
}
