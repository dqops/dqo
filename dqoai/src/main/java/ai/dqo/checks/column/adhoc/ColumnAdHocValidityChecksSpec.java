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
import ai.dqo.checks.column.validity.ColumnValidityMinValidUsaZipcodePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for valid USA Zip codes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocValidityChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocValidityChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("min_valid_usa_zipcode_percent", o -> o.minValidUsaZipcodePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of valid USA Zip codes in a column does not exceed the minimum accepted percentage.")
    private ColumnValidityMinValidUsaZipcodePercentCheckSpec minValidUsaZipcodePercent;

    /**
     * Returns a minimum valid USA Zip codes percent check.
     * @return Minimum valid USA Zip codes percent check.
     */
    public ColumnValidityMinValidUsaZipcodePercentCheckSpec getMinValidUsaZipcodePercent() {
        return minValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum valid USA Zip codes percent check.
     * @param minValidUsaZipcodePercent Minimum valid USA Zip codes percent check.
     */
    public void setMinValidUsaZipcodePercent(ColumnValidityMinValidUsaZipcodePercentCheckSpec minValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.minValidUsaZipcodePercent, minValidUsaZipcodePercent));
        this.minValidUsaZipcodePercent = minValidUsaZipcodePercent;
        propagateHierarchyIdToField(minValidUsaZipcodePercent, "min_valid_usa_zipcode_percent");
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