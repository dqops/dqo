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
import ai.dqo.core.incidents.IncidentStatusChangeParameters;
import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.data.incidents.services.IncidentListFilterParameters;
import ai.dqo.data.incidents.services.IncidentsDataService;
import ai.dqo.data.incidents.services.models.IncidentModel;
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
@RequestMapping("/api/incidents")
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
    @GetMapping("/{connectionName}/{year}/{month}/{incidentId}")
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
     * @param recentMonths Optional number of months to look back.
     * @param includeClosed Include also muted and resolved incidents.
     * @return List of incidents.
     */
    @GetMapping("/{connectionName}")
    @ApiOperation(value = "findRecentIncidentsOnConnection", notes = "Returns a list of recent data quality incidents.",
            response = IncidentModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IncidentModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<IncidentModel>> findRecentIncidentsOnConnection(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam(name = "Number of recent months to load, the default is 3 months", required = false)
                @PathVariable(required = false) Optional<Integer> recentMonths,
            @ApiParam(name = "Also includes closed (resolved or muted) incidents, by default only open and assigned incidents are returned", required = false)
                @PathVariable(required = false) Optional<Boolean> includeClosed,
            @ApiParam(name = "Page number, the first page is 1", required = false)
                @PathVariable(required = false) Optional<Integer> page,
            @ApiParam(name = "Page size, the default is 50 rows", required = false)
                @PathVariable(required = false) Optional<Integer> limit) {
        IncidentListFilterParameters filterParameters = new IncidentListFilterParameters();
        filterParameters.setRecentMonths(recentMonths.orElse(3));
        filterParameters.setLoadResolvedAndMutedIncidents(includeClosed.orElse(Boolean.FALSE));
        if (page.isPresent()) {
            filterParameters.setPage(page.get());
        }
        if (limit.isPresent()) {
            filterParameters.setLimit(limit.get());
        }

        Collection<IncidentModel> incidentModels = this.incidentsDataService.loadRecentIncidentsOnConnection(connectionName, filterParameters);
        if (incidentModels == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.OK);
        }

        return new ResponseEntity<>(Flux.fromStream(incidentModels.stream()), HttpStatus.OK);
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
    @PostMapping("/{connectionName}/{year}/{month}/{incidentId}")
    @ApiOperation(value = "setIncidentStatus", notes = "Changes the incident status to a new status.")
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
            @ApiParam(name = "New incident status, supported values: open, acknowledged, resolved, muted", required = true)
                @PathVariable(required = true) IncidentStatus status) {
        IncidentStatusChangeParameters incidentStatusChangeParameters = new IncidentStatusChangeParameters(connectionName, year, month, incidentId, status);
        this.incidentImportQueueService.setIncidentStatus(incidentStatusChangeParameters); // operation performed in the background, no result is returned

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}
