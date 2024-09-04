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

import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.TableLineageSourceListModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.locking.RestApiLockService;
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

import java.util.List;
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

    /**
     * Default dependency injection constructor.
     * @param userHomeContextFactory DQOps user home factory.
     * @param lockService REST API lock service to avoid updating the same table yaml in two threads.
     */
    @Autowired
    public DataLineageController(
            UserHomeContextFactory userHomeContextFactory,
            RestApiLockService lockService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.lockService = lockService;
    }

    /**
     * Returns a list of source tables on the data lineage that are sources of the given table"
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of source tables on the data lineage that are sources of the given table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage", produces = "application/json")
    @ApiOperation(value = "getTableSourceTables", notes = "Returns a list of source tables on the data lineage that are sources of the given table.", response = TableLineageSourceListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableLineageSourceListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<TableLineageSourceListModel>>> getTableSourceTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);

            if (tableSpec.getSourceTables() == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.OK); // 200 - empty
            }

            List<TableLineageSourceListModel> sourceTables = tableSpec.getSourceTables()
                    .stream()
                    .map(sourceSpec -> TableLineageSourceListModel.fromSpecification(
                            sourceSpec, isEditor))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(Flux.fromStream(sourceTables.stream()), HttpStatus.OK); // 200
        }));
    }
}
