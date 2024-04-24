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

import com.dqops.checks.CheckType;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.statuscache.CurrentTableStatusKey;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.TableListModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
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
     * @param label      Optional list of tables.
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
    public ResponseEntity<Flux<TableListModel>> findTables(
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
        tableSearchFilters.setFullTableName(tableNameFilter + "." + schemaNameFilter);

        if (label.isPresent() && label.get().size() > 0) {
            tableSearchFilters.setLabels(label.get().toArray(String[]::new));
        }

        Integer tableLimit = null;
        Integer skip = null;
        if (page.isPresent() || limit.isPresent()) {
            if (page.isPresent() && page.get() < 1) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }
            if (limit.isPresent() && limit.get() < 1) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            Integer pageValue = page.orElse(1);
            Integer limitValue = limit.orElse(100);
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
            CurrentTableStatusKey tableStatusKey = new CurrentTableStatusKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                    listModel.getConnectionName(), listModel.getTarget());
            TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
            listModel.setDataQualityStatus(currentTableStatus);
        });

        if (tableModelsList.stream().anyMatch(model -> model.getDataQualityStatus() == null)) {
            // the results not loaded yet, we need to wait until the queue is empty
            CompletableFuture<Boolean> waitForLoadTasksFuture = this.tableStatusCache.getQueueEmptyFuture(TableStatusCache.EMPTY_QUEUE_WAIT_TIMEOUT_MS);

            Flux<TableListModel> resultListFilledWithDelay = Mono.fromFuture(waitForLoadTasksFuture)
                    .thenMany(Flux.fromIterable(tableModelsList)
                            .map(tableListModel -> {
                                if (tableListModel.getDataQualityStatus() == null) {
                                    CurrentTableStatusKey tableStatusKey = new CurrentTableStatusKey(principal.getDataDomainIdentity().getDataDomainCloud(),
                                            tableListModel.getConnectionName(), tableListModel.getTarget());
                                    TableCurrentDataQualityStatusModel currentTableStatus = this.tableStatusCache.getCurrentTableStatus(tableStatusKey, checkType.orElse(null));
                                    tableListModel.setDataQualityStatus(currentTableStatus);
                                }
                                return tableListModel;
                            }));

            return new ResponseEntity<>(resultListFilledWithDelay, HttpStatus.OK); // 200
        }

        return new ResponseEntity<>(Flux.fromStream(tableModelsList.stream()), HttpStatus.OK); // 200
    }
}
