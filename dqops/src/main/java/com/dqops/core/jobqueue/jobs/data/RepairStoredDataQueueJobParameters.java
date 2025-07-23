/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.data;

import com.dqops.metadata.search.TableSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Parameters for the {@link RepairStoredDataQueueJob} job that repairs data stored in user's ".data" directory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
public class RepairStoredDataQueueJobParameters implements Cloneable {
    // @NotNull  // should be NotNull, but there are errors in TypeScript (CheckTableHeader.tsx)
    private String connectionName;
    private String schemaTableName;

    private boolean repairErrors = false;
    private boolean repairStatistics = false;
    private boolean repairCheckResults = false;
    private boolean repairSensorReadouts = false;

    public RepairStoredDataQueueJobParameters() {
    }

    /**
     * Creates parameters for a repair stored data job.
     * @param connectionName  Connection name.
     * @param schemaTableName Schema.table name.
     */
    public RepairStoredDataQueueJobParameters(String connectionName,
                                              String schemaTableName) {
        this.connectionName = connectionName;
        this.schemaTableName = schemaTableName;
    }

    /**
     * Creates a job parameters object by relying on info provided in {@link TableSearchFilters}.
     * @param tableSearchFilters Table search filters object providing the basis for the job parameters.
     * @return Repair stored data job parameters based on the filters.
     */
    public static RepairStoredDataQueueJobParameters fromTableSearchFilters(TableSearchFilters tableSearchFilters) {
        return new RepairStoredDataQueueJobParameters() {{
            setConnectionName(tableSearchFilters.getConnection());
            setSchemaTableName(tableSearchFilters.getFullTableName());

            setRepairCheckResults(true);
            setRepairErrors(true);
            setRepairStatistics(true);
            setRepairSensorReadouts(true);
        }};
    }

    @Override
    public RepairStoredDataQueueJobParameters clone() {
        try {
            RepairStoredDataQueueJobParameters clone = (RepairStoredDataQueueJobParameters) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
