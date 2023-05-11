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

import ai.dqo.metadata.sources.PartitionIncrementalTimeWindowSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TimestampColumnsSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Rest model that returns the configuration of table partitioning information.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TablePartitioningModel", description = "Table model with objects that describe the table partitioning.")
public class TablePartitioningModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table details (a physical schema name and a physical table name)")
    private PhysicalTableName target;

    @JsonPropertyDescription("Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).")
    private TimestampColumnsSpec timestampColumns;

    @JsonPropertyDescription("Configuration of time windows for executing partition checks incrementally, configures the number of recent days to analyze for daily partitioned tables or the number of recent months for monthly partitioned data.")
    private PartitionIncrementalTimeWindowSpec incrementalTimeWindow;

    /**
     * Creates a table partitioning model from a table specification by cherry-picking relevant fields.
     * @param connectionName Connection name to store in the model.
     * @param tableSpec      Source table specification.
     * @return Table partitioning model.
     */
    public static TablePartitioningModel fromTableSpecification(String connectionName, TableSpec tableSpec) {
        return new TablePartitioningModel() {{
            setConnectionName(connectionName);
            setTarget(tableSpec.getPhysicalTableName());
            setTimestampColumns(tableSpec.getTimestampColumns());
            setIncrementalTimeWindow(tableSpec.getIncrementalTimeWindow());
        }};
    }

    /**
     * Updates a table specification by copying partitioning fields.
     * @param targetTableSpec Target table specification to update.
     */
    public void copyToTableSpecification(TableSpec targetTableSpec) {
        if (this.getTimestampColumns() != null) {
            targetTableSpec.setTimestampColumns(this.getTimestampColumns());
        }
        else {
            targetTableSpec.setTimestampColumns(new TimestampColumnsSpec()); // default configuration because the object is not null
        }

        if (this.getIncrementalTimeWindow() != null) {
            targetTableSpec.setIncrementalTimeWindow(this.getIncrementalTimeWindow());
        }
        else {
            targetTableSpec.setIncrementalTimeWindow(new PartitionIncrementalTimeWindowSpec()); // default configuration because the object is not null
        }
    }
}
