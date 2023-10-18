/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.rest.models.comparison;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.comparison.*;
import com.dqops.checks.comparison.*;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.utils.exceptions.DqoRuntimeException;
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
     * @param referenceTableConfigurationName The table comparison name.
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param checkTimeScale Check time scale for monitoring and partitioned checks.
     * @return Column comparison model.
     */
    public static ColumnComparisonModel fromColumnSpec(ColumnSpec columnSpec,
                                                       String referenceTableConfigurationName,
                                                       CheckType checkType,
                                                       CheckTimeScale checkTimeScale) {
        ColumnComparisonModel columnComparisonModel = new ColumnComparisonModel();
        if (columnSpec.getColumnName() == null) {
            throw new DqoRuntimeException("Cannot map a detached column, because the connection and table name is unknown");
        }

        columnComparisonModel.setComparedColumnName(columnSpec.getColumnName());

        AbstractRootChecksContainerSpec columnCheckRootContainer = columnSpec.getColumnCheckRootContainer(checkType, checkTimeScale, false);
        AbstractComparisonCheckCategorySpecMap<?> comparisonsMap = columnCheckRootContainer.getComparisons();
        if (comparisonsMap instanceof AbstractColumnComparisonCheckCategorySpecMap) {
            AbstractColumnComparisonCheckCategorySpecMap<? extends AbstractColumnComparisonCheckCategorySpec> columnCheckComparisonsMap =
                    (AbstractColumnComparisonCheckCategorySpecMap<? extends AbstractColumnComparisonCheckCategorySpec>)comparisonsMap;
            AbstractColumnComparisonCheckCategorySpec columnCheckComparisonChecks = columnCheckComparisonsMap.get(referenceTableConfigurationName);

            if (columnCheckComparisonChecks != null) {
                columnComparisonModel.setReferenceColumnName(columnCheckComparisonChecks.getReferenceColumn());

                ComparisonCheckRules minMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.min_match, false);
                columnComparisonModel.setCompareMin(CompareThresholdsModel.fromComparisonCheckSpec(minMatch));

                ComparisonCheckRules maxMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.max_match, false);
                columnComparisonModel.setCompareMax(CompareThresholdsModel.fromComparisonCheckSpec(maxMatch));

                ComparisonCheckRules sumMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.sum_match, false);
                columnComparisonModel.setCompareSum(CompareThresholdsModel.fromComparisonCheckSpec(sumMatch));

                ComparisonCheckRules meanMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.mean_match, false);
                columnComparisonModel.setCompareMean(CompareThresholdsModel.fromComparisonCheckSpec(meanMatch));

                ComparisonCheckRules nullCountMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.null_count_match, false);
                columnComparisonModel.setCompareNullCount(CompareThresholdsModel.fromComparisonCheckSpec(nullCountMatch));

                ComparisonCheckRules notNullCountMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.not_null_count_match, false);
                columnComparisonModel.setCompareNotNullCount(CompareThresholdsModel.fromComparisonCheckSpec(notNullCountMatch));
            }
        }

        return columnComparisonModel;
    }

    /**
     * Copies the configuration of comparisons to column level checks at the given column.
     * @param targetColumnSpec Target column specification.
     * @param referenceTableConfigurationName Table comparison name.
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param checkTimeScale Optional time scale for monitoring and partitioned checks.
     */
    public void copyToColumnSpec(ColumnSpec targetColumnSpec,
                                 String referenceTableConfigurationName,
                                 CheckType checkType,
                                 CheckTimeScale checkTimeScale) {
        AbstractRootChecksContainerSpec columnCheckRootContainer = targetColumnSpec.getColumnCheckRootContainer(
                checkType, checkTimeScale, true);
        AbstractComparisonCheckCategorySpecMap columnCheckComparisonsMap = columnCheckRootContainer.getComparisons();

        if (Strings.isNullOrEmpty(this.referenceColumnName)) {
            // comparison is disabled and should be removed, because there is no reference column, so the model was send to UI and returned back only to show it
            columnCheckComparisonsMap.remove(referenceTableConfigurationName);
            return;
        }

        AbstractColumnComparisonCheckCategorySpec columnCheckComparisonChecks =
                (AbstractColumnComparisonCheckCategorySpec)columnCheckComparisonsMap.getOrAdd(referenceTableConfigurationName);
        columnCheckComparisonChecks.setReferenceColumn(this.referenceColumnName);

        if (this.compareMin != null) {
            ComparisonCheckRules minMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.min_match, true);
            this.compareMin.copyToComparisonCheckSpec(minMatch);
        } else {
            columnCheckComparisonChecks.removeCheckSpec(ColumnCompareCheckType.min_match);
        }

        if (this.compareMax != null) {
            ComparisonCheckRules maxMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.max_match, true);
            this.compareMax.copyToComparisonCheckSpec(maxMatch);
        } else {
            columnCheckComparisonChecks.removeCheckSpec(ColumnCompareCheckType.max_match);
        }

        if (this.compareSum != null) {
            ComparisonCheckRules sumMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.sum_match, true);
            this.compareSum.copyToComparisonCheckSpec(sumMatch);
        } else {
            columnCheckComparisonChecks.removeCheckSpec(ColumnCompareCheckType.sum_match);
        }

        if (this.compareMean != null) {
            ComparisonCheckRules meanMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.mean_match, true);
            this.compareMean.copyToComparisonCheckSpec(meanMatch);
        } else {
            columnCheckComparisonChecks.removeCheckSpec(ColumnCompareCheckType.mean_match);
        }

        if (this.compareNullCount != null) {
            ComparisonCheckRules nullCountMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.null_count_match, true);
            this.compareNullCount.copyToComparisonCheckSpec(nullCountMatch);
        } else {
            columnCheckComparisonChecks.removeCheckSpec(ColumnCompareCheckType.null_count_match);
        }

        if (this.compareNotNullCount != null) {
            ComparisonCheckRules notNullCountMatch = columnCheckComparisonChecks.getCheckSpec(ColumnCompareCheckType.not_null_count_match, true);
            this.compareNotNullCount.copyToComparisonCheckSpec(notNullCountMatch);
        } else {
            columnCheckComparisonChecks.removeCheckSpec(ColumnCompareCheckType.not_null_count_match);
        }
    }
}
