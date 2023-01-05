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
package ai.dqo.checks.column.checkpoints.pii;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.pii.ColumnPiiContainsUsaPhonePercentCheckSpec;
import ai.dqo.checks.column.pii.ColumnPiiContainsUsaZipcodePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_contains_usa_phone_percent", o -> o.monthlyCheckpointContainsUsaPhonePercent);
            put("monthly_checkpoint_contains_usa_zipcode_percent", o -> o.monthlyCheckpointContainsUsaZipcodePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec monthlyCheckpointContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyCheckpointContainsUsaZipcodePercent;

    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getMonthlyCheckpointContainsUsaPhonePercent() {
        return monthlyCheckpointContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of contains USA phone number percent check.
     * @param monthlyCheckpointContainsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setMonthlyCheckpointContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec monthlyCheckpointContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointContainsUsaPhonePercent, monthlyCheckpointContainsUsaPhonePercent));
        this.monthlyCheckpointContainsUsaPhonePercent = monthlyCheckpointContainsUsaPhonePercent;
        propagateHierarchyIdToField(monthlyCheckpointContainsUsaPhonePercent, "monthly_checkpoint_contains_usa_phone_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getMonthlyCheckpointContainsUsaZipcodePercent() {
        return monthlyCheckpointContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of contains USA zip code percent check.
     * @param monthlyCheckpointContainsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setMonthlyCheckpointContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyCheckpointContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointContainsUsaZipcodePercent, monthlyCheckpointContainsUsaZipcodePercent));
        this.monthlyCheckpointContainsUsaZipcodePercent = monthlyCheckpointContainsUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyCheckpointContainsUsaZipcodePercent, "monthly_checkpoint_contains_usa_zipcode_percent");
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