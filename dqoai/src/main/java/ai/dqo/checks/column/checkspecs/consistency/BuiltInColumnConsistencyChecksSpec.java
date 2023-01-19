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
package ai.dqo.checks.column.checkspecs.consistency;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Container of built-in preconfigured consistency checks executed on a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class BuiltInColumnConsistencyChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<BuiltInColumnConsistencyChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
//			put("not_null_percent", o -> o.notNullPercent);
        }
    };
//
//    @JsonPropertyDescription("Verifies that the not null percent.")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    private ColumnConsistencyNotNullPercentCheckSpec notNullPercent;
//
//    /**
//     * Returns a not null percent check.
//     * @return Not null percent check.
//     */
//    public ColumnConsistencyNotNullPercentCheckSpec getNotNullPercent() {
//        return notNullPercent;
//    }
//
//    /**
//     * Sets a new definition of not null percent check.
//     * @param notNullPercent not null percent check.
//     */
//    public void setNotNullPercent(ColumnConsistencyNotNullPercentCheckSpec notNullPercent) {
//		this.setDirtyIf(!Objects.equals(this.notNullPercent, notNullPercent));
//        this.notNullPercent = notNullPercent;
//		propagateHierarchyIdToField(notNullPercent, "not_null_percent");
//    }

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
