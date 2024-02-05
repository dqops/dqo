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
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataStalenessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TablePartitionReloadLagCheckSpec;
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
 * Container of table level date partitioned timeliness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_partition_data_ingestion_delay", o -> o.dailyPartitionDataIngestionDelay);
           put("daily_partition_reload_lag", o -> o.dailyPartitionReloadLag);
        }
    };

    @JsonPropertyDescription("Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec dailyPartitionDataIngestionDelay;

    @JsonPropertyDescription("Daily partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TablePartitionReloadLagCheckSpec dailyPartitionReloadLag;


    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getDailyPartitionDataIngestionDelay() {
        return dailyPartitionDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dailyPartitionDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDailyPartitionDataIngestionDelay(TableDataIngestionDelayCheckSpec dailyPartitionDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDataIngestionDelay, dailyPartitionDataIngestionDelay));
        this.dailyPartitionDataIngestionDelay = dailyPartitionDataIngestionDelay;
        this.propagateHierarchyIdToField(dailyPartitionDataIngestionDelay, "daily_partition_data_ingestion_delay");
    }

    /**
     * Returns a maximum row data ingestion delay check configuration.
     * @return A maximum row data ingestion delay check configuration.
     */
    public TablePartitionReloadLagCheckSpec getDailyPartitionReloadLag() {
        return dailyPartitionReloadLag;
    }

    /**
     * Sets a maximum row data ingestion delay.
     * @param dailyPartitionReloadLag New maximum row data ingestion delay.
     */
    public void setDailyPartitionReloadLag(TablePartitionReloadLagCheckSpec dailyPartitionReloadLag) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionReloadLag, dailyPartitionReloadLag));
        this.dailyPartitionReloadLag = dailyPartitionReloadLag;
        this.propagateHierarchyIdToField(dailyPartitionReloadLag, "daily_partition_reload_lag");
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
        return CheckTimeScale.daily;
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
