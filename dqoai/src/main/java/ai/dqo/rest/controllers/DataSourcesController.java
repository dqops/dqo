/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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

package ai.dqo.rest.controllers;

import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.rest.models.metadata.ConnectionBasicModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.remote.ConnectionTestModel;
import ai.dqo.rest.models.remote.SchemaRemoteModel;
import ai.dqo.rest.models.remote.TableRemoteBasicModel;
import ai.dqo.services.remote.connections.SourceConnectionsService;
import ai.dqo.services.remote.schemas.SourceSchemasService;
import ai.dqo.services.remote.schemas.SourceSchemasServiceException;
import ai.dqo.services.remote.tables.SourceTablesService;
import ai.dqo.services.remote.tables.SourceTablesServiceException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

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
     * @param connectionBasicModel Connection connectionBasicModel. Required import.
     * @param verifyNameUniqueness True when the connection uniqueness must be checked.
     * @return Enum value of connection status and error message.
     */
    @PostMapping(value = "/datasource/testconnection", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "testConnection", notes = "Checks if the given remote connection could be opened and the credentials are valid",
            response = ConnectionTestModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ConnectionTestModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ConnectionTestModel>> testConnection(
            @ApiParam(value = "Basic connection model") @RequestBody ConnectionBasicModel connectionBasicModel,
            @ApiParam(name = "verifyNameUniqueness", value = "Verify if the connection name is unique, the default value is true", required = false)
            @RequestParam(required = false) Optional<Boolean> verifyNameUniqueness) {
        ConnectionTestModel connectionTestModel;

        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionBasicModel.copyToConnectionSpecification(connectionSpec);
        Boolean verifyNameUniquenessValue = verifyNameUniqueness.orElse(true);

        connectionTestModel = sourceConnectionsService.testConnection(connectionBasicModel.getConnectionName(), connectionSpec, verifyNameUniquenessValue);
        return new ResponseEntity<>(Mono.just(connectionTestModel), HttpStatus.OK);
    }

    /**
     * Introspects a list of schemas inside a remote data source, identified by an already imported connection.
     * @param connectionName Connection name. Required import.
     * @return List of schemas inside a connection.
     */
    @GetMapping(value = "/datasource/connections/{connectionName}/schemas", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "getRemoteDataSourceSchemas",
            notes = "Introspects a list of schemas inside a remote data source, identified by an already imported connection.", response = SchemaRemoteModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of schemas on a remote data source was introspected and is returned", response = SchemaRemoteModel[].class),
            @ApiResponse(code = 400, message = "Error accessing the remote data source", response = SpringErrorPayload.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SchemaRemoteModel>> getRemoteDataSourceSchemas(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        List<SchemaRemoteModel> result;
        try {
            result = sourceSchemasService.showSchemas(connectionName);
        }
        catch (SourceSchemasServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        if (result == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Flux.fromStream(result.stream()), HttpStatus.OK);
    }

    /**
     * Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQO.
     * @param connectionName Connection name. Required import.
     * @param schemaName     Schema name.
     * @return List of tables inside a schema.
     */
    @GetMapping(value = "/datasource/connections/{connectionName}/schemas/{schemaName}/tables", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "getRemoteDataSourceTables", notes = "Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQO.",
            response = TableRemoteBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The list of tables on a remote data source was introspected and is returned",
                    response = TableRemoteBasicModel[].class),
            @ApiResponse(code = 400, message = "Error accessing the remote source database", response = SpringErrorPayload.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<TableRemoteBasicModel>> getRemoteDataSourceTables(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName) {
        List<TableRemoteBasicModel> result;
        try {
            result = sourceTablesService.showTablesOnRemoteSchema(connectionName, schemaName);
        }
        catch (SourceTablesServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        if (result == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Flux.fromStream(result.stream()), HttpStatus.OK);
    }
}
