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
package com.dqops.checks.table.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.checks.table.checkspecs.timeliness.TableDataStalenessCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of timeliness data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableTimelinessProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableTimelinessProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("data_freshness", o -> o.dataFreshness);
            put("data_staleness", o -> o.dataStaleness);
            put("data_ingestion_delay", o -> o.dataIngestionDelay);
        }
    };

    @JsonPropertyDescription("Calculates the number of days since the most recent event timestamp (freshness)")
    private TableDataFreshnessCheckSpec dataFreshness;

    @JsonPropertyDescription("Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)")
    private TableDataStalenessCheckSpec dataStaleness;

    @JsonPropertyDescription("Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp")
    private TableDataIngestionDelayCheckSpec dataIngestionDelay;

    /**
     * Returns the number of days since the most recent event check configuration.
     * @return A number of days since the most recent event check configuration.
     */
    public TableDataFreshnessCheckSpec getDataFreshness() {
        return dataFreshness;
    }

    /**
     * Sets the number of days since the most recent event check configuration.
     * @param dataFreshness Maximum days since the most recent event check configuration.
     */
    public void setDataFreshness(TableDataFreshnessCheckSpec dataFreshness) {
        this.setDirtyIf(!Objects.equals(this.dataFreshness, dataFreshness));
        this.dataFreshness = dataFreshness;
        propagateHierarchyIdToField(dataFreshness, "data_freshness");
    }

    /**
     * Returns a number of days since the last data ingestion check configuration.
     * @return A number of days since the last data ingestion check configuration.
     */
    public TableDataStalenessCheckSpec getDataStaleness() {
        return dataStaleness;
    }

    /**
     * Sets a number of days since the last data ingestion check configuration.
     * @param dataStaleness  A number of days since the last load check configuration.
     */
    public void setDataStaleness(TableDataStalenessCheckSpec dataStaleness) {
        this.setDirtyIf(!Objects.equals(this.dataStaleness, dataStaleness));
        this.dataStaleness = dataStaleness;
        propagateHierarchyIdToField(dataStaleness, "data_staleness");
    }

    /**
     * Returns a data ingestion delay check configuration.
     * @return A data ingestion delay check configuration.
     */
    public TableDataIngestionDelayCheckSpec getDataIngestionDelay() {
        return dataIngestionDelay;
    }

    /**
     * Sets a data ingestion delay check configuration.
     * @param dataIngestionDelay A data ingestion delay check configuration.
     */
    public void setDataIngestionDelay(TableDataIngestionDelayCheckSpec dataIngestionDelay) {
        this.setDirtyIf(!Objects.equals(this.dataIngestionDelay, dataIngestionDelay));
        this.dataIngestionDelay = dataIngestionDelay;
        propagateHierarchyIdToField(dataIngestionDelay, "data_ingestion_delay");
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
}
