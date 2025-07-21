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

import java.util.ArrayList;
import java.util.List;

/**
 * The table lineage model that returns all upstream tables, downstream tables, or both.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableLineageModel", description = "The table lineage model that returns all upstream tables, downstream tables, or both.")
@Data
public class TableLineageModel {
    /**
     * The table for which the data lineage is generated.
     */
    @JsonPropertyDescription("The table for which the data lineage is generated.")
    private DomainConnectionTableKey relativeTable;

    /**
     * The data quality status of the reference table (in the middle of the data lineage) showing the highest severity
     * problems detected on the reference table and all upstream tables from which some issues have come.
     */
    @JsonPropertyDescription("The data quality status of the reference table (in the middle of the data lineage) showing the highest severity problems detected on the reference table and all upstream tables from which some issues have come.")
    private TableCurrentDataQualityStatusModel relativeTableCumulativeQualityStatus;

    /**
     * A list of data flows from source tables to direct target tables. Describes the data quality status of the source table.
     */
    @JsonPropertyDescription("A list of data flows from source tables to direct target tables. Describes the data quality status of the source table.")
    private List<TableLineageFlowModel> flows = new ArrayList<>();

    /**
     * This flag tells if the data lineage was fully loaded. If any data flows are missing or the data quality status of some tables is missing, this flag will return false, which means that the data lineage must be loaded again.
     */
    @JsonPropertyDescription("This flag tells if the data lineage was fully loaded. If any data flows are missing or the data quality status of some tables is missing, this flag will return false, which means that the data lineage must be loaded again.")
    private boolean dataLineageFullyLoaded = true;
}
