/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.rest.controllers.remote;

import ai.dqo.rest.controllers.remote.services.SourceTablesService;
import ai.dqo.rest.controllers.remote.services.SourceTablesServiceException;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.remote.TableRemoteBasicModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * REST api controller to manage the tables on a source database related to a connection.
 */
@RestController
@RequestMapping("/api/remote/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "SourceTablesController", description = "Tables remote management")
public class SourceTablesController {
    private SourceTablesService sourceTablesService;

    @Autowired
    public SourceTablesController(SourceTablesService sourceTablesService) {
        this.sourceTablesService = sourceTablesService;
    }

    /**
     * Returns a list of tables inside a schema, on the source database.
     * @param connectionName Connection name. Required import.
     * @param schemaName     Schema name.
     * @return List of tables inside a schema.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables")
    @ApiOperation(value = "getRemoteTables", notes = "Returns a list of tables inside a schema from the source database", response = TableRemoteBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableRemoteBasicModel[].class),
            @ApiResponse(code = 400, message = "Error accessing the remote source database", response = SpringErrorPayload.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<TableRemoteBasicModel>> getRemoteTables(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName) {
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
