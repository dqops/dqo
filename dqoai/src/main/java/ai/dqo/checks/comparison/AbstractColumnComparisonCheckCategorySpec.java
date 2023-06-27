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

import ai.dqo.checks.column.checkspecs.comparison.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Base class for column level comparison (accuracy) checks that run accuracy checks for one comparison (comparing against one target table)
 * for one column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractColumnComparisonCheckCategorySpec extends AbstractComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractColumnComparisonCheckCategorySpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractComparisonCheckCategorySpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The name of the reference column name in the reference table. It is the column to which the current column is compared to.")
    private String referenceColumn;

    /**
     * Returns the name of the reference column name in the reference table. It is the column to which the current column is compared to.
     * @return The name of the reference column.
     */
    public String getReferenceColumn() {
        return referenceColumn;
    }

    /**
     * Sets the name of the reference column from the reference table that we are comparing to.
     * @param referenceColumn The name of the reference column.
     */
    public void setReferenceColumn(String referenceColumn) {
        this.setDirtyIf(!Objects.equals(this.referenceColumn, referenceColumn));
        this.referenceColumn = referenceColumn;
    }


    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public abstract ColumnComparisonSumMatchCheckSpec getSumMatch();

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param sumMatch accuracy total sum match percent check specification.
     */
    public abstract void setSumMatch(ColumnComparisonSumMatchCheckSpec sumMatch);

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public abstract ColumnComparisonMinMatchCheckSpec getMinMatch();

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param minMatch Accuracy min percent check specification.
     */
    public abstract void setMinMatch(ColumnComparisonMinMatchCheckSpec minMatch);

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public abstract ColumnComparisonMaxMatchCheckSpec getMaxMatch();

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param maxMatch Accuracy max percent check specification.
     */
    public abstract void setMaxMatch(ColumnComparisonMaxMatchCheckSpec maxMatch);

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public abstract ColumnComparisonMeanMatchCheckSpec getMeanMatch();

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param meanMatch Accuracy average percent check specification.
     */
    public abstract void setMeanMatch(ColumnComparisonMeanMatchCheckSpec meanMatch);

    /**
     * Returns an accuracy not null count percent check specification.
     * @return Accuracy not null count percent check specification.
     */
    public abstract ColumnComparisonNotNullCountMatchCheckSpec getNotNullCountMatch();

    /**
     * Sets a new definition of an accuracy not null count percent check.
     * @param notNullCountMatch Accuracy not null count percent check specification.
     */
    public abstract void setNotNullCountMatch(ColumnComparisonNotNullCountMatchCheckSpec notNullCountMatch);

    /**
     * Returns an accuracy null count percent check specification.
     * @return Accuracy null count percent check specification.
     */
    public abstract ColumnComparisonNullCountMatchCheckSpec getNullCountMatch();

    /**
     * Sets a new definition of an accuracy null count percent check.
     * @param nullCountMatch Accuracy null count percent check specification.
     */
    public abstract void setNullCountMatch(ColumnComparisonNullCountMatchCheckSpec nullCountMatch);
}
