package ai.dqo.rest.controllers;

import ai.dqo.metadata.sources.ConnectionList;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.TableModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of tables inside a schema.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Tables", description = "Manages tables inside a connection/schema")
public class TablesController {
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public TablesController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of tables inside a database/schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @return List of tables inside a connection's schema.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables")
    @ApiOperation(value = "getTables", notes = "Returns a list of tables inside a connection/schema", response = TableModel[].class)
    public ResponseEntity<Flux<TableModel>> getTables(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        List<TableSpec> tableSpecs = connectionWrapper.getTables().toList()
                .stream()
                .filter(tw -> Objects.equals(tw.getPhysicalTableName().getSchemaName(), schemaName))
                .sorted(Comparator.comparing(tw -> tw.getPhysicalTableName().getTableName()))
                .map(tw -> tw.getSpec().cloneBare())
                .collect(Collectors.toList());

        Stream<TableModel> modelStream = tableSpecs.stream().map(ts -> new TableModel() {{
            setConnectionName(connectionName);
            setSpec(ts);
        }});

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK);
    }
}
