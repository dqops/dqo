/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rest.models.metadata;

import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Table status model that identifies which type of information is already collected, such as data quality checks are configured, or statistics collected.
 * DQOps user interface uses this information to activate red borders to highlight tabs in the user interface that must be clicked to continue profiling the table.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableProfilingSetupStatusModel", description = "Table status model that identifies which type of information is already collected, such as data quality checks are configured, or statistics collected.")
public class TableProfilingSetupStatusModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Table hash that identifies the table using a unique hash code.
     */
    @JsonPropertyDescription("Table hash that identifies the table using a unique hash code.")
    private Long tableHash;

    /**
     * Physical table details (a physical schema name and a physical table name).
     */
    @JsonPropertyDescription("Physical table details (a physical schema name and a physical table name).")
    private PhysicalTableName target;

    /**
     * The basic statistics were collected for this table. If this field returns false, the statistics were not collected and the user should collect basic statistics again.
     */
    @JsonPropertyDescription("The basic statistics were collected for this table. If this field returns false, the statistics were not collected and the user should collect basic statistics again.")
    private boolean basicStatisticsCollected;

    /**
     * Returns true if the table has any profiling checks configured on the table, or any of its column. Returns false when the user should first generate a configuration of the profiling checks using the rule miner.
     */
    @JsonPropertyDescription("Returns true if the table has any profiling checks configured on the table, or any of its column. Returns false when the user should first generate a configuration of the profiling checks using the rule miner.")
    private boolean profilingChecksConfigured;

    /**
     * Returns true if the table has any monitoring checks configured on the table, or any of its column. Returns false when the user should first generate a configuration of the monitoring checks using the rule miner.
     */
    @JsonPropertyDescription("Returns true if the table has any monitoring checks configured on the table, or any of its column. Returns false when the user should first generate a configuration of the monitoring checks using the rule miner.")
    private boolean monitoringChecksConfigured;

    /**
     * Returns true if the table has any partition checks configured on the table, or any of its column. The value is true also when the table is not configured to support partitioned checks, so asking the user to configure partition checks is useless. Returns false when the user should first generate a configuration of the partition checks using the rule miner.
     */
    @JsonPropertyDescription("Returns true if the table has any partition checks configured on the table, or any of its column. The value is true also when the table is not configured to support partitioned checks, so asking the user to configure partition checks is useless. Returns false when the user should first generate a configuration of the partition checks using the rule miner.")
    private boolean partitionChecksConfigured;

    /**
     * Returns true if the table has any recent data quality check results. Returns false when the user should run any checks to get any results.
     */
    @JsonPropertyDescription("Returns true if the table has any recent data quality check results. Returns false when the user should run any checks to get any results.")
    private boolean checkResultsPresent;


    public static class TableProfilingSetupStatusModelSampleFactory implements SampleValueFactory<TableProfilingSetupStatusModel> {
        @Override
        public TableProfilingSetupStatusModel createSample() {
            return new TableProfilingSetupStatusModel() {{
                setConnectionName(SampleStringsRegistry.getConnectionName());
                setTarget(new TableSpec.TableSpecSampleFactory().createSample().getPhysicalTableName());
            }};
        }
    }
}
