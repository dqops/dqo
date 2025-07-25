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

import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.metadata.lineage.TableLineageSource;
import com.dqops.metadata.lineage.TableLineageSourceSpec;
import com.dqops.metadata.lineage.TableLineageSourceSpecList;
import com.dqops.metadata.lineage.lineagecache.TableLineageCache;
import com.dqops.metadata.lineage.lineagecache.TableLineageCacheEntry;
import com.dqops.metadata.lineage.lineagecache.TableLineageRefreshStatus;
import com.dqops.metadata.lineage.lineageservices.TableLineageModel;
import com.dqops.metadata.lineage.lineageservices.TableLineageService;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.TableLineageTableListModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.locking.RestApiLockService;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * REST API controller for managing and inspecting the data lineage between tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DataLineage", description = "Operations related to managing and inspecting table and column lineage.")
@Slf4j
public class DataLineageController {
    private final UserHomeContextFactory userHomeContextFactory;
    private final RestApiLockService lockService;
    private final TableStatusCache tableStatusCache;
    private final TableLineageService tableLineageService;
    private final TableLineageCache tableLineageCache;

    /**
     * Default dependency injection constructor.
     *
     * @param userHomeContextFactory DQOps user home factory.
     * @param lockService            REST API lock service to avoid updating the same table yaml in two threads.
     * @param tableStatusCache       The cache of last known data quality status for a table.
     * @param tableLineageService    Table lineage service to return the current table lineage.
     * @param tableLineageCache      Table data lineage service to find target tables.
     */
    @Autowired
    public DataLineageController(
            UserHomeContextFactory userHomeContextFactory,
            RestApiLockService lockService,
            TableStatusCache tableStatusCache,
            TableLineageService tableLineageService,
            TableLineageCache tableLineageCache) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.lockService = lockService;
        this.tableStatusCache = tableStatusCache;
        this.tableLineageService = tableLineageService;
        this.tableLineageCache = tableLineageCache;
    }

    /**
     * Returns a data lineage around the given table.
     * @param principal      User principal.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @param upstream       Collect upstream tables.
     * @param downstream     Collect downstream tables.
     * @return Returns a data lineage around the given table, including the table quality statues.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/tree", produces = "application/json")
    @ApiOperation(value = "getTableDataLineageGraph", notes = "Returns a data lineage graph around the given table.", response = TableLineageModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableLineageModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableLineageModel>>> getTableDataLineageGraph(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "upstream", value = "Optional parameter to request upstream tables. By default, upstream tables are collected unless it is disabled by passing 'false'.", required = false)
            @RequestParam(required = false) Optional<Boolean> upstream,
            @ApiParam(name = "downstream", value = "Optional parameter to request downstream tables. By default, downstream tables are collected unless it is disabled by passing 'false'.", required = false)
            @RequestParam(required = false) Optional<Boolean> downstream) {
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

            String dataDomain = principal.getDataDomainIdentity().getDataDomainCloud();
            DomainConnectionTableKey targetTableKey = new DomainConnectionTableKey(dataDomain, connectionName, physicalTableName);
            TableLineageModel tableLineageModel = this.tableLineageService.buildDataLineageModel(
                    userHome, targetTableKey, upstream.orElse(true), downstream.orElse(true));

            return new ResponseEntity<>(Mono.justOrEmpty(tableLineageModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns a list of source tables on the data lineage that are sources of the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of source tables on the data lineage that are sources of the given table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources", produces = "application/json")
    @ApiOperation(value = "getTableSourceTables", notes = "Returns a list of source tables on the data lineage that are sources of the given table.", response = TableLineageTableListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableLineageTableListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<TableLineageTableListModel>>> getTableSourceTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "checkType", value = "Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type", required = false)
            @RequestParam(required = false) Optional<CheckType> checkType) {
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
            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

            if (tableSpec.getSourceTables() == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.OK); // 200 - empty
            }

            List<TableLineageTableListModel> sourceTables = tableSpec.getSourceTables()
                    .stream()
                    .map(sourceSpec -> TableLineageTableListModel.fromSpecification(
                            sourceSpec, isEditor))
                    .collect(Collectors.toList());

            sourceTables.forEach(listModel -> {
                TableCurrentDataQualityStatusModel notFoundTableStatus = new TableCurrentDataQualityStatusModel(){{
                    setDataDomain(principal.getDataDomainIdentity().getDataDomainCloud());
                    setConnectionName(listModel.getSourceConnection());
                    setSchemaName(listModel.getSourceSchema());
                    setTableName(listModel.getSourceTable());
                    setTableExist(false);
                }};
                ConnectionWrapper sourceConnectionWrapper = connections.getByObjectName(listModel.getSourceConnection(), true);
                if (sourceConnectionWrapper == null) {
                    listModel.setTableDataQualityStatus(notFoundTableStatus);
                    return;
                }
                TableWrapper sourceTableWrapper = sourceConnectionWrapper.getTables().getByObjectName(
                        new PhysicalTableName(listModel.getSourceSchema(), listModel.getSourceTable()), true);
                if (sourceTableWrapper == null) {
                    listModel.setTableDataQualityStatus(notFoundTableStatus);
                    return;
                }

                DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                        listModel.getSourceConnection(), new PhysicalTableName(listModel.getSourceSchema(), listModel.getSourceTable()));
                TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                listModel.setTableDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
            });

            if (sourceTables.stream().anyMatch(model -> model.getTableDataQualityStatus() == null)) {
                // the results not loaded yet, we need to wait until the queue is empty
                CompletableFuture<Boolean> waitForLoadTasksFuture = this.tableStatusCache.getQueueEmptyFuture(TableStatusCache.EMPTY_QUEUE_WAIT_TIMEOUT_MS);

                Flux<TableLineageTableListModel> resultListFilledWithDelay = Mono.fromFuture(waitForLoadTasksFuture)
                        .thenMany(Flux.fromIterable(sourceTables)
                                .map(tableListModel -> {
                                    if (tableListModel.getTableDataQualityStatus() == null) {
                                        DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                                                tableListModel.getSourceConnection(), new PhysicalTableName(tableListModel.getSourceSchema(), tableListModel.getSourceTable()));
                                        TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                                        tableListModel.setTableDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
                                    }
                                    return tableListModel;
                                }));

                return new ResponseEntity<>(resultListFilledWithDelay, HttpStatus.OK); // 200
            }

            return new ResponseEntity<>(Flux.fromStream(sourceTables.stream()), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns a list of target tables on the data lineage that are targets of the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of target tables on the data lineage that are downstream tables of the given table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/targets", produces = "application/json")
    @ApiOperation(value = "getTableTargetTables", notes = "Returns a list of target tables on the data lineage that are downstream tables of the given table.", response = TableLineageTableListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableLineageTableListModel[].class),
            @ApiResponse(code = 203, message = "The list of target tables returned, but it is probably not complete", response = TableLineageTableListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<TableLineageTableListModel>>> getTableTargetTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "checkType", value = "Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type", required = false)
            @RequestParam(required = false) Optional<CheckType> checkType) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                    physicalTableName, true);
            if (tableWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

            String dataDomain = principal.getDataDomainIdentity().getDataDomainCloud();
            TableLineageCacheEntry tableLineageEntry = this.tableLineageCache.getTableLineageEntry(
                    new DomainConnectionTableKey(dataDomain, connectionName, physicalTableName));

            if (tableLineageEntry == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NON_AUTHORITATIVE_INFORMATION); // 203 - partial
            }
            HttpStatus returnStatus = tableLineageEntry.getStatus() != TableLineageRefreshStatus.LOADED ?
                    HttpStatus.NON_AUTHORITATIVE_INFORMATION : HttpStatus.OK;

            Set<DomainConnectionTableKey> downstreamTargetTables = tableLineageEntry.getDownstreamTargetTables();
            List<TableLineageTableListModel> targetTables = downstreamTargetTables
                    .stream()
                    .map(tableKey -> {
                        ConnectionWrapper targetConnectionWrapper = connections.getByObjectName(tableKey.getConnectionName(), true);
                        if (targetConnectionWrapper == null) {
                            return null;
                        }

                        TableWrapper targetTableWrapper = targetConnectionWrapper.getTables().getByObjectName(tableKey.getPhysicalTableName(), true);
                        if (targetTableWrapper == null || targetTableWrapper.getSpec() == null) {
                            return null;
                        }

                        TableSpec targetTableSpec = targetTableWrapper.getSpec();
                        if (targetTableSpec.getSourceTables() == null || targetTableSpec.getSourceTables().isEmpty()) {
                            return null;
                        }

                        Optional<TableLineageSourceSpec> tableLineageFromSource = targetTableSpec.getSourceTables().stream()
                                .filter(sourceSpec -> Objects.equals(connectionName, sourceSpec.getSourceConnection()) &&
                                        Objects.equals(schemaName, sourceSpec.getSourceSchema()) &&
                                        Objects.equals(tableName, sourceSpec.getSourceTable()))
                                .findFirst();

                        return tableLineageFromSource.orElse(null);
                    })
                    .filter(sourceLineage -> sourceLineage != null)
                    .map(sourceSpec -> TableLineageTableListModel.fromSpecification(
                            sourceSpec, isEditor))
                    .collect(Collectors.toList());

            targetTables.forEach(listModel -> {
                TableCurrentDataQualityStatusModel notFoundTableStatus = new TableCurrentDataQualityStatusModel(){{
                    setDataDomain(dataDomain);
                    setConnectionName(listModel.getSourceConnection());
                    setSchemaName(listModel.getSourceSchema());
                    setTableName(listModel.getSourceTable());
                    setTableExist(false);
                }};
                ConnectionWrapper sourceConnectionWrapper = connections.getByObjectName(listModel.getSourceConnection(), true);
                if (sourceConnectionWrapper == null) {
                    listModel.setTableDataQualityStatus(notFoundTableStatus);
                    return;
                }
                TableWrapper sourceTableWrapper = connectionWrapper.getTables().getByObjectName(
                        new PhysicalTableName(listModel.getSourceSchema(), listModel.getSourceTable()), true);
                if (sourceTableWrapper == null) {
                    listModel.setTableDataQualityStatus(notFoundTableStatus);
                    return;
                }

                DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(dataDomain,
                        listModel.getSourceConnection(), new PhysicalTableName(listModel.getSourceSchema(), listModel.getSourceTable()));
                TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                listModel.setTableDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
            });

            if (targetTables.stream().anyMatch(model -> model.getTableDataQualityStatus() == null)) {
                // the results not loaded yet, we need to wait until the queue is empty
                CompletableFuture<Boolean> waitForLoadTasksFuture = this.tableStatusCache.getQueueEmptyFuture(TableStatusCache.EMPTY_QUEUE_WAIT_TIMEOUT_MS);

                Flux<TableLineageTableListModel> resultListFilledWithDelay = Mono.fromFuture(waitForLoadTasksFuture)
                        .thenMany(Flux.fromIterable(targetTables)
                                .map(tableListModel -> {
                                    if (tableListModel.getTableDataQualityStatus() == null) {
                                        DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(dataDomain,
                                                tableListModel.getSourceConnection(), new PhysicalTableName(tableListModel.getSourceSchema(), tableListModel.getSourceTable()));
                                        TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                                        tableListModel.setTableDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
                                    }
                                    return tableListModel;
                                }));

                HttpStatus mostRecentReturnStatus = tableLineageEntry.getStatus() != TableLineageRefreshStatus.LOADED ?
                        HttpStatus.NON_AUTHORITATIVE_INFORMATION : HttpStatus.OK;
                return new ResponseEntity<>(resultListFilledWithDelay, mostRecentReturnStatus); // 200 or 203
            }

            return new ResponseEntity<>(Flux.fromStream(targetTables.stream()), returnStatus); // 200 or 203
        }));
    }

    /**
     * Returns a specific data lineage source table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name
     * @param sourceConnection              Source connection name.
     * @param sourceSchema                  Source schema name.
     * @param sourceTable                   Source table name.
     * @return Source table specification.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}", produces = "application/json")
    @ApiOperation(value = "getTableSourceTable", notes = "Reads a specific data lineage source table defined on a target tale.", response = TableLineageSourceSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table basic information returned", response = TableLineageSourceSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or table lineage not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<TableLineageSourceSpec>>> getTableSourceTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Source connection name") @PathVariable String sourceConnection,
            @ApiParam("Source schema name") @PathVariable String sourceSchema,
            @ApiParam("Source table name") @PathVariable String sourceTable) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName) ||
                    Strings.isNullOrEmpty(sourceConnection) ||
                    Strings.isNullOrEmpty(sourceSchema) ||
                    Strings.isNullOrEmpty(sourceTable)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);

            TableLineageSourceSpecList sourceTables = readTableLineageSourceSpecList(userHomeContext, connectionName, schemaName, tableName, false);
            if (sourceTables == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            TableLineageSource tableLineageSourceKey = new TableLineageSource(sourceConnection, sourceSchema, sourceTable);
            TableLineageSourceSpec tableLineageSourceSpec = sourceTables.getByObjectName(tableLineageSourceKey, false);

            if (tableLineageSourceSpec == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            userHomeContext.flush();
            return new ResponseEntity<>(Mono.justOrEmpty(tableLineageSourceSpec), HttpStatus.OK); // 200
        }));
    }


    /**
     * Update a specific data lineage source table using a new model.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name
     * @param sourceConnection              Source connection name.
     * @param sourceSchema                  Source schema name.
     * @param sourceTable                   Source table name.
     * @param updatedSourceTableLineage   The model object.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}",
            consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableSourceTable", notes = "Update a specific data lineage source table using a new model.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's source table successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection or table's source table not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Table's source table with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateTableSourceTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Source connection name") @PathVariable String sourceConnection,
            @ApiParam("Source schema name") @PathVariable String sourceSchema,
            @ApiParam("Source table name") @PathVariable String sourceTable,
            @ApiParam("Table lineage source list model") @RequestBody TableLineageSourceSpec updatedSourceTableLineage) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName) ||
                    Strings.isNullOrEmpty(sourceConnection) ||
                    Strings.isNullOrEmpty(sourceSchema) ||
                    Strings.isNullOrEmpty(sourceTable) ||
                    updatedSourceTableLineage == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName), () -> {
                UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);

                TableLineageSourceSpecList sourceTables = readTableLineageSourceSpecList(userHomeContext, connectionName, schemaName, tableName, true);
                if (sourceTables == null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                TableLineageSource tableLineageSourceKey = new TableLineageSource(sourceConnection, sourceSchema, sourceTable);
                TableLineageSourceSpec currentTableLineageSourceSpec = sourceTables.getByObjectName(tableLineageSourceKey, false);

                if (currentTableLineageSourceSpec == null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                if (!Objects.equals(sourceConnection, updatedSourceTableLineage.getSourceConnection()) ||
                        !Objects.equals(sourceSchema, updatedSourceTableLineage.getSourceSchema()) ||
                        !Objects.equals(sourceTable, updatedSourceTableLineage.getSourceTable())
                ) {
                    return new ResponseEntity<>(Mono.empty(),
                            HttpStatus.NOT_ACCEPTABLE); // 406 - wrong values
                }


                int indexOfOldLineage = sourceTables.indexOf(currentTableLineageSourceSpec);
                sourceTables.remove(currentTableLineageSourceSpec);
                sourceTables.add(indexOfOldLineage, updatedSourceTableLineage);

                userHomeContext.flush();
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            });
        }));
    }

    /**
     * Creates (adds) a new source table of the table's data lineage.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name
     * @param sourceConnection              Source connection name.
     * @param sourceSchema                  Source schema name.
     * @param sourceTable                   Source table name.
     * @param newSourceTableLineage   The model object.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}",
            consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTableSourceTable", notes = "Creates a new source table of the table's data lineage.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Table's new source table successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Table's source table with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createTableSourceTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Source connection name") @PathVariable String sourceConnection,
            @ApiParam("Source schema name") @PathVariable String sourceSchema,
            @ApiParam("Source table name") @PathVariable String sourceTable,
            @ApiParam("Table lineage source list model") @RequestBody TableLineageSourceSpec newSourceTableLineage) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName) ||
                    Strings.isNullOrEmpty(sourceConnection) ||
                    Strings.isNullOrEmpty(sourceSchema) ||
                    Strings.isNullOrEmpty(sourceTable) ||
                    newSourceTableLineage == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName), () -> {
                UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);

                TableLineageSourceSpecList sourceTables = readTableLineageSourceSpecList(userHomeContext, connectionName, schemaName, tableName, true);
                if (sourceTables == null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                if (!Objects.equals(sourceConnection, newSourceTableLineage.getSourceConnection()) ||
                        !Objects.equals(sourceSchema, newSourceTableLineage.getSourceSchema()) ||
                        !Objects.equals(sourceTable, newSourceTableLineage.getSourceTable())
                ) {
                    return new ResponseEntity<>(Mono.empty(),
                            HttpStatus.NOT_ACCEPTABLE); // 406 - wrong values
                }

                TableLineageSource tableLineageSourceKey = new TableLineageSource(sourceConnection, sourceSchema, sourceTable);
                TableLineageSourceSpec tableLineageSourceSpec = sourceTables.getByObjectName(tableLineageSourceKey, false);

                if (tableLineageSourceSpec != null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a source table with this name already exists
                }

                sourceTables.add(newSourceTableLineage);

                userHomeContext.flush();
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
            });
        }));
    }

    /**
     * Deletes a specific data lineage source table of the given table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name
     * @param sourceConnection              Source connection name.
     * @param sourceSchema                  Source schema name.
     * @param sourceTable                   Source table name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}", produces = "application/json")
    @ApiOperation(value = "deleteTableSourceTable", notes = "Deletes a specific data lineage source table of the given table.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's source table removed", response = Void.class),
            @ApiResponse(code = 404, message = "Connection or table's source table not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> deleteTableSourceTable(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Source connection name") @PathVariable String sourceConnection,
            @ApiParam("Source schema name") @PathVariable String sourceSchema,
            @ApiParam("Source table name") @PathVariable String sourceTable) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                    Strings.isNullOrEmpty(schemaName) ||
                    Strings.isNullOrEmpty(tableName) ||
                    Strings.isNullOrEmpty(sourceConnection) ||
                    Strings.isNullOrEmpty(sourceSchema) ||
                    Strings.isNullOrEmpty(sourceTable) ) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnTable(connectionName, new PhysicalTableName(schemaName, tableName), () -> {
                UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);

                TableLineageSourceSpecList sourceTables = readTableLineageSourceSpecList(userHomeContext, connectionName, schemaName, tableName, false);
                if (sourceTables == null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                TableLineageSource tableLineageSourceKey = new TableLineageSource(sourceConnection, sourceSchema, sourceTable);
                TableLineageSourceSpec tableLineageSourceSpec = sourceTables.getByObjectName(tableLineageSourceKey, false);

                if (tableLineageSourceSpec == null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                sourceTables.remove(tableLineageSourceSpec);

                userHomeContext.flush();
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            });
        }));
    }

    private TableLineageSourceSpecList readTableLineageSourceSpecList(UserHomeContext userHomeContext,
                                                                      String connectionName,
                                                                      String schemaName,
                                                                      String tableName,
                                                                      boolean addSourceTablesWhenMissing) {
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return null;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        TableLineageSourceSpecList sourceTables = tableSpec.getSourceTables();

        if (sourceTables == null && addSourceTablesWhenMissing) {
            sourceTables = new TableLineageSourceSpecList();
            tableSpec.setSourceTables(sourceTables);
        }

        return sourceTables;
    }

}
