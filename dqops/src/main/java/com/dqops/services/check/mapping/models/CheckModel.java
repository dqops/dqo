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
package com.dqops.services.check.mapping.models;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.DefaultRuleSeverityLevel;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.rules.TargetRuleSeverityLevel;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.services.check.matching.SimilarCheckModel;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.reflection.FieldInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model that returns the form definition and the form data to edit a single data quality check.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckModel", description = "Model that returns the form definition and the form data to edit a single data quality check.")
@NoArgsConstructor
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
     * User assigned display name that is shown instead of the original data quality check name.
     */
    @JsonPropertyDescription("User assigned display name that is shown instead of the original data quality check name.")
    private String displayName;

    /**
     * An alternative check's name that is shown on the check editor as a hint.
     */
    @JsonPropertyDescription("An alternative check's name that is shown on the check editor as a hint.")
    private String friendlyName;

    /**
     * List of fields for editing the sensor parameters.
     */
    @JsonPropertyDescription("List of fields for editing the sensor parameters.")
    private List<FieldModel> sensorParameters = new ArrayList<>();

    /**
     * Full sensor name. This field is for information purposes and can be used to create additional custom checks that reuse the same data quality sensor.
     */
    @JsonPropertyDescription("Full sensor name. This field is for information purposes and can be used to create additional custom checks that reuse the same data quality sensor.")
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
     * The data quality check supports capturing error samples, because an error sampling template is defined.
     */
    @JsonPropertyDescription("The data quality check supports capturing error samples, because an error sampling template is defined.")
    private boolean supportsErrorSampling;

    /**
     * The data quality check supports a custom data stream mapping configuration.
     */
    @JsonPropertyDescription("The data quality check supports a custom data grouping configuration.")
    private boolean supportsGrouping;

    /**
     * This is a standard data quality check that is always shown on the data quality checks editor screen.
     * Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.
     */
    @JsonPropertyDescription("This is a standard data quality check that is always shown on the data quality checks editor screen. Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean standard;

    /**
     * This is a check that was applied on-the-fly, because it is configured as a default data observability check and can be run, but it is not configured in the table YAML.
     */
    @JsonPropertyDescription("This is a check that was applied on-the-fly, because it is configured as a default data observability check and can be run, but it is not configured in the table YAML.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean defaultCheck;

    /**
     * The severity level (warning, error, fatal) for the default rule that is activated in the data quality check editor when the check is enabled.
     */
    @JsonPropertyDescription("The severity level (warning, error, fatal) for the default rule that is activated in the data quality check editor when the check is enabled.")
    private DefaultRuleSeverityLevel defaultSeverity;

    /**
     * Data grouping configuration for this check. When a data grouping configuration is assigned at a check level, it overrides the data grouping configuration from the table level.
     * Data grouping is configured in two cases:
     * (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different groups of rows using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.
     * (2) a static data grouping configuration is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.).
     */
    @JsonPropertyDescription("Data grouping configuration for this check. When a data grouping configuration is assigned at a check level, it overrides the data grouping configuration from the table level. " +
            "Data grouping is configured in two cases: " +
            "(1) the data in the table should be analyzed with a GROUP BY condition, to analyze different groups of rows using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning. " +
            "(2) a static data grouping configuration is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). ")
    private DataGroupingConfigurationSpec dataGroupingOverride;

    /**
     * Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.
     */
    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    private MonitoringScheduleSpec scheduleOverride;

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
    @JsonPropertyDescription("Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.")
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
     * The name of a data grouping configuration defined at a table that should be used for this check.
     */
    @JsonPropertyDescription("The name of a data grouping configuration defined at a table that should be used for this check.")
    private String dataGroupingConfiguration;

    /**
     * Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.
     */
    @JsonPropertyDescription("Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean alwaysCollectErrorSamples;

    /**
     * Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting "run checks" job.
     */
    @JsonPropertyDescription("Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting \"run checks\" job.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean doNotSchedule;

    /**
     * Type of the check's target (column, table).
     */
    @JsonPropertyDescription("Type of the check's target (column, table).")
    private CheckTargetModel checkTarget;

    /**
     * List of configuration errors that must be fixed before the data quality check can be executed.
     */
    @JsonPropertyDescription("List of configuration errors that must be fixed before the data quality check can be executed.")
    private List<String> configurationRequirementsErrors;

    /**
     * List of similar checks in other check types or in other time scales.
     */
    @JsonPropertyDescription("List of similar checks in other check types or in other time scales.")
    private List<SimilarCheckModel> similarChecks;

    /**
     * The check hash code that identifies the check instance.
     */
    @JsonPropertyDescription("The check hash code that identifies the check instance.")
    private Long checkHash;

    /**
     * Boolean flag that decides if the current user can edit the check.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can edit the check.")
    private boolean canEdit;

    /**
     * Boolean flag that decides if the current user can run checks.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can run checks.")
    private boolean canRunChecks;

    /**
     * Boolean flag that decides if the current user can delete data (results).
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can delete data (results).")
    private boolean canDeleteData;

    /**
     * Check field info object, used only internally by the check mining engine.
     */
    @JsonIgnore
    private FieldInfo checkFieldInfo;

    /**
     * Custom check definition specification.
     */
    @JsonIgnore
    private CheckDefinitionSpec customCheckDefinitionSpec;

    /**
     * Check scheduling group.
     */
    @JsonIgnore
    private CheckRunScheduleGroup scheduleGroup;

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
            if (cloned.dataGroupingOverride != null) {
                cloned.dataGroupingOverride = cloned.dataGroupingOverride.deepClone();
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
    public void pushError(String configurationRequirementsError) {
        if (this.configurationRequirementsErrors == null) {
            this.configurationRequirementsErrors = new ArrayList<>();
        }
        this.configurationRequirementsErrors.add(configurationRequirementsError);
    }

    /**
     * Finds a similar check in the profiling checks type.
     * @return Similar checks in the profiling checks type.
     */
    public SimilarCheckModel getSimilarProfilingCheck() {
        if (this.similarChecks == null) {
            return null;
        }

        for (SimilarCheckModel similarCheckModel : this.similarChecks) {
            if (similarCheckModel.getCheckType() == CheckType.profiling) {
                return similarCheckModel;
            }
        }

        return null;
    }

    public static class CheckModelSampleFactory implements SampleValueFactory<CheckModel> {
        @Override
        public CheckModel createSample() {
            CheckModel checkModel = new CheckModel() {{
                setCheckName(SampleStringsRegistry.getCheckName());
                setHelpText(SampleStringsRegistry.getHelpText());
                setSensorName(SampleStringsRegistry.getFullSensorName());
                setQualityDimension(SampleStringsRegistry.getQualityDimension());
                setDefaultSeverity(DefaultRuleSeverityLevel.error);
            }};
            checkModel.applySampleValues();
            return checkModel;
        }
    }
}
