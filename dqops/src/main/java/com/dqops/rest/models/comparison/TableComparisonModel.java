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
import com.dqops.checks.comparison.*;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.docs.SampleStringsRegistry;
import com.dqops.utils.docs.SampleValueFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model that contains the all editable information about a table-to-table comparison defined on a compared table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonModel", description = "Model that contains the all editable information about a table-to-table comparison defined on a compared table.")
@Data
public class TableComparisonModel {
    /**
     * The name of the table comparison configuration that is defined in the 'table_comparisons' node on the table specification.
     */
    @JsonPropertyDescription("The name of the table comparison configuration that is defined in the 'table_comparisons' node on the table specification.")
    private String tableComparisonConfigurationName;

    /**
     * Compared connection name - the connection name to the data source that is compared (verified).
     */
    @JsonPropertyDescription("Compared connection name - the connection name to the data source that is compared (verified).")
    private String comparedConnection;

    /**
     * The schema and table name of the compared table that is verified.
     */
    @JsonPropertyDescription("The schema and table name of the compared table that is verified.")
    private PhysicalTableName comparedTable;

    /**
     * Reference connection name - the connection name to the data source that has the reference data to compare to.
     */
    @JsonPropertyDescription("Reference connection name - the connection name to the data source that has the reference data to compare to.")
    private String referenceConnection;

    /**
     * The schema and table name of the reference table that has the expected data.
     */
    @JsonPropertyDescription("The schema and table name of the reference table that has the expected data.")
    private PhysicalTableName referenceTable;

    /**
     * List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause.
     */
    @JsonPropertyDescription("List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  " +
            "for grouping both the compared table and the reference table (the source of truth). " +
            "The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.")
    private List<TableComparisonGroupingColumnPairModel> groupingColumns = new ArrayList<>();

    /**
     * The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.
     */
    @JsonPropertyDescription("The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.")
    private CompareThresholdsModel defaultCompareThresholds = CompareThresholdsModel.createDefaultCompareThreshold();

    /**
     * The row count comparison configuration.
     */
    @JsonPropertyDescription("The row count comparison configuration.")
    private CompareThresholdsModel compareRowCount;

    /**
     * The column count comparison configuration.
     */
    @JsonPropertyDescription("The column count comparison configuration.")
    private CompareThresholdsModel compareColumnCount;

    /**
     * Boolean flag that decides if this comparison type supports comparing the column count between tables. Partitioned table comparisons do not support comparing the column counts.
     */
    @JsonPropertyDescription("Boolean flag that decides if this comparison type supports comparing the column count between tables. Partitioned table comparisons do not support comparing the column counts.")
    private boolean supportsCompareColumnCount;

    /**
     * The list of compared columns, their matching reference column and the enabled comparisons.
     */
    @JsonPropertyDescription("The list of compared columns, their matching reference column and the enabled comparisons.")
    private List<ColumnComparisonModel> columns = new ArrayList<>();

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to run the table comparison checks for this table, using checks selected in this model
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run the table comparison checks for this table, using checks selected in this model.")
    private CheckSearchFilters compareTableRunChecksJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored check results for this table comparison.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored check results for this table comparison.")
    private DeleteStoredDataQueueJobParameters compareTableCleanDataJobTemplate;

    /**
     * Boolean flag that decides if the current user can update or delete the table comparison.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete the table comparison.")
    private boolean canEdit;

    /**
     * Boolean flag that decides if the current user can run comparison checks.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can run comparison checks.")
    private boolean canRunCompareChecks;

    /**
     * Boolean flag that decides if the current user can delete data (results).
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can delete data (results).")
    private boolean canDeleteData;

    /**
     * Creates a table comparison model, copying the configuration of all comparison checks on the table level and on the column level.
     * @param comparedTableSpec Source table specification (the compared table).
     * @param referenceTableSpec Reference table specification.
     * @param tableComparisonConfigurationName The table comparison name (the reference table configuration name).
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param checkTimeScale Check time scale for monitoring and partitioned checks.
     * @param canCompareTables True if the user can edit, compare tables, delete data.
     * @return Table comparison mode.
     */
    public static TableComparisonModel fromTableSpec(TableSpec comparedTableSpec,
                                                     TableSpec referenceTableSpec,
                                                     String tableComparisonConfigurationName,
                                                     CheckType checkType,
                                                     CheckTimeScale checkTimeScale,
                                                     boolean canCompareTables) {
        TableComparisonModel tableComparisonModel = new TableComparisonModel();
        HierarchyId comparedTableHierarchyId = comparedTableSpec.getHierarchyId();
        if (comparedTableHierarchyId == null) {
            throw new DqoRuntimeException("Cannot map a detached table, because the connection and table name is unknown");
        }

        TableComparisonConfigurationSpec comparisonSpec = comparedTableSpec.getTableComparisons().get(tableComparisonConfigurationName);
        if (comparisonSpec == null) {
            throw new DqoRuntimeException("Table comparison '" + tableComparisonConfigurationName + "' not configured on the table " + comparedTableHierarchyId.toString());
        }

        tableComparisonModel.setTableComparisonConfigurationName(comparisonSpec.getComparisonName());
        tableComparisonModel.comparedConnection = comparedTableHierarchyId.getConnectionName();
        tableComparisonModel.comparedTable = comparedTableHierarchyId.getPhysicalTableName();
        tableComparisonModel.referenceConnection = comparisonSpec.getReferenceTableConnectionName();
        tableComparisonModel.referenceTable = new PhysicalTableName(comparisonSpec.getReferenceTableSchemaName(), comparisonSpec.getReferenceTableName());
        tableComparisonModel.setCanEdit(canCompareTables);
        tableComparisonModel.setCanRunCompareChecks(canCompareTables);
        tableComparisonModel.setCanDeleteData(canCompareTables);

        for (TableComparisonGroupingColumnsPairSpec groupingColumnsPairSpec : comparisonSpec.getGroupingColumns()) {
            TableComparisonGroupingColumnPairModel tableComparisonGroupingColumnPairModel =
                    TableComparisonGroupingColumnPairModel.fromColumnPairSpec(groupingColumnsPairSpec);
            tableComparisonModel.getGroupingColumns().add(tableComparisonGroupingColumnPairModel);
        }

        AbstractRootChecksContainerSpec tableCheckRootContainer = comparedTableSpec.getTableCheckRootContainer(
                checkType, checkTimeScale, false);
        AbstractComparisonCheckCategorySpecMap<?> comparisons = tableCheckRootContainer.getComparisons();
        tableComparisonModel.supportsCompareColumnCount = tableCheckRootContainer.getCheckType() != CheckType.partitioned;

        if (comparisons instanceof AbstractTableComparisonCheckCategorySpecMap) {
            AbstractTableComparisonCheckCategorySpecMap<? extends AbstractTableComparisonCheckCategorySpec> tableCheckComparisonsMap =
                    (AbstractTableComparisonCheckCategorySpecMap<? extends AbstractTableComparisonCheckCategorySpec>)comparisons;
            AbstractTableComparisonCheckCategorySpec tableCheckComparisonChecks = tableCheckComparisonsMap.get(tableComparisonConfigurationName);

            if (tableCheckComparisonChecks != null) {
                ComparisonCheckRules rowCountMatch = tableCheckComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, false);
                tableComparisonModel.setCompareRowCount(CompareThresholdsModel.fromComparisonCheckSpec(rowCountMatch));

                if (tableCheckComparisonChecks.supportsColumnComparisonCheck()) {
                    ComparisonCheckRules columnCountMatch = tableCheckComparisonChecks.getCheckSpec(TableCompareCheckType.column_count_match, false);
                    tableComparisonModel.setCompareColumnCount(CompareThresholdsModel.fromComparisonCheckSpec(columnCountMatch));
                }
            }
        }

        for (ColumnSpec columnSpec : comparedTableSpec.getColumns().values()) {
            ColumnComparisonModel columnComparisonModel = ColumnComparisonModel.fromColumnSpec(
                    columnSpec, tableComparisonConfigurationName, checkType, checkTimeScale);
            if (columnComparisonModel.getReferenceColumnName() == null) {
                // reference column is not configured, let's try to find a column that will match
                if (referenceTableSpec != null) {
                    ColumnSpec mostSimilarReferenceColumn = referenceTableSpec.getColumns().findMostSimilarColumn(columnSpec.getColumnName());
                    if (mostSimilarReferenceColumn != null) {
                        columnComparisonModel.setReferenceColumnName(mostSimilarReferenceColumn.getColumnName());
                    }
                }
            }
            tableComparisonModel.getColumns().add(columnComparisonModel);
        }

        tableComparisonModel.compareTableRunChecksJobTemplate = new CheckSearchFilters() {{
           setConnection(comparedTableHierarchyId.getConnectionName());
           setFullTableName(comparedTableSpec.getPhysicalTableName().toTableSearchFilter());
           setCheckType(checkType);
           setTimeScale(checkTimeScale);
           setCheckCategory(AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME);
           setTableComparisonName(tableComparisonConfigurationName);
           setEnabled(true);
        }};

        tableComparisonModel.compareTableCleanDataJobTemplate =
                DeleteStoredDataQueueJobParameters.fromCheckSearchFilters(tableComparisonModel.compareTableRunChecksJobTemplate);

        return tableComparisonModel;
    }

    /**
     * Copies the configuration of table comparison on the table level and columns level to correct checks, creating these checks when comparison checks are not enabled.
     * @param targetTableSpec Target table specification to update.
     * @param referenceTableConfigurationName Table comparison name (the reference table configuration name).
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param checkTimeScale Check time scale.
     */
    public void copyToTableSpec(TableSpec targetTableSpec,
                                String referenceTableConfigurationName,
                                CheckType checkType,
                                CheckTimeScale checkTimeScale) {
        TableComparisonConfigurationSpec comparisonSpec = targetTableSpec.getTableComparisons().get(referenceTableConfigurationName);
        if (comparisonSpec == null) {
            throw new DqoRuntimeException("Table comparison '" + referenceTableConfigurationName + "' was not found in table " + targetTableSpec.getHierarchyId());
        }

        comparisonSpec.setCheckType(checkType);
        comparisonSpec.setTimeScale(checkTimeScale);
        comparisonSpec.setReferenceTableConnectionName(this.getReferenceConnection());
        if (this.getReferenceTable() != null) {
            comparisonSpec.setReferenceTableSchemaName(this.getReferenceTable().getSchemaName());
            comparisonSpec.setReferenceTableName(this.getReferenceTable().getTableName());
        } else {
            comparisonSpec.setReferenceTableSchemaName(null);
            comparisonSpec.setReferenceTableName(null);
        }

        TableComparisonGroupingColumnsPairsListSpec groupingColumnsSpecList = comparisonSpec.getGroupingColumns();
        groupingColumnsSpecList.clear();
        for (TableComparisonGroupingColumnPairModel groupingColumnPairModel : this.groupingColumns) {
            if (groupingColumnsSpecList.size() >= 9) {
                throw new DqoRuntimeException("Too many data grouping columns. DQOps supports up to 9 columns.");
            }
            TableComparisonGroupingColumnsPairSpec groupingColumnsPairSpec = groupingColumnPairModel.createColumnsPairSpec();
            groupingColumnsSpecList.add(groupingColumnsPairSpec);
        }

        AbstractRootChecksContainerSpec tableCheckRootContainer = targetTableSpec.getTableCheckRootContainer(
                checkType, checkTimeScale, true);

        AbstractComparisonCheckCategorySpecMap<?> tableCheckComparisonsMap = tableCheckRootContainer.getComparisons();
        AbstractTableComparisonCheckCategorySpec tableCheckComparisonChecks =
                (AbstractTableComparisonCheckCategorySpec)tableCheckComparisonsMap.getOrAdd(referenceTableConfigurationName);
        if (this.compareRowCount != null) {
            ComparisonCheckRules rowCountMatch = tableCheckComparisonChecks.getCheckSpec(TableCompareCheckType.row_count_match, true);
            this.compareRowCount.copyToComparisonCheckSpec(rowCountMatch);
        } else {
            tableCheckComparisonChecks.removeCheckSpec(TableCompareCheckType.row_count_match);
        }

        if (tableCheckComparisonChecks.supportsColumnComparisonCheck()) {
            if (this.compareColumnCount != null) {
                ComparisonCheckRules columnCountMatch = tableCheckComparisonChecks.getCheckSpec(TableCompareCheckType.column_count_match, true);
                this.compareColumnCount.copyToComparisonCheckSpec(columnCountMatch);
            } else {
                tableCheckComparisonChecks.removeCheckSpec(TableCompareCheckType.column_count_match);
            }
        }

        for (ColumnComparisonModel columnComparisonModel : this.columns) {
            String comparedColumnName = columnComparisonModel.getComparedColumnName();
            if (comparedColumnName == null) {
                throw new DqoRuntimeException("Missing compared column name, it is required");
            }

            ColumnSpec columnSpec = targetTableSpec.getColumns().get(comparedColumnName);
            if (columnSpec == null) {
                throw new DqoRuntimeException("Compared column " + comparedColumnName + " not found on the table " + targetTableSpec.getHierarchyId());
            }

            columnComparisonModel.copyToColumnSpec(columnSpec, referenceTableConfigurationName, checkType, checkTimeScale);
        }
    }

    public static class TableComparisonModelSampleFactory implements SampleValueFactory<TableComparisonModel> {
        @Override
        public TableComparisonModel createSample() {
            TableSpec.TableSpecSampleFactory tableSpecSampleFactory = new TableSpec.TableSpecSampleFactory();
            TableSpec tableSpec1 = tableSpecSampleFactory.createSample();
            TableSpec tableSpec2 = tableSpecSampleFactory.createSample();
            String tableComparisonName = SampleStringsRegistry.getTableComparison();
            TableComparisonConfigurationSpec tableComparisonConfigurationSpec = new TableComparisonConfigurationSpec.TableComparisonConfigurationSpecSampleFactory().createSample();
            tableSpec1.getTableComparisons().put(tableComparisonName, tableComparisonConfigurationSpec);
            return fromTableSpec(
                    tableSpec1,
                    tableSpec2,
                    tableComparisonName,
                    new CheckType.CheckTypeSampleFactory().createSample(),
                    new CheckTimeScale.CheckTimeScaleSampleFactory().createSample(),
                    true);
        }
    }
}
