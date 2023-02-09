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
import ai.dqo.checks.column.checkspecs.accuracy.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for accuracy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocAccuracyChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocAccuracyChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("accuracy_value_match_count", o -> o.accuracyValueMatchCount);
        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that matches values in another table column does not exceed the set count.")
    private ColumnAccuracyValueMatchCountCheckSpec accuracyValueMatchCount;

    /**
     * Returns an accuracy value match count check specification.
     * @return Accuracy value match count check specification.
     */
    public ColumnAccuracyValueMatchCountCheckSpec getAccuracyValueMatchCount() {
        return accuracyValueMatchCount;
    }

    /**
     * Sets accuracy value match count check specification.
     * @param accuracyValueMatchCount Accuracy value match count check specification.
     */
    public void setAccuracyValueMatchCount(ColumnAccuracyValueMatchCountCheckSpec accuracyValueMatchCount) {
        this.setDirtyIf(!Objects.equals(this.accuracyValueMatchCount, accuracyValueMatchCount));
        this.accuracyValueMatchCount = accuracyValueMatchCount;
        propagateHierarchyIdToField(accuracyValueMatchCount, "accuracy_value_match_count");
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