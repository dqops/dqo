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

import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.controllers.remote.services.SourceConnectionsService;
import ai.dqo.rest.models.metadata.ConnectionBasicModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.rest.models.remote.ConnectionRemoteModel;
import ai.dqo.rest.models.remote.ConnectionStatusRemote;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST api controller to check connection status on a remote source database.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "SourceSchemasController", description = "Connection status remote management")
public class SourceConnectionController {
    private final UserHomeContextFactory userHomeContextFactory;
    private SourceConnectionsService sourceConnectionsService;

    @Autowired
    public SourceConnectionController(UserHomeContextFactory userHomeContextFactory, SourceConnectionsService sourceConnectionsService){
        this.userHomeContextFactory = userHomeContextFactory;
        this.sourceConnectionsService = sourceConnectionsService;
    }

    /**
     * Returns a boolean value of connection status and message exception.
     * @param connectionBasicModel Connection connectionBasicModel. Required import.
     * @return Enum value of connection status and message exception.
     */
    @PostMapping("/checkconnection")
    @ApiOperation(value = "checkconnection", notes = "Checks if the given connection exists", response = ConnectionRemoteModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ConnectionRemoteModel.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 409, message = "Rejected, The connection name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ConnectionRemoteModel>> connection(
            @ApiParam("Basic connection model") @RequestBody ConnectionBasicModel connectionBasicModel) {

        ConnectionRemoteModel connectionRemoteModel;

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionBasicModel.getConnectionName(), true);

        if (connectionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionBasicModel.copyToConnectionSpecification(connectionSpec);

        connectionRemoteModel = sourceConnectionsService.showSchemas(connectionSpec);

        if (connectionRemoteModel.getConnectionStatus() == ConnectionStatusRemote.FAIL) {
            return new ResponseEntity<>(Mono.just(connectionRemoteModel), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Mono.just(connectionRemoteModel), HttpStatus.OK);
    }

}