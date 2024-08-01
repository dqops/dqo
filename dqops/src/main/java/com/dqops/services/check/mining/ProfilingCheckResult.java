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

package com.dqops.services.check.mining;

import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.data.checkresults.models.CheckResultsOverviewDataModel;
import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.utils.conversion.NumericTypeConverter;

import java.time.Instant;

/**
 * Information retrieved from a single profiling, which is the severity level and a configuration of a data quality check.
 */
public class ProfilingCheckResult {
    /**
     * Sensor name.
     */
    private String sensorName;

    /**
     * Check hash that identifies the check.
     */
    private Long checkHash;

    /**
     * Profiling check model with the full configuration of the profiling check, sensor parameters and rule parameters. This information must be added.
     */
    private CheckModel profilingCheckModel;

    /**
     * Rule severity level.
     */
    private CheckResultStatus severityLevel;

    /**
     * Actual value.
     */
    private Double actualValue;

    /**
     * The timestamp when the actual value was collected.
     */
    private Instant executedAt;

    /**
     * A full check results overview model from which the values are retrieved.
     */
    private CheckResultsOverviewDataModel checkResultsOverviewModel;

    /**
     * Returns the sensor name.
     * @return Sensor name.
     */
    public String getSensorName() {
        return sensorName;
    }

    /**
     * Returns a check hash, it is the hash of the hierarchy ID of the check model instance.
     * @return Check hash.
     */
    public Long getCheckHash() {
        return checkHash;
    }

    /**
     * Returns a check model for the check.
     * @return The model of the check.
     */
    public CheckModel getProfilingCheckModel() {
        return profilingCheckModel;
    }

    /**
     * Sets a reference to the check model of the check.
     * @param profilingCheckModel Profiling check model.
     */
    public void setProfilingCheckModel(CheckModel profilingCheckModel) {
        this.profilingCheckModel = profilingCheckModel;
    }

    /**
     * The severity level of the last execution of the check.
     * @return Severity level or null, when the profiling check was never run, or it had no rule defined.
     */
    public CheckResultStatus getSeverityLevel() {
        return severityLevel;
    }

    /**
     * Sets the severity level of the last check. Should be used only from unit tests, because we are importing this value using dedicated methods.
     * @param severityLevel Severity level.
     */
    public void setSeverityLevel(CheckResultStatus severityLevel) {
        this.severityLevel = severityLevel;
    }

    /**
     * Returns the most recent actual value (the sensor readout), retrieved from the sensor. It is the value of the profiling check, or alternatively the value of a similar statistics.
     * @return The most recent actual value.
     */
    public Double getActualValue() {
        return actualValue;
    }

    /**
     * Sets the actual value for a check.
     * @param actualValue Actual value.
     */
    public void setActualValue(Double actualValue) {
        this.actualValue = actualValue;
    }

    /**
     * Returns the result of the check overview with the most recent value.
     * @return Check overview with the most recent value.
     */
    public CheckResultsOverviewDataModel getCheckResultsOverviewModel() {
        return checkResultsOverviewModel;
    }

    /**
     * Imports the most recent check result from the check results overview.
     * @param checkResultsOverviewModel Check results overview.
     */
    public void importCheckResultsOverview(CheckResultsOverviewDataModel checkResultsOverviewModel) {
        this.checkHash = checkResultsOverviewModel.getCheckHash();
        this.checkResultsOverviewModel = checkResultsOverviewModel;
        if (!checkResultsOverviewModel.getResults().isEmpty()) {
            this.actualValue = checkResultsOverviewModel.getResults().get(checkResultsOverviewModel.getResults().size() - 1);
            this.severityLevel = checkResultsOverviewModel.getStatuses().get(checkResultsOverviewModel.getStatuses().size() - 1);
            this.executedAt = checkResultsOverviewModel.getExecutedAtTimestamps().get(checkResultsOverviewModel.getExecutedAtTimestamps().size() - 1);
        }
    }

    /**
     * Import a check model object into the results.
     * @param checkModel Check model object.
     */
    public void importCheckModel(CheckModel checkModel) {
        this.profilingCheckModel = checkModel;
        this.sensorName = checkModel.getSensorName();
        this.checkHash = checkModel.getCheckHash();
    }

    /**
     * Import statistics (the actual value from the statistics sensor, instead of the profiling checks sensor).
     * @param statisticsMetricModel Statistics model.
     */
    public void importStatistics(StatisticsMetricModel statisticsMetricModel) {
        if (this.actualValue == null) {
            this.actualValue = NumericTypeConverter.tryConvertToDouble(statisticsMetricModel.getResult());
            if (this.actualValue != null) {
                this.executedAt = statisticsMetricModel.getExecutedAt();
            }
        }
    }
}
