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
import ai.dqo.metadata.sources.TableOwnerSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableTargetSpec;
import ai.dqo.metadata.sources.TimestampColumnsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Table basic model returned by the rest api that is limited only to the basic fields, excluding nested nodes.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableBasicModel", description = "Basic table model with a subset of parameters, excluding all nested objects.")
public class TableBasicModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Table hash that identifies the table using a unique hash code.")
    private Long tableHash;

    @JsonPropertyDescription("Physical table details (a physical schema name and a physical table name)")
    private TableTargetSpec target;

    @JsonPropertyDescription("Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).")
    private TimestampColumnsSpec timestampColumns;

    @JsonPropertyDescription("Disables all data quality checks on the table. Data quality checks will not be executed.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean disabled;

    @JsonPropertyDescription("Stage name.")
    private String stage;

    @JsonPropertyDescription("SQL WHERE clause added to the sensor queries.")
    private String filter;

    @JsonPropertyDescription("Table owner information like the data steward name or the business application name.")
    private TableOwnerSpec owner;

    @JsonPropertyDescription("True when the table has any checks configured.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean hasAnyConfiguredChecks;

    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to run all checks within this table.")
    private CheckSearchFilters runChecksJobTemplate;

    @JsonPropertyDescription("Configured parameters for the \"profiler run\" job that should be pushed to the job queue in order to run all profilers within this table.")
    private ProfilerSearchFilters runProfilerJobTemplate;

    /**
     * Creates a basic table model from a table specification by cherry-picking relevant fields.
     * This model is used for the table list screen and it has even less fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @return Basic table model.
     */
    public static TableBasicModel fromTableSpecificationForListEntry(String connectionName, TableSpec tableSpec) {
        return new TableBasicModel() {{
            setConnectionName(connectionName);
            setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
            setTarget(tableSpec.getTarget());
            setDisabled(tableSpec.isDisabled());
            setHasAnyConfiguredChecks(tableSpec.hasAnyChecksConfigured());
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getTarget().toTableSearchFilter());
                setEnabled(true);
            }});
            setRunProfilerJobTemplate(new ProfilerSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getTarget().toTableSearchFilter());
                setEnabled(true);
            }});
        }};
    }

    /**
     * Creates a basic table model from a table specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @return Basic table model.
     */
    public static TableBasicModel fromTableSpecification(String connectionName, TableSpec tableSpec) {
        return new TableBasicModel() {{
            setConnectionName(connectionName);
            setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
            setTarget(tableSpec.getTarget());
            setTimestampColumns(tableSpec.getTimestampColumns());
            setDisabled(tableSpec.isDisabled());
            setStage(tableSpec.getStage());
            setFilter(tableSpec.getFilter());
            setOwner(tableSpec.getOwner());
            setHasAnyConfiguredChecks(tableSpec.hasAnyChecksConfigured());
            setRunChecksJobTemplate(new CheckSearchFilters()
            {{
                setConnectionName(connectionName);
                setSchemaTableName(tableSpec.getTarget().toTableSearchFilter());
            }});
        }};
    }

    /**
     * Updates a table specification by copying all fields. The {@see TableSpec#target field is not updated}.
     * @param targetTableSpec Target table specification to update.
     */
    public void copyToTableSpecification(TableSpec targetTableSpec) {
        targetTableSpec.setDisabled(this.isDisabled());
        targetTableSpec.setStage(this.getStage());
        targetTableSpec.setFilter(this.getFilter());
        targetTableSpec.setOwner(this.getOwner());
        if (this.getTimestampColumns() != null) {
            targetTableSpec.setTimestampColumns(this.getTimestampColumns());
        }
        else {
            targetTableSpec.setTimestampColumns(new TimestampColumnsSpec()); // default configuration because the object is not null
        }
    }
}
