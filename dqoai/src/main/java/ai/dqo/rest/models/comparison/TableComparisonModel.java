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
import ai.dqo.checks.comparison.AbstractTableComparisonCheckCategorySpec;
import ai.dqo.checks.comparison.AbstractTableComparisonCheckCategorySpecMap;
import ai.dqo.checks.comparison.CheckCategoriesTableComparisonMapParent;
import ai.dqo.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import ai.dqo.metadata.comparisons.ReferenceTableSpec;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

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
     * The name of the table comparison that is defined in the 'comparisons' node on the table specification.
     */
    @JsonPropertyDescription("The name of the table comparison that is defined in the 'comparisons' node on the table specification.")
    private String comparisonName;

    /**
     * Compared connection name - the connection name to the data source that is compared (verified).
     */
    @JsonPropertyDescription("Compared connection name - the connection name to the data source that is compared (verified).")
    @Setter(AccessLevel.NONE)
    private String comparedConnection;

    /**
     * The schema and table name of the compared table that is verified.
     */
    @JsonPropertyDescription("The schema and table name of the compared table that is verified.")
    @Setter(AccessLevel.NONE)
    private PhysicalTableName comparedTable;

    /**
     * Reference connection name - the connection name to the data source that has the reference data to compare to.
     */
    @JsonPropertyDescription("Reference connection name - the connection name to the data source that has the reference data to compare to.")
    @Setter(AccessLevel.NONE)
    private String referenceConnection;

    /**
     * The schema and table name of the reference table that has the expected data.
     */
    @JsonPropertyDescription("The schema and table name of the reference table that has the expected data.")
    @Setter(AccessLevel.NONE)
    private PhysicalTableName referenceTable;

    /**
     * The name of the data grouping configuration on the parent table that will be used for comparison.
     */
    @JsonPropertyDescription("The name of the data grouping configuration on the parent table that will be used for comparison. " +
            "When the parent table has no data grouping configurations, compares the whole table without grouping.")
    @Setter(AccessLevel.NONE)
    private String comparedTableGroupingName;

    /**
     * The name of the data grouping configuration on the referenced name that will be used for comparison.
     */
    @JsonPropertyDescription("The name of the data grouping configuration on the referenced name that will be used for comparison. " +
            "When the reference table has no data grouping configurations, compares the whole table without grouping. " +
            "The data grouping configurations on the compared table and the reference table must have the same grouping dimension levels configured, but the configuration (the names of the columns) could be different.")
    @Setter(AccessLevel.NONE)
    private String referenceTableGroupingName;

    /**
     * The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.
     */
    @JsonPropertyDescription("The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.")
    private CompareThresholdsModel defaultCompareThresholds = new CompareThresholdsModel();

    /**
     * The row count comparison configuration.
     */
    @JsonPropertyDescription("The row count comparison configuration.")
    private CompareThresholdsModel compareRowCount;

    /**
     * The list of compared columns, their matching reference column and the enabled comparisons.
     */
    @JsonPropertyDescription("The list of compared columns, their matching reference column and the enabled comparisons.")
    private List<ColumnComparisonModel> columns = new ArrayList<>();

    /**
     * Creates a table comparison model, copying the configuration of all comparison checks on the table level and on the column level.
     * @param tableSpec Source table specification.
     * @param tableComparisonName The table comparison name.
     * @param checkType Check type (profiling, recurring, partitioned).
     * @param checkTimeScale Check time scale for recurring and partitioned checks.
     * @return Table comparison mode.
     */
    public static TableComparisonModel fromTableSpec(TableSpec tableSpec,
                                                     String tableComparisonName,
                                                     CheckType checkType,
                                                     CheckTimeScale checkTimeScale) {
        TableComparisonModel tableComparisonModel = new TableComparisonModel();
        HierarchyId comparedTableHierarchyId = tableSpec.getHierarchyId();
        if (comparedTableHierarchyId == null) {
            throw new DqoRuntimeException("Cannot map a detached table, because the connection and table name is unknown");
        }

        ReferenceTableSpec comparisonSpec = tableSpec.getReferenceTables().get(tableComparisonName);
        if (comparisonSpec == null) {
            throw new DqoRuntimeException("Table comparison '" + tableComparisonName + "' not found in the table " + comparedTableHierarchyId.toString());
        }

        tableComparisonModel.setComparisonName(comparisonSpec.getComparisonName());
        tableComparisonModel.comparedConnection = comparedTableHierarchyId.getConnectionName();
        tableComparisonModel.comparedTable = comparedTableHierarchyId.getPhysicalTableName();
        tableComparisonModel.referenceConnection = comparisonSpec.getReferenceTableConnectionName();
        tableComparisonModel.referenceTable = new PhysicalTableName(comparisonSpec.getReferenceTableSchemaName(), comparisonSpec.getReferenceTableName());
        tableComparisonModel.comparedTableGroupingName = comparisonSpec.getComparedTableGroupingName();
        tableComparisonModel.referenceTableGroupingName = comparisonSpec.getReferenceTableGroupingName();

        AbstractRootChecksContainerSpec tableCheckRootContainer = tableSpec.getTableCheckRootContainer(
                checkType, checkTimeScale, false);

        if (tableCheckRootContainer instanceof CheckCategoriesTableComparisonMapParent) {
            CheckCategoriesTableComparisonMapParent tableComparisonMapParent = (CheckCategoriesTableComparisonMapParent)tableCheckRootContainer;
            AbstractTableComparisonCheckCategorySpecMap<? extends AbstractTableComparisonCheckCategorySpec> tableCheckComparisonsMap = tableComparisonMapParent.getComparisons();
            AbstractTableComparisonCheckCategorySpec tableCheckComparisonChecks = tableCheckComparisonsMap.get(tableComparisonName);

            if (tableCheckComparisonChecks != null) {
                TableComparisonRowCountMatchCheckSpec rowCountMatch = tableCheckComparisonChecks.getRowCountMatch();
                tableComparisonModel.setCompareRowCount(CompareThresholdsModel.fromComparisonCheckSpec(rowCountMatch));
            }
        }

        for (ColumnSpec columnSpec : tableSpec.getColumns().values()) {
            ColumnComparisonModel columnComparisonModel = ColumnComparisonModel.fromColumnSpec(
                    columnSpec, tableComparisonName, checkType, checkTimeScale);
            tableComparisonModel.getColumns().add(columnComparisonModel);
        }

        return tableComparisonModel;
    }

    /**
     * Copies the configuration of table comparison on the table level and columns level to correct checks, creating these checks when comparison checks are not enabled.
     * @param targetTableSpec Target table specification to update.
     * @param tableComparisonName Table comparison name.
     * @param checkType Check type (profiling, recurring, partitioned).
     * @param checkTimeScale Check time scale.
     */
    public void copyToTableSpec(TableSpec targetTableSpec,
                                String tableComparisonName,
                                CheckType checkType,
                                CheckTimeScale checkTimeScale) {
        ReferenceTableSpec comparisonSpec = targetTableSpec.getReferenceTables().get(tableComparisonName);
        if (comparisonSpec == null) {
            throw new DqoRuntimeException("Table comparison '" + tableComparisonName + "' was not found in table " + targetTableSpec.getHierarchyId());
        }

        AbstractRootChecksContainerSpec tableCheckRootContainer = targetTableSpec.getTableCheckRootContainer(
                checkType, checkTimeScale, true);
        if (!(tableCheckRootContainer instanceof CheckCategoriesTableComparisonMapParent)) {
            throw new DqoRuntimeException("The target check type does not support comparison checks.");
        }

        CheckCategoriesTableComparisonMapParent tableComparisonMapParent = (CheckCategoriesTableComparisonMapParent)tableCheckRootContainer;
        AbstractTableComparisonCheckCategorySpecMap<? extends AbstractTableComparisonCheckCategorySpec> tableCheckComparisonsMap = tableComparisonMapParent.getComparisons();
        AbstractTableComparisonCheckCategorySpec tableCheckComparisonChecks = tableCheckComparisonsMap.getOrAdd(tableComparisonName);
        if (this.compareRowCount != null) {
            tableCheckComparisonChecks.setRowCountMatch(new TableComparisonRowCountMatchCheckSpec());
            this.compareRowCount.copyToComparisonCheckSpec(tableCheckComparisonChecks.getRowCountMatch());
        } else {
            tableCheckComparisonChecks.setRowCountMatch(null);
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

            columnComparisonModel.copyToColumnSpec(columnSpec, tableComparisonName, checkType, checkTimeScale);
        }
    }
}
