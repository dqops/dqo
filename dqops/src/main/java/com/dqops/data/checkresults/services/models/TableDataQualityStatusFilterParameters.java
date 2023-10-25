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

    /**
     * The connection name in DQOps.
     */
    @JsonPropertyDescription("The connection name in DQOps.")
    private String connectionName;

    /**
     * The physicalTableName composed with a schema and a table name.
     */
    @JsonPropertyDescription("The physicalTableName composed with a schema and a table name.")
    private PhysicalTableName physicalTableName;

    /**
     * The number of recent months to load the data. 1 means the current month and 1 last month.
     */
    @JsonPropertyDescription("The number of recent months to load the data. 1 means the current month and 1 last month.")
    private Integer lastMonths;

    /**
     * Data quality check type (profiling, monitoring, partitioned)
     */
    @JsonPropertyDescription("Data quality check type (profiling, monitoring, partitioned)")
    private CheckType checkType;

    /**
     * Check timescale (for monitoring and partitioned checks).
     */
    @JsonPropertyDescription("Time scale filter for monitoring and partitioned checks (values: daily or monthly).")
    private CheckTimeScale checkTimeScale;

    /**
     * Data group.
     */
    @JsonPropertyDescription("Data group.")
    private String dataGroup;

    /**
     * Data quality check name.
     */
    @JsonPropertyDescription("Data quality check name")
    private String checkName;

    /**
     * Check's category name
     */
    @JsonPropertyDescription("Check's category name")
    private String category;

    /**
     * Table comparison name.
     */
    @JsonPropertyDescription("Table comparison name.")
    private String tableComparison;

}
