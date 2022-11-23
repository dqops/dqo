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
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingService;
import ai.dqo.rest.models.checks.mapping.UiToSpecCheckMappingService;
import ai.dqo.rest.models.metadata.ColumnBasicModel;
import ai.dqo.rest.models.metadata.ColumnModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import com.google.common.base.Strings;
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
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
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

    /**
     * Creates a columns rest controller.
     * @param userHomeContextFactory      User home context factory.
     * @param specToUiCheckMappingService Check mapper to convert the check specification to a UI model.
     * @param uiToSpecCheckMappingService Check mapper to convert the check UI model to a check specification.
     */
    @Autowired
    public ColumnsController(UserHomeContextFactory userHomeContextFactory,
                             SpecToUiCheckMappingService specToUiCheckMappingService,
                             UiToSpecCheckMappingService uiToSpecCheckMappingService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
        this.uiToSpecCheckMappingService = uiToSpecCheckMappingService;
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

        Stream<ColumnBasicModel> columnSpecs = tableWrapper.getSpec().getColumns()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(kv -> kv.getKey()))
                .map(kv -> ColumnBasicModel.fromColumnSpecificationForListEntry(
                        connectionName, tableWrapper.getPhysicalTableName(), kv.getKey(), kv.getValue()));

        return new ResponseEntity<>(Flux.fromStream(columnSpecs), HttpStatus.OK);
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        RecurringScheduleSpec scheduleOverride = columnSpec.getScheduleOverride();

        return new ResponseEntity<>(Mono.justOrEmpty(scheduleOverride), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of an overridden time series on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Overridden time series configuration on a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/timeseriesoverride")
    @ApiOperation(value = "getColumnTimeSeriesOverride", notes = "Return the configuration of an overridden time series on a column", response = TimeSeriesConfigurationSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of an overridden time series on a column returned", response = TimeSeriesConfigurationSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TimeSeriesConfigurationSpec>> getColumnTimeSeriesOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        TimeSeriesConfigurationSpec timeSeriesOverride = columnSpec.getTimeSeriesOverride();

        return new ResponseEntity<>(Mono.justOrEmpty(timeSeriesOverride), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of an overridden data streams mapping on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Overridden data streams configuration on a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/datastreamsoverride")
    @ApiOperation(value = "getColumnDataStreamsOverride", notes = "Return the configuration of overridden data streams on a column", response = DataStreamMappingSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of overridden data streams on a column returned", response = DataStreamMappingSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Deprecated
    public ResponseEntity<Mono<DataStreamMappingSpec>> getColumnDataStreamsOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        DataStreamMappingSpec dataStreamsOverride = columnSpec.getDataStreamsOverride();

        return new ResponseEntity<>(Mono.justOrEmpty(dataStreamsOverride), HttpStatus.OK); // 200
    }

    protected <T extends AbstractRootChecksContainerSpec> Optional<T> getColumnGenericChecks(
            Function<ColumnSpec, T> extractorFromSpec,
            String connectionName,
            String schemaName,
            String tableName,
            String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return null;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return null;
        }

        return Optional.ofNullable(extractorFromSpec.apply(columnSpec));
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        Optional<ColumnAdHocCheckCategoriesSpec> checks = this.getColumnGenericChecks(
                ColumnSpec::getChecks,
                connectionName,
                schemaName,
                tableName,
                columnName
        );
        
        if (checks == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        } else if (checks.isPresent()) {
            return new ResponseEntity<>(Mono.just(checks.get()), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        Optional<ColumnDailyCheckpointCategoriesSpec> dailyCheckpoints = this.getColumnGenericChecks(
                spec -> {
                    ColumnCheckpointsSpec checkpointsSpec = spec.getCheckpoints();
                    if (checkpointsSpec == null) {
                        return null;
                    } else {
                        return checkpointsSpec.getDaily();
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );
       
        if (dailyCheckpoints == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        } else if (dailyCheckpoints.isPresent()) {
            return new ResponseEntity<>(Mono.just(dailyCheckpoints.get()), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        Optional<ColumnMonthlyCheckpointCategoriesSpec> monthlyCheckpoints = this.getColumnGenericChecks(
                spec -> {
                    ColumnCheckpointsSpec checkpointsSpec = spec.getCheckpoints();
                    if (checkpointsSpec == null) {
                        return null;
                    } else {
                        return checkpointsSpec.getMonthly();
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (monthlyCheckpoints == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        } else if (monthlyCheckpoints.isPresent()) {
            return new ResponseEntity<>(Mono.just(monthlyCheckpoints.get()), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        Optional<ColumnDailyPartitionedCheckCategoriesSpec> dailyPartitionedChecks = this.getColumnGenericChecks(
                spec -> {
                    ColumnPartitionedChecksRootSpec partitionedChecksSpec = spec.getPartitionedChecks();
                    if (partitionedChecksSpec == null) {
                        return null;
                    } else {
                        return partitionedChecksSpec.getDaily();
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );
        
        if (dailyPartitionedChecks == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        } else if (dailyPartitionedChecks.isPresent()) {
            return new ResponseEntity<>(Mono.just(dailyPartitionedChecks.get()), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        Optional<ColumnMonthlyPartitionedCheckCategoriesSpec> monthlyPartitionedChecks = this.getColumnGenericChecks(
                spec -> {
                    ColumnPartitionedChecksRootSpec partitionedChecksSpec = spec.getPartitionedChecks();
                    if (partitionedChecksSpec == null) {
                        return null;
                    } else {
                        return partitionedChecksSpec.getMonthly();
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (monthlyPartitionedChecks == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        } else if (monthlyPartitionedChecks.isPresent()) {
            return new ResponseEntity<>(Mono.just(monthlyPartitionedChecks.get()), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }
    }

    protected <T extends AbstractRootChecksContainerSpec> UIAllChecksModel getColumnGenericChecksUI(
            Function<ColumnSpec, T> columnSpecToRootCheck,
            String connectionName,
            String schemaName,
            String tableName,
            String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return null;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return null;
        }

        T genericChecks = columnSpecToRootCheck.apply(columnSpec);
        if (genericChecks == null) {
            return null;
        }
        
        return this.specToUiCheckMappingService.createUiModel(genericChecks);
    }
    
    /**
     * Retrieves a UI friendly model of column level data quality ad-hoc checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return UI friendly data quality ad-hoc check list on a requested column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/ui")
    @ApiOperation(value = "getColumnAdHocChecksUI", notes = "Return a UI friendly model of column level data quality ad-hoc checks on a column", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality ad-hoc checks on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnAdHocChecksUI(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        UIAllChecksModel checksUiModel = this.getColumnGenericChecksUI(
                spec -> {
                    ColumnAdHocCheckCategoriesSpec checks = spec.getChecks();
                    if (checks == null) {
                        checks = new ColumnAdHocCheckCategoriesSpec();
                    }
                    return checks;
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );
        
        if (checksUiModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        else {
            return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
        }
    }

    /**
     * Retrieves a UI friendly model of column level data quality checkpoints on a column given a connection, table name, column name, and time partition.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @return UI friendly data quality checkpoints list on a requested column for a requested time partition.
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Time partition") @PathVariable CheckTimeScale timePartition) {
        UIAllChecksModel checksUiModel = this.getColumnGenericChecksUI(
                spec -> {
                    ColumnCheckpointsSpec checkpoints = spec.getCheckpoints();
                    if (checkpoints == null) {
                        checkpoints = new ColumnCheckpointsSpec();
                    }

                    switch (timePartition) {
                        case DAILY -> {
                            ColumnDailyCheckpointCategoriesSpec checkpointsDaily = checkpoints.getDaily();
                            return (checkpointsDaily != null) ? checkpointsDaily : new ColumnDailyCheckpointCategoriesSpec();
                        }
                        case MONTHLY -> {
                            ColumnMonthlyCheckpointCategoriesSpec checkpointsMonthly = checkpoints.getMonthly();
                            return (checkpointsMonthly != null) ? checkpointsMonthly : new ColumnMonthlyCheckpointCategoriesSpec();
                        }
                        default -> {
                            return null;
                        }
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (checksUiModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        else {
            return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
        }
    }

    /**
     * Retrieves a UI friendly model of column level data quality partitioned checks on a column given a connection, table name, column name, and time partition.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timePartition  Time partition.
     * @return UI friendly data quality partitioned checks list on a requested column for a requested time partition.
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Time partition") @PathVariable CheckTimeScale timePartition) {
        UIAllChecksModel checksUiModel = this.getColumnGenericChecksUI(
                spec -> {
                    ColumnPartitionedChecksRootSpec partitionedChecks = spec.getPartitionedChecks();
                    if (partitionedChecks == null) {
                        partitionedChecks = new ColumnPartitionedChecksRootSpec();
                    }

                    switch (timePartition) {
                        case DAILY -> {
                            ColumnDailyPartitionedCheckCategoriesSpec partitionedChecksDaily = partitionedChecks.getDaily();
                            return (partitionedChecksDaily != null) ? partitionedChecksDaily : new ColumnDailyPartitionedCheckCategoriesSpec();
                        }
                        case MONTHLY -> {
                            ColumnMonthlyPartitionedCheckCategoriesSpec partitionedChecksMonthly = partitionedChecks.getMonthly();
                            return (partitionedChecksMonthly != null) ? partitionedChecksMonthly : new ColumnMonthlyPartitionedCheckCategoriesSpec();
                        }
                        default -> {
                            return null;
                        }
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (checksUiModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        else {
            return new ResponseEntity<>(Mono.just(checksUiModel), HttpStatus.OK); // 200
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Column specification") @RequestBody ColumnSpec columnSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Column specification") @RequestBody ColumnSpec columnSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Basic column information to store") @RequestBody ColumnBasicModel columnBasicModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        if (!Objects.equals(connectionName, columnBasicModel.getConnectionName()) ||
                !Objects.equals(columnName, columnBasicModel.getColumnName())) {
            return new ResponseEntity<>(Mono.just("Connection name and column name in the model must match the connection name and the column name in the url"),
                    HttpStatus.NOT_ACCEPTABLE); // 400 - wrong values
        }

        if (columnBasicModel.getTable() == null ||
                !Objects.equals(schemaName, columnBasicModel.getTable().getSchemaName()) ||
                !Objects.equals(tableName, columnBasicModel.getTable().getTableName())) {
            return new ResponseEntity<>(Mono.just("Target schema and table name in the table model must match the schema and table name in the url"),
                    HttpStatus.NOT_ACCEPTABLE); // 400 - wrong values
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Overridden schedule configuration or an empty object to clear the schedule from the column level")
                @RequestBody Optional<RecurringScheduleSpec> recurringScheduleSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
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
     * Updates the configuration of the overridden data streams configuration on a column.
     * @param connectionName              Connection name.
     * @param schemaName                  Schema name.
     * @param tableName                   Table name.
     * @param columnName                  Column name.
     * @param dataStreamsConfigurationSpec Overridden data streams configuration to store or an empty optional to clear the data streams configuration on a column.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/datastreamsoverride")
    @ApiOperation(value = "updateColumnDataStreamsOverride", notes = "Updates the overridden data streams configuration on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's overridden data streams configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Deprecated
    public ResponseEntity<Mono<?>> updateColumnDataStreamsOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Overridden data streams configuration or an empty object to clear the customized data streams configuration from the column level")
            @RequestBody Optional<DataStreamMappingSpec> dataStreamsConfigurationSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (dataStreamsConfigurationSpec.isPresent()) {
            columnSpec.setDataStreamsOverride(dataStreamsConfigurationSpec.get());
        } else {
            columnSpec.setDataStreamsOverride(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of the overridden time series on a column.
     * @param connectionName              Connection name.
     * @param schemaName                  Schema name.
     * @param tableName                   Table name.
     * @param columnName                  Column name.
     * @param timeSeriesConfigurationSpec Overridden time series configuration to store or an empty optional to clear the time series configuration on a column.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/timeseriesoverride")
    @ApiOperation(value = "updateColumnTimeSeriesOverride", notes = "Updates the overridden time series configuration on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's overridden time series configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnTimeSeriesOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Overridden time series configuration or an empty object to clear the customized time series from the column level")
            @RequestBody Optional<TimeSeriesConfigurationSpec> timeSeriesConfigurationSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (timeSeriesConfigurationSpec.isPresent()) {
            columnSpec.setTimeSeriesOverride(timeSeriesConfigurationSpec.get());
        } else {
            columnSpec.setTimeSeriesOverride(null);
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column")
            @RequestBody Optional<LabelSetSpec> labelSetSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column")
            @RequestBody Optional<CommentsListSpec> commentsListSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
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

    protected <T extends AbstractRootChecksContainerSpec> boolean updateColumnGenericChecks(
            Consumer<ColumnSpec> columnSpecUpdater,
            String connectionName,
            String schemaName,
            String tableName,
            String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return false;
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return false;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        // TODO: validate the column spec
        if (columnSpec == null) {
            return false;
        }
        
        columnSpecUpdater.accept(columnSpec);
        userHomeContext.flush();
        
        return true;
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of column level data quality ad-hoc checks to configure on a column or an empty object to clear the list of assigned data quality ad-hoc checks on the column")
            @RequestBody Optional<ColumnAdHocCheckCategoriesSpec> columnCheckCategoriesSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }
        
        boolean success = this.updateColumnGenericChecks(
                spec -> {
                    if (columnCheckCategoriesSpec.isPresent()) {
                        spec.setChecks(columnCheckCategoriesSpec.get());
                    } else {
                        spec.setChecks(null);
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of daily column level data quality checkpoints to configure on a column or an empty object to clear the list of assigned daily data quality checkpoints on the column")
            @RequestBody Optional<ColumnDailyCheckpointCategoriesSpec> columnDailyCheckpointsSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecks(
                spec -> {
                    ColumnCheckpointsSpec checkpointsSpec = spec.getCheckpoints();
                    
                    if (checkpointsSpec == null && columnDailyCheckpointsSpec.isPresent()) {
                        checkpointsSpec = new ColumnCheckpointsSpec();
                        checkpointsSpec.setDaily(columnDailyCheckpointsSpec.get());
                        spec.setCheckpoints(checkpointsSpec);
                    } else if (checkpointsSpec != null) {
                        if (columnDailyCheckpointsSpec.isPresent()) {
                            checkpointsSpec.setDaily(columnDailyCheckpointsSpec.get());
                        } else {
                            checkpointsSpec.setDaily(null);
                            // TODO: Is this cleaning necessary / beneficial in any way?
                            if (checkpointsSpec.getMonthly() == null) {
                                spec.setCheckpoints(null);
                            }
                        }
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of monthly column level data quality checkpoints to configure on a column or an empty object to clear the list of assigned monthly data quality checkpoints on the column")
            @RequestBody Optional<ColumnMonthlyCheckpointCategoriesSpec> columnMonthlyCheckpointsSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecks(
                spec -> {
                    ColumnCheckpointsSpec checkpointsSpec = spec.getCheckpoints();

                    if (checkpointsSpec == null && columnMonthlyCheckpointsSpec.isPresent()) {
                        checkpointsSpec = new ColumnCheckpointsSpec();
                        checkpointsSpec.setMonthly(columnMonthlyCheckpointsSpec.get());
                        spec.setCheckpoints(checkpointsSpec);
                    } else if (checkpointsSpec != null) {
                        if (columnMonthlyCheckpointsSpec.isPresent()) {
                            checkpointsSpec.setMonthly(columnMonthlyCheckpointsSpec.get());
                        } else {
                            checkpointsSpec.setMonthly(null);
                            // TODO: Is this cleaning necessary / beneficial in any way?
                            if (checkpointsSpec.getMonthly() == null) {
                                spec.setCheckpoints(null);
                            }
                        }
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column")
            @RequestBody Optional<ColumnDailyPartitionedCheckCategoriesSpec> columnDailyPartitionedChecksSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecks(
                spec -> {

                    ColumnPartitionedChecksRootSpec partitionedChecksSpec = spec.getPartitionedChecks();

                    if (partitionedChecksSpec == null && columnDailyPartitionedChecksSpec.isPresent()) {
                        partitionedChecksSpec = new ColumnPartitionedChecksRootSpec();
                        partitionedChecksSpec.setDaily(columnDailyPartitionedChecksSpec.get());
                        spec.setPartitionedChecks(partitionedChecksSpec);
                    } else if (partitionedChecksSpec != null) {
                        if (columnDailyPartitionedChecksSpec.isPresent()) {
                            partitionedChecksSpec.setDaily(columnDailyPartitionedChecksSpec.get());
                        } else {
                            partitionedChecksSpec.setDaily(null);
                            // TODO: Is this cleaning necessary / beneficial in any way?
                            if (partitionedChecksSpec.getDaily() == null) {
                                spec.setPartitionedChecks(null);
                            }
                        }
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column")
            @RequestBody Optional<ColumnMonthlyPartitionedCheckCategoriesSpec> columnMonthlyPartitionedChecksSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecks(
                spec -> {

                    ColumnPartitionedChecksRootSpec partitionedChecksSpec = spec.getPartitionedChecks();

                    if (partitionedChecksSpec == null && columnMonthlyPartitionedChecksSpec.isPresent()) {
                        partitionedChecksSpec = new ColumnPartitionedChecksRootSpec();
                        partitionedChecksSpec.setMonthly(columnMonthlyPartitionedChecksSpec.get());
                        spec.setPartitionedChecks(partitionedChecksSpec);
                    } else if (partitionedChecksSpec != null) {
                        if (columnMonthlyPartitionedChecksSpec.isPresent()) {
                            partitionedChecksSpec.setMonthly(columnMonthlyPartitionedChecksSpec.get());
                        } else {
                            partitionedChecksSpec.setMonthly(null);
                            // TODO: Is this cleaning necessary / beneficial in any way?
                            if (partitionedChecksSpec.getMonthly() == null) {
                                spec.setPartitionedChecks(null);
                            }
                        }
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    protected <T extends AbstractRootChecksContainerSpec> boolean updateColumnGenericChecksUI(
            Function<ColumnSpec, T> columnSpecToRootCheck,
            BiConsumer<ColumnSpec, T> specUpdateCheck,
            String connectionName,
            String schemaName,
            String tableName,
            String columnName,
            Optional<UIAllChecksModel> uiAllChecksModel) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return false;
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            return false;
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        // TODO: validate the columnSpec
        if (columnSpec == null) {
            return false;
        }
        
        T checksToUpdate = columnSpecToRootCheck.apply(columnSpec);
        if (checksToUpdate == null) {
            return false;
        }

        if (uiAllChecksModel.isPresent()) {
            this.uiToSpecCheckMappingService.updateAllChecksSpecs(uiAllChecksModel.get(), checksToUpdate);
            specUpdateCheck.accept(columnSpec, checksToUpdate);
        } else {
            // we cannot just remove all checks because the UI model is a patch, no changes in the patch means no changes to the object
        }

        userHomeContext.flush();
        return true;
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "UI model with the changes to be applied to the data quality ad-hoc checks configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecksUI(
                spec -> {
                    ColumnAdHocCheckCategoriesSpec checks = spec.getChecks();
                    if (checks == null) {
                        checks = new ColumnAdHocCheckCategoriesSpec();
                    }
                    return checks;
                },
                (spec, check) -> {
                    if (!check.isDefault()) {
                        spec.setChecks(check);
                    }
                },
                connectionName,
                schemaName,
                tableName,
                columnName,
                uiAllChecksModel
        );
        
        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Time partition") @PathVariable CheckTimeScale timePartition,
            @Parameter(description = "UI model with the changes to be applied to the data quality checkpoints configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecksUI(
                spec -> {
                    ColumnCheckpointsSpec checkpoints = spec.getCheckpoints();

                    switch (timePartition) {
                        case DAILY -> {
                            if (checkpoints == null || checkpoints.getDaily() == null) {
                                return new ColumnDailyCheckpointCategoriesSpec();
                            }
                            return checkpoints.getDaily();
                        }
                        case MONTHLY -> {
                            if (checkpoints == null || checkpoints.getMonthly() == null) {
                                return new ColumnMonthlyCheckpointCategoriesSpec();
                            }
                            return checkpoints.getMonthly();
                        }
                        default -> {
                            return null;
                        }
                    }
                },
                (spec, check) -> {
                    ColumnCheckpointsSpec checkpoints = spec.getCheckpoints();
                    if (checkpoints == null) {
                        checkpoints = new ColumnCheckpointsSpec();
                    }

                    switch (timePartition) {
                        case DAILY -> {
                            if (!check.isDefault()) {
                                checkpoints.setDaily((ColumnDailyCheckpointCategoriesSpec) check);
                            }
                        }
                        case MONTHLY -> {
                            if (!check.isDefault()) {
                                checkpoints.setMonthly((ColumnMonthlyCheckpointCategoriesSpec) check);
                            }
                        }
                    }
                    spec.setCheckpoints(checkpoints);
                },
                connectionName,
                schemaName,
                tableName,
                columnName,
                uiAllChecksModel
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Time partition (eg. daily)") @PathVariable CheckTimeScale timePartition,
            @Parameter(description = "UI model with the changes to be applied to the data quality partitioned checks configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecksUI(
                spec -> {
                    ColumnPartitionedChecksRootSpec partitionedChecks = spec.getPartitionedChecks();

                    switch (timePartition) {
                        case DAILY -> {
                            if (partitionedChecks == null || partitionedChecks.getDaily() == null) {
                                return new ColumnDailyPartitionedCheckCategoriesSpec();
                            }
                            return partitionedChecks.getDaily();
                        }
                        case MONTHLY -> {
                            if (partitionedChecks == null || partitionedChecks.getMonthly() == null) {
                                return new ColumnMonthlyPartitionedCheckCategoriesSpec();
                            }
                            return partitionedChecks.getMonthly();
                        }
                        default -> {
                            return null;
                        }
                    }
                },
                (spec, check) -> {
                    ColumnPartitionedChecksRootSpec partitionedChecks = spec.getPartitionedChecks();
                    if (partitionedChecks == null) {
                        partitionedChecks = new ColumnPartitionedChecksRootSpec();
                    }

                    switch (timePartition) {
                        case DAILY -> {
                            if (!check.isDefault()) {
                                partitionedChecks.setDaily((ColumnDailyPartitionedCheckCategoriesSpec) check);
                            }
                        }
                        case MONTHLY -> {
                            if (!check.isDefault()) {
                                partitionedChecks.setMonthly((ColumnMonthlyPartitionedCheckCategoriesSpec) check);
                            }
                        }
                    }
                    spec.setPartitionedChecks(partitionedChecks);
                },
                connectionName,
                schemaName,
                tableName,
                columnName,
                uiAllChecksModel
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableList tables = connectionWrapper.getTables();
        TableWrapper tableWrapper = tables.getByObjectName(new PhysicalTableName(schemaName, tableName), true);
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
}
