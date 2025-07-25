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
import com.dqops.data.checkresults.models.CheckResultsListModel;
import com.dqops.data.checkresults.models.HistogramFilterParameters;
import com.dqops.data.checkresults.models.IssueHistogramModel;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusFilterParameters;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.services.CheckResultsDataService;
import com.dqops.data.checkresults.services.CheckResultsDetailedFilterParameters;
import com.dqops.data.checkresults.services.CheckResultsDetailedLoadMode;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Controller over the check results on tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "CheckResults", description = "Returns all the data quality check results of executed checks on tables and columns.")
public class CheckResultsController {
    private UserHomeContextFactory userHomeContextFactory;
    private CheckResultsDataService checkResultsDataService;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param checkResultsDataService Rule results data service.
     * @param defaultTimeZoneProvider Default time zone provider.
     */
    @Autowired
    public CheckResultsController(UserHomeContextFactory userHomeContextFactory,
                                  CheckResultsDataService checkResultsDataService,
                                  DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.checkResultsDataService = checkResultsDataService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Read the most recent results of executed data quality checks on the table and return the current table's data quality status - the number of failed data quality
     * checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently.
     * This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param months         The number of months to load.
     * @param profiling      Optional check type filter to return profiling checks.
     * @param monitoring     Optional check type filter to return monitoring checks.
     * @param partitioned    Optional check type filter to return partitioned checks.
     * @param checkTimeScale Optional check time scale filter.
     * @return The most recent table's data quality status.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/status", produces = "application/json")
    @ApiOperation(value = "getTableDataQualityStatus", notes = "Read the most recent results of executed data quality checks on the table and return the current table's data quality status - the number of failed data quality " +
            "checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently. " +
            "This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.",
            response = TableCurrentDataQualityStatusModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The most recent data quality status of the requested table",
                    response = TableCurrentDataQualityStatusModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableCurrentDataQualityStatusModel>>> getTableDataQualityStatus(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "months", value = "Optional filter - the number of months to review the data quality check results. For partitioned checks, it is the number of months to analyze. The default value is 1 (which is the current month and 1 previous month).", required = false)
            @RequestParam(required = false) Optional<Integer> months,
            @ApiParam(name = "since", value = "Optional filter that accepts an UTC timestamp to read only data quality check results captured since that timestamp.", required = false)
            @RequestParam(required = false) Optional<LocalDateTime> since,
            @ApiParam(name = "profiling", value = "Optional check type filter to detect the current status of the profiling checks results. " +
                    "The default value is false, excluding profiling checks from the current table status detection. " +
                    "If enabled, only the status of the most recent check result is retrieved.", required = false)
            @RequestParam(required = false) Optional<Boolean> profiling,
            @ApiParam(name = "monitoring", value = "Optional check type filter to detect the current status of the monitoring checks results. " +
                    "The default value is true, including monitoring checks in the current table status detection. " +
                    "If enabled, only the status of the most recent check result is retrieved.", required = false)
            @RequestParam(required = false) Optional<Boolean> monitoring,
            @ApiParam(name = "partitioned", value = "Optional check type filter to detect the current status of the partitioned checks results. " +
                    "The default value is true, including partitioned checks in the current table status detection. " +
                    "Detection of the status of partitioned checks is different. When enabled, DQOps checks the highest severity status of all partitions since the **since** date or within the last **months**.", required = false)
            @RequestParam(required = false) Optional<Boolean> partitioned,
            @ApiParam(name = "checkTimeScale", value = "Optional time scale filter for monitoring and partitioned checks (values: daily or monthly).", required = false)
            @RequestParam(required = false) Optional<CheckTimeScale> checkTimeScale,
            @ApiParam(name = "dataGroup", value = "Optional data group", required = false)
            @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "checkName", value = "Optional check name", required = false)
            @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Optional check category name", required = false)
            @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Optional table comparison name", required = false)
            @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "qualityDimension", value = "Optional data quality dimension", required = false)
            @RequestParam(required = false) Optional<String> qualityDimension) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
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

            TableCurrentDataQualityStatusFilterParameters tableCurrentDataQualityStatusFilterParameters = TableCurrentDataQualityStatusFilterParameters.builder()
                    .connectionName(connectionName)
                    .physicalTableName(physicalTableName)
                    .lastMonths(months.orElse(1))
                    .profiling(profiling.orElse(false))
                    .monitoring(monitoring.orElse(true))
                    .partitioned(partitioned.orElse(true))
                    .checkTimeScale(checkTimeScale.orElse(null))
                    .dataGroup(dataGroup.orElse(null))
                    .checkName(checkName.orElse(null))
                    .category(category.orElse(null))
                    .tableComparison(tableComparison.orElse(null))
                    .qualityDimension(qualityDimension.orElse(null))
                    .since(since.isPresent() ? since.get().toInstant(this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(since.get())) : null)
                    .build();

            TableCurrentDataQualityStatusModel tableCurrentDataQualityStatusModel = this.checkResultsDataService
                    .analyzeTableMostRecentQualityStatus(tableCurrentDataQualityStatusFilterParameters, principal.getDataDomainIdentity());

            return new ResponseEntity<>(Mono.just(tableCurrentDataQualityStatusModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the complete results of the recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent check results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/results", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksResults", notes = "Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table",
            response = CheckResultsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for table level data quality profiling checks on a table returned",
                    response = CheckResultsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsListModel>>> getTableProfilingChecksResults(
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
            @ApiParam(name = "loadMode", value = "Results load mode", required = false) @RequestParam(required = false) Optional<CheckResultsDetailedLoadMode> loadMode,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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
            CheckResultsDetailedFilterParameters loadParams = new CheckResultsDetailedFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);
            loadMode.ifPresent(loadParams::setLoadMode);

            CheckResultsListModel[] checkResultsListModels = this.checkResultsDataService.readCheckStatusesDetailed(
                    checks, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the complete results of the most recent monitoring executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataGroup Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent monitoring results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/results", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringChecksResults", notes = "Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale",
            response = CheckResultsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent monitoring executions for the monitoring at a requested time scale on a table returned",
                    response = CheckResultsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsListModel>>> getTableMonitoringChecksResults(
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
            @ApiParam(name = "loadMode", value = "Results load mode", required = false) @RequestParam(required = false) Optional<CheckResultsDetailedLoadMode> loadMode,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

            AbstractRootChecksContainerSpec monitoringPartition = tableSpec.getTableCheckRootContainer(CheckType.monitoring, timeScale, false);
            CheckResultsDetailedFilterParameters loadParams = new CheckResultsDetailedFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);
            loadMode.ifPresent(loadParams::setLoadMode);

            CheckResultsListModel[] checkResultsListModels = this.checkResultsDataService.readCheckStatusesDetailed(
                    monitoringPartition, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the complete view of the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataGroup Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the most recent partitioned checks results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/results", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksResults", notes = "Returns a complete view of the recent table level partitioned checks executions for a requested time scale",
            response = CheckResultsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the most recent partitioned check executions for a requested time scale on a table returned",
                    response = CheckResultsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsListModel>>> getTablePartitionedChecksResults(
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
            @ApiParam(name = "loadMode", value = "Results load mode", required = false) @RequestParam(required = false) Optional<CheckResultsDetailedLoadMode> loadMode,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

            AbstractRootChecksContainerSpec partitionedCheckPartition = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);
            CheckResultsDetailedFilterParameters loadParams = new CheckResultsDetailedFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);
            loadMode.ifPresent(loadParams::setLoadMode);

            CheckResultsListModel[] checkResultsListModels = this.checkResultsDataService.readCheckStatusesDetailed(
                    partitionedCheckPartition, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsListModels), HttpStatus.OK); // 200
        }));
    }


    /**
     * Retrieves the complete results of the recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataGroup Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent check results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/results", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingChecksResults", notes = "Returns an overview of the most recent check executions for all column level data quality profiling checks on a column",
            response = CheckResultsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for column level data quality profiling checks on a column returned",
                    response = CheckResultsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsListModel>>> getColumnProfilingChecksResults(
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
            @ApiParam(name = "loadMode", value = "Results load mode", required = false) @RequestParam(required = false) Optional<CheckResultsDetailedLoadMode> loadMode,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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
            CheckResultsDetailedFilterParameters loadParams = new CheckResultsDetailedFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);
            loadMode.ifPresent(loadParams::setLoadMode);

            CheckResultsListModel[] checkResultsListModels = this.checkResultsDataService.readCheckStatusesDetailed(
                    checks, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the complete view of the most recent monitoring executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataGroup Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent monitoring results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/results", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringChecksResults", notes = "Returns a complete view of the recent column level monitoring executions for the monitoring at a requested time scale",
            response = CheckResultsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent monitoring executions for the monitoring at a requested time scale on a column returned",
                    response = CheckResultsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsListModel>>> getColumnMonitoringChecksResults(
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
            @ApiParam(name = "loadMode", value = "Results load mode", required = false) @RequestParam(required = false) Optional<CheckResultsDetailedLoadMode> loadMode,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

            AbstractRootChecksContainerSpec monitoringPartition = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, false);
            CheckResultsDetailedFilterParameters loadParams = new CheckResultsDetailedFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);
            loadMode.ifPresent(loadParams::setLoadMode);

            CheckResultsListModel[] checkResultsListModels = this.checkResultsDataService.readCheckStatusesDetailed(
                    monitoringPartition, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the complete view of recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group (optional).
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/results", produces = "application/json")
    @ApiOperation(value = "getColumnPartitionedChecksResults", notes = "Returns an overview of the most recent column level partitioned checks executions for a requested time scale",
            response = CheckResultsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent partitioned check executions for a requested time scale on a column returned",
                    response = CheckResultsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CheckResultsListModel>>> getColumnPartitionedChecksResults(
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
            @ApiParam(name = "loadMode", value = "Results load mode", required = false) @RequestParam(required = false) Optional<CheckResultsDetailedLoadMode> loadMode,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

            AbstractRootChecksContainerSpec partitionedCheckPartition = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, false);
            CheckResultsDetailedFilterParameters loadParams = new CheckResultsDetailedFilterParameters();
            checkName.ifPresent(loadParams::setCheckName);
            category.ifPresent(loadParams::setCheckCategory);
            tableComparison.ifPresent(loadParams::setTableComparison);
            dataGroup.ifPresent(loadParams::setDataGroupName);
            monthStart.ifPresent(loadParams::setStartMonth);
            monthEnd.ifPresent(loadParams::setEndMonth);
            maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);
            loadMode.ifPresent(loadParams::setLoadMode);

            CheckResultsListModel[] checkResultsListModels = this.checkResultsDataService.readCheckStatusesDetailed(
                    partitionedCheckPartition, loadParams, principal.getDataDomainIdentity());
            return new ResponseEntity<>(Flux.fromArray(checkResultsListModels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Generates a histogram of data quality issues for each day on a given table., returning the number of data quality issues on that day.
     * The other histograms are by a column name and by a check name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @param filter Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions.
     * @param days Optional filter for a number of recent days to read the related issues.
     * @param date Optional date filter.
     * @param column Optional column name filter.
     * @param check Optional check name filter.
     * @return Incident histogram of data quality issues.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/histogram", produces = "application/json")
    @ApiOperation(value = "getTableIssuesHistogram", notes = "Generates a histograms of data quality issues for each day on a table, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.",
            response = IssueHistogramModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Issue histograms for a table returned", response = IssueHistogramModel.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<IssueHistogramModel>>> getTableIssuesHistogram(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "executedSince", value = "Optional date filter to find issues returned since that date. When missing, loads issues executed during the current and previous months", required = false)
            @RequestParam(required = false) Optional<LocalDate> executedSince,
            @ApiParam(name = "filter", value = "Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions", required = false)
            @RequestParam(required = false) Optional<String> filter,
            @ApiParam(name = "days", value = "Optional filter for a number of recent days to read the related issues", required = false)
            @RequestParam(required = false) Optional<Integer> days,
            @ApiParam(name = "date", value = "Optional date filter to select one day", required = false)
            @RequestParam(required = false) Optional<LocalDate> date,
            @ApiParam(name = "column", value = "Optional column name filter", required = false)
            @RequestParam(required = false) Optional<String> column,
            @ApiParam(name = "check", value = "Optional check name filter", required = false)
            @RequestParam(required = false) Optional<String> check,
            @ApiParam(name = "checkType", value = "Optional check type filter, when not provided, returns a combined histogram of monitoring and partition checks", required = false)
            @RequestParam(required = false) Optional<CheckType> checkType) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(physicalTableName, true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableSpec tableSpec = tableWrapper.getSpec();
            if (tableSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            HistogramFilterParameters filterParameters = new HistogramFilterParameters();
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
            if (checkType.isPresent()) {
                filterParameters.setCheckType(checkType.get());
            }

            LocalDate executedSinceLocalDate = executedSince.orElseGet(() -> LocalDate.now().withDayOfMonth(1).minusMonths(1L));
            Instant executedAtSince = executedSinceLocalDate.atStartOfDay(this.defaultTimeZoneProvider.getDefaultTimeZoneId()).toInstant();

            IssueHistogramModel histogramModel = this.checkResultsDataService.buildDailyIssuesHistogram(
                    connectionName,
                    physicalTableName,
                    null,
                    executedAtSince,
                    null,
                    1,
                    filterParameters,
                    principal.getDataDomainIdentity());

            if (histogramModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            return new ResponseEntity<>(Mono.just(histogramModel), HttpStatus.OK); // 200
        }));
    }
}
