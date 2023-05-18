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
package ai.dqo.checks.table.partitioned.anomaly;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCount30DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCount60DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCount7DaysCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured anomaly data quality checks on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAnomalyDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAnomalyDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_row_count_anomaly_7d", o -> o.dailyPartitionRowCountAnomaly7Days);
            put("daily_partition_row_count_anomaly_30d", o -> o.dailyPartitionRowCountAnomaly30Days);
            put("daily_partition_row_count_anomaly_60d", o -> o.dailyPartitionRowCountAnomaly60Days);
        }
    };

    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 7 days.")
    private TableAnomalyRowCount7DaysCheckSpec dailyPartitionRowCountAnomaly7Days;

    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.")
    private TableAnomalyRowCount30DaysCheckSpec dailyPartitionRowCountAnomaly30Days;

    @JsonPropertyDescription("Verifies that the total row count of the tested table is within a percentile from measurements made during the last 60 days.")
    private TableAnomalyRowCount60DaysCheckSpec dailyPartitionRowCountAnomaly60Days;

    /**
     * Returns the row count match check.
     * @return Row count match check.
     */
    public TableAnomalyRowCount7DaysCheckSpec getDailyPartitionRowCountAnomaly7Days() {
        return dailyPartitionRowCountAnomaly7Days;
    }

    /**
     * Sets a new row count match check.
     * @param dailyPartitionRowCountAnomaly7Days Row count match check.
     */
    public void setDailyPartitionRowCountAnomaly7Days(TableAnomalyRowCount7DaysCheckSpec dailyPartitionRowCountAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomaly7Days, dailyPartitionRowCountAnomaly7Days));
        this.dailyPartitionRowCountAnomaly7Days = dailyPartitionRowCountAnomaly7Days;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomaly7Days, "row_count_anomaly_7d");
    }

    /**
     * Returns the row count anomaly 30 days check.
     * @return Row count anomaly 30 days check.
     */
    public TableAnomalyRowCount30DaysCheckSpec getDailyPartitionRowCountAnomaly30Days() {
        return dailyPartitionRowCountAnomaly30Days;
    }

    /**
     * Sets a new row count anomaly 30 days check.
     * @param dailyPartitionRowCountAnomaly30Days Row count anomaly 30 days check.
     */
    public void setDailyPartitionRowCountAnomaly30Days(TableAnomalyRowCount30DaysCheckSpec dailyPartitionRowCountAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomaly30Days, dailyPartitionRowCountAnomaly30Days));
        this.dailyPartitionRowCountAnomaly30Days = dailyPartitionRowCountAnomaly30Days;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomaly30Days, "row_count_anomaly_30d");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableAnomalyRowCount60DaysCheckSpec getDailyPartitionRowCountAnomaly60Days() {
        return dailyPartitionRowCountAnomaly60Days;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param dailyPartitionRowCountAnomaly60Days Row count anomaly 60 days check.
     */
    public void setDailyPartitionRowCountAnomaly60Days(TableAnomalyRowCount60DaysCheckSpec dailyPartitionRowCountAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionRowCountAnomaly60Days, dailyPartitionRowCountAnomaly60Days));
        this.dailyPartitionRowCountAnomaly60Days = dailyPartitionRowCountAnomaly60Days;
        propagateHierarchyIdToField(dailyPartitionRowCountAnomaly60Days, "row_count_anomaly_60d");
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
