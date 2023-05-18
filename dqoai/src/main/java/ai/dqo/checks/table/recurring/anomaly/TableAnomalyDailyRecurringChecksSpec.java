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
package ai.dqo.checks.table.recurring.anomaly;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCountChange30DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCountChange60DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.anomaly.TableAnomalyRowCountChange7DaysCheckSpec;
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
public class TableAnomalyDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAnomalyDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_row_count_anomaly_7d", o -> o.dailyRowCountAnomaly7Days);
            put("daily_row_count_anomaly_30d", o -> o.dailyRowCountAnomaly30Days);
            put("daily_row_count_anomaly_60d", o -> o.dailyRowCountAnomaly60Days);
        }
    };
    
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.")
    private TableAnomalyRowCountChange7DaysCheckSpec dailyRowCountAnomaly7Days;

    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.")
    private TableAnomalyRowCountChange30DaysCheckSpec dailyRowCountAnomaly30Days;

    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.")
    private TableAnomalyRowCountChange60DaysCheckSpec dailyRowCountAnomaly60Days;

    /**
     * Returns the row count match check.
     * @return Row count match check.
     */
    public TableAnomalyRowCountChange7DaysCheckSpec getDailyRowCountAnomaly7Days() {
        return dailyRowCountAnomaly7Days;
    }

    /**
     * Sets a new row count match check.
     * @param dailyRowCountAnomaly7Days Row count match check.
     */
    public void setDailyRowCountAnomaly7Days(TableAnomalyRowCountChange7DaysCheckSpec dailyRowCountAnomaly7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountAnomaly7Days, dailyRowCountAnomaly7Days));
        this.dailyRowCountAnomaly7Days = dailyRowCountAnomaly7Days;
        propagateHierarchyIdToField(dailyRowCountAnomaly7Days, "row_count_anomaly_7d");
    }

    /**
     * Returns the row count anomaly 30 days check.
     * @return Row count anomaly 30 days check.
     */
    public TableAnomalyRowCountChange30DaysCheckSpec getDailyRowCountAnomaly30Days() {
        return dailyRowCountAnomaly30Days;
    }

    /**
     * Sets a new row count anomaly 30 days check.
     * @param dailyRowCountAnomaly30Days Row count anomaly 30 days check.
     */
    public void setDailyRowCountAnomaly30Days(TableAnomalyRowCountChange30DaysCheckSpec dailyRowCountAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountAnomaly30Days, dailyRowCountAnomaly30Days));
        this.dailyRowCountAnomaly30Days = dailyRowCountAnomaly30Days;
        propagateHierarchyIdToField(dailyRowCountAnomaly30Days, "row_count_anomaly_30d");
    }

    /**
     * Returns the row count anomaly 60 days check.
     * @return Row count anomaly 60 days check.
     */
    public TableAnomalyRowCountChange60DaysCheckSpec getDailyRowCountAnomaly60Days() {
        return dailyRowCountAnomaly60Days;
    }

    /**
     * Sets a new row count anomaly 60 days check.
     * @param dailyRowCountAnomaly60Days Row count anomaly 60 days check.
     */
    public void setDailyRowCountAnomaly60Days(TableAnomalyRowCountChange60DaysCheckSpec dailyRowCountAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyRowCountAnomaly60Days, dailyRowCountAnomaly60Days));
        this.dailyRowCountAnomaly60Days = dailyRowCountAnomaly60Days;
        propagateHierarchyIdToField(dailyRowCountAnomaly60Days, "row_count_anomaly_60d");
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
