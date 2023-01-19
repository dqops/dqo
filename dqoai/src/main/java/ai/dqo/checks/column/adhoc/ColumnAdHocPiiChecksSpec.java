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
import ai.dqo.checks.column.checkspecs.pii.ColumnPiiContainsUsaPhonePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.pii.ColumnPiiContainsUsaZipcodePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for Personal Identifiable Information (PII).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocPiiChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocPiiChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("contains_usa_phone_percent", o -> o.containsUsaPhonePercent);
            put("contains_usa_zipcode_percent", o -> o.containsUsaZipcodePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec containsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec containsUsaZipcodePercent;


    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getContainsUsaPhonePercent() {
        return containsUsaPhonePercent;
    }

    /**
     * Sets a new contains USA phone number percent check specification.
     * @param containsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec containsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.containsUsaPhonePercent, containsUsaPhonePercent));
        this.containsUsaPhonePercent = containsUsaPhonePercent;
        propagateHierarchyIdToField(containsUsaPhonePercent, "contains_usa_phone_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getContainsUsaZipcodePercent() {
        return containsUsaZipcodePercent;
    }

    /**
     * Sets contains USA zip code percent check specification.
     * @param containsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec containsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.containsUsaZipcodePercent, containsUsaZipcodePercent));
        this.containsUsaZipcodePercent = containsUsaZipcodePercent;
        propagateHierarchyIdToField(containsUsaZipcodePercent, "contains_usa_zipcode_percent");
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