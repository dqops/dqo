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
