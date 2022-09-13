package ai.dqo.rest.controllers;

import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.ConnectionModel;
import ai.dqo.rest.models.SchemaModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public SchemasController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of schemas inside a connection.
     * @param connectionName Connection name.
     * @return List of schemas inside a connection.
     */
    @GetMapping("/{connectionName}/schemas")
    @ApiOperation(value = "getSchemas", notes = "Returns a list of schemas inside a connection")
    public ResponseEntity<Flux<SchemaModel>> getSchemas(
            @Parameter(description = "Connection name") @PathVariable String connectionName) {
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

        Stream<SchemaModel> modelStream = schemaNameList.stream().map(s -> new SchemaModel() {{
            setConnectionName(connectionName);
            setSchemaName(s);
        }});

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK);
    }
}
