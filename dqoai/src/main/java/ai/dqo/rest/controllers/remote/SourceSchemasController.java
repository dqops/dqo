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
package ai.dqo.rest.controllers.remote;

import ai.dqo.rest.controllers.remote.services.SourceSchemasService;
import ai.dqo.rest.controllers.remote.services.SourceSchemasServiceException;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.remote.SchemaRemoteModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;


/**
 * REST api controller to manage the list of schemas inside a connection on a remote source database.
 */
@RestController
@RequestMapping("/api/remote/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "SourceSchemasController", description = "Schema remote management")
public class SourceSchemasController {
    private SourceSchemasService sourceSchemasService;

    @Autowired
    public SourceSchemasController(SourceSchemasService sourceSchemasService) {
        this.sourceSchemasService = sourceSchemasService;
    }

    /**
     * Returns a list of schemas inside a connection.
     * @param connectionName Connection name.
     * @return List of schemas inside a connection.
     */
    @GetMapping("/{connectionName}/schemas")
    @ApiOperation(value = "getRemoteSchemas", notes = "Returns a list of schemas inside a connection on a remote database", response = SchemaRemoteModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SchemaRemoteModel[].class),
            @ApiResponse(code = 400, message = "Error accessing the remote source database", response = SpringErrorPayload.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SchemaRemoteModel>> getRemoteSchemas(
            @Parameter(description = "Connection name") @PathVariable String connectionName) {
        List<SchemaRemoteModel> result;
        try {
            result = sourceSchemasService.loadSchemas(connectionName);
        }
        catch (SourceSchemasServiceException e) {
            return new ResponseEntity<>(Flux.error(e), HttpStatus.BAD_REQUEST);
        }

        if (result == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Flux.fromStream(result.stream()), HttpStatus.OK);
    }
}
