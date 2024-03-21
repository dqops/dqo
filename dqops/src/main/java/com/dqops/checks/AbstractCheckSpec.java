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

import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Base class for a data quality check. A check is a pair of a sensor (that reads a value by querying the data) and a rule that validates the value returned by the sensor.
 * @param <RError> Alerting threshold rule parameters type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCheckSpec<S extends AbstractSensorParametersSpec, RWarning extends AbstractRuleParametersSpec, RError extends AbstractRuleParametersSpec, RFatal extends AbstractRuleParametersSpec>
            extends AbstractSpec implements Cloneable {
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
    private MonitoringScheduleSpec scheduleOverride;

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

    /**
     * True when this check was copied from the configuration of the default observability checks and is not stored in the table's YAML file (it is transient).
     */
    @JsonIgnore
    private boolean defaultCheck;

    /**
     * Returns the schedule configuration for running the checks automatically.
     * @return Schedule configuration.
     */
    public MonitoringScheduleSpec getScheduleOverride() {
        return scheduleOverride;
    }

    /**
     * Stores a new schedule configuration.
     * @param scheduleOverride New schedule configuration.
     */
    public void setScheduleOverride(MonitoringScheduleSpec scheduleOverride) {
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
        this.dataGrouping = dataGrouping;
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
}
