package ai.dqo.rest.controllers;

import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.ColumnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of columns inside a table.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Columns", description = "Manages columns inside a table")
public class ColumnsController {
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public ColumnsController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of columns inside a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @parqm tableName      Table name
     * @return List of columns inside a table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns")
    @ApiOperation(value = "getColumns", notes = "Returns a list of columns inside a table")
    public ResponseEntity<Flux<ColumnModel>> getColumns(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        Stream<ColumnModel> columnSpecs = tableWrapper.getSpec().getColumns()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(kv -> kv.getKey()))
                .map(kv -> new ColumnModel() {{
                    setConnectionName(connectionName);
                    setTable(tableWrapper.getPhysicalTableName());
                    setColumnName(kv.getKey());
                    setSpec(kv.getValue().trim());
                }});

        return new ResponseEntity<>(Flux.fromStream(columnSpecs), HttpStatus.OK);
    }
}
