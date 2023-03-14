package ai.dqo.rest.controllers;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.data.ruleresults.services.CheckResultsOverviewParameters;
import ai.dqo.data.ruleresults.services.RuleResultsDataService;
import ai.dqo.data.ruleresults.services.models.CheckResultsOverviewDataModel;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Objects;

/**
 * Controller that returns the overview of the recent results and errors on tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "CheckResultsOverview", description = "Returns the overview of the recently executed checks on tables and columns.")
public class CheckResultsOverviewController {
    private UserHomeContextFactory userHomeContextFactory;
    private RuleResultsDataService ruleResultsDataService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param ruleResultsDataService Rule results data service.
     */
    @Autowired
    public CheckResultsOverviewController(UserHomeContextFactory userHomeContextFactory,
                                          RuleResultsDataService ruleResultsDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.ruleResultsDataService = ruleResultsDataService;
    }

    /**
     * Retrieves the overview of the most recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Overview of the most recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/overview")
    @ApiOperation(value = "getTableProfilingChecksOverview", notes = "Returns an overview of the most recent check executions for all table level data quality profiling checks on a table",
            response = CheckResultsOverviewDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Overview of the most recent check runs for table level data quality profiling checks on a table returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsOverviewDataModel>> getTableProfilingChecksOverview(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableProfilingCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                tableSpec.getChecks(),
                () -> {
                    TableProfilingCheckCategoriesSpec container = new TableProfilingCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "checks"));
                    return container;
                });

        CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.ruleResultsDataService.readMostRecentCheckStatuses(
                checks, new CheckResultsOverviewParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the overview of the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return Overview of the most recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/overview")
    @ApiOperation(value = "getTableCheckpointsOverview", notes = "Returns an overview of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsOverviewDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsOverviewDataModel>> getTableCheckpointsOverview(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checkRootContainer = tableSpec.getTableCheckRootContainer(CheckType.CHECKPOINT, timeScale);

        CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.ruleResultsDataService.readMostRecentCheckStatuses(
                checkRootContainer, new CheckResultsOverviewParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the overview of the most recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return Overview of the most recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/overview")
    @ApiOperation(value = "getTablePartitionedChecksOverview", notes = "Returns an overview of the most recent table level partitioned checks executions for a requested time scale",
            response = CheckResultsOverviewDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent partitioned check executions for a requested time scale on a table returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsOverviewDataModel>> getTablePartitionedChecksOverview(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checkRootContainer = tableSpec.getTableCheckRootContainer(CheckType.PARTITIONED, timeScale);

        CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.ruleResultsDataService.readMostRecentCheckStatuses(
                checkRootContainer, new CheckResultsOverviewParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the overview of the most recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Overview of the most recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/overview")
    @ApiOperation(value = "getColumnProfilingChecksOverview", notes = "Returns an overview of the most recent check executions for all column level data quality profiling checks on a column",
            response = CheckResultsOverviewDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Overview of the most recent check runs for column level data quality profiling checks on a column returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsOverviewDataModel>> getColumnProfilingChecksOverview(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.PROFILING, null);

        CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.ruleResultsDataService.readMostRecentCheckStatuses(
                checks, new CheckResultsOverviewParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the overview of the most recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @return Overview of the most recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/overview")
    @ApiOperation(value = "getColumnCheckpointsOverview", notes = "Returns an overview of the most recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsOverviewDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsOverviewDataModel>> getColumnCheckpointsOverview(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checkRootContainer = columnSpec.getColumnCheckRootContainer(CheckType.CHECKPOINT, timeScale);

        CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.ruleResultsDataService.readMostRecentCheckStatuses(
                checkRootContainer, new CheckResultsOverviewParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the overview of the most recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @return Overview of the most recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/overview")
    @ApiOperation(value = "getColumnPartitionedChecksOverview", notes = "Returns an overview of the most recent column level partitioned checks executions for a requested time scale",
            response = CheckResultsOverviewDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An overview of the most recent partitioned check executions for a requested time scale on a column returned",
                    response = CheckResultsOverviewDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsOverviewDataModel>> getColumnPartitionedChecksOverview(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checkRootContainer = columnSpec.getColumnCheckRootContainer(CheckType.PARTITIONED, timeScale);

        CheckResultsOverviewDataModel[] checkResultsOverviewDataModels = this.ruleResultsDataService.readMostRecentCheckStatuses(
                checkRootContainer, new CheckResultsOverviewParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsOverviewDataModels), HttpStatus.OK); // 200
    }
}
