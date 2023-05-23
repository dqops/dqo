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
package ai.dqo.checks.table.recurring.volume;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.volume.TableAnomalyRowCountChange30DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.volume.TableAnomalyRowCountChange60DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.volume.TableAnomalyRowCountChange7DaysCheckSpec;
import ai.dqo.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level daily recurring for volume data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("daily_row_count", o -> o.dailyRowCount);
           put("daily_row_count_anomaly_7_days", o -> o.dailyRowCountAnomaly7Days);
           put("daily_row_count_anomaly_30_days", o -> o.dailyRowCountAnomaly30Days);
           put("daily_row_count_anomaly_60_days", o -> o.dailyRowCountAnomaly60Days);
        }
    };

    @JsonPropertyDescription("Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec dailyRowCount;

    @JsonProperty("daily_row_count_anomaly_7_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.")
    private TableAnomalyRowCountChange7DaysCheckSpec dailyRowCountAnomaly7Days;

    @JsonProperty("daily_row_count_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.")
    private TableAnomalyRowCountChange30DaysCheckSpec dailyRowCountAnomaly30Days;

    @JsonProperty("daily_row_count_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.")
    private TableAnomalyRowCountChange60DaysCheckSpec dailyRowCountAnomaly60Days;

    /**
     * Returns the row count check configuration.
     * @return Row count check specification.
     */
    public TableRowCountCheckSpec getDailyRowCount() {
        return dailyRowCount;
    }

    /**
     * Sets the row count check.
     * @param dailyRowCount New row count check.
     */
    public void setDailyRowCount(TableRowCountCheckSpec dailyRowCount) {
		this.setDirtyIf(!Objects.equals(this.dailyRowCount, dailyRowCount));
        this.dailyRowCount = dailyRowCount;
		this.propagateHierarchyIdToField(dailyRowCount, "daily_row_count");
    }

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
        propagateHierarchyIdToField(dailyRowCountAnomaly7Days, "daily_row_count_anomaly_7_days");
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
        propagateHierarchyIdToField(dailyRowCountAnomaly30Days, "daily_row_count_anomaly_30_days");
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
        propagateHierarchyIdToField(dailyRowCountAnomaly60Days, "daily_row_count_anomaly_60_days");
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
