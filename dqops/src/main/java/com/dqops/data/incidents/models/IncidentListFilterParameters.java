/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.incidents.models;

import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.rest.models.common.SortDirection;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.NoSuchElementException;

/**
 * Parameters for filtering the list of recent data quality incidents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentListFilterParameters {
    /**
     * Number of recent months to load the recent incidents.
     */
    @JsonPropertyDescription("Number of recent months to load the recent incidents. The default value is 3 months.")
    private int recentMonths = 3;

    /**
     * Optional filter that is a pattern expression. Filter is used on schema names, table names, dimensions, categories, etc.
     */
    @JsonPropertyDescription("Optional filter that is a pattern expression. Filters are used on schema names, table names, dimensions, categories, etc.")
    private String filter;

    /**
     * Optional filter for the data quality dimension name, case sensitive.
     */
    @JsonPropertyDescription("Optional filter for the data quality dimension name, case sensitive.")
    private String dimension;

    /**
     * Optional filter for the data quality check category name, case sensitive.
     */
    @JsonPropertyDescription("Optional filter for the data quality check category name, case sensitive.")
    private String category;

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
     * Load incidents in 'open' status (new incidents). The default value is true.
     */
    @JsonPropertyDescription("Load incidents in 'open' status (new incidents). The default value is true.")
    private boolean open = true;

    /**
     * Load incidents in 'acknowledged' status. The default value is true.
     */
    @JsonPropertyDescription("Load incidents in 'acknowledged' status. The default value is true.")
    private boolean acknowledged = true;

    /**
     * Load incidents in 'resolved' status. The default value is false.
     */
    @JsonPropertyDescription("Load incidents in 'resolved' status. The default value is false.")
    private boolean resolved = false;

    /**
     * Load incidents in 'muted' status. The default value is false.
     */
    @JsonPropertyDescription("Load incidents in 'muted' status. The default value is false.")
    private boolean muted = false;

    /**
     * Load incidents with given severity level.
     */
    @JsonPropertyDescription("Load incidents with given severity level.")
    private Integer severity;

    /**
     * Sort order.
     */
    @JsonPropertyDescription("Sort order.")
    private IncidentSortOrder order = IncidentSortOrder.failedChecksCount;

    /**
     * Sort direction.
     */
    @JsonPropertyDescription("Sort direction.")
    private SortDirection sortDirection = SortDirection.asc;

    /**
     * Checks if the given incident status is on the list of accepted incident statuses that should be returned.
     * @param incidentStatus Tested incident status.
     * @return True when this type of incident status is accepted, false when the user filtered out the incident status.
     */
    public boolean isIncidentStatusEnabled(IncidentStatus incidentStatus) {
        switch (incidentStatus) {
            case open:
                return this.open;

            case acknowledged:
                return this.acknowledged;

            case resolved:
                return this.resolved;

            case muted:
                return this.muted;

            default:
                throw new NoSuchElementException("Incident status not supported: " + incidentStatus);
        }
    }
}
