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

import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.configuration.DqoRuleMiningConfigurationProperties;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.scheduling.SchedulingRootNode;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.TargetRuleSeverityLevel;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mining.*;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.dqops.utils.serialization.JsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import tech.tablesaw.api.IntColumn;

import java.time.Instant;
import java.util.Objects;

/**
 * Base class for a data quality check. A check is a pair of a sensor (that reads a value by querying the data) and a rule that validates the value returned by the sensor.
 * @param <RError> Alerting threshold rule parameters type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCheckSpec<S extends AbstractSensorParametersSpec, RWarning extends AbstractRuleParametersSpec, RError extends AbstractRuleParametersSpec, RFatal extends AbstractRuleParametersSpec>
            extends AbstractSpec implements Cloneable, SchedulingRootNode {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("parameters", o -> o.getParameters());
            put("warning", o -> o.getWarning());
            put("error", o -> o.getError());
            put("fatal", o -> o.getFatal());
            put("schedule_override", o -> o.scheduleOverride);
            put("comments", o -> o.comments);
        }
    };

    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CronScheduleSpec scheduleOverride;

    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;

    @JsonPropertyDescription("Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean excludeFromKpi;

    @JsonPropertyDescription("Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean includeInSla;

    @JsonPropertyDescription("Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String qualityDimension;

    @JsonPropertyDescription("Data quality check display name that can be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String displayName;

    @JsonPropertyDescription("Data grouping configuration name that should be applied to this data quality check. " +
            "The data grouping is used to group the check's result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. " +
            "Use the name of one of data grouping configurations defined on the parent table.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String dataGrouping;

    @JsonPropertyDescription("Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean alwaysCollectErrorSamples;

    @JsonPropertyDescription("Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting \"run checks\" job.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean doNotSchedule;

    /**
     * True when this check was copied from the configuration of the default observability checks and is not stored in the table's YAML file (it is transient).
     */
    @JsonIgnore
    private boolean defaultCheck;

    /**
     * Special field filled with the timestamp of the last modification of the table-level or column-level policy YAML file, if this check
     * was applied from a data quality policy. It is used to detect the modification time of a check to avoid recalculating rules.
     */
    @JsonIgnore
    private Instant policyLastModified;

    /**
     * Returns the schedule configuration for running the checks automatically.
     * @return Schedule configuration.
     */
    public CronScheduleSpec getScheduleOverride() {
        return scheduleOverride;
    }

    /**
     * Stores a new schedule configuration.
     * @param scheduleOverride New schedule configuration.
     */
    public void setScheduleOverride(CronScheduleSpec scheduleOverride) {
        setDirtyIf(!Objects.equals(this.scheduleOverride, scheduleOverride));
        this.scheduleOverride = scheduleOverride;
        propagateHierarchyIdToField(scheduleOverride, "schedule_override");
    }

    /**
     * Returns a collection of comments for this connection.
     * @return List of comments (or null).
     */
    public CommentsListSpec getComments() {
        return comments;
    }

    /**
     * Sets a list of comments for this connection.
     * @param comments Comments list.
     */
    public void setComments(CommentsListSpec comments) {
        setDirtyIf(!Objects.equals(this.comments, comments));
        this.comments = comments;
        propagateHierarchyIdToField(comments, "comments");
    }

    /**
     * Checks if the data quality check (or monitoring) is disabled.
     * @return True when the check is disabled.
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled flag on a check.
     * @param disabled Disabled flag.
     */
    public void setDisabled(boolean disabled) {
        this.setDirtyIf(this.disabled != disabled);
        this.disabled = disabled;
    }

    /**
     * True when the check should not be included in the data quality KPI calculation.
     * @return True - excluded from KPI, false - the data quality check is counted for the data quality KPI calculation.
     */
    public boolean isExcludeFromKpi() {
        return excludeFromKpi;
    }

    /**
     * Sets the flag for excluding checks from a data quality KPI calculation.
     * @param excludeFromKpi true - exclude from the data quality KPI calculation.
     */
    public void setExcludeFromKpi(boolean excludeFromKpi) {
        this.setDirtyIf(this.excludeFromKpi != excludeFromKpi);
        this.excludeFromKpi = excludeFromKpi;
    }

    /**
     * Returs true if the check is a critical data quality check that is part of a data quality SLA.
     * @return True when the check is part of a DQ SLA (data contract).
     */
    public boolean isIncludeInSla() {
        return includeInSla;
    }

    /**
     * Adds or removes a check from the list of data quality checks that are part of a data quality SLA (data contract).
     * @param includeInSla True when the check is a part of a data quality SLA.
     */
    public void setIncludeInSla(boolean includeInSla) {
        this.setDirtyIf(this.includeInSla != includeInSla);
        this.includeInSla = includeInSla;
    }

    /**
     * Returns an overwritten data quality dimension that should be used for reporting the alerts for this data quality check.
     * @return Overwritten data quality dimension name.
     */
    public String getQualityDimension() {
        return qualityDimension;
    }

    /**
     * Sets an overwritten name of the data quality dimension that is used for reporting the alerts of this data quality check.
     * @param qualityDimension Data quality dimension name.
     */
    public void setQualityDimension(String qualityDimension) {
        setDirtyIf(!Objects.equals(this.qualityDimension, qualityDimension));
        this.qualityDimension = qualityDimension;
    }

    /**
     * Returns a custom data quality check display name.
     * @return Custom check display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets a custom check display name that overrides the default display name that is just a copy of the check name.
     * @param displayName Custom check display name.
     */
    public void setDisplayName(String displayName) {
        setDirtyIf(!Objects.equals(this.displayName, displayName));
        this.displayName = displayName;
    }

    /**
     * Returns the name of a named data stream that is defined on the parent table level and should be used on this check.
     * @return Data stream level.
     */
    public String getDataGrouping() {
        return dataGrouping;
    }

    /**
     * Sets a data stream name to be used for this check.
     * @param dataGrouping Data stream name.
     */
    public void setDataGrouping(String dataGrouping) {
        this.setDirtyIf(!Objects.equals(this.dataGrouping, dataGrouping));
        this.dataGrouping = dataGrouping;
    }

    /**
     * Returns a value of a flag which says that this check should always collect error samples when it fails.
     * @return Always collect error samples.
     */
    public boolean isAlwaysCollectErrorSamples() {
        return alwaysCollectErrorSamples;
    }

    /**
     * Sets a flag that this check should always collect error samples when it fails.
     * @param alwaysCollectErrorSamples True when error samples should be always collected.
     */
    public void setAlwaysCollectErrorSamples(boolean alwaysCollectErrorSamples) {
        this.setDirtyIf(this.alwaysCollectErrorSamples != alwaysCollectErrorSamples);
        this.alwaysCollectErrorSamples = alwaysCollectErrorSamples;
    }

    /**
     * Returns true if this check is excluded from running by a job scheduler.
     * @return True when this job should not be run by a job scheduler.
     */
    public boolean isDoNotSchedule() {
        return doNotSchedule;
    }

    /**
     * Sets a flag to disable running this check by a job scheduler.
     * @param doNotSchedule True when excluded from the CRON scheduler.
     */
    public void setDoNotSchedule(boolean doNotSchedule) {
        this.setDirtyIf(this.doNotSchedule != doNotSchedule);
        this.doNotSchedule = doNotSchedule;
    }

    /**
     * Returns true if this check is an observability check that was added as a transient check, because it is configured in the default observability checks.
     * @return True when it is a default check (not persisted in YAML), false when it is a materialized check that is configured in the table.
     */
    public boolean isDefaultCheck() {
        return defaultCheck;
    }

    /**
     * Sets a flag that this check is a default observability check.
     * @param defaultCheck True when it is a default check.
     */
    public void setDefaultCheck(boolean defaultCheck) {
        this.setDirtyIf(this.defaultCheck != defaultCheck);
        this.defaultCheck = defaultCheck;
    }

    /**
     * Returns the timestamp of the YAML file modification date of the DQ policy file from which this check was copied.
     * Filled only when this check was applied form a DQ policy.
     * @return DQ policy file modification timestamp.
     */
    public Instant getPolicyLastModified() {
        return policyLastModified;
    }

    /**
     * Sets the timestamp when the check was modified in the DQ policy (for DQ policy checks only).
     * @param policyLastModified DQ policy YAML file modification date.
     */
    public void setPolicyLastModified(Instant policyLastModified) {
        this.setDirtyIf(!Objects.equals(this.policyLastModified, policyLastModified));
        this.policyLastModified = policyLastModified;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns the sensor parameters spec object that identifies the sensor definition to use and contains parameters.
     * @return Sensor parameters.
     */
    public abstract S getParameters();

    /**
     * Sets a new instance of sensor parameter parameters.
     * @param parameters New parameters specification object.
     */
    public abstract void setParameters(S parameters);

    /**
     * Alerting threshold configuration that raise a "WARNING" severity alerts for unsatisfied rules.
     * @return Warning severity rule parameters.
     */
    public abstract RWarning getWarning();

    /**
     * Sets a new instance of an alerting rule for the warning severity.
     * @param warning New rule parameters for the warning severity or null to disable the severity level.
     */
    public abstract void setWarning(RWarning warning);

    /**
     * Alerting threshold configuration that raise a regular "ERROR" severity alerts for unsatisfied rules.
     * @return Default "error" alerting thresholds.
     */
    public abstract RError getError();

    /**
     * Sets a new instance of an alerting rule for the error severity.
     * @param error New rule parameters for the error severity or null to disable the severity level.
     */
    public abstract void setError(RError error);

    /**
     * Alerting threshold configuration that raise a "FATAL" severity alerts for unsatisfied rules.
     * @return Fatal severity rule parameters.
     */
    public abstract RFatal getFatal();

    /**
     * Sets a new instance of an alerting rule for the fatal severity.
     * @param fatal New rule parameters for the fatal severity or null to disable the severity level.
     */
    public abstract void setFatal(RFatal fatal);

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public AbstractCheckSpec deepClone() {
        AbstractCheckSpec cloned = (AbstractCheckSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and trimmed version of the object. A trimmed and cloned copy is passed to the sensor.
     * All configurable variables that may use a secret value or environment variable expansion in the form ${ENV_VAR} are also expanded.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public AbstractCheckSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        AbstractCheckSpec cloned = this.deepClone();
        cloned.scheduleOverride = null;
        cloned.comments = null;
        return cloned;
    }

    /**
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     * @return Rule definition name (python module name without .py extension) retrieved from the first configured severity level.
     */
    @JsonIgnore
    public String getRuleDefinitionName() {
        if (this.getWarning() != null) {
            return this.getWarning().getRuleDefinitionName();
        }

        if (this.getError() != null) {
            return this.getError().getRuleDefinitionName();
        }

        if (this.getFatal() != null) {
            return this.getFatal().getRuleDefinitionName();
        }

        return null;
    }

    /**
     * Returns the default data quality dimension name used when an overwritten data quality dimension name was not assigned.
     * @return Default data quality dimension name.
     */
    @JsonIgnore
    public abstract DefaultDataQualityDimensions getDefaultDataQualityDimension();

    /**
     * Effective data quality dimension used for reporting the alerts of this check. It is the value of {@link AbstractCheckSpec#qualityDimension} when provided
     * or a result of calling {@link AbstractCheckSpec#getDefaultDataQualityDimension()}.
     * @return Effective data quality dimension name.
     */
    @JsonIgnore
    public String getEffectiveDataQualityDimension() {
        if (!Strings.isNullOrEmpty(this.qualityDimension)) {
            return this.qualityDimension;
        }

        return this.getDefaultDataQualityDimension().name();
    }

    /**
     * Returns the data quality category name retrieved from the category field name used to store a container of check categories
     * in the metadata.
     * @return Check category name.
     */
    @JsonIgnore
    public String getCategoryName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }

        if (Objects.equals(hierarchyId.get(hierarchyId.size() - 3), AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME)) {
            return AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME;
        }

        return hierarchyId.get(hierarchyId.size() - 2).toString();
    }

    /**
     * Returns an alternative check's friendly name that is shown on the check editor. It is used to show "empty table" name next to profile_row_count check.
     * @return An alternative name, or null when the check has no alternative name to show.
     */
    @JsonIgnore
    public String getFriendlyName() {
        return null;
    }

    /**
     * Returns the default rule severity level that is activated when a check is enabled in the check editor.
     * @return The default rule severity level that is activated when a check is enabled in the check editor. The default value is an "error" severity rule.
     */
    @JsonIgnore
    public DefaultRuleSeverityLevel getDefaultSeverity() {
        return DefaultRuleSeverityLevel.error;
    }

    /**
     * Returns the data quality check name (YAML compliant) that is used as a field name on a check category class.
     * @return Check category name, for example, "row_count", etc.
     */
    @JsonIgnore
    public String getCheckName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.getLast().toString();
    }

    /**
     * Returns true if this is a table comparison check that compares tables across data sources.
     * Returns false for simple checks that are executed only on the monitored table.
     * @return True for table comparison checks that use two tables, false for single table checks.
     */
    @JsonIgnore
    public boolean isTableComparisonCheck() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return false;
        }

        return Objects.equals(hierarchyId.get(hierarchyId.size() - 3), AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME);
    }

    /**
     * Returns true if this is a standard data quality check that is always shown on the data quality checks editor screen.
     * Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.
     * @return True when it is a standard check, false when it is an advanced check. The default value is 'false' (all checks are non-standard, advanced checks).
     */
    @JsonIgnore
    public boolean isStandard() {
        return false;
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return false; // we serialize all checks, even when they have no parameters (because they are too simple to have parameters) and have no alert thresholds (because they are only capturing values)
    }

    /**
     * Checks if any rules (warning, error, fatal) are configured.
     * @return True when any severity rule is configured, false otherwise.
     */
    public boolean hasAnyRulesEnabled() {
        return this.getWarning() != null || this.getError() != null || this.getFatal() != null;
    }

    /**
     * Changes the rule parameters to decrease rule severity and generate less alerts.
     * @param checkResultsSingleCheck History of check results for this check for the time period used for analysis.
     */
    public void decreaseCheckSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (checkResultsSingleCheck.isEmpty()) {
            return;
        }

        if (checkResultsSingleCheck.getActualValueColumn().isNotMissing().isEmpty()) {
            return; // no results, most calculations will fail
        }

        IntColumn severityColumn = checkResultsSingleCheck.getSeverityColumn();

        if (this.getFatal() != null) {
            if (!severityColumn.isEqualTo(3.0).isEmpty()) {
                this.getFatal().decreaseRuleSensitivity(checkResultsSingleCheck);
            }
        }

        if (this.getError() != null) {
            if (!severityColumn.isEqualTo(2.0).isEmpty()) {
                this.getError().decreaseRuleSensitivity(checkResultsSingleCheck);
            }
        }

        if (this.getWarning() != null) {
            if (!severityColumn.isEqualTo(1.0).isEmpty()) {
                this.getWarning().decreaseRuleSensitivity(checkResultsSingleCheck);
            }
        }
    }

    /**
     * Proposes the configuration of this check by using information from all related sources.
     * @param sourceProfilingCheck Previous results captured by a similar profiling check. Used to copy configuration to monitoring checks.
     * @param dataAssetProfilingResults Profiling results from the basic statistics and profiling checks for the data asset (table or column).
     * @param tableProfilingResults All profiling results for the table, including table-level profiling results (such as row counts) and results for all columns. Used by rule mining functions that must look into other values.
     * @param tableSpec Parent table specification for reference.
     * @param parentCheckRootContainer Parent check container, to identify the type of checks.
     * @param myCheckModel Check model of this check. This information can be used to get access to the custom check configuration (for custom checks).
     * @param miningParameters Additional rule mining parameters given by the user.
     * @param columnTypeCategory Column type category for column checks.
     * @param checkMiningConfigurationProperties Check mining configuration properties.
     * @param jsonSerializer JSON serializer used to convert sensor parameters and rule parameters to the target class type by serializing and deserializing.
     * @param ruleMiningRuleRegistry Rule mining registry.
     * @return True when the check was configured, false when the function decided not to configure the check.
     */
    public boolean proposeCheckConfiguration(
            ProfilingCheckResult sourceProfilingCheck,
            DataAssetProfilingResults dataAssetProfilingResults,
            TableProfilingResults tableProfilingResults,
            TableSpec tableSpec,
            AbstractRootChecksContainerSpec parentCheckRootContainer,
            CheckModel myCheckModel,
            CheckMiningParametersModel miningParameters,
            DataTypeCategory columnTypeCategory,
            DqoRuleMiningConfigurationProperties checkMiningConfigurationProperties,
            JsonSerializer jsonSerializer,
            RuleMiningRuleRegistry ruleMiningRuleRegistry) {
        if (sourceProfilingCheck == null) {
            return false; // no previous results from a profiling check or statistics
        }

        if (sourceProfilingCheck.getActualValue() != null && (sourceProfilingCheck.getProfilingCheckModel() == null ||
                !sourceProfilingCheck.getProfilingCheckModel().getRule().hasAnyRulesConfigured())) {
            // profiling check that has no rules configured is not a good source to copy from, unless it is a percentage or count check, which we can automatically configure

            String ruleName = myCheckModel.getRule().findFirstNotNullRule().getRuleName();
            RuleMiningRule ruleMiningRule = ruleMiningRuleRegistry.getRule(ruleName);
            if (ruleMiningRule != null) {
                AbstractRuleParametersSpec proposedRuleParameters = ruleMiningRule.proposeCheckConfiguration(
                        sourceProfilingCheck, dataAssetProfilingResults, tableProfilingResults, tableSpec, parentCheckRootContainer,
                        myCheckModel, miningParameters, columnTypeCategory, checkMiningConfigurationProperties);
                if (proposedRuleParameters != null) {
                    setRule(miningParameters.getSeverityLevel(), proposedRuleParameters, jsonSerializer);
                    return true;
                }
            }
        }

        if (sourceProfilingCheck.getProfilingCheckModel() == null) {
            return false; // no source profiling check to copy from
        }

        if (parentCheckRootContainer.getCheckType() == CheckType.profiling) {
            return false; // a profiling check cannot copy from itself
        }

        if (sourceProfilingCheck.getProfilingCheckModel().getCheckSpec().isDefaultCheck()) {
            return false; // do not copy the configuration of default checks, the user should configure a check pattern for a different check type
        }

        if (!miningParameters.isCopyFailedProfilingChecks() &&
                sourceProfilingCheck.getSeverityLevel() != null &&
                sourceProfilingCheck.getSeverityLevel().getSeverity() >= 1) {
            return false; // do not copy configuration of failed profiling checks, they were tested for data quality assessment only
        }

        if (!miningParameters.isCopyProfilingChecks()) {
            return false;
        }

        ClassInfo reflectionClassInfo = this.getChildMap().getReflectionClassInfo();
        AbstractCheckSpec<?, ?, ?, ?> profilingCheckSpec = sourceProfilingCheck.getProfilingCheckModel().getCheckSpec();
        AbstractSensorParametersSpec sensorParametersFromProfilingCheck = profilingCheckSpec.getParameters();
        if (sensorParametersFromProfilingCheck != null) {
            FieldInfo parametersFieldInfo = reflectionClassInfo.getFieldByYamlName("parameters");
            String serializedSensorParameters = jsonSerializer.serialize(sensorParametersFromProfilingCheck);
            Object convertedSensorParameters = jsonSerializer.deserialize(serializedSensorParameters, parametersFieldInfo.getClazz());
            //noinspection unchecked
            this.setParameters((S) convertedSensorParameters);
        }

        AbstractRuleParametersSpec profilingWarningRule = profilingCheckSpec.getWarning();
        if (profilingWarningRule != null) {
            setRule(TargetRuleSeverityLevel.warning, profilingWarningRule, jsonSerializer);
        }

        AbstractRuleParametersSpec profilingErrorRule = profilingCheckSpec.getError();
        if (profilingErrorRule != null) {
            setRule(TargetRuleSeverityLevel.error, profilingErrorRule, jsonSerializer);
        }

        AbstractRuleParametersSpec profilingFatalRule = profilingCheckSpec.getFatal();
        if (profilingFatalRule != null) {
            setRule(TargetRuleSeverityLevel.fatal, profilingFatalRule, jsonSerializer);
        }

        return true;
    }

    /**
     * Sets a rule by performing serialization to JSON and deserialization back to the expected rule class type.
     * @param severityLevel Target rule severity level.
     * @param sourceRuleParameters Source rule parameters object to convert and store in the rule.
     * @param jsonSerializer Json serializer instance that will be used for this operation.
     */
    public void setRule(TargetRuleSeverityLevel severityLevel, AbstractRuleParametersSpec sourceRuleParameters, JsonSerializer jsonSerializer) {
        ClassInfo reflectionClassInfo = this.getChildMap().getReflectionClassInfo();

        switch (severityLevel) {
            case warning: {
                if (sourceRuleParameters == null) {
                    this.setWarning((RWarning) null);
                    return;
                }
                FieldInfo warningFieldInfo = reflectionClassInfo.getFieldByYamlName("warning");
                String serializedWarningParameters = jsonSerializer.serialize(sourceRuleParameters);
                Object convertedWarningParameters = jsonSerializer.deserialize(serializedWarningParameters, warningFieldInfo.getClazz());
                //noinspection unchecked
                RWarning convertedWarningParametersCasted = (RWarning) convertedWarningParameters;
                convertedWarningParametersCasted.setAdditionalProperties(null);
                this.setWarning(convertedWarningParametersCasted);
                break;
            }

            case error: {
                if (sourceRuleParameters == null) {
                    this.setError((RError) null);
                    return;
                }

                FieldInfo errorFieldInfo = reflectionClassInfo.getFieldByYamlName("error");
                String serializedErrorParameters = jsonSerializer.serialize(sourceRuleParameters);
                Object convertedErrorParameters = jsonSerializer.deserialize(serializedErrorParameters, errorFieldInfo.getClazz());
                //noinspection unchecked
                RError convertedErrorParametersCasted = (RError) convertedErrorParameters;
                convertedErrorParametersCasted.setAdditionalProperties(null);
                this.setError(convertedErrorParametersCasted);
                break;
            }

            case fatal: {
                if (sourceRuleParameters == null) {
                    this.setFatal((RFatal) null);
                    return;
                }

                FieldInfo fatalFieldInfo = reflectionClassInfo.getFieldByYamlName("fatal");
                String serializedFatalParameters = jsonSerializer.serialize(sourceRuleParameters);
                Object convertedFatalParameters = jsonSerializer.deserialize(serializedFatalParameters, fatalFieldInfo.getClazz());
                //noinspection unchecked
                RFatal convertedFatalParametersCasted = (RFatal) convertedFatalParameters;
                convertedFatalParametersCasted.setAdditionalProperties(null);
                this.setFatal(convertedFatalParametersCasted);
                break;
            }
        }
    }
}
