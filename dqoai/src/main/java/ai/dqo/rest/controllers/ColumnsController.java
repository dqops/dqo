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
import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.ColumnDailyCheckpointCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnMonthlyCheckpointCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableDailyCheckpointCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableMonthlyCheckpointCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.metadata.basespecs.AbstractSpec;
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
import java.util.NoSuchElementException;
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
                .map(kv -> ColumnBasicModel.fromColumnSpecification(
                        connectionName, tableWrapper.getPhysicalTableName(), kv.getKey(), kv.getValue().trim()));

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

    private <T extends AbstractSpec> T getColumnGenericChecks(
            Function<ColumnSpec, T> extractorFromSpec,
            String connectionName,
            String schemaName,
            String tableName,
            String columnName) throws NoSuchElementException {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            throw new NoSuchElementException("Connection " + connectionName + " not found");
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (tableWrapper == null) {
            throw new NoSuchElementException("Table " + tableName + " not found");
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            throw new NoSuchElementException("Column " + columnName + " not found");
        }

        return extractorFromSpec.apply(columnSpec);
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
        ColumnAdHocCheckCategoriesSpec checks;
        try {
            checks = this.getColumnGenericChecks(
                    ColumnSpec::getChecks,
                    connectionName,
                    schemaName,
                    tableName,
                    columnName
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(Mono.justOrEmpty(checks), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of column level data quality checkpoints on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Data quality checkpoints on a requested column of the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints")
    @ApiOperation(value = "getColumnCheckpoints", notes = "Return the configuration of column level data quality checkpoints on a column", response = ColumnCheckpointsSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of column level data quality checkpoints on a column returned", response = ColumnCheckpointsSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnCheckpointsSpec>> getColumnCheckpoints(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        ColumnCheckpointsSpec checkpoints;
        try {
            checkpoints = this.getColumnGenericChecks(
                    ColumnSpec::getCheckpoints,
                    connectionName,
                    schemaName,
                    tableName,
                    columnName
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(Mono.justOrEmpty(checkpoints), HttpStatus.OK); // 200
    }
    // TODO: Implement GET "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timePartition}"

    /**
     * Retrieves the configuration of column level data quality partitioned checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Data quality partitioned checks on a requested column of the table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedChecks")
    @ApiOperation(value = "getColumnPartitionedChecks", notes = "Return the configuration of column level data quality partitioned checks on a column", response = ColumnPartitionedChecksRootSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of column level data quality partitioned checks on a column returned", response = ColumnPartitionedChecksRootSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnPartitionedChecksRootSpec>> getColumnPartitionedChecks(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
        ColumnPartitionedChecksRootSpec partitionedChecks;
        try {
            partitionedChecks = this.getColumnGenericChecks(
                    ColumnSpec::getPartitionedChecks,
                    connectionName,
                    schemaName,
                    tableName,
                    columnName
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        return new ResponseEntity<>(Mono.justOrEmpty(partitionedChecks), HttpStatus.OK); // 200
    }
    // TODO: Implement GET "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedChecks/{timePartition}"

    private <T extends AbstractRootChecksContainerSpec> UIAllChecksModel getColumnGenericChecksUI(
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
     * @param timePartition  Time partition (daily, monthly, etc.).
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
            @Parameter(description = "Time partition (eg. daily)") @PathVariable String timePartition) {
        UIAllChecksModel checksUiModel = this.getColumnGenericChecksUI(
                spec -> {
                    ColumnCheckpointsSpec checkpoints = spec.getCheckpoints();
                    if (checkpoints == null) {
                        checkpoints = new ColumnCheckpointsSpec();
                    }

                    // TODO: Enum for allowed time partitions
                    if (timePartition.equals("daily")) {
                        ColumnDailyCheckpointCategoriesSpec checkpointsDaily = checkpoints.getDaily();
                        return (checkpointsDaily != null) ? checkpointsDaily : new ColumnDailyCheckpointCategoriesSpec();
                    } else if (timePartition.equals("monthly")) {
                        ColumnMonthlyCheckpointCategoriesSpec checkpointsMonthly = checkpoints.getMonthly();
                        return (checkpointsMonthly != null) ? checkpointsMonthly : new ColumnMonthlyCheckpointCategoriesSpec();
                    } else {
                        return null;
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
     * @param timePartition  Time partition (daily, monthly, etc.).
     * @return UI friendly data quality partitioned checks list on a requested column for a requested time partition.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedChecks/{timePartition}/ui")
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
            @Parameter(description = "Time partition (eg. daily)") @PathVariable String timePartition) {
        UIAllChecksModel checksUiModel = this.getColumnGenericChecksUI(
                spec -> {
                    ColumnPartitionedChecksRootSpec partitionedChecks = spec.getPartitionedChecks();
                    if (partitionedChecks == null) {
                        partitionedChecks = new ColumnPartitionedChecksRootSpec();
                    }

                    // TODO: Enum for allowed time partitions
                    if (timePartition.equals("daily")) {
                        ColumnDailyPartitionedCheckCategoriesSpec partitionedChecksDaily = partitionedChecks.getDaily();
                        return (partitionedChecksDaily != null) ? partitionedChecksDaily : new ColumnDailyPartitionedCheckCategoriesSpec();
                    } else if (timePartition.equals("monthly")) {
                        ColumnMonthlyPartitionedCheckCategoriesSpec partitionedChecksMonthly = partitionedChecks.getMonthly();
                        return (partitionedChecksMonthly != null) ? partitionedChecksMonthly : new ColumnMonthlyPartitionedCheckCategoriesSpec();
                    } else {
                        return null;
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

    private <T extends AbstractSpec> boolean updateColumnGenericChecks(
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
     * Updates the configuration of column level data quality checkpoints configured on a column.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param columnCheckpointsSpec New configuration of the column level data quality checkpoints to configure on a column or an empty optional to clear the list of checkpoints.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints")
    @ApiOperation(value = "updateColumnCheckpoints", notes = "Updates configuration of column level data quality checkpoints on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality checkpoints successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnCheckpoints(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of column level data quality checkpoints to configure on a column or an empty object to clear the list of assigned data quality checkpoints on the column")
            @RequestBody Optional<ColumnCheckpointsSpec> columnCheckpointsSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecks(
                spec -> {
                    if (columnCheckpointsSpec.isPresent()) {
                        spec.setCheckpoints(columnCheckpointsSpec.get());
                    } else {
                        spec.setCheckpoints(null);
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
     * Updates the configuration of column level data quality partitioned checks configured on a column.
     * @param connectionName                  Connection name.
     * @param schemaName                      Schema name.
     * @param tableName                       Table name.
     * @param columnName                      Column name.
     * @param columnPartitionedChecksRootSpec New configuration of the column level data quality partitioned checks to configure on a column or an empty optional to clear the list of partitioned checks.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedChecks")
    @ApiOperation(value = "updateColumnPartitionedChecks", notes = "Updates configuration of column level data quality partitioned checks on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality partitioned checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnPartitionedChecks(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column")
            @RequestBody Optional<ColumnPartitionedChecksRootSpec> columnPartitionedChecksRootSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecks(
                spec -> {
                    if (columnPartitionedChecksRootSpec.isPresent()) {
                        spec.setPartitionedChecks(columnPartitionedChecksRootSpec.get());
                    } else {
                        spec.setPartitionedChecks(null);
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

    private <T extends AbstractRootChecksContainerSpec> boolean updateColumnGenericChecksUI(
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
     * @param timePartition             Time partition (daily, monthly, etc.).
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
            @Parameter(description = "Time partition (eg. daily)") @PathVariable String timePartition,
            @Parameter(description = "UI model with the changes to be applied to the data quality checkpoints configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName) ||
                Strings.isNullOrEmpty(timePartition)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecksUI(
                spec -> {
                    ColumnCheckpointsSpec checkpoints = spec.getCheckpoints();

                    // TODO: Enum for time partitions
                    if (timePartition.equals("daily")) {
                        if (checkpoints == null || checkpoints.getDaily() == null) {
                            return new ColumnDailyCheckpointCategoriesSpec();
                        }

                        return checkpoints.getDaily();
                    } else if (timePartition.equals("monthly")) {
                        if (checkpoints == null || checkpoints.getMonthly() == null) {
                            return new ColumnMonthlyCheckpointCategoriesSpec();
                        }

                        return checkpoints.getMonthly();
                    } else {
                        return null; // invalid time partition
                    }
                },
                (spec, check) -> {
                    ColumnCheckpointsSpec checkpoints = spec.getCheckpoints();
                    if (checkpoints == null) {
                        checkpoints = new ColumnCheckpointsSpec();
                    }
                    
                    // TODO: Enum for time partitions
                    if (timePartition.equals("daily")) {
                        if (!check.isDefault()) {
                            checkpoints.setDaily((ColumnDailyCheckpointCategoriesSpec) check);
                        }
                    } else if (timePartition.equals("monthly")) {
                        if (!check.isDefault()) {
                            checkpoints.setMonthly((ColumnMonthlyCheckpointCategoriesSpec) check);
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
     * @param timePartition             Time partition (daily, monthly, etc.).
     * @param uiAllChecksModel          UI model of the column level data quality checks to be applied on the configuration of the data quality partitioned checks on a column, for a given time partition. Only data quality dimensions and data quality checks that are present in the UI model are updated (patched).
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedChecks/{timePartition}/ui")
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
            @Parameter(description = "Time partition (eg. daily)") @PathVariable String timePartition,
            @Parameter(description = "UI model with the changes to be applied to the data quality partitioned checks configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName) ||
                Strings.isNullOrEmpty(timePartition)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateColumnGenericChecksUI(
                spec -> {
                    ColumnPartitionedChecksRootSpec partitionedChecks = spec.getPartitionedChecks();

                    // TODO: Enum for time partitions
                    if (timePartition.equals("daily")) {
                        if (partitionedChecks == null || partitionedChecks.getDaily() == null) {
                            return new ColumnDailyPartitionedCheckCategoriesSpec();
                        }

                        return partitionedChecks.getDaily();
                    } else if (timePartition.equals("monthly")) {
                        if (partitionedChecks == null || partitionedChecks.getMonthly() == null) {
                            return new ColumnMonthlyPartitionedCheckCategoriesSpec();
                        }

                        return partitionedChecks.getMonthly();
                    } else {
                        return null; // invalid time partition
                    }
                },
                (spec, check) -> {
                    ColumnPartitionedChecksRootSpec partitionedChecks = spec.getPartitionedChecks();
                    if (partitionedChecks == null) {
                        partitionedChecks = new ColumnPartitionedChecksRootSpec();
                    }

                    // TODO: Enum for time partitions
                    if (timePartition.equals("daily")) {
                        if (!check.isDefault()) {
                            partitionedChecks.setDaily((ColumnDailyPartitionedCheckCategoriesSpec) check);
                        }
                    } else if (timePartition.equals("monthly")) {
                        if (!check.isDefault()) {
                            partitionedChecks.setMonthly((ColumnMonthlyPartitionedCheckCategoriesSpec) check);
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
