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

package ai.dqo.checks.comparison;

import ai.dqo.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Base class for comparison (accuracy) check category at a table level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractTableComparisonCheckCategorySpec extends AbstractComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractTableComparisonCheckCategorySpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractComparisonCheckCategorySpec.FIELDS) {
        {
        }
    };

    /**
     * Returns the row count match check.
     * @return Row count match check.
     */
    public abstract TableComparisonRowCountMatchCheckSpec getRowCountMatch();

    /**
     * Sets a new row count match check.
     * @param rowCountMatch Row count match check.
     */
    public abstract void setRowCountMatch(TableComparisonRowCountMatchCheckSpec rowCountMatch);
}
