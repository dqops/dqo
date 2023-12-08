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
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringChecksRootSpec;
import com.dqops.checks.column.monitoring.ColumnMonthlyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.models.DeleteStoredDataResult;
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
import com.dqops.rest.models.metadata.ColumnListModel;
import com.dqops.rest.models.metadata.ColumnModel;
import com.dqops.rest.models.metadata.ColumnStatisticsModel;
import com.dqops.rest.models.metadata.TableColumnsStatisticsModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.basicmodels.CheckContainerListModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.metadata.ColumnService;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of columns inside a table.
 */
@RestController
@RequestMapping("/api/connections")
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
     * @param dqoHomeContextFactory       DQOps home context factory, used to find built-in sensors.
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
    @ApiOperation(value = "getColumns", notes = "Returns a list of columns inside a table", response = ColumnListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ColumnListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<ColumnListModel>> getColumns(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());

        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
        boolean isOperator = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        Stream<ColumnListModel> columnSpecs = tableWrapper.getSpec().getColumns()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(kv -> kv.getKey()))
                .map(kv -> ColumnListModel.fromColumnSpecificationForListEntry(
                        connectionName, tableWrapper.getPhysicalTableName(), kv.getKey(), kv.getValue(), isEditor, isOperator));

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
            response = TableColumnsStatisticsModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableColumnsStatisticsModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<TableColumnsStatisticsModel>> getColumnsStatistics(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
                        CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME, true, principal.getDomainIdentity());
        boolean canCollectStatistics = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        List<ColumnStatisticsModel> columnModels = tableWrapper.getSpec().getColumns()
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(kv -> kv.getKey()))
                .map(kv -> ColumnStatisticsModel.fromColumnSpecificationAndStatistic(
                        connectionName, tableWrapper.getPhysicalTableName(),
                        kv.getKey(), // column name
                        kv.getValue(), // column specification
                        mostRecentStatisticsMetricsForTable.getColumns().get(kv.getKey()),
                        canCollectStatistics))
                .collect(Collectors.toList());

        TableColumnsStatisticsModel resultModel = new TableColumnsStatisticsModel();
        resultModel.setConnectionName(connectionName);
        resultModel.setTable(physicalTableName);
        resultModel.setColumnStatistics(columnModels);
        resultModel.setCanCollectStatistics(canCollectStatistics);

        resultModel.setCollectColumnStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
        {{
            setConnection(connectionName);
            setFullTableName(physicalTableName.toTableSearchFilter());
//            setTarget(StatisticsCollectorTarget.column);
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
    @ApiOperation(value = "getColumn", notes = "Returns the full column specification", response = ColumnModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column returned", response = ColumnModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnModel>> getColumn(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());

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
            setCanEdit(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
            setYamlParsingError(tableSpec.getYamlParsingError());
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
    @ApiOperation(value = "getColumnBasic", notes = "Returns the column specification", response = ColumnListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column basic details returned", response = ColumnListModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnListModel>> getColumnBasic(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());

        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnListModel columnListModel = ColumnListModel.fromColumnSpecification(
                connectionName, tableWrapper.getPhysicalTableName(), columnName, columnSpec,
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

        return new ResponseEntity<>(Mono.just(columnListModel), HttpStatus.OK); // 200
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
            response = ColumnStatisticsModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column statistics returned", response = ColumnStatisticsModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnStatisticsModel>> getColumnStatistics(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());

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
                        CommonTableNormalizationService.NO_GROUPING_DATA_GROUP_NAME,
                        principal.getDomainIdentity());

        boolean canRunStatisticsJob = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        ColumnStatisticsModel columnModel = ColumnStatisticsModel.fromColumnSpecificationAndStatistic(
                connectionName, tableWrapper.getPhysicalTableName(), columnName, columnSpec,
                mostRecentStatisticsMetricsForColumn, canRunStatisticsJob);

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
    @ApiOperation(value = "getColumnLabels", notes = "Return the list of labels assigned to a column", response = LabelSetSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of labels assigned to a column returned", response = LabelSetSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<LabelSetSpec>> getColumnLabels(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
    @ApiOperation(value = "getColumnComments", notes = "Return the list of comments assigned to a column", response = CommentsListSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of comments assigned to a column returned", response = CommentsListSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CommentsListSpec>> getColumnComments(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
    @ApiOperation(value = "getColumnProfilingChecks", notes = "Return the configuration of column level data quality profiling checks on a column",
            response = ColumnProfilingCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of column level data quality profiling checks on a column returned", response = ColumnProfilingCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnProfilingCheckCategoriesSpec>> getColumnProfilingChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnProfilingCheckCategoriesSpec checks = columnSpec.getProfilingChecks();
        return new ResponseEntity<>(Mono.justOrEmpty(checks), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of daily column level data quality monitoring on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Daily data quality monitoring on a requested column of the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringChecksDaily", notes = "Return the configuration of daily column level data quality monitoring on a column",
            response = ColumnMonitoringChecksRootSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of daily column level data quality monitoring on a column returned", response = ColumnDailyMonitoringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnDailyMonitoringCheckCategoriesSpec>> getColumnMonitoringChecksDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnMonitoringChecksRootSpec monitoringSpec = columnSpec.getMonitoringChecks();
        if (monitoringSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnDailyMonitoringCheckCategoriesSpec dailyMonitoring = monitoringSpec.getDaily();
        return new ResponseEntity<>(Mono.justOrEmpty(dailyMonitoring), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of monthly column level data quality monitoring on a column given a connection, table name, and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Monthly data quality monitoring on a requested column of the table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringChecksMonthly", notes = "Return the configuration of monthly column level data quality monitoring on a column",
            response = ColumnMonitoringChecksRootSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of monthly column level data quality monitoring on a column returned", response = ColumnMonthlyMonitoringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnMonthlyMonitoringCheckCategoriesSpec>> getColumnMonitoringChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnMonitoringChecksRootSpec monitoringSpec = columnSpec.getMonitoringChecks();
        if (monitoringSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        ColumnMonthlyMonitoringCheckCategoriesSpec monthlyMonitoring = monitoringSpec.getMonthly();
        return new ResponseEntity<>(Mono.justOrEmpty(monthlyMonitoring), HttpStatus.OK); // 200
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
    @ApiOperation(value = "getColumnPartitionedChecksDaily", notes = "Return the configuration of daily column level data quality partitioned checks on a column",
            response = ColumnDailyPartitionedCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of daily column level data quality partitioned checks on a column returned", response = ColumnDailyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnDailyPartitionedCheckCategoriesSpec>> getColumnPartitionedChecksDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
    @ApiOperation(value = "getColumnPartitionedChecksMonthly", notes = "Return the configuration of monthly column level data quality partitioned checks on a column",
            response = ColumnMonthlyPartitionedCheckCategoriesSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of monthly column level data quality partitioned checks on a column returned",
                    response = ColumnMonthlyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<ColumnMonthlyPartitionedCheckCategoriesSpec>> getColumnPartitionedChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
    @ApiOperation(value = "getColumnProfilingChecksModel", notes = "Return a UI friendly model of data quality profiling checks on a column",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality profiling checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getColumnProfilingChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
            setConnection(connectionWrapper.getName());
            setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumn(columnName);
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
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality monitoring on a column given a connection, table name, column name, and time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @return UI friendly model of data quality monitoring on a requested column for a requested time scale.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringChecksModel", notes = "Return a UI friendly model of column level data quality monitoring on a column",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality monitoring on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getColumnMonitoringChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, false);
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnection(connectionWrapper.getName());
            setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumn(columnName);
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
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

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
    @ApiOperation(value = "getColumnPartitionedChecksModel", notes = "Return a UI friendly model of column level data quality partitioned checks on a column",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality partitioned checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getColumnPartitionedChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
            setConnection(connectionWrapper.getName());
            setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumn(columnName);
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
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

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
    @ApiOperation(value = "getColumnProfilingChecksBasicModel", notes = "Return a simplistic UI friendly model of column level data quality profiling checks on a column",
            response = CheckContainerListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic model of column level data quality profiling checks on a column returned", response = CheckContainerListModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerListModel>> getColumnProfilingChecksBasicModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
        CheckContainerListModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

        return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a simplistic UI friendly model of column level data quality monitoring on a column given a connection, table name, column name, and time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @return Simplistic UI friendly data quality monitoring list on a requested column for a requested time scale.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/basic", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringChecksBasicModel", notes = "Return a simplistic UI friendly model of column level data quality monitoring on a column",
            response = CheckContainerListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic model of column level data quality monitoring on a column returned", response = CheckContainerListModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerListModel>> getColumnMonitoringChecksBasicModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, false);
        CheckContainerListModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

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
    @ApiOperation(value = "getColumnPartitionedChecksBasicModel", notes = "Return a simplistic UI friendly model of column level data quality partitioned checks on a column",
            response = CheckContainerListModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Simplistic model of column level data quality partitioned checks on a column returned", response = CheckContainerListModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerListModel>> getColumnPartitionedChecksBasicModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
        CheckContainerListModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

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
    @ApiOperation(value = "getColumnProfilingChecksModelFilter", notes = "Return a UI friendly model of data quality profiling checks on a column filtered by category and check name",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality profiling checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getColumnProfilingChecksModelFilter(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
            setConnection(connectionWrapper.getName());
            setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumn(columnName);
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
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

        return new ResponseEntity<>(Mono.just(checksModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality monitoring on a column given a connection, table name, column name, and time scale, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale  Time scale.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly model of data quality monitoring on a requested column for a requested time scale, filtered by category and check name.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringChecksModelFilter", notes = "Return a UI friendly model of column level data quality monitoring on a column filtered by category and check name",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality monitoring on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getColumnMonitoringChecksModelFilter(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, false);
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnection(connectionWrapper.getName());
            setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumn(columnName);
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
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

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
    @ApiOperation(value = "getColumnPartitionedChecksModelFilter", notes = "Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name",
            response = CheckContainerModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Model of column level data quality partitioned checks on a column returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found, or invalid time scale"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<CheckContainerModel>> getColumnPartitionedChecksModelFilter(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Check category") @PathVariable String checkCategory,
            @ApiParam("Check name") @PathVariable String checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
            setConnection(connectionWrapper.getName());
            setFullTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
            setColumn(columnName);
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
                connectionWrapper.getSpec().getProviderType(),
                principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));

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
    @ApiOperation(value = "createColumn", notes = "Creates a new column (adds a column metadata to the table)", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New column successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Column with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> createColumn(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
    @ApiOperation(value = "updateColumn", notes = "Updates an existing column specification, changing all the fields (even the column level data quality checks).", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateColumn(
            @AuthenticationPrincipal DqoUserPrincipal principal,
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

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
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
     * @param columnListModel New column basic information to store.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnBasic", notes = "Updates an existing column, changing only the basic information like the expected data type (the data type snapshot).", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column basic information successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateColumnBasic(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Basic column information to store") @RequestBody ColumnListModel columnListModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        if (!Objects.equals(connectionName, columnListModel.getConnectionName()) ||
                !Objects.equals(columnName, columnListModel.getColumnName())) {
            return new ResponseEntity<>(Mono.empty(),
                    HttpStatus.NOT_ACCEPTABLE); // 406 - wrong values
        }

        if (columnListModel.getTable() == null ||
                !Objects.equals(schemaName, columnListModel.getTable().getSchemaName()) ||
                !Objects.equals(tableName, columnListModel.getTable().getTableName())) {
            return new ResponseEntity<>(Mono.empty(),
                    HttpStatus.NOT_ACCEPTABLE); // 406 - wrong values
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        // TODO: validate the columnSpec
        columnListModel.copyToColumnSpecification(columnSpec);
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
    @ApiOperation(value = "updateColumnLabels", notes = "Updates the list of labels assigned to a column.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's list of labels successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnLabels(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column")
            @RequestBody LabelSetSpec labelSetSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        columnSpec.setLabels(labelSetSpec);
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
    @ApiOperation(value = "updateColumnComments", notes = "Updates the list of comments assigned to a column.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's list of comments successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnComments(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column")
            @RequestBody CommentsListSpec commentsListSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        columnSpec.setComments(commentsListSpec);
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
    @ApiOperation(value = "updateColumnProfilingChecks", notes = "Updates configuration of column level data quality profiling checks on a column.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality profiling checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnProfilingChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of column level data quality profiling checks to configure on a column or an empty object to clear the list of assigned data quality profiling checks on the column")
            @RequestBody ColumnProfilingCheckCategoriesSpec columnCheckCategoriesSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        columnSpec.setProfilingChecks(columnCheckCategoriesSpec);

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of daily column level data quality monitoring checks configured on a column.
     * @param connectionName             Connection name.
     * @param schemaName                 Schema name.
     * @param tableName                  Table name.
     * @param columnName                 Column name.
     * @param columnDailyMonitoringSpec New configuration of the daily column level data quality monitoring checks to configure on a column or an empty optional to clear the list of daily monitoring.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnMonitoringChecksDaily", notes = "Updates configuration of daily column level data quality monitoring on a column.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily column level data quality monitoring successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnMonitoringChecksDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of daily column level data quality monitoring to configure on a column or an empty object to clear the list of assigned daily data quality monitoring on the column")
            @RequestBody ColumnDailyMonitoringCheckCategoriesSpec columnDailyMonitoringSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)  ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        
        ColumnMonitoringChecksRootSpec monitoringChecksSpec = columnSpec.getMonitoringChecks();
        if (monitoringChecksSpec == null) {
            monitoringChecksSpec = new ColumnMonitoringChecksRootSpec();
        }
        
        if (columnDailyMonitoringSpec != null) {
            monitoringChecksSpec.setDaily(columnDailyMonitoringSpec);
            columnSpec.setMonitoringChecks(monitoringChecksSpec);
        } else if (monitoringChecksSpec.getMonthly() == null) {
            // If there is no monthly monitoring checks, and it's been requested to delete daily monitoring checks, then delete all.
            columnSpec.setMonitoringChecks(null);
        } else {
            monitoringChecksSpec.setDaily(null);
        }

        userHomeContext.flush();
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of monthly column level data quality monitoring checks configured on a column.
     * @param connectionName               Connection name.
     * @param schemaName                   Schema name.
     * @param tableName                    Table name.
     * @param columnName                   Column name.
     * @param columnMonthlyMonitoringSpec New configuration of the monthly column level data quality monitoring checks to configure on a column or an empty optional to clear the list of monthly monitoring.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnMonitoringChecksMonthly", notes = "Updates configuration of monthly column level data quality monitoring checks on a column.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly column level data quality monitoring checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnMonitoringChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of monthly column level data quality monitoring to configure on a column or an empty object to clear the list of assigned monthly data quality monitoring on the column")
            @RequestBody ColumnMonthlyMonitoringCheckCategoriesSpec columnMonthlyMonitoringSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnMonitoringChecksRootSpec monitoringChecksSpec = columnSpec.getMonitoringChecks();
        if (monitoringChecksSpec == null) {
            monitoringChecksSpec = new ColumnMonitoringChecksRootSpec();
        }

        if (columnMonthlyMonitoringSpec != null) {
            monitoringChecksSpec.setMonthly(columnMonthlyMonitoringSpec);
            columnSpec.setMonitoringChecks(monitoringChecksSpec);
        } else if (monitoringChecksSpec.getDaily() == null) {
            // If there is no daily monitoring checks, and it's been requested to delete monthly monitoring checks, then delete all.
            columnSpec.setMonitoringChecks(null);
        } else {
            monitoringChecksSpec.setMonthly(null);
        }

        userHomeContext.flush();
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
    @ApiOperation(value = "updateColumnPartitionedChecksDaily", notes = "Updates configuration of daily column level data quality partitioned checks on a column.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily column level data quality partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnPartitionedChecksDaily(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column")
            @RequestBody ColumnDailyPartitionedCheckCategoriesSpec columnDailyPartitionedSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecksSpec = columnSpec.getPartitionedChecks();
        if (partitionedChecksSpec == null) {
            partitionedChecksSpec = new ColumnPartitionedChecksRootSpec();
        }

        if (columnDailyPartitionedSpec != null) {
            partitionedChecksSpec.setDaily(columnDailyPartitionedSpec);
            columnSpec.setPartitionedChecks(partitionedChecksSpec);
        } else if (partitionedChecksSpec.getMonthly() == null) {
            // If there is no monthly partitioned checks, and it's been requested to delete daily partitioned checks, then delete all.
            columnSpec.setPartitionedChecks(null);
        } else {
            partitionedChecksSpec.setDaily(null);
        }

        userHomeContext.flush();
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
    @ApiOperation(value = "updateColumnPartitionedChecksMonthly", notes = "Updates configuration of monthly column level data quality partitioned checks on a column.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly column level data quality partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnPartitionedChecksMonthly(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column")
            @RequestBody ColumnMonthlyPartitionedCheckCategoriesSpec columnMonthlyPartitionedSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        ColumnSpec columnSpec = this.readColumnSpec(userHomeContext, connectionName, schemaName, tableName, columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ColumnPartitionedChecksRootSpec partitionedChecksSpec = columnSpec.getPartitionedChecks();
        if (partitionedChecksSpec == null) {
            partitionedChecksSpec = new ColumnPartitionedChecksRootSpec();
        }

        if (columnMonthlyPartitionedSpec != null) {
            partitionedChecksSpec.setMonthly(columnMonthlyPartitionedSpec);
            columnSpec.setPartitionedChecks(partitionedChecksSpec);
        } else if (partitionedChecksSpec.getMonthly() == null) {
            // If there is no daily partitioned checks, and it's been requested to delete monthly partitioned checks, then delete all.
            columnSpec.setPartitionedChecks(null);
        } else {
            partitionedChecksSpec.setMonthly(null);
        }

        userHomeContext.flush();
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
    @ApiOperation(value = "updateColumnProfilingChecksModel", notes = "Updates configuration of column level data quality profiling checks on a column from a UI friendly model.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality profiling checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnProfilingChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checksToUpdate = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, true);

        if (checkContainerModel != null) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, checksToUpdate, tableSpec);
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
     * Updates the configuration of column level data quality monitoring configured on a column, for a given time scale, from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param timeScale                 Time scale.
     * @param checkContainerModel       Model of the column level data quality checks to be applied on the configuration of the data quality monitoring on a column, for a given time scale. Only data quality dimensions and data quality checks that are present in the model are updated (patched).
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateColumnMonitoringChecksModel", notes = "Updates configuration of column level data quality monitoring on a column, for a given time scale, from a UI friendly model.",response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality monitoring successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found, or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnMonitoringChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality monitoring configuration")
            @RequestBody CheckContainerModel checkContainerModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checksToUpdate = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, true);

        if (checkContainerModel != null) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel, checksToUpdate, tableSpec);
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
    @ApiOperation(value = "updateColumnPartitionedChecksModel",
            notes = "Updates configuration of column level data quality partitioned checks on a column, for a given time scale, from a UI friendly model.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality partitioned checks successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table or column not found, or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<Void>> updateColumnPartitionedChecksModel(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality partitioned checks configuration")
            @RequestBody CheckContainerModel allChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpec columnSpec = tableSpec.getColumns().get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checksToUpdate = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, true);

        if (allChecksModel != null) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(allChecksModel, checksToUpdate, tableSpec);
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
    @ApiOperation(value = "deleteColumn", notes = "Deletes a column from the table",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public ResponseEntity<Mono<DqoQueueJobId>> deleteColumn(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDomainIdentity());
        TableWrapper tableWrapper = this.readTableWrapper(userHomeContext, connectionName, schemaName, tableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the table was not found
        }

        ColumnSpec existingColumnSpec = tableWrapper.getSpec().getColumns().get(columnName);
        if (existingColumnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        PushJobResult<DeleteStoredDataResult> backgroundJob = this.columnService.deleteColumn(
                connectionName, tableWrapper.getPhysicalTableName(), columnName, principal);
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
