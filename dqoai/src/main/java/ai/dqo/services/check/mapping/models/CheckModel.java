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
package ai.dqo.services.check.mapping.models;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.services.check.matching.SimilarCheckModel;
import ai.dqo.services.check.matching.SimilarCheckSensorRuleKey;
import ai.dqo.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UI model that returns the form definition and the form data to edit a single data quality check.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UICheckModel", description = "UI model that returns the form definition and the form data to edit a single data quality check.")
public class CheckModel implements Cloneable {
    /**
     * Data quality check name that is used in YAML file. Identifies the data quality check.
     */
    @JsonPropertyDescription("Data quality check name that is used in YAML.")
    private String checkName;

    /**
     * Help text that describes the data quality check.
     */
    @JsonPropertyDescription("Help text that describes the data quality check.")
    private String helpText;

    /**
     * List of fields for editing the sensor parameters.
     */
    @JsonPropertyDescription("List of fields for editing the sensor parameters.")
    private List<FieldModel> sensorParameters = new ArrayList<>();

    /**
     * Full sensor name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality sensor.
     */
    @JsonPropertyDescription("Full sensor name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality sensor.")
    private String sensorName;

    /**
     * Data quality dimension used for tagging the results of this data quality checks.
     */
    @JsonPropertyDescription("Data quality dimension used for tagging the results of this data quality checks.")
    private String qualityDimension;

    /**
     * Sensor parameters, returned only for reference and for tools such as the documentation generator.
     */
    @JsonIgnore
    private AbstractSensorParametersSpec sensorParametersSpec;

    /**
     * Check specification object, returned only for reference and for tools such as the documentation generator.
     */
    @JsonIgnore
    private AbstractCheckSpec<?, ?, ?, ?> checkSpec;

    /**
     * Threshold (alerting) rules defined for a check.
     */
    @JsonPropertyDescription("Threshold (alerting) rules defined for a check.")
    private RuleThresholdsModel rule;

    /**
     * The data quality check supports a custom data stream mapping configuration.
     */
    @JsonPropertyDescription("The data quality check supports a custom data stream mapping configuration.")
    private boolean supportsDataStreams;

    /**
     * Data streams configuration for a sensor query. When a data stream configuration is assigned at a sensor level, it overrides any data stream settings from the connection, table or column levels. Data streams are configured in two cases: (1) a static data stream level is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.
     */
    @JsonPropertyDescription("Data streams configuration for a sensor query. When a data stream configuration is assigned at a sensor level, it overrides any data stream settings from the connection, table or column levels. Data streams are configured in two cases: (1) a static data stream level is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.")
    private DataStreamMappingSpec dataStreamsOverride;

    /**
     * Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.
     */
    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    private RecurringScheduleSpec scheduleOverride;

    /**
     * Model of configured schedule enabled on the check level.
     */
    @JsonPropertyDescription("Model of configured schedule enabled on the check level.")
    private EffectiveScheduleModel effectiveSchedule;

    /**
     * State of the scheduling override for this check.
     */
    @JsonPropertyDescription("State of the scheduling override for this check.")
    private ScheduleEnabledStatusModel scheduleEnabledStatus;

    /**
     * Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).
     */
    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    private CommentsListSpec comments;

    /**
     * Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.
     */
    @JsonPropertyDescription("Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.")
    private boolean disabled;

    /**
     * Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.
     */
    @JsonPropertyDescription("Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.")
    private boolean excludeFromKpi;

    /**
     * Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.
     */
    @JsonPropertyDescription("Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.")
    private boolean includeInSla;

    /**
     * True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.
     */
    @JsonPropertyDescription("True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.")
    private boolean configured;

    /**
     * SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.
     */
    @JsonPropertyDescription("SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.")
    private String filter;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * The name of a data stream mapping defined at a table that should be used for this check.
     */
    @JsonPropertyDescription("The name of a data stream mapping defined at a table that should be used for this check.")
    private String dataStream;

    /**
     * Type of the check's target (column, table).
     */
    @JsonPropertyDescription("Type of the check's target (column, table).")
    private CheckTargetModel checkTarget;

    /**
     * List of configuration errors that must be fixed before the data quality check could be executed.
     */
    @JsonPropertyDescription("List of configuration errors that must be fixed before the data quality check could be executed.")
    private List<CheckConfigurationRequirementsError> configurationRequirementsErrors;

    /**
     * List of similar checks in other check types or in other time scales.
     */
    @JsonPropertyDescription("List of similar checks in other check types or in other time scales.")
    private List<SimilarCheckModel> similarChecks;
    /**
     * Create a matching key with the sensor name and rule names. Used to match similar checks that are based on the same sensor and rules.
     * @return Check sensor rule key.
     */
    public SimilarCheckSensorRuleKey createSimilarCheckMatchKey() {
        return new SimilarCheckSensorRuleKey(
                this.sensorName,
                this.rule.getWarning() != null ? this.rule.getWarning().getRuleName() : null,
                this.rule.getError() != null ? this.rule.getError().getRuleName() : null,
                this.rule.getFatal() != null ? this.rule.getFatal().getRuleName() : null);
    }

    /**
     * Creates a selective deep/shallow clone of the object. Definition objects are not cloned, but all other editable objects are.
     * @return Cloned instance.
     */
    public CheckModel cloneForUpdate() {
        try {
            CheckModel cloned = (CheckModel)super.clone();
            if (cloned.sensorParametersSpec != null) {
                cloned.sensorParametersSpec = cloned.sensorParametersSpec.deepClone();
            }
            if (cloned.checkSpec != null) {
                cloned.checkSpec = cloned.checkSpec.deepClone();
            }
            if (cloned.rule != null) {
                cloned.rule = cloned.rule.cloneForUpdate();
            }
            if (cloned.dataStreamsOverride != null) {
                cloned.dataStreamsOverride = cloned.dataStreamsOverride.deepClone();
            }
            if (cloned.scheduleOverride != null) {
                cloned.scheduleOverride = cloned.scheduleOverride.deepClone();
            }
            if (cloned.comments != null) {
                cloned.comments = cloned.comments.deepClone();
            }
            if (cloned.runChecksJobTemplate != null) {
                cloned.runChecksJobTemplate = cloned.runChecksJobTemplate.clone();
            }
            if (cloned.dataCleanJobTemplate != null) {
                cloned.dataCleanJobTemplate = cloned.dataCleanJobTemplate.clone();
            }

            if (cloned.sensorParameters != null) {
                cloned.sensorParameters = cloned.sensorParameters
                        .stream()
                        .map(FieldModel::cloneForUpdate)
                        .collect(Collectors.toList());
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported: " + ex.toString(), ex);
        }
    }

    /**
     * Applies sample values for fields that have a sample value. Overrides the current values.
     * The model filled with sample values is used for generating the documentation model.
     */
    public void applySampleValues() {
        if (this.sensorParameters != null) {
            for (FieldModel sensorParameterFieldModel : this.sensorParameters) {
                sensorParameterFieldModel.applySampleValues();
            }
        }

        if (this.rule != null) {
            this.rule.applySampleValues();
        }
    }

    /**
     * Adds a configuration error to the list of errors.
     * @param configurationRequirementsError Configuration requirement error.
     */
    public void pushError(CheckConfigurationRequirementsError configurationRequirementsError) {
        if (this.configurationRequirementsErrors == null) {
            this.configurationRequirementsErrors = new ArrayList<>();
        }
        this.configurationRequirementsErrors.add(configurationRequirementsError);
    }
}
