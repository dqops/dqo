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

import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.services.remote.connections.SourceConnectionsService;
import ai.dqo.rest.models.metadata.ConnectionBasicModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.remote.ConnectionTestModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * REST api controller to check connection status on a remote source database.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "SourceConnectionController", description = "Source connection test service")
public class SourceConnectionController {
    private SourceConnectionsService sourceConnectionsService;

    @Autowired
    public SourceConnectionController(SourceConnectionsService sourceConnectionsService){
        this.sourceConnectionsService = sourceConnectionsService;
    }

    /**
     * Returns an enum value of connection status
     * and if the connection status value is FAILURE then it returns error message.
     * @param connectionBasicModel Connection connectionBasicModel. Required import.
     * @param verifyNameUniqueness True when the connection uniqueness must be checked.
     * @return Enum value of connection status and error message.
     */
    @PostMapping("/connectiontest")
    @ApiOperation(value = "testConnection", notes = "Checks if the given remote connection could be opened and the credentials are valid", response = ConnectionTestModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ConnectionTestModel.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ConnectionTestModel>> testConnection(
            @ApiParam("Basic connection model") @RequestBody ConnectionBasicModel connectionBasicModel,
            @ApiParam("Verify if the connection name is unique, the default value is true")
                @RequestParam(required = false) Optional<Boolean> verifyNameUniqueness) {

        ConnectionTestModel connectionTestModel;

        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionBasicModel.copyToConnectionSpecification(connectionSpec);

        Boolean verifyNameUniquenessValue = verifyNameUniqueness.orElse(true);

        connectionTestModel = sourceConnectionsService.testConnection(connectionBasicModel.getConnectionName(), connectionSpec, verifyNameUniquenessValue);

        return new ResponseEntity<>(Mono.just(connectionTestModel), HttpStatus.OK);
    }

}