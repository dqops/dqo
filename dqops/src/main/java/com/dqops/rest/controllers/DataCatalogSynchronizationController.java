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

import com.dqops.core.catalogsync.DataCatalogHealthSyncService;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Operations related to synchronization of data quality health results to the a data catalog.
 */
@RestController
@RequestMapping("/api/datacatalog")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DataCatalogSynchronization", description = "Operations related to synchronization of data quality health results to the a data catalog.")
@Slf4j
public class DataCatalogSynchronizationController {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DataCatalogHealthSyncService dataCatalogHealthSyncService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home factory to open a user home.
     * @param dataCatalogHealthSyncService  Data catalog health synchronization service.
     */
    @Autowired
    public DataCatalogSynchronizationController(
            UserHomeContextFactory userHomeContextFactory,
            DataCatalogHealthSyncService dataCatalogHealthSyncService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dataCatalogHealthSyncService = dataCatalogHealthSyncService;
    }


    /**
     * Pushes the data quality status of tables matching the search filters to the data catalog.
     * @param connection Connection name.
     * @param schema     Schema name.
     * @param table      Table name.
     * @return Empty response.
     */
    @PutMapping(value = "/sync/pushdatahealth", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "pushDataQualityStatusToDataCatalog", notes = "Pushes the data quality status of tables matching the search filters to the data catalog.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data quality health status push initiated", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> pushDataQualityStatusToDataCatalog(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam(name = "connection", value = "Optional connection name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.", required = false)
            @RequestParam(required = false) Optional<String> connection,
            @ApiParam(name = "schema", value = "Optional schema name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.", required = false)
            @RequestParam(required = false) Optional<String> schema,
            @ApiParam(name = "table", value = "Optional table name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.", required = false)
            @RequestParam(required = false) Optional<String> table) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            TableSearchFilters tableSearchFilters = new TableSearchFilters();
            tableSearchFilters.setConnection(connection.orElse(null));
            tableSearchFilters.setFullTableName(schema.orElse("*") + "." + table.orElse("*"));

            this.dataCatalogHealthSyncService.synchronizeDataCatalog(userHome, tableSearchFilters);

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }
}
