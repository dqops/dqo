/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
     * The current data quality status of the target table.
     */
    @JsonPropertyDescription("The current data quality status of the target table.")
    private TableCurrentDataQualityStatusModel targetTableQualityStatus;

    /**
     * The data quality status identified from the data quality status of all upstream tables and the target table.
     */
    @JsonPropertyDescription("The data quality status identified from the data quality status of all upstream tables and the target table.")
    private TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus;

    /**
     * The row count of the source table.
     */
    @JsonPropertyDescription("The row count of the source table.")
    private long rowCount;

    public TableLineageFlowModel() {
    }

    /**
     * Create a new flow from a source table to a target table.
     * @param sourceTable Source table (upstream).
     * @param targetTable Target table (downstream).
     * @param sourceTableQualityStatus The current table data quality status of the source table (if present).
     * @param targetTableQualityStatus The current table data quality status of the target table (if present).
     * @param upstreamCombinedQualityStatus The combined data quality status of the target table, including the worst status of the upstream tables.
     */
    public TableLineageFlowModel(DomainConnectionTableKey sourceTable,
                                 DomainConnectionTableKey targetTable,
                                 TableCurrentDataQualityStatusModel sourceTableQualityStatus,
                                 TableCurrentDataQualityStatusModel targetTableQualityStatus,
                                 TableCurrentDataQualityStatusModel upstreamCombinedQualityStatus) {
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.sourceTableQualityStatus = sourceTableQualityStatus;
        this.targetTableQualityStatus = targetTableQualityStatus;
        this.upstreamCombinedQualityStatus = upstreamCombinedQualityStatus;

        if (sourceTableQualityStatus != null && sourceTableQualityStatus.getTotalRowCount() != null &&
                sourceTableQualityStatus.getTotalRowCount() > 0L) {
            this.rowCount = sourceTableQualityStatus.getTotalRowCount();
        } else {
            this.rowCount = 1L;
        }
    }
}
