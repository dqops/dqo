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
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessAnomalyCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
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
 * Container of table level daily monitoring for timeliness data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_data_freshness", o -> o.dailyDataFreshness);
           put("daily_data_freshness_anomaly", o -> o.dailyDataFreshnessAnomaly);
           put("daily_data_ingestion_delay", o -> o.dailyDataIngestionDelay);
           put("daily_data_staleness", o -> o.dailyDataStaleness);
        }
    };

    @JsonPropertyDescription("Daily calculating the number of days since the most recent event timestamp (freshness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataFreshnessCheckSpec dailyDataFreshness;

    @JsonPropertyDescription("Verifies that the number of days since the most recent event timestamp (freshness) changes in a rate within a percentile boundary during the last 90 days.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataFreshnessAnomalyCheckSpec dailyDataFreshnessAnomaly;

    @JsonPropertyDescription("Daily calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataStalenessCheckSpec dailyDataStaleness;

    @JsonPropertyDescription("Daily calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableDataIngestionDelayCheckSpec dailyDataIngestionDelay;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDataFreshnessCheckSpec getDailyDataFreshness() {
        return dailyDataFreshness;
    }

    /**
     * Sets the number of days since the most recent event.
     * @param dailyDataFreshness New days since the most recent event check.
     */
    public void setDailyDataFreshness(TableDataFreshnessCheckSpec dailyDataFreshness) {
		this.setDirtyIf(!Objects.equals(this.dailyDataFreshness, dailyDataFreshness));
        this.dailyDataFreshness = dailyDataFreshness;
		this.propagateHierarchyIdToField(dailyDataFreshness, "daily_data_freshness");
    }

    /**
     * Returns the number of days since the most recent event value anomaly 90 days check specification.
     * @return A number of days since the most recent event value anomaly 90 days check specification.
     */
    public TableDataFreshnessAnomalyCheckSpec getDailyDataFreshnessAnomaly() {
        return dailyDataFreshnessAnomaly;
    }

    /**
     * Sets the number of days since the most recent event value anomaly 90 days check specification.
     * @param dailyDataFreshnessAnomaly freshness value anomaly 90 days check specification.
     */
    public void setDailyDataFreshnessAnomaly(TableDataFreshnessAnomalyCheckSpec dailyDataFreshnessAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyDataFreshnessAnomaly, dailyDataFreshnessAnomaly));
        this.dailyDataFreshnessAnomaly = dailyDataFreshnessAnomaly;
        this.propagateHierarchyIdToField(dailyDataFreshnessAnomaly, "daily_data_freshness_anomaly");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration..
     */
    public TableDataStalenessCheckSpec getDailyDataStaleness() {
        return dailyDataStaleness;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param dailyDataStaleness A number of days since the last data ingestion check configuration.
     */
    public void setDailyDataStaleness(TableDataStalenessCheckSpec dailyDataStaleness) {
        this.setDirtyIf(!Objects.equals(this.dailyDataStaleness, dailyDataStaleness));
        this.dailyDataStaleness = dailyDataStaleness;
        this.propagateHierarchyIdToField(dailyDataStaleness, "daily_data_staleness");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getDailyDataIngestionDelay() {
        return dailyDataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dailyDataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDailyDataIngestionDelay(TableDataIngestionDelayCheckSpec dailyDataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dailyDataIngestionDelay, dailyDataIngestionDelay));
        this.dailyDataIngestionDelay = dailyDataIngestionDelay;
        this.propagateHierarchyIdToField(dailyDataIngestionDelay, "daily_data_ingestion_delay");
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
