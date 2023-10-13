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

package com.dqops.rest.controllers;

import com.dqops.core.incidents.IncidentImportQueueService;
import com.dqops.core.incidents.IncidentIssueUrlChangeParameters;
import com.dqops.core.incidents.IncidentStatusChangeParameters;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.data.checkresults.services.models.*;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.data.incidents.services.models.*;
import com.dqops.data.incidents.services.IncidentsDataService;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.common.SortDirection;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Data quality incidents REST API controller.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Incidents", description = "Data quality incidents controller that supports loading incidents and changing the status of an incident.")
public class IncidentsController {
    private IncidentsDataService incidentsDataService;
    private IncidentImportQueueService incidentImportQueueService;
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Creates an incident management service, given all used dependencies.
     * @param incidentsDataService Incident data service used to load incidents.
     * @param incidentImportQueueService Incident queued update service that updates the statuses of incidents.
     * @param userHomeContextFactory User home factory.
     */
    @Autowired
    public IncidentsController(IncidentsDataService incidentsDataService,
                               IncidentImportQueueService incidentImportQueueService,
                               UserHomeContextFactory userHomeContextFactory) {
        this.incidentsDataService = incidentsDataService;
        this.incidentImportQueueService = incidentImportQueueService;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Retrieves a single incident.
     * @param connectionName Connection name.
     * @param year Year when the incident was first seen.
     * @param month Month when the incident was first seen.
     * @param incidentId Incident id.
     * @return Incident model of the loaded incident.
     */
    @GetMapping(value = "/incidents/{connectionName}/{year}/{month}/{incidentId}", produces = "application/json")
    @ApiOperation(value = "getIncident", notes = "Return a single data quality incident's details.", response = IncidentModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Incident returned", response = IncidentModel.class),
            @ApiResponse(code = 404, message = "Incident not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<IncidentModel>> getIncident(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Year when the incident was first seen") @PathVariable int year,
            @ApiParam("Month when the incident was first seen") @PathVariable int month,
            @ApiParam("Incident id") @PathVariable String incidentId) {
        IncidentModel incidentModel = this.incidentsDataService.loadIncident(connectionName, year, month, incidentId);

        if (incidentModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(Mono.just(incidentModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a list of failed data quality checks for an incident.
     * @param connectionName Connection name.
     * @param year Year when the incident was first seen.
     * @param month Month when the incident was first seen.
     * @param incidentId Incident id.
     * @param limit Page size.
     * @param page Page number.
     * @param filter Optional filter.
     * @param days Optional filter for a number of recent days to read the related issues.
     * @param date Optional filter for a date.
     * @param column Optional column name to filter.
     * @param check Optional check name to filter.
     * @param order Sort order.
     * @param direction Sort direction.
     * @return Incident model of the loaded incident.
     */
    @GetMapping(value = "/incidents/{connectionName}/{year}/{month}/{incidentId}/issues", produces = "application/json")
    @ApiOperation(value = "getIncidentIssues", notes = "Return a paged list of failed data quality check results that are related to an incident.",
            response = CheckResultEntryModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Incident returned", response = CheckResultEntryModel[].class),
            @ApiResponse(code = 404, message = "Incident not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<CheckResultEntryModel>> getIncidentIssues(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Year when the incident was first seen") @PathVariable int year,
            @ApiParam("Month when the incident was first seen") @PathVariable int month,
            @ApiParam("Incident id") @PathVariable String incidentId,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
            @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 50 rows", required = false)
            @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "filter", value = "Optional filter", required = false)
            @RequestParam(required = false) Optional<String> filter,
            @ApiParam(name = "days", value = "Optional filter for a number of recent days to read the related issues", required = false)
            @RequestParam(required = false) Optional<Integer> days,
            @ApiParam(name = "date", value = "Optional filter to return data quality issues only for a given date. The date should be an ISO8601 formatted date, it is treated as the timezone of the DQOps server.", required = false)
            @RequestParam(required = false) Optional<LocalDate> date,
            @ApiParam(name = "column", value = "Optional column name filter", required = false)
            @RequestParam(required = false) Optional<String> column,
            @ApiParam(name = "check", value = "Optional check name filter", required = false)
            @RequestParam(required = false) Optional<String> check,
            @ApiParam(name = "order", value = "Optional sort order, the default sort order is by the execution date", required = false)
            @RequestParam(required = false) Optional<CheckResultSortOrder> order,
            @ApiParam(name = "direction", value = "Optional sort direction, the default sort direction is ascending", required = false)
            @RequestParam(required = false) Optional<SortDirection> direction) {
        CheckResultListFilterParameters filterParameters = new CheckResultListFilterParameters();

        if (page.isPresent()) {
            filterParameters.setPage(page.get());
        }
        if (limit.isPresent()) {
            filterParameters.setLimit(limit.get());
        }
        if (filter.isPresent()) {
            filterParameters.setFilter(filter.get());
        }
        if (days.isPresent()) {
            filterParameters.setDays(days.get());
        }
        if (date.isPresent()) {
            filterParameters.setDate(date.get());
        }
        if (column.isPresent()) {
            filterParameters.setColumn(column.get());
        }
        if (check.isPresent()) {
            filterParameters.setCheck(check.get());
        }
        if (order.isPresent()) {
            filterParameters.setOrder(order.get());
        }
        if (direction.isPresent()) {
            filterParameters.setSortDirection(direction.get());
        }

        CheckResultEntryModel[] checkResultEntryModels = this.incidentsDataService.loadCheckResultsForIncident(
                connectionName, year, month, incidentId, filterParameters);

        if (checkResultEntryModels == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(Flux.just(checkResultEntryModels), HttpStatus.OK); // 200
    }

    /**
     * Generates a histogram of data quality issues for each day, returning the number of data quality issues on that day.
     * The other histograms are by a column name and by a check name.
     * @param connectionName Connection name.
     * @param year Year when the incident was first seen.
     * @param month Month when the incident was first seen.
     * @param incidentId Incident id.
     * @param filter Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions.
     * @param days Optional filter for a number of recent days to read the related issues.
     * @param date Optional date filter.
     * @param column Optional column name filter.
     * @param check Optional check name filter.
     * @return Incident histogram of data quality issues.
     */
    @GetMapping(value = "/incidents/{connectionName}/{year}/{month}/{incidentId}/histogram", produces = "application/json")
    @ApiOperation(value = "getIncidentHistogram", notes = "Generates histograms of data quality issues for each day, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.",
            response = IncidentIssueHistogramModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Incidents' histograms returned", response = IncidentIssueHistogramModel.class),
            @ApiResponse(code = 404, message = "Incident not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<IncidentIssueHistogramModel>> getIncidentHistogram(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Year when the incident was first seen") @PathVariable int year,
            @ApiParam("Month when the incident was first seen") @PathVariable int month,
            @ApiParam("Incident id") @PathVariable String incidentId,
            @ApiParam(name = "filter", value = "Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions", required = false)
            @RequestParam(required = false) Optional<String> filter,
            @ApiParam(name = "days", value = "Optional filter for a number of recent days to read the related issues", required = false)
            @RequestParam(required = false) Optional<Integer> days,
            @ApiParam(name = "date", value = "Optional date filter", required = false)
            @RequestParam(required = false) Optional<LocalDate> date,
            @ApiParam(name = "column", value = "Optional column name filter", required = false)
            @RequestParam(required = false) Optional<String> column,
            @ApiParam(name = "check", value = "Optional check name filter", required = false)
            @RequestParam(required = false) Optional<String> check) {
        IncidentHistogramFilterParameters filterParameters = new IncidentHistogramFilterParameters();
        if (filter.isPresent()) {
            filterParameters.setFilter(filter.get());
        }
        if (days.isPresent()) {
            filterParameters.setDays(days.get());
        }
        if (date.isPresent()) {
            filterParameters.setDate(date.get());
        }
        if (column.isPresent()) {
            filterParameters.setColumn(column.get());
        }
        if (check.isPresent()) {
            filterParameters.setCheck(check.get());
        }

        IncidentIssueHistogramModel histogramModel = this.incidentsDataService.buildDailyIssuesHistogramForIncident(
                connectionName, year, month, incidentId, filterParameters);

        if (histogramModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(Mono.just(histogramModel), HttpStatus.OK); // 200
    }

    /**
     * Finds recent data quality incidents on a connection.
     * @param connectionName Connection name.
     * @param months Optional number of months to look back.
     * @param open Return open incidents.
     * @param acknowledged Return acknowledged incidents.
     * @param resolved Return resolved incidents.
     * @param muted Return muted incidents.
     * @param filter Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions.
     * @param limit Page size.
     * @param page Page number.
     * @param order Sort order.
     * @param direction Sort direction.
     * @return List of incidents.
     */
    @GetMapping(value = "/incidents/{connectionName}", produces = "application/json")
    @ApiOperation(value = "findRecentIncidentsOnConnection", notes = "Returns a list of recent data quality incidents.",
            response = IncidentModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IncidentModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<IncidentModel>> findRecentIncidentsOnConnection(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam(name = "months", value = "Number of recent months to load, the default is 3 months", required = false)
                @RequestParam(required = false) Optional<Integer> months,
            @ApiParam(name = "open", value = "Returns open incidents, when the parameter is missing, the default value is true", required = false)
                @RequestParam(required = false) Optional<Boolean> open,
            @ApiParam(name = "acknowledged", value = "Returns acknowledged incidents, when the parameter is missing, the default value is true", required = false)
                @RequestParam(required = false) Optional<Boolean> acknowledged,
            @ApiParam(name = "resolved", value = "Returns resolved incidents, when the parameter is missing, the default value is false", required = false)
                @RequestParam(required = false) Optional<Boolean> resolved,
            @ApiParam(name = "muted", value = "Returns muted incidents, when the parameter is missing, the default value is false", required = false)
                @RequestParam(required = false) Optional<Boolean> muted,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
                @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 50 rows", required = false)
                @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "filter", value = "Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions", required = false)
                @RequestParam(required = false) Optional<String> filter,
            @ApiParam(name = "order", value = "Optional sort order, the default sort order is by the number of failed data quality checks", required = false)
                @RequestParam(required = false) Optional<IncidentSortOrder> order,
            @ApiParam(name = "direction", value = "Optional sort direction, the default sort direction is ascending", required = false)
               @RequestParam(required = false) Optional<SortDirection> direction) {
        IncidentListFilterParameters filterParameters = new IncidentListFilterParameters();
        filterParameters.setRecentMonths(months.orElse(3));
        filterParameters.setOpen(open.orElse(Boolean.TRUE));
        filterParameters.setAcknowledged(acknowledged.orElse(Boolean.TRUE));
        filterParameters.setResolved(resolved.orElse(Boolean.FALSE));
        filterParameters.setMuted(muted.orElse(Boolean.FALSE));

        if (page.isPresent()) {
            filterParameters.setPage(page.get());
        }
        if (limit.isPresent()) {
            filterParameters.setLimit(limit.get());
        }
        if (filter.isPresent()) {
            filterParameters.setFilter(filter.get());
        }
        if (order.isPresent()) {
            filterParameters.setOrder(order.get());
        }
        if (direction.isPresent()) {
            filterParameters.setSortDirection(direction.get());
        }

        Collection<IncidentModel> incidentModels = this.incidentsDataService.loadRecentIncidentsOnConnection(connectionName, filterParameters);
        if (incidentModels == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.OK);
        }

        return new ResponseEntity<>(Flux.fromStream(incidentModels.stream()), HttpStatus.OK);
    }

    /**
     * Lists connections with their recent open incidents stats.
     * @return List of connections with their incidents counts.
     */
    @GetMapping(value = "/incidentstat", produces = "application/json")
    @ApiOperation(value = "findConnectionIncidentStats", notes = "Returns a list of connection names with incident statistics - the count of recent open incidents.",
            response = IncidentsPerConnectionModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IncidentsPerConnectionModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<IncidentsPerConnectionModel>> findConnectionIncidentStats(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        Collection<IncidentsPerConnectionModel> connectionIncidentStats = this.incidentsDataService.findConnectionIncidentStats();
        return new ResponseEntity<>(Flux.fromStream(connectionIncidentStats.stream()), HttpStatus.OK);
    }

    /**
     * Updates the status of a single data quality incident.
     * @param connectionName Connection name.
     * @param year Year when the incident was first seen.
     * @param month Month when the incident was first seen.
     * @param incidentId Incident id.
     * @param status New incident status to set.
     * @return None.
     */
    @PostMapping(value = "/incidents/{connectionName}/{year}/{month}/{incidentId}/status", produces = "application/json")
    @ApiOperation(value = "setIncidentStatus", notes = "Changes the incident's status to a new status.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality incident's status successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Connection was not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> setIncidentStatus(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Year when the incident was first seen") @PathVariable int year,
            @ApiParam("Month when the incident was first seen") @PathVariable int month,
            @ApiParam("Incident id") @PathVariable String incidentId,
            @ApiParam(name = "status", value = "New incident status, supported values: open, acknowledged, resolved, muted")
                @RequestParam IncidentStatus status) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        IncidentStatusChangeParameters incidentStatusChangeParameters = new IncidentStatusChangeParameters(
                connectionName, year, month, incidentId, status, connectionWrapper.getSpec().getIncidentGrouping());
        this.incidentImportQueueService.setIncidentStatus(incidentStatusChangeParameters); // operation performed in the background, no result is returned

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the issueUrl of a single data quality incident.
     * @param connectionName Connection name.
     * @param year Year when the incident was first seen.
     * @param month Month when the incident was first seen.
     * @param incidentId Incident id.
     * @param issueUrl New incident's issueUrl to set.
     * @return None.
     */
    @PostMapping(value = "/incidents/{connectionName}/{year}/{month}/{incidentId}/issueurl", produces = "application/json")
    @ApiOperation(value = "setIncidentIssueUrl", notes = "Changes the incident's issueUrl to a new status.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality incident's issueUrl successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> setIncidentIssueUrl(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Year when the incident was first seen") @PathVariable int year,
            @ApiParam("Month when the incident was first seen") @PathVariable int month,
            @ApiParam("Incident id") @PathVariable String incidentId,
            @ApiParam(name = "issueUrl", value = "New incident's issueUrl") @RequestParam String issueUrl) {
        IncidentIssueUrlChangeParameters incidentIssueUrlChangeParameters = new IncidentIssueUrlChangeParameters(connectionName, year, month, incidentId, issueUrl);
        this.incidentImportQueueService.setIncidentIssueUrl(incidentIssueUrlChangeParameters); // operation performed in the background, no result is returned

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}
