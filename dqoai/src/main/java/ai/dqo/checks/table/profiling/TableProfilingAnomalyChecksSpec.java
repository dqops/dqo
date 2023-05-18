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
package ai.dqo.checks.table.profiling;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.accuracy.TableAccuracyRowCountMatchPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCountChange30DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCountChange60DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCountChange7DaysCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TableProfilingAnomalyChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableProfilingAnomalyChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("row_count_anomaly_7_days", o -> o.rowCountAnomaly7Days);
            put("row_count_anomaly_30_days", o -> o.rowCountAnomaly30Days);
            put("row_count_anomaly_60_days", o -> o.rowCountAnomaly60Days);
        }
    };

    @JsonProperty("row_count_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.")
    private TableAnomalyRowCountChange7DaysCheckSpec rowCountAnomaly7Days;

    @JsonProperty("row_count_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.")
    private TableAnomalyRowCountChange30DaysCheckSpec rowCountAnomaly30Days;

    @JsonProperty("row_count_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.")
    private TableAnomalyRowCountChange60DaysCheckSpec rowCountAnomaly60Days;

    /**
     * Returns the row count match check.
     * @return Row count match check.
     */
    public TableAnomalyRowCountChange7DaysCheckSpec getRowCountAnomaly7Days() {
        return rowCountAnomaly7Days;
    }

    /**
     * Sets a new row count match check.
     * @param rowCountAnomaly7Days Row count match check.
     */
    public void setRowCountAnomaly7Days(TableAnomalyRowCountChange7DaysCheckSpec rowCountAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountAnomaly7Days, rowCountAnomaly7Days));
        this.rowCountAnomaly7Days = rowCountAnomaly7Days;
        propagateHierarchyIdToField(rowCountAnomaly7Days, "row_count_anomaly_7_days");
    }

    /**
     * Returns the row count anomaly 30 days check.
     * @return Row count anomaly 30 days check.
     */
    public TableAnomalyRowCountChange30DaysCheckSpec getRowCountAnomaly30Days() {
        return rowCountAnomaly30Days;
    }

    /**
     * Sets a new row count anomaly 30 days check.
     * @param rowCountAnomaly30Days Row count anomaly 30 days check.
     */
    public void setRowCountAnomaly30Days(TableAnomalyRowCountChange30DaysCheckSpec rowCountAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountAnomaly30Days, rowCountAnomaly30Days));
        this.rowCountAnomaly30Days = rowCountAnomaly30Days;
        propagateHierarchyIdToField(rowCountAnomaly30Days, "row_count_anomaly_30_days");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableAnomalyRowCountChange60DaysCheckSpec getRowCountAnomaly60Days() {
        return rowCountAnomaly60Days;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param rowCountAnomaly60Days Row count anomaly 60 days check.
     */
    public void setRowCountAnomaly60Days(TableAnomalyRowCountChange60DaysCheckSpec rowCountAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.rowCountAnomaly60Days, rowCountAnomaly60Days));
        this.rowCountAnomaly60Days = rowCountAnomaly60Days;
        propagateHierarchyIdToField(rowCountAnomaly60Days, "row_count_anomaly_60_days");
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
