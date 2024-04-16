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

package com.dqops.data.checkresults.models.currentstatus;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * The parameters for retrieving the TableDataQualityStatusModel.
 */
@Data
@Builder
public class TableCurrentDataQualityStatusFilterParameters {
    /**
     * The connection name in DQOps.
     */
    @JsonPropertyDescription("The connection name in DQOps.")
    private String connectionName;

    /**
     * The physicalTableName composed with a schema and a table name.
     */
    @JsonPropertyDescription("The physical table name object composed with a schema and a table name.")
    private PhysicalTableName physicalTableName;

    /**
     * The number of recent months to load the data. 1 means the current month and 1 last month.
     */
    @JsonPropertyDescription("The number of recent months to load the data. 1 means the current month and 1 last month. The default value is 1, which means that DQOps will read only the parquet partition for this month and the previous month.")
    private Integer lastMonths = 1;

    /**
     * Optional filter that accepts an UTC timestamp to read only data quality check results captured since that timestamp.
     */
    @JsonPropertyDescription("Optional filter that accepts an UTC timestamp to read only data quality check results captured since that timestamp.")
    private Instant since;

    /**
     * Boolean flag to enable detecting the current status of profiling checks. The default value is false, so the current status does not include profiling checks.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonPropertyDescription("Boolean flag to enable detecting the current status of profiling checks. The default value is false, so the current status does not include profiling checks.")
    private boolean profiling = false;

    /**
     * Boolean flag to enable detecting the current status of monitoring checks. The default value is true, so the current status depends on the statu of monitoring checks
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonPropertyDescription("Boolean flag to enable detecting the current status of monitoring checks. The default value is true, so the current status depends on the status of monitoring checks.")
    private boolean monitoring = true;

    /**
     * Boolean flag to enable detecting the current status of partitioned checks. The default value is true, so the current status depends on the statu of partitioned checks.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonPropertyDescription("Boolean flag to enable detecting the current status of partitioned checks. The default value is true, so the current status depends on the status of partitioned checks.")
    @Builder.Default
    private boolean partitioned = true;

    /**
     * Time scale filter for monitoring and partitioned checks (values: daily or monthly).
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
    @JsonPropertyDescription("Data quality check name.")
    private String checkName;

    /**
     * Check's category name
     */
    @JsonPropertyDescription("Check's category name.")
    private String category;

    /**
     * Table comparison name.
     */
    @JsonPropertyDescription("Table comparison name.")
    private String tableComparison;

    /**
     * Check data quality dimension.
     */
    @JsonPropertyDescription("Check data quality dimension.")
    private String qualityDimension;
}
