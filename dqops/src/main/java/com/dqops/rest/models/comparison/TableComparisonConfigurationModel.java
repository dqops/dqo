/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.comparison;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.parquet.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Model that contains the basic information about a table comparison configuration that specifies how the current table can be compared with another table that is a source of truth for comparison.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonConfigurationModel", description = "Model that contains the basic information about a table comparison configuration that specifies how the current table can be compared with another table that is a source of truth for comparison.")
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

    /**
     * The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is 'profiling'.
     */
    @JsonPropertyDescription("The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is 'profiling'.")
    private CheckType checkType = CheckType.profiling;

    /**
     * The time scale that this check comparison configuration is applicable. Supported values are 'daily' and 'monthly' for monitoring and partitioned checks or an empty value for profiling checks.
     */
    @JsonPropertyDescription("The time scale that this check comparison configuration is applicable. Supported values are 'daily' and 'monthly' for monitoring and partitioned checks or an empty value for profiling checks.")
    private CheckTimeScale timeScale;

    /**
     * Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table.
     * This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.
     */
    @JsonPropertyDescription("Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. " +
            "This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.")
    private String comparedTableFilter;

    /**
     * Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth).
     * This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.
     */
    @JsonPropertyDescription("Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). " +
            "This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.")
    private String referenceTableFilter;

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
        model.setComparedTableFilter(comparisonSpec.getComparedTableFilter());
        model.setReferenceTableFilter(comparisonSpec.getReferenceTableFilter());
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
        comparisonSpec.setComparedTableFilter(this.getComparedTableFilter());
        comparisonSpec.setReferenceTableFilter(this.getReferenceTableFilter());
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
            if (groupingColumnPairModel == null || (
                    Strings.isNullOrEmpty(groupingColumnPairModel.getComparedTableColumnName()) &&
                            Strings.isNullOrEmpty(groupingColumnPairModel.getReferenceTableColumnName()))) {
                continue; // empty mapping
            }

            if (groupingColumnsSpecList.size() >= 9) {
                throw new DqoRuntimeException("Too many data grouping columns. DQOps supports up to 9 columns.");
            }
            TableComparisonGroupingColumnsPairSpec groupingColumnsPairSpec = groupingColumnPairModel.createColumnsPairSpec();
            groupingColumnsSpecList.add(groupingColumnsPairSpec);
        }
    }

    public static class TableComparisonConfigurationModelSampleFactory implements SampleValueFactory<TableComparisonConfigurationModel> {
        @Override
        public TableComparisonConfigurationModel createSample() {
            return fromTableComparisonSpec(
                    new TableComparisonConfigurationSpec.TableComparisonConfigurationSpecSampleFactory().createSample(),
                    true);
        }
    }
}
