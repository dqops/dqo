/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.controllers;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.checkresults.models.CheckResultsOverviewDataModel;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.checkresults.services.CheckResultsOverviewParameters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Controller that returns the overview of the recent results and errors on tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "CheckResultsOverview", description = "Returns the overview of the recently executed checks on tables and columns, returning a summary of the last 5 runs.")
public class CheckResultsOverviewController {
    private UserHomeContextFactory userHomeContextFactory;
    private CheckResultsDataService checkResultsDataService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param checkResultsDataService Rule results data service.
     */
    @Autowired
    public CheckResultsOverviewController(UserHomeContextFactory userHomeContextFactory,
                                          CheckResultsDataService checkResultsDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.checkResultsDataService = checkResultsDataService;
    }

    /**
     * Retrieves the overview of the most recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param principal      Principal that identifies the calling user.
     * @param category       Optional check category filter.
     * @param checkName      Optional check name filter.
     * @param resultsCount   Optional number of results to return.
     * @return Overview of the most recent check results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/overview", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksOverview", notes = "Returns an overview of the most recent check executions for all table level data quality profiling checks on a table",
            response = CheckResultsOverviewDataModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Overview of the most recent check runs for table level data quality profiling checks on a table returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsOverviewDataModel>>> getTableProfilingChecksOverview(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "category", value = "Optional check category", required = false)
            @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "checkName", value = "Optional check name", required = false)
            @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "resultsCount", value = "Optional number of recent results to return. The default value is 5.", required = false)
            @RequestParam(required = false) Optional<Integer> resultsCount) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false);

            CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
            checkResultsOverviewParameters.setCategory(category.orElse(null));
            checkResultsOverviewParameters.setCheckName(checkName.orElse(null));
            checkResultsOverviewParameters.setResultsCount(resultsCount.orElse(CheckResultsOverviewParameters.DEFAULT_RESULTS_COUNT));

            CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.checkResultsDataService.readMostRecentCheckStatuses(
                    checks, checkResultsOverviewParameters, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the overview of the most recent monitoring executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param principal      Principal that identifies the calling user.
     * @param category       Optional check category filter.
     * @param checkName      Optional check name filter.
     * @param resultsCount   Optional number of results to return.
     * @return Overview of the most recent monitoring results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/overview", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksOverview", notes = "Returns an overview of the most recent table level monitoring executions for the monitoring at a requested time scale",
            response = CheckResultsOverviewDataModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent monitoring executions for the monitoring at a requested time scale on a table returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsOverviewDataModel>>> getTableMonitoringChecksOverview(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "category", value = "Optional check category", required = false)
            @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "checkName", value = "Optional check name", required = false)
            @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "resultsCount", value = "Optional number of recent results to return. The default value is 5.", required = false)
            @RequestParam(required = false) Optional<Integer> resultsCount) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            AbstractRootChecksContainerSpec checkRootContainer = tableSpec.getTableCheckRootContainer(CheckType.monitoring, timeScale, false);

            CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
            checkResultsOverviewParameters.setCategory(category.orElse(null));
            checkResultsOverviewParameters.setCheckName(checkName.orElse(null));
            checkResultsOverviewParameters.setResultsCount(resultsCount.orElse(CheckResultsOverviewParameters.DEFAULT_RESULTS_COUNT));

            CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.checkResultsDataService.readMostRecentCheckStatuses(
                    checkRootContainer, checkResultsOverviewParameters, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the overview of the most recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param principal      Principal that identifies the calling user.
     * @param category       Optional check category filter.
     * @param checkName      Optional check name filter.
     * @param resultsCount   Optional number of results to return.
     * @return Overview of the most recent partitioned checks results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/overview", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksOverview", notes = "Returns an overview of the most recent table level partitioned checks executions for a requested time scale",
            response = CheckResultsOverviewDataModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent partitioned check executions for a requested time scale on a table returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsOverviewDataModel>>> getTablePartitionedChecksOverview(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "category", value = "Optional check category", required = false)
            @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "checkName", value = "Optional check name", required = false)
            @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "resultsCount", value = "Optional number of recent results to return. The default value is 5.", required = false)
            @RequestParam(required = false) Optional<Integer> resultsCount) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            AbstractRootChecksContainerSpec checkRootContainer = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);

            CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
            checkResultsOverviewParameters.setCategory(category.orElse(null));
            checkResultsOverviewParameters.setCheckName(checkName.orElse(null));
            checkResultsOverviewParameters.setResultsCount(resultsCount.orElse(CheckResultsOverviewParameters.DEFAULT_RESULTS_COUNT));

            CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.checkResultsDataService.readMostRecentCheckStatuses(
                    checkRootContainer, checkResultsOverviewParameters, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the overview of the most recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param principal      Principal that identifies the calling user.
     * @param category       Optional check category filter.
     * @param checkName      Optional check name filter.
     * @param resultsCount   Optional number of results to return.
     * @return Overview of the most recent check results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/overview", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingChecksOverview", notes = "Returns an overview of the most recent check executions for all column level data quality profiling checks on a column",
            response = CheckResultsOverviewDataModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Overview of the most recent check runs for column level data quality profiling checks on a column returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsOverviewDataModel>>> getColumnProfilingChecksOverview(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam(name = "category", value = "Optional check category", required = false)
            @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "checkName", value = "Optional check name", required = false)
            @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "resultsCount", value = "Optional number of recent results to return. The default value is 5.", required = false)
            @RequestParam(required = false) Optional<Integer> resultsCount) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
            if (columnSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);

            CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
            checkResultsOverviewParameters.setCategory(category.orElse(null));
            checkResultsOverviewParameters.setCheckName(checkName.orElse(null));
            checkResultsOverviewParameters.setResultsCount(resultsCount.orElse(CheckResultsOverviewParameters.DEFAULT_RESULTS_COUNT));

            CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.checkResultsDataService.readMostRecentCheckStatuses(
                    checks, checkResultsOverviewParameters, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the overview of the most recent monitoring executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param principal      Principal that identifies the calling user.
     * @param category       Optional check category filter.
     * @param checkName      Optional check name filter.
     * @param resultsCount   Optional number of results to return.
     * @return Overview of the most recent monitoring results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/overview", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringChecksOverview", notes = "Returns an overview of the most recent column level monitoring executions for the monitoring at a requested time scale",
            response = CheckResultsOverviewDataModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent monitoring executions for the monitoring at a requested time scale on a column returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsOverviewDataModel>>> getColumnMonitoringChecksOverview(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "category", value = "Optional check category", required = false)
            @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "checkName", value = "Optional check name", required = false)
            @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "resultsCount", value = "Optional number of recent results to return. The default value is 5.", required = false)
            @RequestParam(required = false) Optional<Integer> resultsCount) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
            if (columnSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            AbstractRootChecksContainerSpec checkRootContainer = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, false);

            CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
            checkResultsOverviewParameters.setCategory(category.orElse(null));
            checkResultsOverviewParameters.setCheckName(checkName.orElse(null));
            checkResultsOverviewParameters.setResultsCount(resultsCount.orElse(CheckResultsOverviewParameters.DEFAULT_RESULTS_COUNT));

            CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.checkResultsDataService.readMostRecentCheckStatuses(
                    checkRootContainer, checkResultsOverviewParameters, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the overview of the most recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param principal      Principal that identifies the calling user.
     * @param category       Optional check category filter.
     * @param checkName      Optional check name filter.
     * @param resultsCount   Optional number of results to return.
     * @return Overview of the most recent partitioned checks results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/overview", produces = "application/json")
    @ApiOperation(value = "getColumnPartitionedChecksOverview", notes = "Returns an overview of the most recent column level partitioned checks executions for a requested time scale",
            response = CheckResultsOverviewDataModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent partitioned check executions for a requested time scale on a column returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsOverviewDataModel>>> getColumnPartitionedChecksOverview(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "category", value = "Optional check category", required = false)
            @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "checkName", value = "Optional check name", required = false)
            @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "resultsCount", value = "Optional number of recent results to return. The default value is 5.", required = false)
            @RequestParam(required = false) Optional<Integer> resultsCount) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    new PhysicalTableName(schemaName, tableName), true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
            if (columnSpec == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            AbstractRootChecksContainerSpec checkRootContainer = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, false);

            CheckResultsOverviewParameters checkResultsOverviewParameters = new CheckResultsOverviewParameters();
            checkResultsOverviewParameters.setCategory(category.orElse(null));
            checkResultsOverviewParameters.setCheckName(checkName.orElse(null));
            checkResultsOverviewParameters.setResultsCount(resultsCount.orElse(CheckResultsOverviewParameters.DEFAULT_RESULTS_COUNT));

            CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.checkResultsDataService.readMostRecentCheckStatuses(
                    checkRootContainer, checkResultsOverviewParameters, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
        }));
    }
}
