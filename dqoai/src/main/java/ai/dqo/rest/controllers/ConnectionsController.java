package ai.dqo.rest.controllers;

import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.ConnectionModel;
import ai.dqo.rest.models.SpringErrorPayload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

/**
 * REST api controller to return a list of connections.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Connections", description = "Connection management")
public class ConnectionsController {
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public ConnectionsController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of connections.
     * @return List of connections.
     */
    @GetMapping
    @ApiOperation(value = "getAllConnections", notes = "Returns a list of connection")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Flux<ConnectionModel>> getAllConnections() {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        Stream<ConnectionModel> modelStream = connections.toList().stream().map(cw -> new ConnectionModel() {{
            setName(cw.getName());
            setSpec(cw.getSpec());
        }});

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the connection specification.
     * @param connectionName Connection name.
     * @return Connection model with the connection name and the connection specification.
     */
    @GetMapping("/{connectionName}")
    @ApiOperation(value = "getConnection", notes = "Return the connection specification")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection returned"),
            @ApiResponse(code = 404, message = "Connection not found", response = SpringErrorPayload.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<ConnectionModel>> getConnection(
            @Parameter(description = "Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ConnectionModel connectionModel = new ConnectionModel() {{
            setName(connectionName);
            setSpec(connectionWrapper.getSpec());
        }};

        return new ResponseEntity<>(Mono.just(connectionModel), HttpStatus.OK); // 200
    }

    /**
     * Creates (adds) a new connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Empty response.
     */
    @PostMapping("/{connectionName}")
    @ApiOperation(value = "createConnection", notes = "Creates a new connection")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New connection successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = SpringErrorPayload.class), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields", response = SpringErrorPayload.class),
            @ApiResponse(code = 409, message = "Connection with the same name already exist", response = SpringErrorPayload.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<Void>> createConnection(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Connection specification") @RequestBody ConnectionSpec connectionSpec) {
        if (Strings.isNullOrEmpty(connectionName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper existingConnectionWrapper = connections.getByObjectName(connectionName, true);
        if (existingConnectionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a connection with this name already exists
        }

        ConnectionWrapper connectionWrapper = connections.createAndAddNew(connectionName);
        connectionWrapper.setSpec(connectionSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Updates an existing connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}")
    @ApiOperation(value = "updateConnection", notes = "Updates an existing connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = SpringErrorPayload.class), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found", response = SpringErrorPayload.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<Void>> updateConnection(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Connection specification") @RequestBody ConnectionSpec connectionSpec) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        // TODO: validate the connectionSpec
        connectionWrapper.setSpec(connectionSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Deletes a connection.
     * @param connectionName Connection name to delete.
     * @return Empty response.
     */
    @DeleteMapping("/{connectionName}")
    @ApiOperation(value = "deleteConnection", notes = "Deletes a connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection successfully deleted"),
            @ApiResponse(code = 404, message = "Connection not found", response = SpringErrorPayload.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class )
    })
    public ResponseEntity<Mono<Void>> deleteConnection(
            @Parameter(description = "Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        connectionWrapper.markForDeletion(); // will be deleted
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}
