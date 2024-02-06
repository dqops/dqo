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
package com.dqops.checks.table.monitoring.timeliness;
import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataStalenessCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
 * Container of table level monthly monitoring for timeliness data quality checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("monthly_data_freshness", o -> o.monthlyDataFreshness);
           put("monthly_data_ingestion_delay", o -> o.monthlyDataIngestionDelay);
           put("monthly_data_staleness", o -> o.monthlyDataStaleness);
        }
    };

    @JsonPropertyDescription("Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataFreshnessCheckSpec monthlyDataFreshness;

    @JsonPropertyDescription("Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataStalenessCheckSpec monthlyDataStaleness;

    @JsonPropertyDescription("Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec monthlyDataIngestionDelay;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return The number of days since the most recent event check configuration.
     */
    public TableDataFreshnessCheckSpec getMonthlyDataFreshness() {
        return monthlyDataFreshness;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param monthlyDataFreshness New days since the most recent event check.
     */
    public void setMonthlyDataFreshness(TableDataFreshnessCheckSpec monthlyDataFreshness) {
		this.setDirtyIf(!Objects.equals(this.monthlyDataFreshness, monthlyDataFreshness));
        this.monthlyDataFreshness = monthlyDataFreshness;
		this.propagateHierarchyIdToField(monthlyDataFreshness, "monthly_data_freshness");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDataStalenessCheckSpec getMonthlyDataStaleness() {
        return monthlyDataStaleness;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param monthlyDataStaleness A number of days since the last data ingestion check configuration.
     */

    public void setMonthlyDataStaleness(TableDataStalenessCheckSpec monthlyDataStaleness) {
        this.setDirtyIf(!Objects.equals(this.monthlyDataStaleness, monthlyDataStaleness));
        this.monthlyDataStaleness = monthlyDataStaleness;
        this.propagateHierarchyIdToField(monthlyDataStaleness, "monthly_data_staleness");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getMonthlyDataIngestionDelay() {
        return monthlyDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param monthlyDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setMonthlyDataIngestionDelay(TableDataIngestionDelayCheckSpec monthlyDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.monthlyDataIngestionDelay, monthlyDataIngestionDelay));
        this.monthlyDataIngestionDelay = monthlyDataIngestionDelay;
        this.propagateHierarchyIdToField(monthlyDataIngestionDelay, "monthly_data_ingestion_delay");
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
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.monitoring;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }
}
