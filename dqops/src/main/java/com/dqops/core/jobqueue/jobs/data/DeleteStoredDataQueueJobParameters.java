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

import com.dqops.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * Parameters for the {@link DeleteStoredDataQueueJob} job that deletes data stored in user's ".data" directory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteStoredDataQueueJobParameters implements Cloneable {
    // @NotNull  // should be NotNull, but there are errors in TypeScript (CheckTableHeader.tsx)
    private String connectionName;
    private String schemaTableName;
    // TODO: Add proper descriptions using this annotation.
    // @JsonPropertyDescription("Data start data")
    private LocalDate dateStart;
    private LocalDate dateEnd;

    private boolean deleteErrors = false;
    private boolean deleteStatistics = false;
    private boolean deleteCheckResults = false;
    private boolean deleteSensorReadouts = false;

    private List<String> columnNames;
    private String checkCategory;
    private String tableComparisonName;
    private String checkName;
    private String checkType;
    private String sensorName;
    private String dataGroupTag;
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
        if (checkSearchFilters == null) {
            return null;
        }

        return new DeleteStoredDataQueueJobParameters() {{
            setConnectionName(checkSearchFilters.getConnectionName());
            setSchemaTableName(checkSearchFilters.getSchemaTableName());
            setColumnNames(checkSearchFilters.getColumnName() != null ? List.of(checkSearchFilters.getColumnName()) : null);
            setCheckType(checkSearchFilters.getCheckType() != null ? checkSearchFilters.getCheckType().getDisplayName() : null);
            setTimeGradient(checkSearchFilters.getTimeScale() != null ? checkSearchFilters.getTimeScale().toTimeSeriesGradient().name() : null);
            setCheckName(checkSearchFilters.getCheckName());
            setSensorName(checkSearchFilters.getSensorName());
            setCheckCategory(checkSearchFilters.getCheckCategory());
            setTableComparisonName(checkSearchFilters.getTableComparisonName());

            setDeleteCheckResults(true);
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
