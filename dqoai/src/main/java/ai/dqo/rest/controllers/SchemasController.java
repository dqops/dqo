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

import ai.dqo.checks.CheckType;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.metadata.SchemaModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.check.CheckTemplate;
import ai.dqo.services.metadata.SchemaService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of schemas inside a connection.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Schemas", description = "Schema management")
public class SchemasController {
    private final SchemaService schemaService;
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public SchemasController(SchemaService schemaService,
                             UserHomeContextFactory userHomeContextFactory) {
        this.schemaService = schemaService;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of schemas inside a connection.
     * @param connectionName Connection name.
     * @return List of schemas inside a connection.
     */
    @GetMapping("/{connectionName}/schemas")
    @ApiOperation(value = "getSchemas", notes = "Returns a list of schemas inside a connection", response = SchemaModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchemaModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<SchemaModel>> getSchemas(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        List<String> schemaNameList = connectionWrapper.getTables().toList()
                .stream()
                .map(tw -> tw.getPhysicalTableName().getSchemaName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        Stream<SchemaModel> modelStream = schemaNameList.stream()
                .map(s -> SchemaModel.fromSchemaNameStrings(connectionName, s));

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK);
    }

    /**
     * Retrieves the list of profiling checks templates on the given schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @return Data quality checks templates on a requested schema.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/bulkenable/profiling")
    @ApiOperation(value = "getSchemaProfilingTemplates", notes = "Return available data quality checks on a requested schema.", response = CheckTemplate.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a schema returned", response = CheckTemplate.class),
            @ApiResponse(code = 404, message = "Connection or schema not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckTemplate>> getSchemaProfilingTemplates(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        List<TableWrapper> tableWrappers = this.schemaService.getSchemaTables(userHome, connectionName, schemaName);
        if (tableWrappers == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckTemplate> checkTemplates = this.schemaService.getCheckTemplates(
                connectionName, schemaName, CheckType.PROFILING,
                null, null, null, null);

        return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
    }
}
