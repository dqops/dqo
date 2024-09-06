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

package com.dqops.rest.models.metadata;

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.lineage.TableLineageSourceSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

/**
 * Data lineage model that describes one source table of the current table.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableLineageSourceListModel", description = "Data lineage model that describes one source table of the current table.")
public class TableLineageSourceListModel {
    /**
     * The connection name where the target table is defined
     */
    @JsonPropertyDescription("The connection name where the target table is defined.")
    private String targetConnection;

    /**
     * The schema name in the target connection where the target table is defined
     */
    @JsonPropertyDescription("The schema name in the target connection where the target table is defined.")
    private String targetSchema;

    /**
     * The name of the target table inside the target schema
     */
    @JsonPropertyDescription("The name of the target table inside the target schema.")
    private String targetTable;

    /**
     * The name of a source connection that is defined in DQOps and contains a source table from which the current table receives data.
     */
    @JsonPropertyDescription("The name of a source connection that is defined in DQOps and contains a source table from which the current table receives data.")
    private String sourceConnection;

    /**
     * The name of a source schema within the source connection that contains a source table from which the current table receives data.
     */
    @JsonPropertyDescription("The name of a source schema within the source connection that contains a source table from which the current table receives data.")
    private String sourceSchema;

    /**
     * The name of a source schema within the source connection that contains a source table from which the current table receives data.
     */
    @JsonPropertyDescription("The name of a source schema within the source connection that contains a source table from which the current table receives data.")
    private String sourceTable;

    /**
     * The name of a source tool from which this data lineage information was copied. This field should be filled when the data lineage was imported from another data catalog or a data lineage tracking platform.
     */
    @JsonPropertyDescription("The name of a source tool from which this data lineage information was copied. This field should be filled when the data lineage was imported from another data catalog or a data lineage tracking platform.")
    private String dataLineageSourceTool;

    /**
     * A dictionary of mapping properties stored as a key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external data lineage sources can use it to store mapping information.
     */
    @JsonPropertyDescription("A dictionary of mapping properties stored as a key/value dictionary. Data lineage synchronization tools that are importing data lineage mappings from external data lineage sources can use it to store mapping information.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * Creates a table lineage list model from a source specification.
     * @param sourceSpec Source data lineage specification.
     * @param canEdit True if the current user can edit the object.
     * @return Source table lineage model.
     */
    public static TableLineageSourceListModel fromSpecification(TableLineageSourceSpec sourceSpec, boolean canEdit) {
        HierarchyId hierarchyId = sourceSpec.getHierarchyId();

        return new TableLineageSourceListModel() {{
            setTargetConnection(hierarchyId != null ? hierarchyId.getConnectionName() : null);
            setTargetSchema(hierarchyId != null ? hierarchyId.getPhysicalTableName().getSchemaName() : null);
            setTargetTable(hierarchyId != null ? hierarchyId.getPhysicalTableName().getTableName() : null);
            setSourceConnection(sourceSpec.getSourceConnection());
            setSourceSchema(sourceSpec.getSourceSchema());
            setSourceTable(sourceSpec.getSourceTable());
            setProperties(sourceSpec.getProperties());
            setCanEdit(canEdit);
        }};
    }

    public static class TableLineageSourceListModelSampleFactory implements SampleValueFactory<TableLineageSourceListModel> {
        @Override
        public TableLineageSourceListModel createSample() {
            return new TableLineageSourceListModel() {{
                setSourceConnection("sourcedb");
                setSourceSchema("app");
                setSourceTable("t_customers");
                setTargetConnection("datalake");
                setTargetSchema("landing_app");
                setTargetTable("customers_landing");
            }};
        }
    }
}
