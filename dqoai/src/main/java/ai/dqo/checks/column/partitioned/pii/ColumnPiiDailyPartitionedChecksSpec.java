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
package ai.dqo.checks.column.partitioned.pii;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.pii.ColumnMaxPiiContainsUsaPhonePercentCheckSpec;
import ai.dqo.checks.column.pii.ColumnMaxPiiContainsUsaZipcodePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_max_contains_usa_phone_percent", o -> o.dailyPartitionMaxContainsUsaPhonePercent);
            put("daily_partition_max_contains_usa_zipcode_percent", o -> o.dailyPartitionMaxContainsUsaZipcodePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxPiiContainsUsaPhonePercentCheckSpec dailyPartitionMaxContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxPiiContainsUsaZipcodePercentCheckSpec dailyPartitionMaxContainsUsaZipcodePercent;

    /**
     * Returns a maximum rows that contains USA phone number percentage check.
     * @return Maximum rows that contains USA phone number percentage check.
     */
    public ColumnMaxPiiContainsUsaPhonePercentCheckSpec getDailyPartitionMaxContainsUsaPhonePercent() {
        return dailyPartitionMaxContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of a maximum rows that contains USA phone number percentage check.
     * @param dailyPartitionMaxContainsUsaPhonePercent Maximum rows that contains USA phone number percentage check.
     */
    public void setDailyPartitionMaxContainsUsaPhonePercent(ColumnMaxPiiContainsUsaPhonePercentCheckSpec dailyPartitionMaxContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxContainsUsaPhonePercent, dailyPartitionMaxContainsUsaPhonePercent));
        this.dailyPartitionMaxContainsUsaPhonePercent = dailyPartitionMaxContainsUsaPhonePercent;
        propagateHierarchyIdToField(dailyPartitionMaxContainsUsaPhonePercent, "daily_partition_max_contains_usa_phone_percent");
    }

    /**
     * Returns a maximum rows that contains USA zip code percentage check.
     * @return Maximum rows that contains USA zip code percentage check.
     */
    public ColumnMaxPiiContainsUsaZipcodePercentCheckSpec getDailyPartitionMaxContainsUsaZipcodePercent() {
        return dailyPartitionMaxContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a maximum rows that contains USA zip code percentage check.
     * @param dailyPartitionMaxContainsUsaZipcodePercent Maximum rows that contains USA zip code percentage check.
     */
    public void setDailyPartitionMaxContainsUsaZipcodePercent(ColumnMaxPiiContainsUsaZipcodePercentCheckSpec dailyPartitionMaxContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxContainsUsaZipcodePercent, dailyPartitionMaxContainsUsaZipcodePercent));
        this.dailyPartitionMaxContainsUsaZipcodePercent = dailyPartitionMaxContainsUsaZipcodePercent;
        propagateHierarchyIdToField(dailyPartitionMaxContainsUsaZipcodePercent, "daily_partition_max_contains_usa_zipcode_percent");
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
