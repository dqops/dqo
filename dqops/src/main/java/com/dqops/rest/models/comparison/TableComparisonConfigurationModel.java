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

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
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
 * Model that contains the basic information about a table comparison configuration that specifies how the current table could be compared to another table that is a source of truth for comparison.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonConfigurationModel", description = "Model that contains the basic information about a table comparison configuration that specifies how the current table could be compared to another table that is a source of truth for comparison.")
@Data
public class TableComparisonConfigurationModel {
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

    @JsonPropertyDescription("The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is 'profiling'.")
    private CheckType checkType = CheckType.profiling;

    @JsonPropertyDescription("The time scale that this check comparison configuration is applicable. Supported values are 'daily' and 'monthly' for monitoring and partitioned checks or an empty value for profiling checks.")
    private CheckTimeScale timeScale;

    /**
     * List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause.
     */
    @JsonPropertyDescription("List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  " +
            "for grouping both the compared table and the reference table (the source of truth). " +
            "The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.")
    private List<TableComparisonGroupingColumnPairModel> groupingColumns = new ArrayList<>();

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
     * Creates a basic model with the comparison from the comparison specification.
     * @param comparisonSpec Comparison specification.
     * @param canCompareTables User can compare tables, edit the comparison definition, run comparison checks.
     * @return Table comparison model with the basic information.
     */
    public static TableComparisonConfigurationModel fromTableComparisonSpec(
            TableComparisonConfigurationSpec comparisonSpec,
            boolean canCompareTables) {
        HierarchyId comparedTableHierarchyId = comparisonSpec.getHierarchyId();
        if (comparedTableHierarchyId == null) {
            throw new DqoRuntimeException("Cannot map a detached comparison, because the connection and table name is unknown");
        }

        TableComparisonConfigurationModel model = new TableComparisonConfigurationModel();
        model.setTableComparisonConfigurationName(comparisonSpec.getComparisonName());
        model.setComparedConnection(comparedTableHierarchyId.getConnectionName());
        model.setComparedTable(comparedTableHierarchyId.getPhysicalTableName());
        model.setReferenceConnection(comparisonSpec.getReferenceTableConnectionName());
        model.setReferenceTable(new PhysicalTableName(comparisonSpec.getReferenceTableSchemaName(), comparisonSpec.getReferenceTableName()));
        model.setCheckType(comparisonSpec.getCheckType());
        model.setTimeScale(comparisonSpec.getTimeScale());
        model.setCanEdit(canCompareTables);
        model.setCanRunCompareChecks(canCompareTables);
        model.setCanDeleteData(canCompareTables);

        for (TableComparisonGroupingColumnsPairSpec groupingColumnsPairSpec : comparisonSpec.getGroupingColumns()) {
            TableComparisonGroupingColumnPairModel tableComparisonGroupingColumnPairModel =
                    TableComparisonGroupingColumnPairModel.fromColumnPairSpec(groupingColumnsPairSpec);
            model.getGroupingColumns().add(tableComparisonGroupingColumnPairModel);
        }

        return model;
    }

    /**
     * Copies selected values (the reference table name and data grouping names) to the comparison specification.
     * @param comparisonSpec Target comparison specification to copy values to.
     */
    public void copyToTableComparisonSpec(TableComparisonConfigurationSpec comparisonSpec) {
        comparisonSpec.setCheckType(this.getCheckType());
        comparisonSpec.setTimeScale(this.getTimeScale());
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
                throw new DqoRuntimeException("Too many data grouping columns. DQO supports up to 9 columns.");
            }
            TableComparisonGroupingColumnsPairSpec groupingColumnsPairSpec = groupingColumnPairModel.createColumnsPairSpec();
            groupingColumnsSpecList.add(groupingColumnsPairSpec);
        }
    }
}
