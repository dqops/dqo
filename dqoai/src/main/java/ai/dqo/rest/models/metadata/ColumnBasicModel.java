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
package ai.dqo.rest.models.metadata;

import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.ProfilerSearchFilters;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ColumnTypeSnapshotSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Basic column model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnBasicModel", description = "Basic column model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.")
public class ColumnBasicModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table name including the schema and table names.")
    private PhysicalTableName table;

    @JsonPropertyDescription("Column name.")
    private String columnName;

    @JsonPropertyDescription("Column hash that identifies the column using a unique hash code.")
    private Long columnHash;

    @JsonPropertyDescription("Disables all data quality checks on the column. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("True when the column has any checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredChecks;

    @JsonPropertyDescription("Column data type that was retrieved when the table metadata was imported.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ColumnTypeSnapshotSpec typeSnapshot;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this column.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"profiler run\" job that should be pushed to the job queue in order to run all profilers within this column.")
    private ProfilerSearchFilters runProfilerJobTemplate;

    /**
     * Creates a basic column model from a column specification by cherry-picking relevant fields.
     * This model is used for the column list screen and it has even less fields.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @return Basic column model.
     */
    public static ColumnBasicModel fromColumnSpecificationForListEntry(String connectionName,
                                                                      PhysicalTableName physicalTableName,
                                                                      String columnName,
                                                                      ColumnSpec columnSpec) {
        return new ColumnBasicModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setDisabled(columnSpec.isDisabled());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setEnabled(true);
            }});
            setRunProfilerJobTemplate(new ProfilerSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(physicalTableName.toTableSearchFilter());
                setColumnName(columnName);
                setEnabled(true);
            }});
        }};
    }

    /**
     * Creates a basic column model from a column specification by cherry-picking relevant fields.
     * @param connectionName    Connection name to store in the model.
     * @param physicalTableName Physical table name.
     * @param columnName        Column name.
     * @param columnSpec        Source column specification.
     * @return Basic column model.
     */
    public static ColumnBasicModel fromColumnSpecification(String connectionName,
                                                           PhysicalTableName physicalTableName,
                                                           String columnName,
                                                           ColumnSpec columnSpec) {
        return new ColumnBasicModel() {{
            setConnectionName(connectionName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setTable(physicalTableName);
            setColumnName(columnName);
            setDisabled(columnSpec.isDisabled());
            setTypeSnapshot(columnSpec.getTypeSnapshot());
            setHasAnyConfiguredChecks(columnSpec.hasAnyChecksConfigured());
        }};
    }

    /**
     * Updates a column specification by copying all fields.
     * @param targetColumnSpec Target column specification to update.
     */
    public void copyToColumnSpecification(ColumnSpec targetColumnSpec) {
        targetColumnSpec.setDisabled(this.isDisabled());
        targetColumnSpec.setTypeSnapshot(this.getTypeSnapshot());
    }
}
