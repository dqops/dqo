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

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.errorsamples.models.ErrorSamplesListModel;
import com.dqops.data.errorsamples.services.ErrorSamplesDataService;
import com.dqops.data.errorsamples.services.ErrorSamplesFilterParameters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Controller over the error samples on tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "ErrorSamples", description = "Operations that return the error samples collected when data quality checks were executed on data sources from the check editor, and rules failed with an error.")
public class ErrorSamplesController {
    private UserHomeContextFactory userHomeContextFactory;
    private ErrorSamplesDataService errorSamplesDataService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param errorSamplesDataService Error samples data service.
     */
    @Autowired
    public ErrorSamplesController(UserHomeContextFactory userHomeContextFactory,
                                  ErrorSamplesDataService errorSamplesDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.errorSamplesDataService = errorSamplesDataService;
    }

    /**
     * Retrieves the error samples related to check on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent error samples.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/errorsamples", produces = "application/json")
    @ApiOperation(value = "getTableProfilingErrorSamples", notes = "Returns the error samples related to a check for all table level data quality profiling checks on a table",
            response = ErrorSamplesListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Error samples related to the check for table level data quality profiling checks on a table returned",
                    response = ErrorSamplesListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ErrorSamplesListModel>>> getTableProfilingErrorSamples(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    ErrorSamplesFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
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
            ErrorSamplesFilterParameters loadParams = new ErrorSamplesFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

            ErrorSamplesListModel[] errorSamplesListModels = this.errorSamplesDataService.readErrorSamplesDetailed(
                    checks, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(errorSamplesListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the error samples related to a check on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent error samples.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/errorsamples", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringErrorSamples", notes = "Returns the error samples related to a table level monitoring check a requested time scale",
            response = ErrorSamplesListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Error samples related to a monitoring check at a requested time scale on a table returned",
                    response = ErrorSamplesListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ErrorSamplesListModel>>> getTableMonitoringErrorSamples(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    ErrorSamplesFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
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

            AbstractRootChecksContainerSpec monitoring = tableSpec.getTableCheckRootContainer(CheckType.monitoring, timeScale, false);
            ErrorSamplesFilterParameters loadParams = new ErrorSamplesFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

            ErrorSamplesListModel[] errorsListModels = this.errorSamplesDataService.readErrorSamplesDetailed(
                    monitoring, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(errorsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the error samples related to a partitioned check on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the error samples related to a partitioned check.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/errorsamples", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedErrorSamples", notes = "Returns error samples related to a table level partitioned check for a requested time scale",
            response = ErrorSamplesListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The error samples related to a partitioned check a requested time scale on a table returned",
                    response = ErrorSamplesListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ErrorSamplesListModel>>> getTablePartitionedErrorSamples(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    ErrorSamplesFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
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

            AbstractRootChecksContainerSpec Partitioned = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);
            ErrorSamplesFilterParameters loadParams = new ErrorSamplesFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

            ErrorSamplesListModel[] errorsListModels = this.errorSamplesDataService.readErrorSamplesDetailed(
                    Partitioned, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(errorsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the error samples related to a profiling check on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent error samples.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/errorsamples", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingErrorSamples", notes = "Returns the error samples related to a profiling check for all column level data quality profiling checks on a column",
            response = ErrorSamplesListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Error samples related to a check runs for column level data quality profiling checks on a column returned",
                    response = ErrorSamplesListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ErrorSamplesListModel>>> getColumnProfilingErrorSamples(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    ErrorSamplesFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
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
            ErrorSamplesFilterParameters loadParams = new ErrorSamplesFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

            ErrorSamplesListModel[] errorsListModels = this.errorSamplesDataService.readErrorSamplesDetailed(
                    checks, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(errorsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the error samples related to monitoring checks on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent error samples.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/errorsamples", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringErrorSamples", notes = "Returns error samples related to a column level monitoring checks at a requested time scale",
            response = ErrorSamplesListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of error samples for the monitoring checks at a requested time scale on a column returned",
                    response = ErrorSamplesListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ErrorSamplesListModel>>> getColumnMonitoringErrorSamples(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    ErrorSamplesFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
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

            AbstractRootChecksContainerSpec monitoring = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, false);
            ErrorSamplesFilterParameters loadParams = new ErrorSamplesFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

            ErrorSamplesListModel[] errorsListModels = this.errorSamplesDataService.readErrorSamplesDetailed(
                    monitoring, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(errorsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the error samples related to column level partitioned checks on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of error samples related to the recent partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/errorsamples", produces = "application/json")
    @ApiOperation(value = "getColumnPartitionedErrorSamples", notes = "Returns the error samples related to column level partitioned checks for a requested time scale",
            response = ErrorSamplesListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of error samples related to column level partitioned checks for a requested time scale on a column returned",
                    response = ErrorSamplesListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ErrorSamplesListModel>>> getColumnPartitionedErrorSamples(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    ErrorSamplesFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
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

            AbstractRootChecksContainerSpec Partitioned = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, false);
            ErrorSamplesFilterParameters loadParams = new ErrorSamplesFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

            ErrorSamplesListModel[] errorsListModels = this.errorSamplesDataService.readErrorSamplesDetailed(
                    Partitioned, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(errorsListModels), HttpStatus.OK); // 200
        }));
    }
}
