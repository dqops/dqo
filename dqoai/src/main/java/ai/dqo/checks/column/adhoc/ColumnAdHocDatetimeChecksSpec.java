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
package ai.dqo.checks.column.adhoc;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.datetime.ColumnMaxDatetimeValuesInFuturePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for datetime.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocDatetimeChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocDatetimeChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("max_datetime_values_in_future_percent", o -> o.maxDatetimeValuesInFuturePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of datetime values in future in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxDatetimeValuesInFuturePercentCheckSpec maxDatetimeValuesInFuturePercent;

    /**
     * Returns a maximum datetime values in future percent check.
     * @return Maximum datetime values in future percent check.
     */
    public ColumnMaxDatetimeValuesInFuturePercentCheckSpec getMaxDatetimeValuesInFuturePercent() {
        return maxDatetimeValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a maximum datetime values in future percent check.
     * @param maxDatetimeValuesInFuturePercent Maximum datetime values in future percent check.
     */
    public void setMaxDatetimeValuesInFuturePercent(ColumnMaxDatetimeValuesInFuturePercentCheckSpec maxDatetimeValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.maxDatetimeValuesInFuturePercent, maxDatetimeValuesInFuturePercent));
        this.maxDatetimeValuesInFuturePercent = maxDatetimeValuesInFuturePercent;
        propagateHierarchyIdToField(maxDatetimeValuesInFuturePercent, "max_datetime_values_in_future_percent");
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