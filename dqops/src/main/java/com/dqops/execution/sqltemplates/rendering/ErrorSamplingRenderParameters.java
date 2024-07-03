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
package com.dqops.execution.sqltemplates.rendering;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Json object that is sent to the python script that renders the error sampling query.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class ErrorSamplingRenderParameters {
    private int samplesLimit = 50;
    private int totalSamplesLimit = 1000;
    private List<String> idColumns = new ArrayList<>();

    /**
     * Returns the limit of error samples that are captured for each data grouping (data segment).
     * @return Maximum number of samples that are captured for each group.
     */
    public int getSamplesLimit() {
        return samplesLimit;
    }

    /**
     * Sets the maximum limit of results that are captured for each group.
     * @param samplesLimit The limit of samples captured per group.
     */
    public void setSamplesLimit(int samplesLimit) {
        this.samplesLimit = samplesLimit;
    }

    /**
     * Returns the maximum number of error samples returned when data grouping is enabled and the results come from different data groups.
     * @return Returns the maximum total number of error results returned when data grouping is enabled.
     */
    public int getTotalSamplesLimit() {
        return totalSamplesLimit;
    }

    /**
     * Sets the limit for the maximum number of results returned when data grouping is enabled.
     * @param totalSamplesLimit Maximum number of grouped error samples.
     */
    public void setTotalSamplesLimit(int totalSamplesLimit) {
        this.totalSamplesLimit = totalSamplesLimit;
    }

    /**
     * Returns a list of column names that are the key columns (ID columns) and will be captured for error samples to identify the row that contains the error.
     * @return A list of columns that are the identifiers.
     */
    public List<String> getIdColumns() {
        return idColumns;
    }

    /**
     * Sets (replaces) the list of column names that are the identifier columns, captured for error samples.
     * @param idColumns New list of identifier columns.
     */
    public void setIdColumns(List<String> idColumns) {
        this.idColumns = idColumns;
    }
}
