package com.dqops.data.checkresults.services.models;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

/**
 * The parameters for retrieving the TableDataQualityStatusModel.
 */
@Data
@Builder
public class TableDataQualityStatusFilterParameters {

    // Connection name.
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    private PhysicalTableName physicalTableName;

    private Integer lastMonths;

    private CheckType checkType;

    private CheckTimeScale checkTimeScale;

    private String dataGroup;

    private String checkName;

    private String category;

    private String tableComparison;

//     * @param physicalTableName Physical table name.
//            * @param lastMonths The number of recent months to load the data. 1 means the current month and 1 last month.
//            * @param checkType Check type (optional filter).
//            * @param checkTimeScale Check time scale (optional filter).

}
