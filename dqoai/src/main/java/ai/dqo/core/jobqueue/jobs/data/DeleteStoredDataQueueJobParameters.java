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

import ai.dqo.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Parameters for the {@link DeleteStoredDataQueueJob} job that deletes data stored in user's ".data" directory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeleteStoredDataQueueJobParameters implements Cloneable {
    // @NotNull  // should be NotNull, but there are errors in TypeScript (CheckTableHeader.tsx)
    private String connectionName;
    private String schemaTableName;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    private boolean deleteErrors = false;
    private boolean deleteStatistics = false;
    private boolean deleteRuleResults = false;
    private boolean deleteSensorReadouts = false;

    private List<String> columnNames;
    private String checkCategory;
    private String checkName;
    private String checkType;
    private String sensorName;
    private String dataStreamName;
    private String qualityDimension;
    private String timeGradient;

    private String collectorCategory;
    private String collectorName;
    private String collectorTarget;

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

    /**
     * Creates a job parameters object by relying on info provided in CheckSearchFilters.
     * @param checkSearchFilters Check search filters object providing the basis for the job parameters.
     * @return Delete stored data job parameters based on the filters.
     */
    public static DeleteStoredDataQueueJobParameters fromCheckSearchFilters(CheckSearchFilters checkSearchFilters) {
        return new DeleteStoredDataQueueJobParameters() {{
            setConnectionName(checkSearchFilters.getConnectionName());
            setSchemaTableName(checkSearchFilters.getSchemaTableName());
            setColumnNames(new LinkedList<>(){{add(checkSearchFilters.getColumnName());}});

            setCheckType(checkSearchFilters.getCheckType() != null ? checkSearchFilters.getCheckType().getDisplayName() : null);
            setCheckName(checkSearchFilters.getCheckName());
            setSensorName(checkSearchFilters.getSensorName());
            setCheckCategory(checkSearchFilters.getCheckCategory());

            setDeleteRuleResults(true);
            setDeleteErrors(true);
            setDeleteStatistics(true);
            setDeleteSensorReadouts(true);
        }};
    }

    @Override
    public DeleteStoredDataQueueJobParameters clone() {
        try {
            DeleteStoredDataQueueJobParameters clone = (DeleteStoredDataQueueJobParameters) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
