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
package ai.dqo.checks;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
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
 * @param <R> Alerting threshold rule parameters type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCheckSpec<S extends AbstractSensorParametersSpec, R extends AbstractRuleParametersSpec> extends AbstractSpec implements Cloneable {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("parameters", o -> o.getParameters());
            put("error", o -> o.getError());
            put("warning", o -> o.getWarning());
            put("fatal", o -> o.getFatal());
            put("schedule_override", o -> o.scheduleOverride);
            put("comments", o -> o.comments);
        }
    };

    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec scheduleOverride;

    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;

    @JsonPropertyDescription("Disables the data quality check. Only enabled data quality checks and checkpoints are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean excludeFromKpi;

    @JsonPropertyDescription("Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String qualityDimension;

    /**
     * Returns the schedule configuration for running the checks automatically.
     * @return Schedule configuration.
     */
    public RecurringScheduleSpec getScheduleOverride() {
        return scheduleOverride;
    }

    /**
     * Stores a new schedule configuration.
     * @param scheduleOverride New schedule configuration.
     */
    public void setScheduleOverride(RecurringScheduleSpec scheduleOverride) {
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
     * Checks if the data quality check (or checkpoint) is disabled.
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
     * Alerting threshold configuration that raise a regular "ERROR" severity alerts for unsatisfied rules.
     * @return Default "error" alerting thresholds.
     */
    public abstract R getError();

    /**
     * Alerting threshold configuration that raise a "WARNING" severity alerts for unsatisfied rules.
     * @return Warning severity rule parameters.
     */
    public abstract R getWarning();

    /**
     * Alerting threshold configuration that raise a "FATAL" severity alerts for unsatisfied rules.
     * @return Fatal severity rule parameters.
     */
    public abstract R getFatal();

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public AbstractCheckSpec clone() {
        try {
            AbstractCheckSpec cloned = (AbstractCheckSpec)super.clone();
            if (cloned.scheduleOverride != null) {
                cloned.scheduleOverride = cloned.scheduleOverride.clone();
            }
            if (cloned.comments != null) {
                cloned.comments = cloned.comments.clone();
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object.");
        }
    }

    /**
     * Creates a cloned and trimmed version of the object. A trimmed and cloned copy is passed to the sensor.
     * All configurable variables that may use a secret value or environment variable expansion in the form ${ENV_VAR} are also expanded.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public AbstractCheckSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            AbstractCheckSpec cloned = (AbstractCheckSpec)super.clone();
            cloned.scheduleOverride = null;
            cloned.comments = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object.");
        }
    }

    /**
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     * @return Rule definition name (python module name without .py extension) retrieved from the first configured severity level.
     */
    @JsonIgnore
    public String getRuleDefinitionName() {
        if (this.getError() != null) {
            return this.getError().getRuleDefinitionName();
        }

        if (this.getWarning() != null) {
            return this.getWarning().getRuleDefinitionName();
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

        return this.getDefaultDataQualityDimension().getDisplayName();
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
        return hierarchyId.get(hierarchyId.size() - 2).toString();
    }

    /**
     * Returns the data quality check name (YAML compliant) that is used as a field name on a check category class.
     * @return Check category name, for example "min_row_count", etc.
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
