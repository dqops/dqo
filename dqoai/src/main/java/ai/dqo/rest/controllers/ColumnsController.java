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

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.ColumnDailyCheckpointCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnMonthlyCheckpointCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.profilingresults.services.ProfilerDataService;
import ai.dqo.data.profilingresults.services.models.ProfilerResultsForTableModel;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.rest.models.checks.basic.UIAllChecksBasicModel;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingService;
import ai.dqo.rest.models.checks.mapping.UiToSpecCheckMappingService;
import ai.dqo.rest.models.metadata.ColumnBasicModel;
import ai.dqo.rest.models.metadata.ColumnModel;
import ai.dqo.rest.models.metadata.ColumnProfileModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
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
    private SpecToUiCheckMappingService specToUiCheckMappingService;
    private UiToSpecCheckMappingService uiToSpecCheckMappingService;
    private final ProfilerDataService profilerDataService;

    /**
     * Creates a columns rest controller.
     * @param userHomeContextFactory      User home context factory.
     * @param specToUiCheckMappingService Check mapper to convert the check specification to a UI model.
     * @param uiToSpecCheckMappingService Check mapper to convert the check UI model to a check specification.
     * @param profilerDataService         Profiler data service.
     */
    @Autowired
    public ColumnsController(UserHomeContextFactory userHomeContextFactory,
                             SpecToUiCheckMappingService specToUiCheckMappingService,
                             UiToSpecCheckMappingService uiToSpecCheckMappingService,
                             ProfilerDataService profilerDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
        this.uiToSpecCheckMappingService = uiToSpecCheckMappingService;
        this.profilerDataService = profilerDataService;
    }

    /**
     * Returns a list of columns inside a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of columns inside a table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns")
    @ApiOperation(value = "getColumns", notes = "Returns a list of columns inside a table", response = ColumnBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ColumnBasicModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ColumnBasicModel>> getColumns(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();

        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        Stream<ColumnBasicModel> columnSpecs = tableWrapper.getSpec().getColumns()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(kv -> kv.getKey()))
                .map(kv -> ColumnBasicModel.fromColumnSpecificationForListEntry(
                        connectionName, tableWrapper.getPhysicalTableName(), kv.getKey(), kv.getValue()));

        return new ResponseEntity<>(Flux.fromStream(columnSpecs), HttpStatus.OK);
    }

    /**
     * Returns a list of columns inside a table with the profiler metrics.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of columns inside a table with additional summary of the most recent profiler session.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiledcolumns")
    @ApiOperation(value = "getProfiledColumns",
            notes = "Returns a list of columns inside a table with the metrics captured by the most recent profiler execution.",
            response = ColumnProfileModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ColumnProfileModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ColumnProfileModel>> getProfiledColumns(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                physicalTableName, true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        ProfilerResultsForTableModel mostRecentProfilerMetricsForTable =
                this.profilerDataService.getMostRecentProfilerMetricsForTable(connectionName, physicalTableName,
                        CommonTableNormalizationService.ALL_DATA_DATA_STREAM_NAME);

        Stream<ColumnProfileModel> columnModels = tableWrapper.getSpec().getColumns()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(kv -> kv.getKey()))
                .map(kv -> ColumnProfileModel.fromColumnSpecificationAndProfile(
                        connectionName, tableWrapper.getPhysicalTableName(),
                        kv.getKey(), // column name
                        kv.getValue(), // column specification
                        mostRecentProfilerMetricsForTable.getColumns().get(kv.getKey())));

        return new ResponseEntity<>(Flux.fromStream(columnModels), HttpStatus.OK);
    }

    /**
     * Retrieves the full column specification given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Column full specification for the requested column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
    @ApiOperation(value = "getColumn", notes = "Return the full column specification", response = ColumnModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column returned", response = ColumnModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnModel>> getColumn(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();

        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnModel columnModel = new ColumnModel() {{
            setConnectionName(connectionName);
            setTable(tableWrapper.getPhysicalTableName());
            setColumnName(columnName);
            setColumnHash(columnSpec.getHierarchyId() != null ? columnSpec.getHierarchyId().hashCode64() : null);
            setSpec(columnSpec);
        }};

        return new ResponseEntity<>(Mono.just(columnModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the column basic details given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Basic column details for the requested column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic")
    @ApiOperation(value = "getColumnBasic", notes = "Return the column specification", response = ColumnBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column basic details returned", response = ColumnBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnBasicModel>> getColumnBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();

        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnBasicModel columnBasicModel = ColumnBasicModel.fromColumnSpecification(
                connectionName, tableWrapper.getPhysicalTableName(), columnName, columnSpec);

        return new ResponseEntity<>(Mono.just(columnBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of labels assigned to a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return List of labels assigned to a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels")
    @ApiOperation(value = "getColumnLabels", notes = "Return the list of labels assigned to a column", response = LabelSetSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of labels assigned to a column returned", response = LabelSetSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<LabelSetSpec>> getColumnLabels(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        LabelSetSpec labels = columnSpec.getLabels();
        return new ResponseEntity<>(Mono.justOrEmpty(labels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of comments assigned to a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return List of comments assigned to a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments")
    @ApiOperation(value = "getColumnComments", notes = "Return the list of comments assigned to a column", response = CommentsListSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of comments assigned to a column returned", response = CommentsListSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CommentsListSpec>> getColumnComments(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        CommentsListSpec comments = columnSpec.getComments();
        return new ResponseEntity<>(Mono.justOrEmpty(comments), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of an overridden schedule on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Overridden schedule configuration on a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/scheduleoverride")
    @ApiOperation(value = "getColumnScheduleOverride", notes = "Return the configuration of an overridden schedule on a column", response = RecurringScheduleSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of an overridden schedule on a column returned", response = RecurringScheduleSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RecurringScheduleSpec>> getColumnScheduleOverride(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        RecurringScheduleSpec scheduleOverride = columnSpec.getScheduleOverride();

        return new ResponseEntity<>(Mono.justOrEmpty(scheduleOverride), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of column level data quality ad-hoc checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Data quality ad-hoc checks on a requested column of the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks")
    @ApiOperation(value = "getColumnAdHocChecks", notes = "Return the configuration of column level data quality ad-hoc checks on a column", response = ColumnAdHocCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of column level data quality ad-hoc checks on a column returned", response = ColumnAdHocCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnAdHocCheckCategoriesSpec>> getColumnAdHocChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnAdHocCheckCategoriesSpec checks = columnSpec.getChecks();
        return new ResponseEntity<>(Mono.justOrEmpty(checks), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of daily column level data quality checkpoints on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Daily data quality checkpoints on a requested column of the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/daily")
    @ApiOperation(value = "getColumnCheckpointsDaily", notes = "Return the configuration of daily column level data quality checkpoints on a column", response = ColumnCheckpointsSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of daily column level data quality checkpoints on a column returned", response = ColumnDailyCheckpointCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnDailyCheckpointCategoriesSpec>> getColumnCheckpointsDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnCheckpointsSpec checkpointsSpec = columnSpec.getCheckpoints();
        if (checkpointsSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnDailyCheckpointCategoriesSpec dailyCheckpoints = checkpointsSpec.getDaily();
        return new ResponseEntity<>(Mono.justOrEmpty(dailyCheckpoints), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of monthly column level data quality checkpoints on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Monthly data quality checkpoints on a requested column of the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/monthly")
    @ApiOperation(value = "getColumnCheckpointsMonthly", notes = "Return the configuration of monthly column level data quality checkpoints on a column", response = ColumnCheckpointsSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of monthly column level data quality checkpoints on a column returned", response = ColumnMonthlyCheckpointCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnMonthlyCheckpointCategoriesSpec>> getColumnCheckpointsMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnCheckpointsSpec checkpointsSpec = columnSpec.getCheckpoints();
        if (checkpointsSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnMonthlyCheckpointCategoriesSpec monthlyCheckpoints = checkpointsSpec.getMonthly();
        return new ResponseEntity<>(Mono.justOrEmpty(monthlyCheckpoints), HttpStatus.OK); // 200
    }
    
    /**
     * Retrieves the configuration of daily column level data quality partitioned checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Daily data quality partitioned checks on a requested column of the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/daily")
    @ApiOperation(value = "getColumnPartitionedChecksDaily", notes = "Return the configuration of daily column level data quality partitioned checks on a column", response = ColumnDailyPartitionedCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of daily column level data quality partitioned checks on a column returned", response = ColumnDailyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnDailyPartitionedCheckCategoriesSpec>> getColumnPartitionedChecksDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecksSpec = columnSpec.getPartitionedChecks();
        if (partitionedChecksSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitionedChecks = partitionedChecksSpec.getDaily();
        return new ResponseEntity<>(Mono.justOrEmpty(dailyPartitionedChecks), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of monthly column level data quality partitioned checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Monthly data quality partitioned checks on a requested column of the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/monthly")
    @ApiOperation(value = "getColumnPartitionedChecksMonthly", notes = "Return the configuration of monthly column level data quality partitioned checks on a column", response = ColumnMonthlyPartitionedCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of monthly column level data quality partitioned checks on a column returned", response = ColumnMonthlyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnMonthlyPartitionedCheckCategoriesSpec>> getColumnPartitionedChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecksSpec = columnSpec.getPartitionedChecks();
        if (partitionedChecksSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedChecks = partitionedChecksSpec.getMonthly();
        return new ResponseEntity<>(Mono.justOrEmpty(monthlyPartitionedChecks), HttpStatus.OK); // 200
    }


    /**
     * Retrieves a UI friendly model of column level data quality ad-hoc checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return UI friendly model of data quality ad-hoc checks on a requested column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/ui")
    @ApiOperation(value = "getColumnAdHocChecksUI", notes = "Return a UI friendly model of data quality ad-hoc checks on a column", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality ad-hoc checks on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal iServer Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnAdHocChecksUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnAdHocCheckCategoriesSpec tempChecks = columnSpec.getChecks();
        if (tempChecks == null) {
            tempChecks = new ColumnAdHocCheckCategoriesSpec();
        }
        ColumnAdHocCheckCategoriesSpec checks = tempChecks;
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(checks.getCheckType());
            setTimeScale(checks.getCheckTimeScale());
            setEnabled(true);
        }};

        UIAllChecksModel checksUiModel = this.specToUiCheckMappingService.createUiModel(
                checks,
                checkSearchFilters,
                tableSpec.getDataStreams().getFirstDataStreamMappingName()
        );
        return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality checkpoints on a column given a connection, table name, column name, and time partition.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @return UI friendly model of data quality checkpoints on a requested column for a requested time partition.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timePartition}/ui")
    @ApiOperation(value = "getColumnCheckpointsUI", notes = "Return a UI friendly model of column level data quality checkpoints on a column", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality checkpoints on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time partition"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnCheckpointsUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition") @PathVariable CheckTimeScale timePartition) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnCheckpointsSpec checkpoints = Objects.requireNonNullElseGet(
                columnSpec.getCheckpoints(),
                ColumnCheckpointsSpec::new);

        AbstractRootChecksContainerSpec tempCheckpointsPartition = null;
        switch (timePartition) {
            case daily:
                tempCheckpointsPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        ColumnDailyCheckpointCategoriesSpec::new);
                break;

            case monthly:
                tempCheckpointsPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        ColumnMonthlyCheckpointCategoriesSpec::new);
                break;
        }

        final AbstractRootChecksContainerSpec checkpointsPartition = tempCheckpointsPartition;
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(checkpointsPartition.getCheckType());
            setTimeScale(checkpointsPartition.getCheckTimeScale());
            setEnabled(true);
        }};

        UIAllChecksModel checksUiModel = this.specToUiCheckMappingService.createUiModel(
                checkpointsPartition,
                checkSearchFilters,
                tableSpec.getDataStreams().getFirstDataStreamMappingName()
        );
        return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality partitioned checks on a column given a connection, table name, column name, and time partition.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @return UI friendly model of data quality partitioned checks on a requested column for a requested time partition.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timePartition}/ui")
    @ApiOperation(value = "getColumnPartitionedChecksUI", notes = "Return a UI friendly model of column level data quality partitioned checks on a column", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality partitioned checks on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time partition"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnPartitionedChecksUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition") @PathVariable CheckTimeScale timePartition) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecks = Objects.requireNonNullElseGet(
                columnSpec.getPartitionedChecks(),
                ColumnPartitionedChecksRootSpec::new);

        AbstractRootChecksContainerSpec tempPartitionedChecksPartition = null;
        switch (timePartition) {
            case daily:
                tempPartitionedChecksPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        ColumnDailyPartitionedCheckCategoriesSpec::new);
                break;

            case monthly:
                tempPartitionedChecksPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        ColumnMonthlyPartitionedCheckCategoriesSpec::new);
                break;
        }

        final AbstractRootChecksContainerSpec partitionedChecksPartition = tempPartitionedChecksPartition;
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(partitionedChecksPartition.getCheckType());
            setTimeScale(partitionedChecksPartition.getCheckTimeScale());
            setEnabled(true);
        }};

        UIAllChecksModel checksUiModel = this.specToUiCheckMappingService.createUiModel(
                partitionedChecksPartition,
                checkSearchFilters,
                tableSpec.getDataStreams().getFirstDataStreamMappingName()
        );
        return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
    }


    /**
     * Retrieves a simplistic UI friendly model of column level data quality ad-hoc checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Simplistic UI friendly data quality ad-hoc check list on a requested column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/ui/basic")
    @ApiOperation(value = "getColumnAdHocChecksUIBasic", notes = "Return a simplistic UI friendly model of column level data quality ad-hoc checks on a column", response = UIAllChecksBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic UI model of column level data quality ad-hoc checks on a column returned", response = UIAllChecksBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksBasicModel>> getColumnAdHocChecksUIBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnAdHocCheckCategoriesSpec checks = columnSpec.getChecks();
        if (checks == null) {
            checks = new ColumnAdHocCheckCategoriesSpec();
        }

        UIAllChecksBasicModel checksUiBasicModel = this.specToUiCheckMappingService.createUiBasicModel(checks);
        return new ResponseEntity<>(Mono.just(checksUiBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a simplistic UI friendly model of column level data quality checkpoints on a column given a connection, table name, column name, and time partition.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @return Simplistic UI friendly data quality checkpoints list on a requested column for a requested time partition.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timePartition}/ui/basic")
    @ApiOperation(value = "getColumnCheckpointsUIBasic", notes = "Return a simplistic UI friendly model of column level data quality checkpoints on a column", response = UIAllChecksBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic UI model of column level data quality checkpoints on a column returned", response = UIAllChecksBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time partition"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksBasicModel>> getColumnCheckpointsUIBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition") @PathVariable CheckTimeScale timePartition) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnCheckpointsSpec checkpoints = Objects.requireNonNullElseGet(
                columnSpec.getCheckpoints(),
                ColumnCheckpointsSpec::new);

        AbstractRootChecksContainerSpec checkpointsPartition = null;
        switch (timePartition) {
            case daily:
                checkpointsPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        ColumnDailyCheckpointCategoriesSpec::new);
                break;

            case monthly:
                checkpointsPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        ColumnMonthlyCheckpointCategoriesSpec::new);
                break;
        }

        UIAllChecksBasicModel checksUiBasicModel = this.specToUiCheckMappingService.createUiBasicModel(checkpointsPartition);
        return new ResponseEntity<>(Mono.just(checksUiBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a simplistic UI friendly model of column level data quality partitioned checks on a column given a connection, table name, column name, and time partition.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @return Simplistic UI friendly data quality partitioned checks list on a requested column for a requested time partition.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timePartition}/ui/basic")
    @ApiOperation(value = "getColumnPartitionedChecksUIBasic", notes = "Return a simplistic UI friendly model of column level data quality partitioned checks on a column", response = UIAllChecksBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic UI model of column level data quality partitioned checks on a column returned", response = UIAllChecksBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time partition"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksBasicModel>> getColumnPartitionedChecksUIBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition") @PathVariable CheckTimeScale timePartition) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecks = Objects.requireNonNullElseGet(
                columnSpec.getPartitionedChecks(),
                ColumnPartitionedChecksRootSpec::new);

        AbstractRootChecksContainerSpec partitionedChecksPartition = null;
        switch (timePartition) {
            case daily:
                partitionedChecksPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        ColumnDailyPartitionedCheckCategoriesSpec::new);
                break;

            case monthly:
                partitionedChecksPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        ColumnMonthlyPartitionedCheckCategoriesSpec::new);
                break;
        }

        UIAllChecksBasicModel checksUiBasicModel = this.specToUiCheckMappingService.createUiBasicModel(partitionedChecksPartition);
        return new ResponseEntity<>(Mono.just(checksUiBasicModel), HttpStatus.OK); // 200
    }


    /**
     * Retrieves a UI friendly model of column level data quality ad-hoc checks on a column given a connection, table name, and column name, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param checkCategory  Check category.
     * @param checkName      (Optional) Check name.
     * @return UI friendly model of data quality ad-hoc checks on a requested column.
     */
    @GetMapping(value = {
            "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/ui/filter/{checkCategory}",
            "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/ui/filter/{checkCategory}/{checkName}"
    })
    @ApiOperation(value = "getColumnAdHocChecksUIFilter", notes = "Return a UI friendly model of data quality ad-hoc checks on a column filtered by category and check name", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality ad-hoc checks on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal iServer Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnAdHocChecksUIFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable(required = false) String checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnAdHocCheckCategoriesSpec tempChecks = columnSpec.getChecks();
        if (tempChecks == null) {
            tempChecks = new ColumnAdHocCheckCategoriesSpec();
        }
        ColumnAdHocCheckCategoriesSpec checks = tempChecks;
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(checks.getCheckType());
            setTimeScale(checks.getCheckTimeScale());
            setCheckCategory(checkCategory);
            setCheckName(checkName);
            setEnabled(true);
        }};

        UIAllChecksModel checksUiModel = this.specToUiCheckMappingService.createUiModel(
                checks,
                checkSearchFilters,
                tableSpec.getDataStreams().getFirstDataStreamMappingName()
        );
        return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality checkpoints on a column given a connection, table name, column name, and time partition, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @param checkCategory  Check category.
     * @param checkName      (Optional) Check name.
     * @return UI friendly model of data quality checkpoints on a requested column for a requested time partition, filtered by category and check name.
     */
    @GetMapping(value = {
            "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timePartition}/ui/filter/{checkCategory}",
            "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timePartition}/ui/filter/{checkCategory}/{checkName}"
    })
    @ApiOperation(value = "getColumnCheckpointsUIFilter", notes = "Return a UI friendly model of column level data quality checkpoints on a column filtered by category and check name", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality checkpoints on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time partition"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnCheckpointsUIFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition") @PathVariable CheckTimeScale timePartition,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable(required = false) String checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnCheckpointsSpec checkpoints = Objects.requireNonNullElseGet(
                columnSpec.getCheckpoints(),
                ColumnCheckpointsSpec::new);

        AbstractRootChecksContainerSpec tempCheckpointsPartition = null;
        switch (timePartition) {
            case daily:
                tempCheckpointsPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        ColumnDailyCheckpointCategoriesSpec::new);
                break;

            case monthly:
                tempCheckpointsPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        ColumnMonthlyCheckpointCategoriesSpec::new);
                break;
        }

        final AbstractRootChecksContainerSpec checkpointsPartition = tempCheckpointsPartition;
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(checkpointsPartition.getCheckType());
            setTimeScale(checkpointsPartition.getCheckTimeScale());
            setCheckCategory(checkCategory);
            setCheckName(checkName);
            setEnabled(true);
        }};

        UIAllChecksModel checksUiModel = this.specToUiCheckMappingService.createUiModel(
                checkpointsPartition,
                checkSearchFilters,
                tableSpec.getDataStreams().getFirstDataStreamMappingName()
        );
        return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality partitioned checks on a column given a connection, table name, column name, and time partition, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @param checkCategory  (Optional) Check category.
     * @param checkName      Check name.
     * @return UI friendly model of data quality partitioned checks on a requested column for a requested time partition, filtered by category and check name.
     */
    @GetMapping(value = {
            "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timePartition}/ui/filter/{checkCategory}",
            "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timePartition}/ui/filter/{checkCategory}/{checkName}"
    })
    @ApiOperation(value = "getColumnPartitionedChecksUIFilter", notes = "Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality partitioned checks on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time partition"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnPartitionedChecksUIFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition") @PathVariable CheckTimeScale timePartition,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable(required = false) String checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecks = Objects.requireNonNullElseGet(
                columnSpec.getPartitionedChecks(),
                ColumnPartitionedChecksRootSpec::new);

        AbstractRootChecksContainerSpec tempPartitionedChecksPartition = null;
        switch (timePartition) {
            case daily:
                tempPartitionedChecksPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        ColumnDailyPartitionedCheckCategoriesSpec::new);
                break;

            case monthly:
                tempPartitionedChecksPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        ColumnMonthlyPartitionedCheckCategoriesSpec::new);
                break;
        }

        final AbstractRootChecksContainerSpec partitionedChecksPartition = tempPartitionedChecksPartition;
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(partitionedChecksPartition.getCheckType());
            setTimeScale(partitionedChecksPartition.getCheckTimeScale());
            setCheckCategory(checkCategory);
            setCheckName(checkName);
            setEnabled(true);
        }};

        UIAllChecksModel checksUiModel = this.specToUiCheckMappingService.createUiModel(
                partitionedChecksPartition,
                checkSearchFilters,
                tableSpec.getDataStreams().getFirstDataStreamMappingName()
        );
        return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
    }


    /**
     * Creates (adds) a new column metadata.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnSpec     Column specification.
     * @return Empty response.
     */
    @PostMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
    @ApiOperation(value = "createColumn", notes = "Creates a new column (adds a column metadata to the table)")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New column successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Column with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createColumn(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Column specification") @RequestBody ColumnSpec columnSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec existingColumnSpec = columns.get(columnName);
        if (existingColumnSpec != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409
        }

        columns.put(columnName, columnSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Updates an existing column given a full column specification (also with checks).
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param columnSpec     Full column specification.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
    @ApiOperation(value = "updateColumn", notes = "Updates an existing column specification, changing all the fields (even the column level data quality checks).")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumn(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Column specification") @RequestBody ColumnSpec columnSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec existingColumnSpec = columns.get(columnName);
        if (existingColumnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        columns.replace(columnName, columnSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates an existing column given the basic information.
     * @param connectionName   Connection name.
     * @param schemaName       Schema name.
     * @param tableName        Table name.
     * @param columnName       Column name.
     * @param columnBasicModel New column basic information to store.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic")
    @ApiOperation(value = "updateColumnBasic", notes = "Updates an existing column, changing only the basic information like the expected data type (the data type snapshot).")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column basic information successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Basic column information to store") @RequestBody ColumnBasicModel columnBasicModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        if (!Objects.equals(connectionName, columnBasicModel.getConnectionName()) ||
                !Objects.equals(columnName, columnBasicModel.getColumnName())) {
            return new ResponseEntity<>(Mono.just("Connection name and column name in the model must match the connection name and the column name in the url"),
                    HttpStatus.NOT_ACCEPTABLE); // 406 - wrong values
        }

        if (columnBasicModel.getTable() == null ||
                !Objects.equals(schemaName, columnBasicModel.getTable().getSchemaName()) ||
                !Objects.equals(tableName, columnBasicModel.getTable().getTableName())) {
            return new ResponseEntity<>(Mono.just("Target schema and table name in the table model must match the schema and table name in the url"),
                    HttpStatus.NOT_ACCEPTABLE); // 406 - wrong values
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // TODO: validate the columnSpec
        columnBasicModel.copyToColumnSpecification(columnSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of the overridden schedule on a column.
     * @param connectionName        Connection name.
     * @param schemaName            Schema name.
     * @param tableName             Table name.
     * @param columnName            Column name.
     * @param recurringScheduleSpec Overridden schedule configuration to store or an empty optional to clear the schedule configuration on a column.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/scheduleoverride")
    @ApiOperation(value = "updateColumnScheduleOverride", notes = "Updates the overridden schedule configuration on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's overridden schedule configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnScheduleOverride(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Overridden schedule configuration or an empty object to clear the schedule from the column level")
                @RequestBody Optional<RecurringScheduleSpec> recurringScheduleSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (recurringScheduleSpec.isPresent()) {
            columnSpec.setScheduleOverride(recurringScheduleSpec.get());
        } else {
            columnSpec.setScheduleOverride(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the list of labels assigned to a column.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param labelSetSpec   New list of labels to store on a column or an empty optional to clear the labels.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels")
    @ApiOperation(value = "updateColumnLabels", notes = "Updates the list of labels assigned to a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's list of labels successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnLabels(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column")
            @RequestBody Optional<LabelSetSpec> labelSetSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // TODO: validate the columnSpec
        if (labelSetSpec.isPresent()) {
            columnSpec.setLabels(labelSetSpec.get());
        } else {
            columnSpec.setLabels(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the list of comments assigned to a column.
     * @param connectionName   Connection name.
     * @param schemaName       Schema name.
     * @param tableName        Table name.
     * @param columnName       Column name.
     * @param commentsListSpec New list of comments to store on a column or an empty optional to clear the comments.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments")
    @ApiOperation(value = "updateColumnComments", notes = "Updates the list of comments assigned to a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's list of comments successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnComments(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column")
            @RequestBody Optional<CommentsListSpec> commentsListSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (commentsListSpec.isPresent()) {
            columnSpec.setComments(commentsListSpec.get());
        } else {
            columnSpec.setComments(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
    
    
    /**
     * Updates the configuration of column level data quality ad-hoc checks configured on a column.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param columnCheckCategoriesSpec New configuration of the column level data quality ad-hoc checks to configure on a column or an empty optional to clear the list of ad-hoc checks.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks")
    @ApiOperation(value = "updateColumnAdHocChecks", notes = "Updates configuration of column level data quality ad-hoc checks on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality ad-hoc checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnAdHocChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of column level data quality ad-hoc checks to configure on a column or an empty object to clear the list of assigned data quality ad-hoc checks on the column")
            @RequestBody Optional<ColumnAdHocCheckCategoriesSpec> columnCheckCategoriesSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        if (columnCheckCategoriesSpec.isPresent()) {
            columnSpec.setChecks(columnCheckCategoriesSpec.get());
        } else {
            columnSpec.setChecks(null);
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of daily column level data quality checkpoints configured on a column.
     * @param connectionName             Connection name.
     * @param schemaName                 Schema name.
     * @param tableName                  Table name.
     * @param columnName                 Column name.
     * @param columnDailyCheckpointsSpec New configuration of the daily column level data quality checkpoints to configure on a column or an empty optional to clear the list of daily checkpoints.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/daily")
    @ApiOperation(value = "updateColumnCheckpointsDaily", notes = "Updates configuration of daily column level data quality checkpoints on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily column level data quality checkpoints successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnCheckpointsDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of daily column level data quality checkpoints to configure on a column or an empty object to clear the list of assigned daily data quality checkpoints on the column")
            @RequestBody Optional<ColumnDailyCheckpointCategoriesSpec> columnDailyCheckpointsSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)  ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        
        ColumnCheckpointsSpec checkpointsSpec = columnSpec.getCheckpoints();
        if (checkpointsSpec == null) {
            checkpointsSpec = new ColumnCheckpointsSpec();
        }
        
        if (columnDailyCheckpointsSpec.isPresent()) {
            checkpointsSpec.setDaily(columnDailyCheckpointsSpec.get());
            columnSpec.setCheckpoints(checkpointsSpec);
        } else if (checkpointsSpec.getMonthly() == null) {
            // If there is no monthly checkpoints, and it's been requested to delete daily checkpoints, then delete all.
            columnSpec.setCheckpoints(null);
        } else {
            checkpointsSpec.setDaily(null);
        }
        
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of monthly column level data quality checkpoints configured on a column.
     * @param connectionName               Connection name.
     * @param schemaName                   Schema name.
     * @param tableName                    Table name.
     * @param columnName                   Column name.
     * @param columnMonthlyCheckpointsSpec New configuration of the monthly column level data quality checkpoints to configure on a column or an empty optional to clear the list of monthly checkpoints.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/monthly")
    @ApiOperation(value = "updateColumnCheckpointsMonthly", notes = "Updates configuration of monthly column level data quality checkpoints on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly column level data quality checkpoints successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnCheckpointsMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of monthly column level data quality checkpoints to configure on a column or an empty object to clear the list of assigned monthly data quality checkpoints on the column")
            @RequestBody Optional<ColumnMonthlyCheckpointCategoriesSpec> columnMonthlyCheckpointsSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnCheckpointsSpec checkpointsSpec = columnSpec.getCheckpoints();
        if (checkpointsSpec == null) {
            checkpointsSpec = new ColumnCheckpointsSpec();
        }

        if (columnMonthlyCheckpointsSpec.isPresent()) {
            checkpointsSpec.setMonthly(columnMonthlyCheckpointsSpec.get());
            columnSpec.setCheckpoints(checkpointsSpec);
        } else if (checkpointsSpec.getDaily() == null) {
            // If there is no daily checkpoints, and it's been requested to delete monthly checkpoints, then delete all.
            columnSpec.setCheckpoints(null);
        } else {
            checkpointsSpec.setMonthly(null);
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of daily column level data quality partitioned checks configured on a column.
     * @param connectionName                   Connection name.
     * @param schemaName                       Schema name.
     * @param tableName                        Table name.
     * @param columnName                       Column name.
     * @param columnDailyPartitionedChecksSpec New configuration of the daily column level data quality partitioned checks to configure on a column or an empty optional to clear the list of daily partitioned checks.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/daily")
    @ApiOperation(value = "updateColumnPartitionedChecksDaily", notes = "Updates configuration of daily column level data quality partitioned checks on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily column level data quality partitioned checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnPartitionedChecksDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column")
            @RequestBody Optional<ColumnDailyPartitionedCheckCategoriesSpec> columnDailyPartitionedChecksSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecksSpec = columnSpec.getPartitionedChecks();
        if (partitionedChecksSpec == null) {
            partitionedChecksSpec = new ColumnPartitionedChecksRootSpec();
        }

        if (columnDailyPartitionedChecksSpec.isPresent()) {
            partitionedChecksSpec.setDaily(columnDailyPartitionedChecksSpec.get());
            columnSpec.setPartitionedChecks(partitionedChecksSpec);
        } else if (partitionedChecksSpec.getMonthly() == null) {
            // If there is no monthly partitionedChecks, and it's been requested to delete daily partitionedChecks, then delete all.
            columnSpec.setPartitionedChecks(null);
        } else {
            partitionedChecksSpec.setDaily(null);
        }
        
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of monthly column level data quality partitioned checks configured on a column.
     * @param connectionName                     Connection name.
     * @param schemaName                         Schema name.
     * @param tableName                          Table name.
     * @param columnName                         Column name.
     * @param columnMonthlyPartitionedChecksSpec New configuration of the monthly column level data quality partitioned checks to configure on a column or an empty optional to clear the list of monthly partitioned checks.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/monthly")
    @ApiOperation(value = "updateColumnPartitionedChecksMonthly", notes = "Updates configuration of monthly column level data quality partitioned checks on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly column level data quality partitioned checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnPartitionedChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column")
            @RequestBody Optional<ColumnMonthlyPartitionedCheckCategoriesSpec> columnMonthlyPartitionedChecksSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecksSpec = columnSpec.getPartitionedChecks();
        if (partitionedChecksSpec == null) {
            partitionedChecksSpec = new ColumnPartitionedChecksRootSpec();
        }

        if (columnMonthlyPartitionedChecksSpec.isPresent()) {
            partitionedChecksSpec.setMonthly(columnMonthlyPartitionedChecksSpec.get());
            columnSpec.setPartitionedChecks(partitionedChecksSpec);
        } else if (partitionedChecksSpec.getMonthly() == null) {
            // If there is no daily partitionedChecks, and it's been requested to delete monthly partitionedChecks, then delete all.
            columnSpec.setPartitionedChecks(null);
        } else {
            partitionedChecksSpec.setMonthly(null);
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }


    /**
     * Updates the configuration of column level data quality ad-hoc checks configured on a column from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param uiAllChecksModel          UI model of the column level data quality checks to be applied on the configuration of the data quality ad-hoc checks on a column. Only data quality dimensions and data quality checks that are present in the UI model are updated (patched).
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/ui")
    @ApiOperation(value = "updateColumnAdHocChecksUI", notes = "Updates configuration of column level data quality ad-hoc checks on a column from a UI friendly model.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality ad-hoc checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnAdHocChecksUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("UI model with the changes to be applied to the data quality ad-hoc checks configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        // TODO: validate the columnSpec
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnAdHocCheckCategoriesSpec checksToUpdate = columnSpec.getChecks();
        if (checksToUpdate == null) {
            checksToUpdate = new ColumnAdHocCheckCategoriesSpec();
        }

        if (uiAllChecksModel.isPresent()) {
            this.uiToSpecCheckMappingService.updateAllChecksSpecs(uiAllChecksModel.get(), checksToUpdate);
            if (!checksToUpdate.isDefault()) {
                columnSpec.setChecks(checksToUpdate);
            }
        } else {
            // we cannot just remove all checks because the UI model is a patch, no changes in the patch means no changes to the object
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of column level data quality checkpoints configured on a column, for a given time partition, from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param timePartition             Time partition.
     * @param uiAllChecksModel          UI model of the column level data quality checks to be applied on the configuration of the data quality checkpoints on a column, for a given time partition. Only data quality dimensions and data quality checks that are present in the UI model are updated (patched).
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timePartition}/ui")
    @ApiOperation(value = "updateColumnCheckpointsUI", notes = "Updates configuration of column level data quality checkpoints on a column, for a given time partition, from a UI friendly model.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality checkpoints successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found, or invalid time partition"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnCheckpointsUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition") @PathVariable CheckTimeScale timePartition,
            @ApiParam("UI model with the changes to be applied to the data quality checkpoints configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        // TODO: validate the columnSpec
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnCheckpointsSpec checkpoints = Objects.requireNonNullElseGet(columnSpec.getCheckpoints(), ColumnCheckpointsSpec::new);
        AbstractRootChecksContainerSpec checksToUpdate = null;

        switch (timePartition) {
            case daily:
                checksToUpdate = Objects.requireNonNullElseGet(checkpoints.getDaily(), ColumnDailyCheckpointCategoriesSpec::new);
                break;

            case monthly:
                checksToUpdate = Objects.requireNonNullElseGet(checkpoints.getMonthly(), ColumnMonthlyCheckpointCategoriesSpec::new);
                break;
        }


        if (uiAllChecksModel.isPresent()) {
            this.uiToSpecCheckMappingService.updateAllChecksSpecs(uiAllChecksModel.get(), checksToUpdate);
            
            switch (timePartition) {
                case daily:
                    if (!checksToUpdate.isDefault()) {
                        checkpoints.setDaily((ColumnDailyCheckpointCategoriesSpec) checksToUpdate);
                    }
                    break;

                case monthly:
                    if (!checksToUpdate.isDefault()) {
                        checkpoints.setMonthly((ColumnMonthlyCheckpointCategoriesSpec) checksToUpdate);
                    }
                    break;
            }
            columnSpec.setCheckpoints(checkpoints);
        } else {
            // we cannot just remove all checks because the UI model is a patch, no changes in the patch means no changes to the object
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of column level data quality partitioned checks configured on a column, for a given time partition, from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param timePartition             Time partition.
     * @param uiAllChecksModel          UI model of the column level data quality checks to be applied on the configuration of the data quality partitioned checks on a column, for a given time partition. Only data quality dimensions and data quality checks that are present in the UI model are updated (patched).
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timePartition}/ui")
    @ApiOperation(value = "updateColumnPartitionedChecksUI", notes = "Updates configuration of column level data quality partitioned checks on a column, for a given time partition, from a UI friendly model.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality partitioned checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found, or invalid time partition"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnPartitionedChecksUI(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time partition (eg. daily)") @PathVariable CheckTimeScale timePartition,
            @ApiParam("UI model with the changes to be applied to the data quality partitioned checks configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        // TODO: validate the columnSpec
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecks = Objects.requireNonNullElseGet(columnSpec.getPartitionedChecks(), ColumnPartitionedChecksRootSpec::new);
        AbstractRootChecksContainerSpec checksToUpdate = null;

        switch (timePartition) {
            case daily:
                checksToUpdate = Objects.requireNonNullElseGet(partitionedChecks.getDaily(), ColumnDailyPartitionedCheckCategoriesSpec::new);
                break;

            case monthly:
                checksToUpdate = Objects.requireNonNullElseGet(partitionedChecks.getMonthly(), ColumnMonthlyPartitionedCheckCategoriesSpec::new);
                break;
        }


        if (uiAllChecksModel.isPresent()) {
            this.uiToSpecCheckMappingService.updateAllChecksSpecs(uiAllChecksModel.get(), checksToUpdate);

            switch (timePartition) {
                case daily:
                    if (!checksToUpdate.isDefault()) {
                        partitionedChecks.setDaily((ColumnDailyPartitionedCheckCategoriesSpec) checksToUpdate);
                    }
                    break;

                case monthly:
                    if (!checksToUpdate.isDefault()) {
                        partitionedChecks.setMonthly((ColumnMonthlyPartitionedCheckCategoriesSpec) checksToUpdate);
                    }
                    break;
            }
            columnSpec.setPartitionedChecks(partitionedChecks);
        } else {
            // we cannot just remove all checks because the UI model is a patch, no changes in the patch means no changes to the object
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Deletes a column from the table metadata.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Empty response.
     */
    @DeleteMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
    @ApiOperation(value = "deleteColumn", notes = "Deletes a column from the table")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column successfully deleted"),
            @ApiResponse(code = 404, message = "Column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteColumn(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec existingColumnSpec = columns.get(columnName);
        if (existingColumnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        columns.remove(columnName);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }


    /**
     * Reads a wrapper for a table, given userHomeContext, connection, schema and table names.
     * @param userHomeContext User-home context.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @return TableWrapper object for the table. Null if not found.
     */
    protected TableWrapper readTableWrapper(
            UserHomeContext userHomeContext,
            String connectionName,
            String schemaName,
            String tableName) {
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        return tableWrapper;
    }

    /**
     * Reads specifications for a column, given userHomeContext, connection, schema, table and column names.
     * @param userHomeContext User-home context.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param columnName      Column name.
     * @return ColumnSpec object for the column. Null if not found.
     */
    protected ColumnSpec readColumnSpec(
            UserHomeContext userHomeContext,
            String connectionName,
            String schemaName,
            String tableName,
            String columnName) {
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return null;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        if (tableSpec == null) {
            return null;
        }

        return tableSpec.getColumns().get(columnName);
    }
}
