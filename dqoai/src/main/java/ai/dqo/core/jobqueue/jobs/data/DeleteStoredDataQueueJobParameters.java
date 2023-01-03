/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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

import java.time.LocalDate;

/**
 * Parameters for the {@link DeleteStoredDataQueueJob} job that deletes data stored in user's ".data" directory.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteStoredDataQueueJobParameters {
    private String connectionName;
    private String schemaName;
    private String tableName;
    private LocalDate dateStart;
    private LocalDate dateEnd;


    private boolean deleteRuleResults = false;
    private boolean deleteSensorReadouts = false;
    private boolean deleteProfilingResults = false;
    private boolean deleteErrors = false;

    private boolean ignoreDateDay = true;
    private String checkCategory;
    private String checkName;
    private String checkType;
    private String columnName;
    private String dataStreamName;
    private String qualityDimension;
    private String timeGradient;

    public DeleteStoredDataQueueJobParameters() {
    }

    /**
     * Creates parameters for a delete stored data job.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dateStart      Beginning of the period marked for deletion (only year and month considered by default).
     * @param dateEnd        End of the period marked for deletion (only year and month considered by default).
     */
    public DeleteStoredDataQueueJobParameters(String connectionName,
                                              String schemaName,
                                              String tableName,
                                              LocalDate dateStart,
                                              LocalDate dateEnd) {
        this.connectionName = connectionName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets the connection name.
     * @param connectionName Connection name.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns the schema name.
     * @return Schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets the schema name.
     * @param schemaName Schema name.
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * Returns the table name.
     * @return Table name.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the table name.
     * @param tableName Table name.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public boolean isDeleteRuleResults() {
        return deleteRuleResults;
    }

    public void setDeleteRuleResults(boolean deleteRuleResults) {
        this.deleteRuleResults = deleteRuleResults;
    }

    public boolean isDeleteSensorReadouts() {
        return deleteSensorReadouts;
    }

    public void setDeleteSensorReadouts(boolean deleteSensorReadouts) {
        this.deleteSensorReadouts = deleteSensorReadouts;
    }

    public boolean isDeleteProfilingResults() {
        return deleteProfilingResults;
    }

    public void setDeleteProfilingResults(boolean deleteProfilingResults) {
        this.deleteProfilingResults = deleteProfilingResults;
    }

    public boolean isDeleteErrors() {
        return deleteErrors;
    }

    public void setDeleteErrors(boolean deleteErrors) {
        this.deleteErrors = deleteErrors;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean isIgnoreDateDay() {
        return ignoreDateDay;
    }

    public void setIgnoreDateDay(boolean ignoreDateDay) {
        this.ignoreDateDay = ignoreDateDay;
    }

    public String getCheckCategory() {
        return checkCategory;
    }

    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataStreamName() {
        return dataStreamName;
    }

    public void setDataStreamName(String dataStreamName) {
        this.dataStreamName = dataStreamName;
    }

    public String getQualityDimension() {
        return qualityDimension;
    }

    public void setQualityDimension(String qualityDimension) {
        this.qualityDimension = qualityDimension;
    }

    public String getTimeGradient() {
        return timeGradient;
    }

    public void setTimeGradient(String timeGradient) {
        this.timeGradient = timeGradient;
    }
}
