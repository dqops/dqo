/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.jobqueue.jobs.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

/**
 * Parameters for the {@link DeleteStoredDataQueueJob} job that deletes data stored in user's ".data" directory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeleteStoredDataQueueJobParameters {
    private String connectionName;
    private String schemaTableName;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private boolean ignoreDateDay = true;

    private boolean deleteErrors = false;
    private boolean deleteProfilingResults = false;
    private boolean deleteRuleResults = false;
    private boolean deleteSensorReadouts = false;

    private String checkCategory;
    private String checkName;
    private String checkType;
    private String sensorName;
    private String columnName;
    private String dataStreamName;
    private String qualityDimension;
    private String timeGradient;

    private String profilerCategory;
    private String profilerName;
    private String profilerType;

    public DeleteStoredDataQueueJobParameters() {
    }

    /**
     * Creates parameters for a delete stored data job.
     * @param connectionName  Connection name.
     * @param schemaTableName Schema.table name.
     * @param dateStart       Beginning of the period marked for deletion (only year and month considered by default).
     * @param dateEnd         End of the period marked for deletion (only year and month considered by default).
     */
    public DeleteStoredDataQueueJobParameters(String connectionName,
                                              String schemaTableName,
                                              LocalDate dateStart,
                                              LocalDate dateEnd) {
        this.connectionName = connectionName;
        this.schemaTableName = schemaTableName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
