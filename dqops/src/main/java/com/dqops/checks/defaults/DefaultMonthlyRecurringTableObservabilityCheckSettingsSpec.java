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

package com.dqops.checks.defaults;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.table.recurring.TableMonthlyRecurringCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableRecurringChecksSpec;
import com.dqops.checks.table.recurring.availability.TableAvailabilityMonthlyRecurringChecksSpec;
import com.dqops.checks.table.recurring.schema.TableSchemaMonthlyRecurringChecksSpec;
import com.dqops.checks.table.recurring.volume.TableVolumeMonthlyRecurringChecksSpec;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.scheduling.CheckRunRecurringScheduleGroup;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * The default configuration of checks that are enabled as data observability monthly recurring checks that will be detecting anomalies
 * for all tables that are imported. This configuration of checks is copied to the list of enabled table level checks on all tables that are imported, for monthly recurring checks only.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DefaultMonthlyRecurringTableObservabilityCheckSettingsSpec extends AbstractRootChecksContainerSpec {
    public static final ChildHierarchyNodeFieldMapImpl<DefaultMonthlyRecurringTableObservabilityCheckSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRootChecksContainerSpec.FIELDS) {
        {
            put("volume", o -> o.volume);
            put("availability", o -> o.availability);
            put("schema", o -> o.schema);
        }
    };

    @JsonPropertyDescription("The default configuration of volume data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableVolumeMonthlyRecurringChecksSpec volume;

    @JsonPropertyDescription("The default configuration of the table availability data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableAvailabilityMonthlyRecurringChecksSpec availability;

    @JsonPropertyDescription("The default configuration of schema (column count and schema) data quality checks on a table level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableSchemaMonthlyRecurringChecksSpec schema;

    /**
     * Returns a container of volume check configuration on a table level.
     * @return Volume checks configuration.
     */
    public TableVolumeMonthlyRecurringChecksSpec getVolume() {
        return volume;
    }

    /**
     * Sets a reference to a volume checks container.
     * @param volume New volume checks configuration.
     */
    public void setVolume(TableVolumeMonthlyRecurringChecksSpec volume) {
        this.setDirtyIf(!Objects.equals(this.volume, volume));
        this.volume = volume;
        this.propagateHierarchyIdToField(volume, "volume");
    }

    /**
     * Returns a container of the table availability checks.
     * @return Table availability checks.
     */
    public TableAvailabilityMonthlyRecurringChecksSpec getAvailability() {
        return availability;
    }

    /**
     * Sets a reference to an availability checks container.
     * @param availability Table availability checks.
     */
    public void setAvailability(TableAvailabilityMonthlyRecurringChecksSpec availability) {
        this.setDirtyIf(!Objects.equals(this.availability, availability));
        this.availability = availability;
        this.propagateHierarchyIdToField(availability, "availability");
    }

    /**
     * Returns a container of table schema checks.
     * @return Table schema checks.
     */
    public TableSchemaMonthlyRecurringChecksSpec getSchema() {
        return schema;
    }

    /**
     * Sets the reference to the table schema checks.
     * @param schema Table schema checks container.
     */
    public void setSchema(TableSchemaMonthlyRecurringChecksSpec schema) {
        this.setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
        this.propagateHierarchyIdToField(schema, "schema");
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
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
     * Retrieves or creates and retrieves the check categories class on a table.
     * @param targetTable Target table.
     * @return Check categories.
     */
    protected TableMonthlyRecurringCheckCategoriesSpec getTableCheckCategories(TableSpec targetTable) {
        TableRecurringChecksSpec recurringChecksSpec = targetTable.getRecurringChecks();
        if (recurringChecksSpec == null) {
            recurringChecksSpec = new TableRecurringChecksSpec();
            targetTable.setRecurringChecks(recurringChecksSpec);
        }

        TableMonthlyRecurringCheckCategoriesSpec checkCategoriesSpec = recurringChecksSpec.getMonthly();
        if (checkCategoriesSpec == null) {
            checkCategoriesSpec = new TableMonthlyRecurringCheckCategoriesSpec();
            recurringChecksSpec.setMonthly(checkCategoriesSpec);
        }

        return checkCategoriesSpec;
    }
    /**
     * Applies the checks on a target table.
     * @param targetTable Target table.
     * @param dialectSettings Dialect settings, to decide if the checks are applicable.
     */
    public void applyOnTable(TableSpec targetTable, ProviderDialectSettings dialectSettings) {
        if (this.volume != null && !this.volume.isDefault()) {
            this.getTableCheckCategories(targetTable).setVolume(this.volume.deepClone());
        }

        if (this.availability != null && !this.availability.isDefault()) {
            this.getTableCheckCategories(targetTable).setAvailability(this.availability.deepClone());
        }

        if (this.schema != null && !this.schema.isDefault()) {
            this.getTableCheckCategories(targetTable).setSchema(this.schema.deepClone());
        }
    }

    /**
     * Returns the type of checks (profiling, recurring, partitioned).
     *
     * @return Check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.recurring;
    }

    /**
     * Returns the time scale for recurring and partitioned checks (daily, monthly, etc.).
     * Profiling checks do not have a time scale and return null.
     *
     * @return Time scale (daily, monthly, ...).
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }

    /**
     * Returns the check target, where the check could be applied.
     *
     * @return Check target, "table" or "column".
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }

    /**
     * Returns the name of the cron expression that is used to schedule checks in this check root object.
     *
     * @return Recurring schedule group (named schedule) that is used to schedule the checks in this root.
     */
    @Override
    @JsonIgnore
    public CheckRunRecurringScheduleGroup getSchedulingGroup() {
        return null;
    }

    /**
     * Returns the comparisons container for table comparison checks, indexed by the reference table configuration name.
     *
     * @return Table comparison container.
     */
    @Override
    @JsonIgnore
    public AbstractComparisonCheckCategorySpecMap<?> getComparisons() {
        return null;
    }

    /**
     * Returns time series configuration for the given group of checks.
     *
     * @param tableSpec Parent table specification - used to get the details about the time partitioning column.
     * @return Time series configuration.
     */
    @Override
    @JsonIgnore
    public TimeSeriesConfigurationSpec getTimeSeriesConfiguration(TableSpec tableSpec) {
        return null;
    }
}
