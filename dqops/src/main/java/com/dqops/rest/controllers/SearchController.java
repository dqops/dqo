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
import com.dqops.connectors.DataTypeCategory;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.checkresults.models.currentstatus.ColumnCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.metadata.search.ColumnSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.ColumnListModel;
import com.dqops.rest.models.metadata.TableListModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.utils.threading.CompletableFutureRunner;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * REST API controller with search operations for finding data assets, such as tables.
 */
@RestController
@RequestMapping("/api/search")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Search", description = "Search operations for finding data assets, such as tables.")
@Slf4j
public class SearchController {
    /**
     * The default limit for the number of results returned, when the "limit" parameter is not given.
     */
    public static final int DEFAULT_SEARCH_LIMIT = 100;

    private final UserHomeContextFactory userHomeContextFactory;
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private final TableStatusCache tableStatusCache;

    /**
     * Default IoC constructor for the search controller.
     * @param userHomeContextFactory User home context factory used to open a connection to the user home.
     * @param hierarchyNodeTreeSearcher Search service for finding target objects.
     * @param tableStatusCache Table status cache to look up the most recent table status.
     */
    @Autowired
    public SearchController(UserHomeContextFactory userHomeContextFactory,
                            HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                            TableStatusCache tableStatusCache) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.tableStatusCache = tableStatusCache;
    }

    /**
     * Returns a list of tables that match a filters.
     * @param principal  User principal.
     * @param connection Connection name.
     * @param schema     Schema name.
     * @param table      Table name.
     * @param label      Optional list of labels.
     * @param page       Optional page number, the default is 1 (the first page).
     * @param limit      Optional page size limit, the default is 100 rows.
     * @param checkType  Optional type of checks. It is used to look up the right most recent table results.
     * @return List of tables matching the filters.
     */
    @GetMapping(value = "/tables", produces = "application/json")
    @ApiOperation(value = "findTables", notes = "Finds tables in any data source and schema", response = TableListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<TableListModel>>> findTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam(name = "connection", value = "Optional connection name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.", required = false)
            @RequestParam(required = false) Optional<String> connection,
            @ApiParam(name = "schema", value = "Optional schema name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.", required = false)
            @RequestParam(required = false) Optional<String> schema,
            @ApiParam(name = "table", value = "Optional table name filter", required = false)
            @RequestParam(required = false) Optional<String> table,
            @ApiParam(name = "label", value = "Optional labels to filter the tables", required = false)
            @RequestParam(required = false) Optional<List<String>> label,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
            @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided", required = false)
            @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "checkType", value = "Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type", required = false)
            @RequestParam(required = false) Optional<CheckType> checkType) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            ConnectionList connections = userHome.getConnections();

            TableSearchFilters tableSearchFilters = new TableSearchFilters();
            tableSearchFilters.setConnection(connection.orElse(null));

            String schemaNameFilter = schema.orElse("*");
            String tableNameFilter = table.orElse("*");
            if (Strings.isNullOrEmpty(schemaNameFilter)) {
                schemaNameFilter = "*";
            }
            if (Strings.isNullOrEmpty(tableNameFilter)) {
                tableNameFilter = "*";
            }
            tableSearchFilters.setFullTableName(schemaNameFilter + "." + tableNameFilter);

            if (label.isPresent() && label.get().size() > 0) {
                tableSearchFilters.setLabels(label.get().toArray(String[]::new));
            }

            Integer tableLimit = DEFAULT_SEARCH_LIMIT;
            Integer skip = null;
            if (page.isPresent() || limit.isPresent()) {
                if (page.isPresent() && page.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }
                if (limit.isPresent() && limit.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }

                Integer pageValue = page.orElse(1);
                Integer limitValue = limit.orElse(DEFAULT_SEARCH_LIMIT);
                tableLimit = pageValue * limitValue;
                skip = (pageValue - 1) * limitValue;
            }

            tableSearchFilters.setMaxResults(tableLimit);
            Collection<TableWrapper> matchingTableWrappers = this.hierarchyNodeTreeSearcher.findTables(
                    connections, tableSearchFilters);

            List<TableSpec> tableSpecs = matchingTableWrappers
                    .stream()
                    .skip(skip == null ? 0 : skip)
                    .map(TableWrapper::getSpec)
                    .collect(Collectors.toList());

            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            boolean isOperator = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);
            List<TableListModel> tableModelsList = tableSpecs.stream()
                    .map(ts -> TableListModel.fromTableSpecificationForListEntry(
                            ts.getHierarchyId().getConnectionName(), ts, isEditor, isOperator))
                    .collect(Collectors.toList());

            tableModelsList.forEach(listModel -> {
                DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                        listModel.getConnectionName(), listModel.getTarget());
                TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                listModel.setDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
            });

            if (tableModelsList.stream().anyMatch(model -> model.getDataQualityStatus() == null)) {
                // the results not loaded yet, we need to wait until the queue is empty
                CompletableFuture<Boolean> waitForLoadTasksFuture = this.tableStatusCache.getQueueEmptyFuture(TableStatusCache.EMPTY_QUEUE_WAIT_TIMEOUT_MS);

                Flux<TableListModel> resultListFilledWithDelay = Mono.fromFuture(waitForLoadTasksFuture)
                        .thenMany(Flux.fromIterable(tableModelsList)
                                .map(tableListModel -> {
                                    if (tableListModel.getDataQualityStatus() == null) {
                                        DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                                                tableListModel.getConnectionName(), tableListModel.getTarget());
                                        TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                                        tableListModel.setDataQualityStatus(currentTableStatus != null ? currentTableStatus.shallowCloneWithoutCheckResultsAndColumns() : null);
                                    }
                                    return tableListModel;
                                }));

                return new ResponseEntity<>(resultListFilledWithDelay, HttpStatus.OK); // 200
            }

            return new ResponseEntity<>(Flux.fromStream(tableModelsList.stream()), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns a list of columns that match a filters.
     * @param principal  User principal.
     * @param connection Connection name.
     * @param schema     Schema name.
     * @param table      Table name.
     * @param column     Column name.
     * @param label      Optional list of labels.
     * @param page       Optional page number, the default is 1 (the first page).
     * @param limit      Optional page size limit, the default is 100 rows.
     * @param checkType  Optional type of checks. It is used to look up the right most recent column data quality results.
     * @return List of columns matching the filters.
     */
    @GetMapping(value = "/columns", produces = "application/json")
    @ApiOperation(value = "findColumns", notes = "Finds columns in any data source and schema", response = ColumnListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ColumnListModel>>> findColumns(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam(name = "connection", value = "Optional connection name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.", required = false)
            @RequestParam(required = false) Optional<String> connection,
            @ApiParam(name = "schema", value = "Optional schema name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.", required = false)
            @RequestParam(required = false) Optional<String> schema,
            @ApiParam(name = "table", value = "Optional table name filter", required = false)
            @RequestParam(required = false) Optional<String> table,
            @ApiParam(name = "column", value = "Optional column name filter", required = false)
            @RequestParam(required = false) Optional<String> column,
            @ApiParam(name = "columnType", value = "Optional physical column's data type filter", required = false)
            @RequestParam(required = false) Optional<String> columnType,
            @ApiParam(name = "columnCategory", value = "Optional data type category filter", required = false)
            @RequestParam(required = false) Optional<DataTypeCategory> columnCategory,
            @ApiParam(name = "label", value = "Optional labels to filter the columns", required = false)
            @RequestParam(required = false) Optional<List<String>> label,
            @ApiParam(name = "page", value = "Page number, the first page is 1", required = false)
            @RequestParam(required = false) Optional<Integer> page,
            @ApiParam(name = "limit", value = "Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided", required = false)
            @RequestParam(required = false) Optional<Integer> limit,
            @ApiParam(name = "checkType", value = "Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type", required = false)
            @RequestParam(required = false) Optional<CheckType> checkType) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            ConnectionList connections = userHome.getConnections();

            ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
            columnSearchFilters.setConnectionName(connection.orElse(null));

            String schemaNameFilter = schema.orElse("*");
            String tableNameFilter = table.orElse("*");
            if (Strings.isNullOrEmpty(schemaNameFilter)) {
                schemaNameFilter = "*";
            }
            if (Strings.isNullOrEmpty(tableNameFilter)) {
                tableNameFilter = "*";
            }
            columnSearchFilters.setSchemaTableName(schemaNameFilter + "." + tableNameFilter);

            if (label.isPresent() && label.get().size() > 0) {
                columnSearchFilters.setColumnLabels(label.get().toArray(String[]::new));
            }

            columnSearchFilters.setColumnName(column.orElse(null));
            columnSearchFilters.setColumnDataType(columnType.orElse(null));
            columnSearchFilters.setDataTypeCategory(columnCategory.orElse(null));

            Integer resultsLimit = DEFAULT_SEARCH_LIMIT;
            Integer skip = null;
            if (page.isPresent() || limit.isPresent()) {
                if (page.isPresent() && page.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }
                if (limit.isPresent() && limit.get() < 1) {
                    return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
                }

                Integer pageValue = page.orElse(1);
                Integer limitValue = limit.orElse(DEFAULT_SEARCH_LIMIT);
                resultsLimit = pageValue * limitValue;
                skip = (pageValue - 1) * limitValue;
            }

            columnSearchFilters.setMaxResults(resultsLimit);
            Collection<ColumnSpec> allMatchingColumnSpecs = this.hierarchyNodeTreeSearcher.findColumns(
                    connections, columnSearchFilters);

            List<ColumnSpec> columnSpecsPage = allMatchingColumnSpecs
                    .stream()
                    .skip(skip == null ? 0 : skip)
                    .collect(Collectors.toList());

            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            boolean isOperator = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);
            List<ColumnListModel> columnModelsList = columnSpecsPage.stream()
                    .map(colSpec -> ColumnListModel.fromColumnSpecificationForListEntry(
                            colSpec.getHierarchyId().getConnectionName(),
                            colSpec.getHierarchyId().getPhysicalTableName(),
                            colSpec.getColumnName(),
                            colSpec, isEditor, isOperator))
                    .collect(Collectors.toList());

            columnModelsList.forEach(listModel -> {
                DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                        listModel.getConnectionName(), listModel.getTable());
                TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                if (currentTableStatus != null) {
                    ColumnCurrentDataQualityStatusModel columnQualityStatusModel = currentTableStatus.getColumns().get(listModel.getColumnName());
                    listModel.setDataQualityStatus(columnQualityStatusModel != null ?
                            columnQualityStatusModel.shallowCloneWithoutChecks() : new ColumnCurrentDataQualityStatusModel());
                }
            });

            if (columnModelsList.stream().anyMatch(model -> model.getDataQualityStatus() == null)) {
                // the results not loaded yet, we need to wait until the queue is empty
                CompletableFuture<Boolean> waitForLoadTasksFuture = this.tableStatusCache.getQueueEmptyFuture(TableStatusCache.EMPTY_QUEUE_WAIT_TIMEOUT_MS);

                Flux<ColumnListModel> resultListFilledWithDelay = Mono.fromFuture(waitForLoadTasksFuture)
                        .thenMany(Flux.fromIterable(columnModelsList)
                                .map(listModel -> {
                                    if (listModel.getDataQualityStatus() == null) {
                                        DomainConnectionTableKey tableStatusKey = new DomainConnectionTableKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                                                listModel.getConnectionName(), listModel.getTable());
                                        TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                                        if (currentTableStatus != null) {
                                            ColumnCurrentDataQualityStatusModel columnQualityStatusModel = currentTableStatus.getColumns().get(listModel.getColumnName());
                                            listModel.setDataQualityStatus(columnQualityStatusModel != null ?
                                                    columnQualityStatusModel.shallowCloneWithoutChecks() : new ColumnCurrentDataQualityStatusModel());
                                        }
                                    }
                                    return listModel;
                                }));

                return new ResponseEntity<>(resultListFilledWithDelay, HttpStatus.OK); // 200
            }

            return new ResponseEntity<>(Flux.fromStream(columnModelsList.stream()), HttpStatus.OK); // 200
        }));
    }
}
