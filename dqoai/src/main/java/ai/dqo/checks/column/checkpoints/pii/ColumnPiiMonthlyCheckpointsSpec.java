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
import ai.dqo.checks.column.pii.ColumnMaxPiiContainsUsaPhonePercentCheckSpec;
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
            put("monthly_checkpoint_max_contains_usa_phone_percent", o -> o.monthlyCheckpointMaxContainsUsaPhonePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxPiiContainsUsaPhonePercentCheckSpec monthlyCheckpointMaxContainsUsaPhonePercent;

    /**
     * Returns a maximum rows that contains USA phone number percent check.
     * @return Maximum rows that contains USA phone number percent check.
     */
    public ColumnMaxPiiContainsUsaPhonePercentCheckSpec getMonthlyCheckpointMaxContainsUsaPhonePercent() {
        return monthlyCheckpointMaxContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of a maximum rows that contains USA phone number percent check.
     * @param monthlyCheckpointMaxContainsUsaPhonePercent Maximum rows that contains USA phone number percent check.
     */
    public void setMonthlyCheckpointMaxContainsUsaPhonePercent(ColumnMaxPiiContainsUsaPhonePercentCheckSpec monthlyCheckpointMaxContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxContainsUsaPhonePercent, monthlyCheckpointMaxContainsUsaPhonePercent));
        this.monthlyCheckpointMaxContainsUsaPhonePercent = monthlyCheckpointMaxContainsUsaPhonePercent;
        propagateHierarchyIdToField(monthlyCheckpointMaxContainsUsaPhonePercent, "monthly_checkpoint_max_contains_usa_phone_percent");
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