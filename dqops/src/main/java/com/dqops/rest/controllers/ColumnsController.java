/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.rest.controllers;

import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnDailyRecurringCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnMonthlyRecurringCheckCategoriesSpec;
import com.dqops.checks.column.recurring.ColumnRecurringChecksRootSpec;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.data.statistics.services.models.StatisticsResultsForColumnModel;
import com.dqops.data.statistics.services.models.StatisticsResultsForTableModel;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.*;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.basicmodels.CheckContainerBasicModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.metadata.ColumnService;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of columns inside a table.
 */
@RestController
@RequestMapping("api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Columns", description = "Manages columns inside a table")
public class ColumnsController {
    private final ColumnService columnService;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoHomeContextFactory dqoHomeContextFactory;
    private SpecToModelCheckMappingService specToModelCheckMappingService;
    private ModelToSpecCheckMappingService modelToSpecCheckMappingService;
    private StatisticsDataService statisticsDataService;

    /**
     * Creates a columns rest controller.
     * @param columnService               Column logic service.
     * @param userHomeContextFactory      User home context factory.
     * @param dqoHomeContextFactory       DQO home context factory, used to find built-in sensors.
     * @param specToModelCheckMappingService Check mapper to convert the check specification to a model.
     * @param modelToSpecCheckMappingService Check mapper to convert the check model to a check specification.
     * @param statisticsDataService       Statistics data service.
     */
    @Autowired
    public ColumnsController(ColumnService columnService,
                             UserHomeContextFactory userHomeContextFactory,
                             DqoHomeContextFactory dqoHomeContextFactory,
                             SpecToModelCheckMappingService specToModelCheckMappingService,
                             ModelToSpecCheckMappingService modelToSpecCheckMappingService,
                             StatisticsDataService statisticsDataService) {
        this.columnService = columnService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
        this.statisticsDataService = statisticsDataService;
    }

    /**
     * Returns a list of columns inside a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of columns inside a table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns", produces = "application/json")
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
     * Returns a list of columns inside a table with the statistics metrics.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of columns inside a table with additional summary of the most recent profiler session.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/statistics", produces = "application/json")
    @ApiOperation(value = "getColumnsStatistics",
            notes = "Returns a list of columns inside a table with the metrics captured by the most recent statistics collection.",
            response = TableColumnsStatisticsModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableColumnsStatisticsModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableColumnsStatisticsModel>> getColumnsStatistics(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                physicalTableName, true);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        StatisticsResultsForTableModel mostRecentStatisticsMetricsForTable =
                this.statisticsDataService.getMostRecentStatisticsForTable(connectionName, physicalTableName,
                        CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME, true);

        List<ColumnStatisticsModel> columnModels = tableWrapper.getSpec().getColumns()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(kv -> kv.getKey()))
                .map(kv -> ColumnStatisticsModel.fromColumnSpecificationAndStatistic(
                        connectionName, tableWrapper.getPhysicalTableName(),
                        kv.getKey(), // column name
                        kv.getValue(), // column specification
                        mostRecentStatisticsMetricsForTable.getColumns().get(kv.getKey())))
                .collect(Collectors.toList());

        TableColumnsStatisticsModel resultModel = new TableColumnsStatisticsModel();
        resultModel.setConnectionName(connectionName);
        resultModel.setTable(physicalTableName);
        resultModel.setColumnStatistics(columnModels);

        resultModel.setCollectColumnStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
        {{
            setConnectionName(connectionName);
            setSchemaTableName(physicalTableName.toTableSearchFilter());
            setTarget(StatisticsCollectorTarget.column);
            setEnabled(true);
        }});

        return new ResponseEntity<>(Mono.just(resultModel), HttpStatus.OK);
    }

    /**
     * Retrieves the full column specification given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Column full specification for the requested column.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}", produces = "application/json")
    @ApiOperation(value = "getColumn", notes = "Returns the full column specification", response = ColumnModel.class)
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
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic", produces = "application/json")
    @ApiOperation(value = "getColumnBasic", notes = "Returns the column specification", response = ColumnBasicModel.class)
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
     * Returns a basic model of requested column with the statistics metrics.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Basic column details for the requested column with a summary of the most recent profiler session.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/statistics", produces = "application/json")
    @ApiOperation(value = "getColumnStatistics",
            notes = "Returns the column specification with the metrics captured by the most recent statistics collection.",
            response = ColumnStatisticsModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column statistics returned", response = ColumnStatisticsModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnStatisticsModel>> getColumnStatistics(
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

        StatisticsResultsForColumnModel mostRecentStatisticsMetricsForColumn =
                this.statisticsDataService.getMostRecentStatisticsForColumn(
                        connectionName, tableWrapper.getPhysicalTableName(), columnName,
                        CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME);

        ColumnStatisticsModel columnModel = ColumnStatisticsModel.fromColumnSpecificationAndStatistic(
                connectionName, tableWrapper.getPhysicalTableName(), columnName, columnSpec,
                mostRecentStatisticsMetricsForColumn);

        return new ResponseEntity<>(Mono.just(columnModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of labels assigned to a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return List of labels assigned to a column.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels", produces = "application/json")
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
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments", produces = "application/json")
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
     * Retrieves the configuration of column level data quality profiling checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Data quality profiling checks on a requested column of the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingChecks", notes = "Return the configuration of column level data quality profiling checks on a column", response = ColumnProfilingCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of column level data quality profiling checks on a column returned", response = ColumnProfilingCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnProfilingCheckCategoriesSpec>> getColumnProfilingChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnProfilingCheckCategoriesSpec checks = columnSpec.getProfilingChecks();
        return new ResponseEntity<>(Mono.justOrEmpty(checks), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of daily column level data quality recurring on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Daily data quality recurring on a requested column of the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/daily", produces = "application/json")
    @ApiOperation(value = "getColumnRecurringChecksDaily", notes = "Return the configuration of daily column level data quality recurring on a column", response = ColumnRecurringChecksRootSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of daily column level data quality recurring on a column returned", response = ColumnDailyRecurringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnDailyRecurringCheckCategoriesSpec>> getColumnRecurringChecksDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnRecurringChecksRootSpec recurringSpec = columnSpec.getRecurringChecks();
        if (recurringSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnDailyRecurringCheckCategoriesSpec dailyRecurring = recurringSpec.getDaily();
        return new ResponseEntity<>(Mono.justOrEmpty(dailyRecurring), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of monthly column level data quality recurring on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Monthly data quality recurring on a requested column of the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/monthly", produces = "application/json")
    @ApiOperation(value = "getColumnRecurringChecksMonthly", notes = "Return the configuration of monthly column level data quality recurring on a column", response = ColumnRecurringChecksRootSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of monthly column level data quality recurring on a column returned", response = ColumnMonthlyRecurringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnMonthlyRecurringCheckCategoriesSpec>> getColumnRecurringChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnRecurringChecksRootSpec recurringSpec = columnSpec.getRecurringChecks();
        if (recurringSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnMonthlyRecurringCheckCategoriesSpec monthlyRecurring = recurringSpec.getMonthly();
        return new ResponseEntity<>(Mono.justOrEmpty(monthlyRecurring), HttpStatus.OK); // 200
    }
    
    /**
     * Retrieves the configuration of daily column level data quality partitioned checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Daily data quality partitioned checks on a requested column of the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily", produces = "application/json")
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

        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitioned = partitionedChecksSpec.getDaily();
        return new ResponseEntity<>(Mono.justOrEmpty(dailyPartitioned), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of monthly column level data quality partitioned checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Monthly data quality partitioned checks on a requested column of the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly", produces = "application/json")
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

        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = partitionedChecksSpec.getMonthly();
        return new ResponseEntity<>(Mono.justOrEmpty(monthlyPartitioned), HttpStatus.OK); // 200
    }


    /**
     * Retrieves a UI friendly model of column level data quality profiling checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return UI friendly model of data quality profiling checks on a requested column.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingChecksModel", notes = "Return a UI friendly model of data quality profiling checks on a column", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality profiling checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getColumnProfilingChecksModel(
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(checks.getCheckType());
            setTimeScale(checks.getCheckTimeScale());
            setEnabled(true);
        }};

        CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                checks,
                checkSearchFilters,
                connectionWrapper.getSpec(),
                tableSpec,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality recurring on a column given a connection, table name, column name, and time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @return UI friendly model of data quality recurring on a requested column for a requested time scale.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getColumnRecurringChecksModel", notes = "Return a UI friendly model of column level data quality recurring on a column", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality recurring on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getColumnRecurringChecksModel(
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.recurring, timeScale, false);
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(checks.getCheckType());
            setTimeScale(checks.getCheckTimeScale());
            setEnabled(true);
        }};

        CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                checks,
                checkSearchFilters,
                connectionWrapper.getSpec(),
                tableSpec,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality partitioned checks on a column given a connection, table name, column name, and time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @return UI friendly model of data quality partitioned checks on a requested column for a requested time scale.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getColumnPartitionedChecksModel", notes = "Return a UI friendly model of column level data quality partitioned checks on a column", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality partitioned checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getColumnPartitionedChecksModel(
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, false);

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumnName(columnName);
            setCheckType(checks.getCheckType());
            setTimeScale(checks.getCheckTimeScale());
            setEnabled(true);
        }};

        CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                checks,
                checkSearchFilters,
                connectionWrapper.getSpec(),
                tableSpec,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }


    /**
     * Retrieves a simplistic UI friendly model of column level data quality profiling checks on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Simplistic UI friendly data quality profiling check list on a requested column.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/basic", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingChecksBasicModel", notes = "Return a simplistic UI friendly model of column level data quality profiling checks on a column", response = CheckContainerBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic model of column level data quality profiling checks on a column returned", response = CheckContainerBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerBasicModel>> getColumnProfilingChecksBasicModel(
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);
        CheckContainerBasicModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a simplistic UI friendly model of column level data quality recurring on a column given a connection, table name, column name, and time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @return Simplistic UI friendly data quality recurring list on a requested column for a requested time scale.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/{timeScale}/model/basic", produces = "application/json")
    @ApiOperation(value = "getColumnRecurringChecksBasicModel", notes = "Return a simplistic UI friendly model of column level data quality recurring on a column", response = CheckContainerBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic model of column level data quality recurring on a column returned", response = CheckContainerBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerBasicModel>> getColumnRecurringChecksBasicModel(
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.recurring, timeScale, false);
        CheckContainerBasicModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a simplistic UI friendly model of column level data quality partitioned checks on a column given a connection, table name, column name, and time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @return Simplistic UI friendly data quality partitioned checks list on a requested column for a requested time scale.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/basic", produces = "application/json")
    @ApiOperation(value = "getColumnPartitionedChecksBasicModel", notes = "Return a simplistic UI friendly model of column level data quality partitioned checks on a column", response = CheckContainerBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic model of column level data quality partitioned checks on a column returned", response = CheckContainerBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerBasicModel>> getColumnPartitionedChecksBasicModel(
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, false);
        CheckContainerBasicModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality profiling checks on a column given a connection, table name, and column name, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly model of data quality profiling checks on a requested column.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingChecksModelFilter", notes = "Return a UI friendly model of data quality profiling checks on a column filtered by category and check name", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality profiling checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getColumnProfilingChecksModelFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);

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

        CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                checks,
                checkSearchFilters,
                connectionWrapper.getSpec(),
                tableSpec,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality recurring on a column given a connection, table name, column name, and time scale, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly model of data quality recurring on a requested column for a requested time scale, filtered by category and check name.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/{timeScale}/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getColumnRecurringChecksModelFilter", notes = "Return a UI friendly model of column level data quality recurring on a column filtered by category and check name", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality recurring on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getColumnRecurringChecksModelFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.recurring, timeScale, false);
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

        CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                checks,
                checkSearchFilters,
                connectionWrapper.getSpec(),
                tableSpec,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality partitioned checks on a column given a connection, table name, column name, and time scale, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly model of data quality partitioned checks on a requested column for a requested time scale, filtered by category and check name.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getColumnPartitionedChecksModelFilter", notes = "Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality partitioned checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getColumnPartitionedChecksModelFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, false);
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

        CheckContainerModel checksModel = this.specToModelCheckMappingService.createModel(
                checks,
                checkSearchFilters,
                connectionWrapper.getSpec(),
                tableSpec,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }

    /**
     * Creates (adds) a new column metadata.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnSpec     Column specification.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}", consumes = "application/json", produces = "application/json")
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
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}", consumes = "application/json", produces = "application/json")
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
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic", consumes = "application/json", produces = "application/json")
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
     * Updates the list of labels assigned to a column.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param labelSetSpec   New list of labels to store on a column or an empty optional to clear the labels.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels", consumes = "application/json", produces = "application/json")
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
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments", consumes = "application/json", produces = "application/json")
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
     * Updates the configuration of column level data quality profiling checks configured on a column.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param columnCheckCategoriesSpec New configuration of the column level data quality profiling checks to configure on a column or an empty optional to clear the list of profiling checks.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnProfilingChecks", notes = "Updates configuration of column level data quality profiling checks on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality profiling checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnProfilingChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of column level data quality profiling checks to configure on a column or an empty object to clear the list of assigned data quality profiling checks on the column")
            @RequestBody Optional<ColumnProfilingCheckCategoriesSpec> columnCheckCategoriesSpec) {
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
            columnSpec.setProfilingChecks(columnCheckCategoriesSpec.get());
        } else {
            columnSpec.setProfilingChecks(null);
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of daily column level data quality recurring checks configured on a column.
     * @param connectionName             Connection name.
     * @param schemaName                 Schema name.
     * @param tableName                  Table name.
     * @param columnName                 Column name.
     * @param columnDailyRecurringSpec New configuration of the daily column level data quality recurring checks to configure on a column or an empty optional to clear the list of daily recurring.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnRecurringChecksDaily", notes = "Updates configuration of daily column level data quality recurring on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily column level data quality recurring successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnRecurringChecksDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of daily column level data quality recurring to configure on a column or an empty object to clear the list of assigned daily data quality recurring on the column")
            @RequestBody Optional<ColumnDailyRecurringCheckCategoriesSpec> columnDailyRecurringSpec) {
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
        
        ColumnRecurringChecksRootSpec recurringChecksSpec = columnSpec.getRecurringChecks();
        if (recurringChecksSpec == null) {
            recurringChecksSpec = new ColumnRecurringChecksRootSpec();
        }
        
        if (columnDailyRecurringSpec.isPresent()) {
            recurringChecksSpec.setDaily(columnDailyRecurringSpec.get());
            columnSpec.setRecurringChecks(recurringChecksSpec);
        } else if (recurringChecksSpec.getMonthly() == null) {
            // If there is no monthly recurring checks, and it's been requested to delete daily recurring checks, then delete all.
            columnSpec.setRecurringChecks(null);
        } else {
            recurringChecksSpec.setDaily(null);
        }
        
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of monthly column level data quality recurring checks configured on a column.
     * @param connectionName               Connection name.
     * @param schemaName                   Schema name.
     * @param tableName                    Table name.
     * @param columnName                   Column name.
     * @param columnMonthlyRecurringSpec New configuration of the monthly column level data quality recurring checks to configure on a column or an empty optional to clear the list of monthly recurring.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnRecurringChecksMonthly", notes = "Updates configuration of monthly column level data quality recurring checks on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly column level data quality recurring checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnRecurringChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of monthly column level data quality recurring to configure on a column or an empty object to clear the list of assigned monthly data quality recurring on the column")
            @RequestBody Optional<ColumnMonthlyRecurringCheckCategoriesSpec> columnMonthlyRecurringSpec) {
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

        ColumnRecurringChecksRootSpec recurringChecksSpec = columnSpec.getRecurringChecks();
        if (recurringChecksSpec == null) {
            recurringChecksSpec = new ColumnRecurringChecksRootSpec();
        }

        if (columnMonthlyRecurringSpec.isPresent()) {
            recurringChecksSpec.setMonthly(columnMonthlyRecurringSpec.get());
            columnSpec.setRecurringChecks(recurringChecksSpec);
        } else if (recurringChecksSpec.getDaily() == null) {
            // If there is no daily recurring checks, and it's been requested to delete monthly recurring checks, then delete all.
            columnSpec.setRecurringChecks(null);
        } else {
            recurringChecksSpec.setMonthly(null);
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of daily column level data quality partitioned checks configured on a column.
     * @param connectionName                   Connection name.
     * @param schemaName                       Schema name.
     * @param tableName                        Table name.
     * @param columnName                       Column name.
     * @param columnDailyPartitionedSpec New configuration of the daily column level data quality partitioned checks to configure on a column or an empty optional to clear the list of daily partitioned checks.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily", consumes = "application/json", produces = "application/json")
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
            @RequestBody Optional<ColumnDailyPartitionedCheckCategoriesSpec> columnDailyPartitionedSpec) {
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

        if (columnDailyPartitionedSpec.isPresent()) {
            partitionedChecksSpec.setDaily(columnDailyPartitionedSpec.get());
            columnSpec.setPartitionedChecks(partitionedChecksSpec);
        } else if (partitionedChecksSpec.getMonthly() == null) {
            // If there is no monthly partitioned checks, and it's been requested to delete daily partitioned checks, then delete all.
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
     * @param columnMonthlyPartitionedSpec New configuration of the monthly column level data quality partitioned checks to configure on a column or an empty optional to clear the list of monthly partitioned checks.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
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
            @RequestBody Optional<ColumnMonthlyPartitionedCheckCategoriesSpec> columnMonthlyPartitionedSpec) {
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

        if (columnMonthlyPartitionedSpec.isPresent()) {
            partitionedChecksSpec.setMonthly(columnMonthlyPartitionedSpec.get());
            columnSpec.setPartitionedChecks(partitionedChecksSpec);
        } else if (partitionedChecksSpec.getMonthly() == null) {
            // If there is no daily partitioned checks, and it's been requested to delete monthly partitioned checks, then delete all.
            columnSpec.setPartitionedChecks(null);
        } else {
            partitionedChecksSpec.setMonthly(null);
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }


    /**
     * Updates the configuration of column level data quality profiling checks configured on a column from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param checkContainerModel       Model of the column level data quality checks to be applied on the configuration of the data quality profiling checks on a column. Only data quality dimensions and data quality checks that are present in the model are updated (patched).
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnProfilingChecksModel", notes = "Updates configuration of column level data quality profiling checks on a column from a UI friendly model.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality profiling checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnProfilingChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
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

        AbstractRootChecksContainerSpec checksToUpdate = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, true);

        if (checkContainerModel.isPresent()) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checksToUpdate);
            if (!checksToUpdate.isDefault()) {
                columnSpec.setColumnCheckRootContainer(checksToUpdate);
            }
        } else {
            // we cannot just remove all checks because the model is a patch, no changes in the patch means no changes to the object
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of column level data quality recurring configured on a column, for a given time scale, from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param timeScale                 Time scale.
     * @param checkContainerModel       Model of the column level data quality checks to be applied on the configuration of the data quality recurring on a column, for a given time scale. Only data quality dimensions and data quality checks that are present in the model are updated (patched).
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/{timeScale}/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnRecurringChecksModel", notes = "Updates configuration of column level data quality recurring on a column, for a given time scale, from a UI friendly model.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality recurring successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found, or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnRecurringChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality recurring configuration")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
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

        AbstractRootChecksContainerSpec checksToUpdate = columnSpec.getColumnCheckRootContainer(CheckType.recurring, timeScale, true);

        if (checkContainerModel.isPresent()) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checksToUpdate);
            if (!checksToUpdate.isDefault()) {
                columnSpec.setColumnCheckRootContainer(checksToUpdate);
            }
        } else {
            // we cannot just remove all checks because the model is a patch, no changes in the patch means no changes to the object
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of column level data quality partitioned checks configured on a column, for a given time scale, from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param timeScale                 Time scale.
     * @param allChecksModel            Model of the column level data quality checks to be applied on the configuration of the data quality partitioned checks on a column, for a given time scale. Only data quality dimensions and data quality checks that are present in the model are updated (patched).
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnPartitionedChecksModel", notes = "Updates configuration of column level data quality partitioned checks on a column, for a given time scale, from a UI friendly model.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality partitioned checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found, or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnPartitionedChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality partitioned checks configuration")
            @RequestBody Optional<CheckContainerModel> allChecksModel) {
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

        AbstractRootChecksContainerSpec checksToUpdate = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, true);

        if (allChecksModel.isPresent()) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(allChecksModel.get(), checksToUpdate);
            if (!checksToUpdate.isDefault()) {
                columnSpec.setColumnCheckRootContainer(checksToUpdate);
            }
        } else {
            // we cannot just remove all checks because the model is a patch, no changes in the patch means no changes to the object
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
     * @return Deferred operations job id.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}", produces = "application/json")
    @ApiOperation(value = "deleteColumn", notes = "Deletes a column from the table")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> deleteColumn(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        ColumnSpec existingColumnSpec = tableWrapper.getSpec().getColumns().get(columnName);
        if (existingColumnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        PushJobResult<DeleteStoredDataQueueJobResult> backgroundJob = this.columnService.deleteColumn(
                connectionName, tableWrapper.getPhysicalTableName(), columnName);
        return new ResponseEntity<>(Mono.just(backgroundJob.getJobId()), HttpStatus.OK); // 200
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
