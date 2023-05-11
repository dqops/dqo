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

package ai.dqo.rest.controllers;

import ai.dqo.core.incidents.IncidentImportQueueService;
import ai.dqo.core.incidents.IncidentIssueUrlChangeParameters;
import ai.dqo.core.incidents.IncidentStatusChangeParameters;
import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.data.incidents.services.models.*;
import ai.dqo.data.incidents.services.IncidentsDataService;
import ai.dqo.rest.models.metadata.ConnectionModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    /**
     * Creates an incident management service, given all used dependencies.
     * @param incidentsDataService Incident data service used to load incidents.
     * @param incidentImportQueueService Incident queued update service that updates the statuses of incidents.
     */
    @Autowired
    public IncidentsController(IncidentsDataService incidentsDataService,
                               IncidentImportQueueService incidentImportQueueService) {
        this.incidentsDataService = incidentsDataService;
        this.incidentImportQueueService = incidentImportQueueService;
    }

    /**
     * Retrieves a single incident.
     * @param connectionName Connection name.
     * @param year Year when the incident was first seen.
     * @param month Month when the incident was first seen.
     * @param incidentId Incident id.
     * @return Incident model of the loaded incident.
     */
    @GetMapping("/incidents/{connectionName}/{year}/{month}/{incidentId}")
    @ApiOperation(value = "getIncident", notes = "Return a single data quality incident's details.", response = IncidentModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Incident returned", response = ConnectionModel.class),
            @ApiResponse(code = 404, message = "Incident not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<IncidentModel>> getIncident(
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
     * Finds recent data quality incidents on a connection.
     * @param connectionName Connection name.
     * @param months Optional number of months to look back.
     * @param open Return open incidents.
     * @param acknowledged Return acknowledged incidents.
     * @param resolved Return resolved incidents.
     * @param muted Return muted incidents.
     * @param filter Additional filter.
     * @param limit Page size.
     * @param page Page number.
     * @param order Sort order.
     * @param direction Sort direction.
     * @return List of incidents.
     */
    @GetMapping("/incidents/{connectionName}")
    @ApiOperation(value = "findRecentIncidentsOnConnection", notes = "Returns a list of recent data quality incidents.",
            response = IncidentModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IncidentModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<IncidentModel>> findRecentIncidentsOnConnection(
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
            @ApiParam(name = "filter", value = "Optional filter", required = false)
                @RequestParam(required = false) Optional<String> filter,
            @ApiParam(name = "order", value = "Optional sort order, the default sort order is by the number of failed data quality checks", required = false)
                @RequestParam(required = false) Optional<IncidentSortOrder> order,
            @ApiParam(name = "direction", value = "Optional sort direction, the default sort direction is ascending", required = false)
               @RequestParam(required = false) Optional<IncidentSortDirection> direction) {
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
    @GetMapping("/incidentstat")
    @ApiOperation(value = "findConnectionIncidentStats", notes = "Returns a list of connection names with incident statistics - the count of recent open incidents.",
            response = IncidentsPerConnectionModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IncidentsPerConnectionModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<IncidentsPerConnectionModel>> findConnectionIncidentStats() {
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
    @PostMapping("/incidents/{connectionName}/{year}/{month}/{incidentId}/status")
    @ApiOperation(value = "setIncidentStatus", notes = "Changes the incident's status to a new status.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality incident's status successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> setIncidentStatus(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Year when the incident was first seen") @PathVariable int year,
            @ApiParam("Month when the incident was first seen") @PathVariable int month,
            @ApiParam("Incident id") @PathVariable String incidentId,
            @ApiParam(name = "status", value = "New incident status, supported values: open, acknowledged, resolved, muted")
                @RequestParam IncidentStatus status) {
        IncidentStatusChangeParameters incidentStatusChangeParameters = new IncidentStatusChangeParameters(connectionName, year, month, incidentId, status);
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
    @PostMapping("/incidents/{connectionName}/{year}/{month}/{incidentId}/issueurl")
    @ApiOperation(value = "setIncidentIssueUrl", notes = "Changes the incident's issueUrl to a new status.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality incident's issueUrl successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> setIncidentIssueUrl(
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
