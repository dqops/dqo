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

package com.dqops.metadata.lineage.lineageservices;


import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Table lineage flow model that describes the data flow from one table to another table, and the data quality status of the source table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableLineageFlowModel", description = "Table lineage flow model that describes the data flow from one table to another table, and the data quality status of the source table.")
@Data
public class TableLineageFlowModel {
    /**
     * The source table.
     */
    @JsonPropertyDescription("The source table.")
    private DomainConnectionTableKey sourceTable;

    /**
     * The target table.
     */
    @JsonPropertyDescription("The target table.")
    private DomainConnectionTableKey targetTable;

    /**
     * The current data quality status of the source table.
     */
    @JsonPropertyDescription("The current data quality status of the source table.")
    private TableCurrentDataQualityStatusModel sourceTableQualityStatus;

    /**
     * The data quality status identified from the data quality status of all upstream tables.
     */
    @JsonPropertyDescription("The data quality status identified from the data quality status of all upstream tables.")
    private TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus;

    public TableLineageFlowModel() {
    }

    /**
     * Create a new flow from a source table to a target table.
     * @param sourceTable Source table (upstream).
     * @param targetTable Target table (downstream).
     * @param sourceTableQualityStatus The current table data quality status of the source table (if present).
     * @param upstreamCombinedQualityStatus The combined data quality status of the target table, including the worst status of the upstream tables.
     */
    public TableLineageFlowModel(DomainConnectionTableKey sourceTable,
                                 DomainConnectionTableKey targetTable,
                                 TableCurrentDataQualityStatusModel sourceTableQualityStatus,
                                 TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus) {
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.sourceTableQualityStatus = sourceTableQualityStatus;
        this.upstreamCombinedQualityStatus = upstreamCombinedQualityStatus;
    }
}
