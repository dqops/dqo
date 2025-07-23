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

import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.rest.models.metadata.ConnectionModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.rest.models.remote.ConnectionTestModel;
import com.dqops.rest.models.remote.ConnectionTestStatus;
import com.dqops.rest.models.remote.RemoteTableListModel;
import com.dqops.rest.models.remote.SchemaRemoteModel;
import com.dqops.services.remote.connections.SourceConnectionsService;
import com.dqops.services.remote.schemas.SourceSchemasService;
import com.dqops.services.remote.schemas.SourceSchemasServiceException;
import com.dqops.services.remote.tables.SourceTablesService;
import com.dqops.services.remote.tables.SourceTablesServiceException;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Rest API controller that operates on target data sources, testing connections or retrieving the metadata.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "DataSources", description = "Rest API controller that operates on data sources that are not yet imported, testing connections or retrieving the metadata (schemas and tables).")
public class DataSourcesController {
    private SourceConnectionsService sourceConnectionsService;
    private SourceSchemasService sourceSchemasService;
    private SourceTablesService sourceTablesService;

    /**
     * Dependency injection constructor.
     * @param sourceConnectionsService Service that tests connections.
     * @param sourceSchemasService Service that lists remote schemas.
     * @param sourceTablesService Service that lists remote tables.
     */
    @Autowired
    public DataSourcesController(SourceConnectionsService sourceConnectionsService,
                                 SourceSchemasService sourceSchemasService,
                                 SourceTablesService sourceTablesService) {
        this.sourceConnectionsService = sourceConnectionsService;
        this.sourceSchemasService = sourceSchemasService;
        this.sourceTablesService = sourceTablesService;
    }

    /**
     * Returns an enum value of connection status
     * and if the connection status value is FAILURE then it returns error message.
     * @param connectionModel Connection connectionBasicModel. Required import.
     * @param verifyNameUniqueness True when the connection uniqueness must be checked.
     * @return Enum value of connection status and error message.
     */
    @PostMapping(value = "/datasource/testconnection", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "testConnection", notes = "Checks if the given remote connection can be opened and if the credentials are valid",
            response = ConnectionTestModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection was tested, check the status code to see the connection's test status",  response = ConnectionTestModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<ConnectionTestModel>>> testConnection(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam(value = "Basic connection model") @RequestBody ConnectionModel connectionModel,
            @ApiParam(name = "verifyNameUniqueness", value = "Verify if the connection name is unique, the default value is true", required = false)
            @RequestParam(required = false) Optional<Boolean> verifyNameUniqueness) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            ConnectionTestModel connectionTestModel;

            ConnectionSpec connectionSpec = new ConnectionSpec();
            connectionModel.copyToConnectionSpecification(connectionSpec);
            Boolean verifyNameUniquenessValue = verifyNameUniqueness.orElse(true);

            try {
                connectionTestModel = sourceConnectionsService.testConnection(principal, connectionModel.getConnectionName(), connectionSpec, verifyNameUniquenessValue);
                return new ResponseEntity<>(Mono.just(connectionTestModel), HttpStatus.OK);
            }
            catch (Throwable ex)
            {
                ConnectionTestModel errorTestModel = new ConnectionTestModel() {{
                    setConnectionTestResult(ConnectionTestStatus.FAILURE);
                    setErrorMessage(ex.getMessage());
                }};

                return new ResponseEntity<>(Mono.just(errorTestModel), HttpStatus.OK);
            }
        }));
    }

    /**
     * Introspects a list of schemas inside a remote data source, identified by an already imported connection.
     * @param connectionName Connection name. Required import.
     * @return List of schemas inside a connection.
     */
    @GetMapping(value = "/datasource/connections/{connectionName}/schemas", produces = "application/json")
    @ApiOperation(value = "getRemoteDataSourceSchemas",
            notes = "Introspects a list of schemas inside a remote data source, identified by an already imported connection.",
            response = SchemaRemoteModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of schemas on a remote data source was introspected and is returned", response = SchemaRemoteModel[].class),
            @ApiResponse(code = 400, message = "Error accessing the remote data source", response = SpringErrorPayload.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Flux<SchemaRemoteModel>>> getRemoteDataSourceSchemas(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            List<SchemaRemoteModel> result;
            try {
                result = sourceSchemasService.showSchemas(connectionName, principal);
            }
            catch (SourceSchemasServiceException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }

            if (result == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(Flux.fromStream(result.stream()), HttpStatus.OK);
        }));
    }

    /**
     * Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQOps.
     * @param connectionName Connection name. Required import.
     * @param schemaName     Schema name.
     * @param tableNameContains Optional filter for a text inside table names.
     * @param principal      User principal.
     * @return List of tables inside a schema.
     */
    @GetMapping(value = "/datasource/connections/{connectionName}/schemas/{schemaName}/tables", produces = "application/json")
    @ApiOperation(value = "getRemoteDataSourceTables", notes = "Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQOps.",
            response = RemoteTableListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of tables on a remote data source was introspected and is returned",
                    response = RemoteTableListModel[].class),
            @ApiResponse(code = 400, message = "Error accessing the remote source database", response = SpringErrorPayload.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Flux<RemoteTableListModel>>> getRemoteDataSourceTables(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam(name = "tableNameContains", value = "Optional filter to return tables that contain this text inside the table name (case sensitive)", required = false)
            @RequestParam(required = false) Optional<String> tableNameContains) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            List<RemoteTableListModel> result;
            try {
                result = sourceTablesService.showTablesOnRemoteSchema(connectionName, schemaName, tableNameContains.orElse(null), principal);
            }
            catch (SourceTablesServiceException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }

            if (result == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(Flux.fromStream(result.stream()), HttpStatus.OK);
        }));
    }
}
