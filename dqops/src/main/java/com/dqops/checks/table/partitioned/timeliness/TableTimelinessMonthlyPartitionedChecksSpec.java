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
package com.dqops.checks.table.partitioned.timeliness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataStalenessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TablePartitionReloadLagCheckSpec;
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
 * Container of table level monthly partitioned timeliness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("monthly_partition_data_freshness", o -> o.monthlyPartitionDataFreshness);
           put("monthly_partition_data_staleness", o -> o.monthlyPartitionDataStaleness);
           put("monthly_partition_data_ingestion_delay", o -> o.monthlyPartitionDataIngestionDelay);
           put("monthly_partition_reload_lag", o -> o.monthlyPartitionReloadLag);
        }
    };

    @JsonPropertyDescription("Monthly partitioned check calculating the number of days since the most recent event (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataFreshnessCheckSpec monthlyPartitionDataFreshness;

    @JsonPropertyDescription("Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataStalenessCheckSpec monthlyPartitionDataStaleness;

    @JsonPropertyDescription("Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec monthlyPartitionDataIngestionDelay;

    @JsonPropertyDescription("Monthly partitioned check calculating the longest time a row waited to be load")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TablePartitionReloadLagCheckSpec monthlyPartitionReloadLag;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDataFreshnessCheckSpec getMonthlyPartitionDataFreshness() {
        return monthlyPartitionDataFreshness;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param monthlyPartitionDataFreshness New  days since the most recent event check.
     */
    public void setMonthlyPartitionDataFreshness(TableDataFreshnessCheckSpec monthlyPartitionDataFreshness) {
		this.setDirtyIf(!Objects.equals(this.monthlyPartitionDataFreshness, monthlyPartitionDataFreshness));
        this.monthlyPartitionDataFreshness = monthlyPartitionDataFreshness;
		this.propagateHierarchyIdToField(monthlyPartitionDataFreshness, "monthly_partition_data_freshness");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDataStalenessCheckSpec getMonthlyPartitionDataStaleness() {
        return monthlyPartitionDataStaleness;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param monthlyPartitionDataStaleness A number of days since the last data ingestion check configuration.
     */
    public void setMonthlyPartitionDataStaleness(TableDataStalenessCheckSpec monthlyPartitionDataStaleness) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDataStaleness, monthlyPartitionDataStaleness));
        this.monthlyPartitionDataStaleness = monthlyPartitionDataStaleness;
        this.propagateHierarchyIdToField(monthlyPartitionDataStaleness, "monthly_partition_data_staleness");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getMonthlyPartitionDataIngestionDelay() {
        return monthlyPartitionDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param monthlyPartitionDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setMonthlyPartitionDataIngestionDelay(TableDataIngestionDelayCheckSpec monthlyPartitionDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDataIngestionDelay, monthlyPartitionDataIngestionDelay));
        this.monthlyPartitionDataIngestionDelay = monthlyPartitionDataIngestionDelay;
        this.propagateHierarchyIdToField(monthlyPartitionDataIngestionDelay, "monthly_partition_data_ingestion_delay");
    }

    /**
     * Returns a maximum row data ingestion delay check configuration.
     * @return A maximum row data ingestion delay check configuration.
     */
    public TablePartitionReloadLagCheckSpec getMonthlyPartitionReloadLag() {
        return monthlyPartitionReloadLag;
    }

    /**
     * Sets a maximum row data ingestion delay.
     * @param monthlyPartitionReloadLag New maximum row data ingestion delay.
     */
    public void setMonthlyPartitionReloadLag(TablePartitionReloadLagCheckSpec monthlyPartitionReloadLag) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionReloadLag, monthlyPartitionReloadLag));
        this.monthlyPartitionReloadLag = monthlyPartitionReloadLag;
        this.propagateHierarchyIdToField(monthlyPartitionReloadLag, "monthly_partition_reload_lag");
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
        return CheckType.partitioned;
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
}
