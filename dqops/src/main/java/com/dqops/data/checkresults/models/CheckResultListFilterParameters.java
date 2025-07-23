/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.models;

import com.dqops.rest.models.common.SortDirection;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;

/**
 * Parameters for filtering the list of data quality issues related to an incident.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class CheckResultListFilterParameters {
    /**
     * Optional filter that is a pattern expression. Filter is used on schema names, table names, dimensions, categories, etc.
     */
    @JsonPropertyDescription("Optional filter that is a pattern expression. Filters are used on column names, sensor names, etc.")
    private String filter;

    /**
     * Optional filter for the number of recent days (1, 7, 30, etc.) that are scanned for issues related to an incident.
     */
    @JsonPropertyDescription("Optional filter for the number of recent days (1, 7, 30, etc.) that are scanned for issues related to an incident.")
    private Integer days;

    /**
     * Optional filter for a single date to return data quality issues that were detected on that date. The date uses a local time zone of the DQOps server.
     */
    @JsonPropertyDescription("Optional filter for a single date to return data quality issues that were detected on that date. The date uses a local time zone of the DQOps server.")
    private LocalDate date;

    /**
     * Optional filter for an exact column name.
     */
    @JsonPropertyDescription("Optional filter for an exact column name.")
    private String column;

    /**
     * Optional filter for an exact data quality check name.
     */
    @JsonPropertyDescription("Optional filter for an exact data quality check name.")
    private String check;

    /**
     * Page number. The first page is 1.
     */
    @JsonPropertyDescription("Page number. The first page is 1 which is the default value.")
    private int page = 1;

    /**
     * Page size. The default page size is 50.
     */
    @JsonPropertyDescription("Page size. The default page size is 50.")
    private int limit = 50;

    /**
     * Sort order.
     */
    @JsonPropertyDescription("Sort order.")
    private CheckResultSortOrder order = CheckResultSortOrder.executedAt;

    /**
     * Sort direction.
     */
    @JsonPropertyDescription("Sort direction.")
    private SortDirection sortDirection = SortDirection.asc;
}
