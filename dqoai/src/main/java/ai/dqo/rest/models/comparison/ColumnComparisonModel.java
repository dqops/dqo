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
package ai.dqo.rest.models.comparison;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.checkspecs.comparison.*;
import ai.dqo.checks.comparison.*;
import ai.dqo.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.parquet.Strings;

/**
 * The column to column comparison model used to select which measures (min, max, sum, mean, null count, not nul count) are compared
 * for this column between the compared (tested) column and the reference column from the reference table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnComparisonModel", description = "The column to column comparison model used to select which measures (min, max, sum, mean, null count, not nul count) are compared " +
        "for this column between the compared (tested) column and the reference column from the reference table.")
@Data
public class ColumnComparisonModel {
    /**
     * The name of the compared column in the compared table (the tested table). The REST API returns all columns defined in the metadata.
     */
    @JsonPropertyDescription("The name of the compared column in the compared table (the tested table). The REST API returns all columns defined in the metadata.")
    private String comparedColumnName;

    /**
     * The name of the reference column in the reference table (the source of truth). Set the name of the reference column to enable comparison between the compared and the reference columns.
     */
    @JsonPropertyDescription("The name of the reference column in the reference table (the source of truth). Set the name of the reference column to enable comparison between the compared and the reference columns.")
    private String referenceColumnName;

//    /**
//     * The compared column's data type allows to compare the minimum value.
//     */
//    @JsonPropertyDescription("The compared column's data type allows to compare the minimum value.")
//    private boolean supportsMin = true;

    /**
     * The column compare configuration for comparing the minimum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.
     */
    @JsonPropertyDescription("The column compare configuration for comparing the minimum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.")
    private CompareThresholdsModel compareMin;

//    /**
//     * The compared column's data type allows to compare the maximum value.
//     */
//    @JsonPropertyDescription("The compared column's data type allows to compare the maximum value.")
//    private boolean supportsMax = true;

    /**
     * The column compare configuration for comparing the maximum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.
     */
    @JsonPropertyDescription("The column compare configuration for comparing the maximum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.")
    private CompareThresholdsModel compareMax;

//    /**
//     * The compared column's data type allows to compare the sum of values.
//     */
//    @JsonPropertyDescription("The compared column's data type allows to compare the sum of values.")
//    private boolean supportsSum = true;

    /**
     * The column compare configuration for comparing the sum of values between the compared (tested) column and the reference column. Leave null when the measure is not compared.
     */
    @JsonPropertyDescription("The column compare configuration for comparing the sum of values between the compared (tested) column and the reference column. Leave null when the measure is not compared.")
    private CompareThresholdsModel compareSum;

//    /**
//     * The compared column's data type allows to compare the mean of values.
//     */
//    @JsonPropertyDescription("The compared column's data type allows to compare the mean of values.")
//    private boolean supportsMean = true;

    /**
     * The column compare configuration for comparing the mean (average) value between the compared (tested) column and the reference column. Leave null when the measure is not compared.
     */
    @JsonPropertyDescription("The column compare configuration for comparing the mean (average) value between the compared (tested) column and the reference column. Leave null when the measure is not compared.")
    private CompareThresholdsModel compareMean;

//    /**
//     * The compared column's data type allows to compare the count of nulls.
//     */
//    @JsonPropertyDescription("The compared column's data type allows to compare the count of nulls.")
//    private boolean supportsNullCount = true;

    /**
     * The column compare configuration for comparing the count of null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.
     */
    @JsonPropertyDescription("The column compare configuration for comparing the count of null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.")
    private CompareThresholdsModel compareNullCount;

//    /**
//     * The compared column's data type allows to compare the count of not nulls.
//     */
//    @JsonPropertyDescription("The compared column's data type allows to compare the count of not nulls.")
//    private boolean supportsNotNullCount = true;

    /**
     * The column compare configuration for comparing the count of not null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.
     */
    @JsonPropertyDescription("The column compare configuration for comparing the count of not null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.")
    private CompareThresholdsModel compareNotNullCount;

    /**
     * Creates a column comparison model, copying the configuration of all comparison checks on the column level.
     * @param columnSpec Source column specification.
     * @param tableComparisonName The table comparison name.
     * @param checkType Check type (profiling, recurring, partitioned).
     * @param checkTimeScale Check time scale for recurring and partitioned checks.
     * @return Column comparison model.
     */
    public static ColumnComparisonModel fromColumnSpec(ColumnSpec columnSpec,
                                                       String tableComparisonName,
                                                       CheckType checkType,
                                                       CheckTimeScale checkTimeScale) {
        ColumnComparisonModel columnComparisonModel = new ColumnComparisonModel();
        if (columnSpec.getColumnName() == null) {
            throw new DqoRuntimeException("Cannot map a detached column, because the connection and table name is unknown");
        }

        columnComparisonModel.setComparedColumnName(columnSpec.getColumnName());

        AbstractRootChecksContainerSpec columnCheckRootContainer = columnSpec.getColumnCheckRootContainer(checkType, checkTimeScale, false);
        if (columnCheckRootContainer != null && columnCheckRootContainer instanceof CheckCategoriesColumnComparisonMapParent) {
            CheckCategoriesColumnComparisonMapParent columnComparisonMapParent = (CheckCategoriesColumnComparisonMapParent)columnCheckRootContainer;
            AbstractColumnComparisonCheckCategorySpecMap<? extends AbstractColumnComparisonCheckCategorySpec> columnCheckComparisonsMap = columnComparisonMapParent.getComparisons();
            AbstractColumnComparisonCheckCategorySpec columnCheckComparisonChecks = columnCheckComparisonsMap.get(tableComparisonName);

            if (columnCheckComparisonChecks != null) {
                columnComparisonModel.setReferenceColumnName(columnCheckComparisonChecks.getReferenceColumn());

                ColumnComparisonMinMatchCheckSpec minMatch = columnCheckComparisonChecks.getMinMatch();
                columnComparisonModel.setCompareMin(CompareThresholdsModel.fromComparisonCheckSpec(minMatch));

                ColumnComparisonMaxMatchCheckSpec maxMatch = columnCheckComparisonChecks.getMaxMatch();
                columnComparisonModel.setCompareMax(CompareThresholdsModel.fromComparisonCheckSpec(maxMatch));

                ColumnComparisonSumMatchCheckSpec sumMatch = columnCheckComparisonChecks.getSumMatch();
                columnComparisonModel.setCompareSum(CompareThresholdsModel.fromComparisonCheckSpec(sumMatch));

                ColumnComparisonMeanMatchCheckSpec meanMatch = columnCheckComparisonChecks.getMeanMatch();
                columnComparisonModel.setCompareMean(CompareThresholdsModel.fromComparisonCheckSpec(meanMatch));

                ColumnComparisonNullCountMatchCheckSpec nullCountMatch = columnCheckComparisonChecks.getNullCountMatch();
                columnComparisonModel.setCompareNullCount(CompareThresholdsModel.fromComparisonCheckSpec(nullCountMatch));

                ColumnComparisonNotNullCountMatchCheckSpec notNullCountMatch = columnCheckComparisonChecks.getNotNullCountMatch();
                columnComparisonModel.setCompareNotNullCount(CompareThresholdsModel.fromComparisonCheckSpec(notNullCountMatch));
            }
        }

        return columnComparisonModel;
    }

    /**
     * Copies the configuration of comparisons to column level checks at the given column.
     * @param targetColumnSpec Target column specification.
     * @param tableComparisonName Table comparison name.
     * @param checkType Check type (profiling, recurring, partitioned).
     * @param checkTimeScale Optional time scale for recurring and partitioned checks.
     */
    public void copyToColumnSpec(ColumnSpec targetColumnSpec,
                                 String tableComparisonName,
                                 CheckType checkType,
                                 CheckTimeScale checkTimeScale) {
        AbstractRootChecksContainerSpec columnCheckRootContainer = targetColumnSpec.getColumnCheckRootContainer(
                checkType, checkTimeScale, true);

        if (!(columnCheckRootContainer instanceof CheckCategoriesColumnComparisonMapParent)) {
            throw new DqoRuntimeException("The target check type does not support comparison checks.");
        }

        CheckCategoriesColumnComparisonMapParent columnComparisonMapParent = (CheckCategoriesColumnComparisonMapParent)columnCheckRootContainer;
        AbstractColumnComparisonCheckCategorySpecMap<? extends AbstractColumnComparisonCheckCategorySpec> columnCheckComparisonsMap = columnComparisonMapParent.getComparisons();

        if (Strings.isNullOrEmpty(this.referenceColumnName)) {
            // comparison is disabled and should be removed, because there is no reference column, so the model was send to UI and returned back only to show it
            columnCheckComparisonsMap.remove(tableComparisonName);
            return;
        }

        AbstractColumnComparisonCheckCategorySpec columnCheckComparisonChecks = columnCheckComparisonsMap.getOrAdd(tableComparisonName);

        if (this.compareMin != null) {
            columnCheckComparisonChecks.setMinMatch(new ColumnComparisonMinMatchCheckSpec());
            this.compareMin.copyToComparisonCheckSpec(columnCheckComparisonChecks.getMinMatch());
        } else {
            columnCheckComparisonChecks.setMinMatch(null);
        }

        if (this.compareMax != null) {
            columnCheckComparisonChecks.setMaxMatch(new ColumnComparisonMaxMatchCheckSpec());
            this.compareMax.copyToComparisonCheckSpec(columnCheckComparisonChecks.getMaxMatch());
        } else {
            columnCheckComparisonChecks.setMaxMatch(null);
        }

        if (this.compareSum != null) {
            columnCheckComparisonChecks.setSumMatch(new ColumnComparisonSumMatchCheckSpec());
            this.compareSum.copyToComparisonCheckSpec(columnCheckComparisonChecks.getSumMatch());
        } else {
            columnCheckComparisonChecks.setSumMatch(null);
        }

        if (this.compareMean != null) {
            columnCheckComparisonChecks.setMeanMatch(new ColumnComparisonMeanMatchCheckSpec());
            this.compareMean.copyToComparisonCheckSpec(columnCheckComparisonChecks.getMeanMatch());
        } else {
            columnCheckComparisonChecks.setMeanMatch(null);
        }

        if (this.compareNullCount != null) {
            columnCheckComparisonChecks.setNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec());
            this.compareNullCount.copyToComparisonCheckSpec(columnCheckComparisonChecks.getNullCountMatch());
        } else {
            columnCheckComparisonChecks.setNullCountMatch(null);
        }

        if (this.compareNotNullCount != null) {
            columnCheckComparisonChecks.setNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec());
            this.compareNotNullCount.copyToComparisonCheckSpec(columnCheckComparisonChecks.getNotNullCountMatch());
        } else {
            columnCheckComparisonChecks.setNotNullCountMatch(null);
        }
    }
}
