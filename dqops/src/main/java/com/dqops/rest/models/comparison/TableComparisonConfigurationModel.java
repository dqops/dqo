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

import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Model that contains the basic information about a table comparison configuration that specifies how the current table could be compared to another table that is a source of truth for comparison.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonConfigurationModel", description = "Model that contains the basic information about a table comparison configuration that specifies how the current table could be compared to another table that is a source of truth for comparison.")
@Data
public class TableComparisonConfigurationModel {
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

    @JsonPropertyDescription("The name of the data grouping configuration on the parent table that will be used for comparison. " +
            "When the parent table has no data grouping configurations, compares the whole table without grouping.")
    private String comparedTableGroupingName;

    @JsonPropertyDescription("The name of the data grouping configuration on the referenced name that will be used for comparison. " +
            "When the reference table has no data grouping configurations, compares the whole table without grouping. " +
            "The data grouping configurations on the compared table and the reference table must have the same grouping dimension levels configured, but the configuration (the names of the columns) could be different.")
    private String referenceTableGroupingName;

    /**
     * Creates a basic model with the comparison from teh comparison specification.
     * @param comparisonSpec Comparison specification.
     * @return Reference table with the basic information.
     */
    public static TableComparisonConfigurationModel fromReferenceTableComparisonSpec(TableComparisonConfigurationSpec comparisonSpec) {
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
        model.setComparedTableGroupingName(comparisonSpec.getComparedTableGroupingName());
        model.setReferenceTableGroupingName(comparisonSpec.getReferenceTableGroupingName());

        return model;
    }

    /**
     * Copies selected values (the reference table name and data grouping names) to the comparison specification.
     * @param comparisonSpec Target comparison specification to copy values to.
     */
    public void copyToReferenceTableComparisonSpec(TableComparisonConfigurationSpec comparisonSpec) {
        comparisonSpec.setComparedTableGroupingName(this.getComparedTableGroupingName());
        comparisonSpec.setReferenceTableGroupingName(this.getReferenceTableGroupingName());
        comparisonSpec.setReferenceTableConnectionName(this.getReferenceConnection());
        if (this.getReferenceTable() != null) {
            comparisonSpec.setReferenceTableSchemaName(this.getReferenceTable().getSchemaName());
            comparisonSpec.setReferenceTableName(this.getReferenceTable().getTableName());
        } else {
            comparisonSpec.setReferenceTableSchemaName(null);
            comparisonSpec.setReferenceTableName(null);
        }
    }
}
