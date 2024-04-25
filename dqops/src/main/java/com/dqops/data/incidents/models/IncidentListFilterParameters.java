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
