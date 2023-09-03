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

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.checkresults.services.models.TableComparisonResultsModel;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controller that returns the results of the most recent table comparison that was performed between the compared table and the reference table (the source of truth).
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "TableComparisonResults", description = "Controller that returns the results of the most recent table comparison that was performed between the compared table and the reference table (the source of truth).")
public class TableComparisonResultsController {
    private UserHomeContextFactory userHomeContextFactory;
    private CheckResultsDataService checkResultsDataService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param checkResultsDataService Rule results data service.
     */
    @Autowired
    public TableComparisonResultsController(UserHomeContextFactory userHomeContextFactory,
                                            CheckResultsDataService checkResultsDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.checkResultsDataService = checkResultsDataService;
    }

    /**
     * Retrieves the results of the most table comparison performed using the profiling checks comparison checks.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return The results of the most recent table comparison.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/comparisons/{tableComparisonConfigurationName}/results", produces = "application/json")
    @ApiOperation(value = "getTableComparisonProfilingResults", notes = "Retrieves the results of the most table comparison performed using the profiling checks comparison checks.",
            response = TableComparisonResultsModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The results of the most recent table comparison using the profiling checks on a table returned",
                    response = TableComparisonResultsModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<TableComparisonResultsModel>> getTableComparisonProfilingResults(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                physicalTableName, true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonResultsModel tableComparisonResultsModel = this.checkResultsDataService.readMostRecentTableComparisonResults(
                connectionName, physicalTableName, CheckType.profiling, null, tableComparisonConfigurationName);
        return new ResponseEntity<>(Mono.just(tableComparisonResultsModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the results of the most table comparison performed using the monitoring comparison checks.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale (daily or monthly).
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return The results of the most recent table comparison.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/comparisons/{tableComparisonConfigurationName}/results", produces = "application/json")
    @ApiOperation(value = "getTableComparisonMonitoringResults", notes = "Retrieves the results of the most table comparison performed using the monitoring comparison checks.",
            response = TableComparisonResultsModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The results of the most recent table comparison using the monitoring checks on a table returned",
                    response = TableComparisonResultsModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<TableComparisonResultsModel>> getTableComparisonMonitoringResults(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                physicalTableName, true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonResultsModel tableComparisonResultsModel = this.checkResultsDataService.readMostRecentTableComparisonResults(
                connectionName, physicalTableName, CheckType.monitoring, timeScale, tableComparisonConfigurationName);
        return new ResponseEntity<>(Mono.just(tableComparisonResultsModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the results of the most table comparison performed using the partitioned comparison checks (comparing daily or monthly partitions).
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale (daily or monthly).
     * @param tableComparisonConfigurationName Table comparison configuration name.
     * @return The results of the most recent table comparison.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/comparisons/{tableComparisonConfigurationName}/results", produces = "application/json")
    @ApiOperation(value = "getTableComparisonPartitionedResults", notes = "Retrieves the results of the most table comparison performed using the partitioned comparison checks, comparing days or months of data.",
            response = TableComparisonResultsModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The results of the most recent table comparison using the partitioned checks on a table returned",
                    response = TableComparisonResultsModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<TableComparisonResultsModel>> getTableComparisonPartitionedResults(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Table comparison configuration name") @PathVariable String tableComparisonConfigurationName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                physicalTableName, true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableComparisonResultsModel tableComparisonResultsModel = this.checkResultsDataService.readMostRecentTableComparisonResults(
                connectionName, physicalTableName, CheckType.partitioned, timeScale, tableComparisonConfigurationName);
        return new ResponseEntity<>(Mono.just(tableComparisonResultsModel), HttpStatus.OK); // 200
    }
}
