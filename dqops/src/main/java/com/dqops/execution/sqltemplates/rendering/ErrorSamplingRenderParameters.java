/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
