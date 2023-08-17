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
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TablePartitionedChecksRootSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableDailyRecurringCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableMonthlyRecurringCheckCategoriesSpec;
import com.dqops.checks.table.recurring.TableRecurringChecksSpec;
import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.statistics.services.StatisticsDataService;
import com.dqops.data.statistics.services.models.StatisticsResultsForTableModel;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.incidents.TableIncidentGroupingSpec;
import com.dqops.metadata.scheduling.CheckRunRecurringScheduleGroup;
import com.dqops.metadata.scheduling.RecurringScheduleSpec;
import com.dqops.metadata.scheduling.RecurringSchedulesSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.check.CheckTemplate;
import com.dqops.rest.models.metadata.TableBasicModel;
import com.dqops.rest.models.metadata.TableModel;
import com.dqops.rest.models.metadata.TablePartitioningModel;
import com.dqops.rest.models.metadata.TableStatisticsModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import com.dqops.services.check.mapping.basicmodels.CheckContainerBasicModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.dqops.services.check.models.CheckConfigurationModel;
import com.dqops.services.metadata.TableService;
import com.dqops.statistics.StatisticsCollectorTarget;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.function.Consumer;
import java.util.function.Function;
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
    private static final Logger LOG = LoggerFactory.getLogger(TablesController.class);
    private final TableService tableService;
    private UserHomeContextFactory userHomeContextFactory;
    private DqoHomeContextFactory dqoHomeContextFactory;
    private SpecToModelCheckMappingService specToModelCheckMappingService;
    private ModelToSpecCheckMappingService modelToSpecCheckMappingService;
    private StatisticsDataService statisticsDataService;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param tableService                     Table logic service.
     * @param userHomeContextFactory           User home context factory.
     * @param dqoHomeContextFactory            DQO home context factory, used to retrieve the definition of built-in sensors.
     * @param specToModelCheckMappingService   Check mapper to convert the check specification to a model.
     * @param modelToSpecCheckMappingService   Check mapper to convert the check model to a check specification.
     * @param statisticsDataService            Statistics data service, provides access to the statistics (basic profiling).
     */
    @Autowired
    public TablesController(TableService tableService,
                            UserHomeContextFactory userHomeContextFactory,
                            DqoHomeContextFactory dqoHomeContextFactory,
                            SpecToModelCheckMappingService specToModelCheckMappingService,
                            ModelToSpecCheckMappingService modelToSpecCheckMappingService,
                            StatisticsDataService statisticsDataService) {
        this.tableService = tableService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoHomeContextFactory = dqoHomeContextFactory;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
        this.statisticsDataService = statisticsDataService;
    }

    /**
     * Returns a list of tables inside a database/schema.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @return List of tables inside a connection's schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables", produces = "application/json")
    @ApiOperation(value = "getTables", notes = "Returns a list of tables inside a connection/schema", response = TableBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableBasicModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<TableBasicModel>> getTables(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<TableSpec> tableSpecs = connectionWrapper.getTables().toList()
                .stream()
                .filter(tw -> Objects.equals(tw.getPhysicalTableName().getSchemaName(), schemaName))
                .sorted(Comparator.comparing(tw -> tw.getPhysicalTableName().getTableName()))
                .map(TableWrapper::getSpec)
                .collect(Collectors.toList());

        Stream<TableBasicModel> modelStream = tableSpecs.stream()
                .map(ts -> TableBasicModel.fromTableSpecificationForListEntry(connectionName, ts));

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the table details given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Table specification for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", produces = "application/json")
    @ApiOperation(value = "getTable", notes = "Return the table specification", response = TableModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table full specification returned", response = TableModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableModel>> getTable(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        TableModel tableModel = new TableModel() {{
            setConnectionName(connectionWrapper.getName());
            setTableHash(tableSpec.getHierarchyId() != null ? tableSpec.getHierarchyId().hashCode64() : null);
            setSpec(tableSpec);
        }};

        return new ResponseEntity<>(Mono.just(tableModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the basic table details given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Table basic information for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic", produces = "application/json")
    @ApiOperation(value = "getTableBasic", notes = "Return the basic table information", response = TableBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table basic information returned", response = TableBasicModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableBasicModel>> getTableBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        TableBasicModel tableBasicModel = TableBasicModel.fromTableSpecification(connectionWrapper.getName(), tableSpec);

        return new ResponseEntity<>(Mono.just(tableBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the table partitioning details given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Table partitioning information for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning", produces = "application/json")
    @ApiOperation(value = "getTablePartitioning", notes = "Return the table partitioning information", response = TablePartitioningModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table partitioning information returned", response = TablePartitioningModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TablePartitioningModel>> getTablePartitioning(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        TablePartitioningModel tablePartitioningModel = TablePartitioningModel.fromTableSpecification(connectionWrapper.getName(), tableSpec);

        return new ResponseEntity<>(Mono.just(tablePartitioningModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the default data grouping configuration for a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Default data grouping configuration for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration", produces = "application/json")
    @ApiOperation(value = "getTableDefaultGroupingConfiguration", notes = "Return the default data grouping configuration for a table.", response = DataGroupingConfigurationSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Default data grouping configuration for a table returned", response = DataGroupingConfigurationSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DataGroupingConfigurationSpec>> getTableDefaultGroupingConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        DataGroupingConfigurationSpec dataGroupingConfiguration = tableSpec.getDefaultDataGroupingConfiguration();

        return new ResponseEntity<>(Mono.justOrEmpty(dataGroupingConfiguration), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the overridden scheduling group configuration for a table given a connection name, a table name and a scheduling group (schedule name).
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param schedulingGroup Scheduling group name.
     * @return Overridden schedule configuration for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}", produces = "application/json")
    @ApiOperation(value = "getTableSchedulingGroupOverride", notes = "Return the schedule override configuration for a table", response = RecurringScheduleSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Overridden schedule configuration for a table returned", response = RecurringScheduleSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RecurringScheduleSpec>> getTableSchedulingGroupOverride(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunRecurringScheduleGroup schedulingGroup) {
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
        RecurringSchedulesSpec schedules = tableSpec.getSchedulesOverride();
        if (schedules == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        RecurringScheduleSpec schedule = schedules.getScheduleForCheckSchedulingGroup(schedulingGroup);

        return new ResponseEntity<>(Mono.justOrEmpty(schedule), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the incident grouping configuration given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Incident grouping configuration for the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping", produces = "application/json")
    @ApiOperation(value = "getTableIncidentGrouping", notes = "Return the configuration of incident grouping on a table", response = TableIncidentGroupingSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table's incident grouping configuration returned", response = TableIncidentGroupingSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableIncidentGroupingSpec>> getTableIncidentGrouping(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        TableIncidentGroupingSpec incidentGrouping = tableSpec.getIncidentGrouping();
        if (incidentGrouping == null) {
            incidentGrouping = new TableIncidentGroupingSpec();
        }

        return new ResponseEntity<>(Mono.just(incidentGrouping), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the labels for a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Labels assigned to the requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels", produces = "application/json")
    @ApiOperation(value = "getTableLabels", notes = "Return the list of labels assigned to a table", response = LabelSetSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of labels on a table returned", response = LabelSetSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<LabelSetSpec>> getTableLabels(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        LabelSetSpec labels = tableSpec.getLabels();

        return new ResponseEntity<>(Mono.justOrEmpty(labels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the comments on a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Comments on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments", produces = "application/json")
    @ApiOperation(value = "getTableComments", notes = "Return the list of comments added to a table", response = CommentsListSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of comments on a table returned", response = CommentsListSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CommentsListSpec>> getTableComments(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        CommentsListSpec comments = tableSpec.getComments();

        return new ResponseEntity<>(Mono.justOrEmpty(comments), HttpStatus.OK); // 200
    }

    
    /**
     * Retrieves the configuration of data quality checks on a table given a connection name and a table names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Data quality checks on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecks", notes = "Return the configuration of table level data quality checks on a table", response = TableProfilingCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality checks on a table returned", response = TableProfilingCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableProfilingCheckCategoriesSpec>> getTableProfilingChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        
        TableProfilingCheckCategoriesSpec checks = tableSpec.getProfilingChecks();
        return new ResponseEntity<>(Mono.justOrEmpty(checks), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of daily data quality recurring on a table given a connection name and table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Daily data quality recurring on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/daily", produces = "application/json")
    @ApiOperation(value = "getTableRecurringChecksDaily", notes = "Return the configuration of daily table level data quality recurring on a table", response = TableDailyRecurringCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of daily table level data quality recurring on a table returned", response = TableDailyRecurringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableDailyRecurringCheckCategoriesSpec>> getTableDailyRecurringChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        
        TableDailyRecurringCheckCategoriesSpec dailyRecurring = tableSpec.getRecurringChecks().getDaily();
        return new ResponseEntity<>(Mono.justOrEmpty(dailyRecurring), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of monthly data quality recurring on a table given a connection name and table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Monthly data quality recurring on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/monthly", produces = "application/json")
    @ApiOperation(value = "getTableRecurringChecksMonthly", notes = "Return the configuration of monthly table level data quality recurring on a table", response = TableMonthlyRecurringCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of monthly table level data quality recurring on a table returned", response = TableMonthlyRecurringCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableMonthlyRecurringCheckCategoriesSpec>> getTableRecurringChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableMonthlyRecurringCheckCategoriesSpec monthlyRecurring = tableSpec.getRecurringChecks().getMonthly();
        return new ResponseEntity<>(Mono.justOrEmpty(monthlyRecurring), HttpStatus.OK); // 200
    }
    
    /**
     * Retrieves the configuration of daily data quality partitioned checks on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Daily data quality partitioned checks on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksDaily", notes = "Return the configuration of daily table level data quality partitioned checks on a table", response = TableDailyPartitionedCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality partitioned checks on a table returned", response = TableDailyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableDailyPartitionedCheckCategoriesSpec>> getTableDailyPartitionedChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableDailyPartitionedCheckCategoriesSpec dailyPartitioned = tableSpec.getPartitionedChecks().getDaily();
        return new ResponseEntity<>(Mono.justOrEmpty(dailyPartitioned), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of monthly data quality partitioned checks on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Monthly data quality partitioned checks on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksMonthly", notes = "Return the configuration of monthly table level data quality partitioned checks on a table", response = TableMonthlyPartitionedCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality partitioned checks on a table returned", response = TableMonthlyPartitionedCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableMonthlyPartitionedCheckCategoriesSpec>> getTablePartitionedChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        TableMonthlyPartitionedCheckCategoriesSpec monthlyPartitioned = tableSpec.getPartitionedChecks().getMonthly();
        return new ResponseEntity<>(Mono.justOrEmpty(monthlyPartitioned), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of data quality profiling checks as a UI friendly model on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return UI friendly data quality profiling check configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksModel", notes = "Return a UI friendly model of configurations for all table level data quality profiling checks on a table", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality profiling checks on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getTableProfilingChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false);

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
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
     * Retrieves the configuration of data quality recurring as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return UI friendly data quality recurring configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTableRecurringChecksModel", notes = "Return a UI friendly model of configurations for table level data quality recurring on a table for a given time scale", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality recurring on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getTableRecurringChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.recurring, timeScale, false);
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
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
     * Retrieves the configuration of data quality partitioned checks as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return UI friendly data quality partitioned check configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksModel", notes = "Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality partitioned checks on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getTablePartitionedChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
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
     * Retrieves a simplistic list of data quality profiling checks as a UI friendly model on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Simplistic UI friendly data quality profiling checks list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/basic", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksBasicModel", notes = "Return a simplistic UI friendly model of all table level data quality profiling checks on a table", response = CheckContainerBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of table level data quality profiling checks on a table returned", response = CheckContainerBasicModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerBasicModel>> getTableProfilingChecksBasicModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false);
        CheckContainerBasicModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a simplistic list of data quality recurring as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return Simplistic UI friendly data quality recurring list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/{timeScale}/model/basic", produces = "application/json")
    @ApiOperation(value = "getTableRecurringChecksBasicModel", notes = "Return a simplistic UI friendly model of table level data quality recurring on a table for a given time scale", response = CheckContainerBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of table level {timeScale} data quality recurring on a table returned", response = CheckContainerBasicModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerBasicModel>> getTableRecurringChecksBasicModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.recurring, timeScale, false);
        CheckContainerBasicModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks,
                new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of data quality partitioned checks as a UI friendly model on a table given a connection name, a table name, and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @return Simplistic UI friendly data quality partitioned checks list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/basic", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksBasicModel", notes = "Return a simplistic UI friendly model of table level data quality partitioned checks on a table for a given time scale", response = CheckContainerBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of table level {timeScale} data quality partitioned checks on a table returned", response = CheckContainerBasicModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerBasicModel>> getTablePartitionedChecksBasicModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);
        CheckContainerBasicModel checksBasicModel = this.specToModelCheckMappingService.createBasicModel(
                checks, new ExecutionContext(userHomeContext, this.dqoHomeContextFactory.openLocalDqoHome()),
                connectionWrapper.getSpec().getProviderType());

        return new ResponseEntity<>(Mono.just(checksBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of data quality profiling checks as a UI friendly model on a table given a connection name and a table name, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly data quality profiling check configuration list on a requested table, filtered by category and check name.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksModelFilter", notes = "Return a UI friendly model of configurations for all table level data quality profiling checks on a table passing a filter", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level data quality profiling checks on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getTableProfilingChecksModelFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false);

        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
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
     * Retrieves the configuration of data quality recurring as a UI friendly model on a table given a connection name, a table name, and a time scale, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly data quality recurring configuration list on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/{timeScale}/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getTableRecurringChecksModelFilter", notes = "Return a UI friendly model of configurations for table level data quality recurring on a table for a given time scale, filtered by category and check name.", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality recurring on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getTableRecurringChecksModelFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.recurring, timeScale, false);
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
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
     * Retrieves the configuration of data quality partitioned checks as a UI friendly model on a table given a connection name, a table name, and a time scale, filtered by category and check name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale  Time scale.
     * @param checkCategory  Check category.
     * @param checkName      Check name.
     * @return UI friendly data quality partitioned check configuration list on a requested table, filtered by category and check name.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksModelFilter", notes = "Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale, filtered by category and check name.", response = CheckContainerModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of table level {timeScale} data quality partitioned checks on a table returned", response = CheckContainerModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CheckContainerModel>> getTablePartitionedChecksModelFilter(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
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
        if (tableSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
            setSchemaTableName(tableWrapper.getPhysicalTableName().toTableSearchFilter());
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
     * Returns a list of table level statistics for a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name
     * @return List of table level statistics.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/statistics", produces = "application/json")
    @ApiOperation(value = "getTableStatistics",
            notes = "Returns a list of the profiler (statistics) metrics on a chosen table captured during the most recent statistics collection.",
            response = TableStatisticsModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TableStatisticsModel.class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TableStatisticsModel>> getTableStatistics(
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

        TableStatisticsModel resultModel = new TableStatisticsModel();
        resultModel.setConnectionName(connectionName);
        resultModel.setTable(physicalTableName);
        resultModel.setStatistics(mostRecentStatisticsMetricsForTable.getMetrics());

        resultModel.setCollectTableStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
        {{
            setConnectionName(connectionName);
            setSchemaTableName(physicalTableName.toTableSearchFilter());
            setTarget(StatisticsCollectorTarget.table);
            setEnabled(true);
        }});

        resultModel.setCollectTableAndColumnStatisticsJobTemplate(new StatisticsCollectorSearchFilters()
        {{
            setConnectionName(connectionName);
            setSchemaTableName(physicalTableName.toTableSearchFilter());
            setEnabled(true);
        }});

        return new ResponseEntity<>(Mono.just(resultModel), HttpStatus.OK);
    }

    /**
     * Retrieves a UI friendly data quality profiling check configuration list of column-level checks on a requested table.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableName         Table name.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @return UI friendly data quality profiling check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/profiling/model", produces = "application/json")
    @ApiOperation(value = "getTableColumnsProfilingChecksModel", notes = "Return a UI friendly model of configurations for column-level data quality profiling checks on a table", response = CheckConfigurationModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality profiling checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckConfigurationModel>> getTableColumnsProfilingChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false)
            Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false)
            Optional<String> checkName,
            @ApiParam(value = "Check enabled", required = false) @RequestParam(required = false)
            Optional<Boolean> checkEnabled,
            @ApiParam(value = "Check configured", required = false) @RequestParam(required = false)
            Optional<Boolean> checkConfigured) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        
        PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, schemaTableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckConfigurationModel> checkConfigurationModels = this.tableService.getCheckConfigurationsOnTable(
                connectionName, schemaTableName, new CheckContainerTypeModel(CheckType.profiling, null),
                columnNamePattern.orElse(null),
                columnDataType.orElse(null),
                CheckTarget.column,
                checkCategory.orElse(null),
                checkName.orElse(null),
                checkEnabled.orElse(null),
                checkConfigured.orElse(null)
        );

        return new ResponseEntity<>(Flux.fromIterable(checkConfigurationModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly data quality recurring check configuration list of column-level checks on a requested table.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableName         Table name.
     * @param timeScale         Check time-scale.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @return UI friendly data quality recurring check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/recurring/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTableColumnsRecurringChecksModel", notes = "Return a UI friendly model of configurations for column-level data quality recurring checks on a table", response = CheckConfigurationModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality recurring checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckConfigurationModel>> getTableColumnsRecurringChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check time-scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false)
            Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false)
            Optional<String> checkName,
            @ApiParam(value = "Check enabled", required = false) @RequestParam(required = false)
            Optional<Boolean> checkEnabled,
            @ApiParam(value = "Check configured", required = false) @RequestParam(required = false)
            Optional<Boolean> checkConfigured) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, schemaTableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckConfigurationModel> checkConfigurationModels = this.tableService.getCheckConfigurationsOnTable(
                connectionName, schemaTableName, new CheckContainerTypeModel(CheckType.recurring, timeScale),
                columnNamePattern.orElse(null),
                columnDataType.orElse(null),
                CheckTarget.column,
                checkCategory.orElse(null),
                checkName.orElse(null),
                checkEnabled.orElse(null),
                checkConfigured.orElse(null)
        );

        return new ResponseEntity<>(Flux.fromIterable(checkConfigurationModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly data quality partitioned check configuration list of column-level checks on a requested table.
     * @param connectionName    Connection name.
     * @param schemaName        Schema name.
     * @param tableName         Table name.
     * @param timeScale         Check time-scale.
     * @param columnNamePattern (Optional) Column search pattern filter.
     * @param columnDataType    (Optional) Filter on column data-type.
     * @param checkCategory     (Optional) Filter on check category.
     * @param checkName         (Optional) Filter on check name.
     * @param checkEnabled      (Optional) Filter on check enabled status.
     * @param checkConfigured   (Optional) Filter on check configured status.
     * @return UI friendly data quality partitioned check configuration list on a requested schema.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/partitioned/{timeScale}/model", produces = "application/json")
    @ApiOperation(value = "getTableColumnsPartitionedChecksModel", notes = "Return a UI friendly model of configurations for column-level data quality partitioned checks on a table", response = CheckConfigurationModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of data quality partitioned checks on a schema returned", response = CheckConfigurationModel[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckConfigurationModel>> getTableColumnsPartitionedChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check time-scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Column name pattern", required = false) @RequestParam(required = false)
            Optional<String> columnNamePattern,
            @ApiParam(value = "Column data-type", required = false) @RequestParam(required = false)
            Optional<String> columnDataType,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false)
            Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false)
            Optional<String> checkName,
            @ApiParam(value = "Check enabled", required = false) @RequestParam(required = false)
            Optional<Boolean> checkEnabled,
            @ApiParam(value = "Check configured", required = false) @RequestParam(required = false)
            Optional<Boolean> checkConfigured) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        PhysicalTableName schemaTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, schemaTableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckConfigurationModel> checkConfigurationModels = this.tableService.getCheckConfigurationsOnTable(
                connectionName, schemaTableName, new CheckContainerTypeModel(CheckType.partitioned, timeScale),
                columnNamePattern.orElse(null),
                columnDataType.orElse(null),
                CheckTarget.column,
                checkCategory.orElse(null),
                checkName.orElse(null),
                checkEnabled.orElse(null),
                checkConfigured.orElse(null)
        );

        return new ResponseEntity<>(Flux.fromIterable(checkConfigurationModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of profiling checks templates on the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/profiling", produces = "application/json")
    @ApiOperation(value = "getTableProfilingChecksTemplates", notes = "Return available data quality checks on a requested table.", response = CheckTemplate[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a table returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckTemplate>> getTableProfilingChecksTemplates(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        PhysicalTableName fullTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, fullTableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckTemplate> checkTemplates = this.tableService.getCheckTemplates(
                connectionName, fullTableName, CheckType.profiling,
                null, checkCategory.orElse(null), checkName.orElse(null));

        return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of recurring checks templates on the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Check time scale.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/recurring/{timeScale}", produces = "application/json")
    @ApiOperation(value = "getTableRecurringChecksTemplates", notes = "Return available data quality checks on a requested table.", response = CheckTemplate[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a table returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckTemplate>> getTableRecurringChecksTemplates(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        PhysicalTableName fullTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, fullTableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckTemplate> checkTemplates = this.tableService.getCheckTemplates(
                connectionName, fullTableName, CheckType.recurring,
                timeScale, checkCategory.orElse(null), checkName.orElse(null));

        return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of partitioned checks templates on the given table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Check time scale.
     * @param checkCategory  (Optional) Filter on check category.
     * @param checkName      (Optional) Filter on check name.
     * @return Data quality checks templates on a requested table.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/partitioned/{timeScale}", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedChecksTemplates", notes = "Return available data quality checks on a requested table.", response = CheckTemplate[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Potential data quality checks on a table returned", response = CheckTemplate[].class),
            @ApiResponse(code = 404, message = "Connection, schema or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckTemplate>> getTablePartitionedChecksTemplates(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Check category", required = false) @RequestParam(required = false) Optional<String> checkCategory,
            @ApiParam(value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        PhysicalTableName fullTableName = new PhysicalTableName(schemaName, tableName);
        TableWrapper tableWrapper = this.tableService.getTable(userHome, connectionName, fullTableName);
        if (tableWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<CheckTemplate> checkTemplates = this.tableService.getCheckTemplates(
                connectionName, fullTableName, CheckType.partitioned,
                timeScale, checkCategory.orElse(null), checkName.orElse(null));

        return new ResponseEntity<>(Flux.fromIterable(checkTemplates), HttpStatus.OK); // 200
    }


    /**
     * Creates (adds) a new table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableSpec      Table specification.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createTable", notes = "Creates a new table (adds a table metadata)")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New table successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Table with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createTable(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table specification") @RequestBody TableSpec tableSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
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
        TableWrapper existingTableWrapper = tables.getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
        if (existingTableWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a table with this name already exists
        }

        TableWrapper tableWrapper = tables.createAndAddNew(new PhysicalTableName(schemaName, tableName));
        tableWrapper.setSpec(tableSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Updates an existing table, updating the whole specification.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param tableSpec      Table specification.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTable", notes = "Updates an existing table specification, changing all the fields")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTable(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Full table specification") @RequestBody TableSpec tableSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
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

        // TODO: validate the tableSpec
        tableWrapper.setSpec(tableSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the basic fields of an existing table.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tableBasicModel Basic table model with the new values.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableBasic", notes = "Updates the basic field of an existing table, changing only the most important fields.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table basic model with the updated settings") @RequestBody TableBasicModel tableBasicModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        if (tableBasicModel.getTarget() == null ||
                !Objects.equals(schemaName, tableBasicModel.getTarget().getSchemaName()) ||
                !Objects.equals(tableName, tableBasicModel.getTarget().getTableName())) {
            return new ResponseEntity<>(Mono.justOrEmpty("Target schema and table name in the table model must match the schema and table name in the url"),
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

        // TODO: validate the tableSpec
        TableSpec tableSpec = tableWrapper.getSpec();
        tableBasicModel.copyToTableSpecification(tableSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the partitioning fields of an existing table.
     * @param connectionName  Connection name.
     * @param schemaName      Schema name.
     * @param tableName       Table name.
     * @param tablePartitioningModel Table partitioning model with the new values.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitioning", notes = "Updates the table partitioning configuration of an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table partitioning successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTablePartitioning(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Table partitioning model with the updated settings") @RequestBody TablePartitioningModel tablePartitioningModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        if (tablePartitioningModel.getTarget() == null ||
                !Objects.equals(schemaName, tablePartitioningModel.getTarget().getSchemaName()) ||
                !Objects.equals(tableName, tablePartitioningModel.getTarget().getTableName())) {
            return new ResponseEntity<>(Mono.justOrEmpty("Target schema and table name in the table model must match the schema and table name in the url"),
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

        // TODO: validate the tableSpec
        TableSpec tableSpec = tableWrapper.getSpec();
        tablePartitioningModel.copyToTableSpecification(tableSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the default data grouping configuration of an existing table.
     * @param connectionName         Connection name.
     * @param schemaName             Schema name.
     * @param tableName              Table name.
     * @param dataGroupingConfigurationSpec New default data grouping configuration or null (an empty optional).
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableDefaultGroupingConfiguration", notes = "Updates the default data grouping configuration at a table level.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's default data grouping configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableDefaultGroupingConfiguration(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Default data grouping configuration to store or an empty object to clear the data grouping configuration on a table level")
            @RequestBody Optional<DataGroupingConfigurationSpec> dataGroupingConfigurationSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
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
        if (dataGroupingConfigurationSpec.isPresent()) {
            if (!Strings.isNullOrEmpty(tableSpec.getDefaultGroupingName())) {
                tableSpec.getGroupings().remove(tableSpec.getDefaultGroupingName());
                tableSpec.getGroupings().put(tableSpec.getDefaultGroupingName(), dataGroupingConfigurationSpec.get());
            } else {
                tableSpec.getGroupings().put(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME, dataGroupingConfigurationSpec.get());
                tableSpec.setDefaultGroupingName(DataGroupingConfigurationSpecMap.DEFAULT_CONFIGURATION_NAME);
            }
        } else {
            if (tableSpec.getDefaultGroupingName() != null) {
                tableSpec.getGroupings().remove(tableSpec.getDefaultGroupingName());
                tableSpec.setDefaultGroupingName(null);
            }
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the overridden schedule configuration of an existing table for a named scheduling group.
     * @param connectionName              Connection name.
     * @param schemaName                  Schema name.
     * @param tableName                   Table name.
     * @param recurringScheduleSpec       New recurring schedule configuration or an emtpy optional to clear the schedule configuration.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableSchedulingGroupOverride", notes = "Updates the overridden schedule configuration of an existing table for a named schedule group (named schedule for checks using the same time scale).")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's overridden schedule configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableSchedulingGroupOverride(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunRecurringScheduleGroup schedulingGroup,
            @ApiParam("Table's overridden schedule configuration to store or an empty object to clear the schedule configuration on a table")
                @RequestBody Optional<RecurringScheduleSpec> recurringScheduleSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
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
        RecurringSchedulesSpec schedules = tableSpec.getSchedulesOverride();
        if (schedules == null) {
            schedules = new RecurringSchedulesSpec();
            tableSpec.setSchedulesOverride(schedules);
        }

        RecurringScheduleSpec newScheduleSpec = recurringScheduleSpec.orElse(null);
        switch (schedulingGroup) {
            case profiling:
                schedules.setProfiling(newScheduleSpec);
                break;

            case recurring_daily:
                schedules.setRecurringDaily(newScheduleSpec);
                break;

            case recurring_monthly:
                schedules.setRecurringMonthly(newScheduleSpec);
                break;

            case partitioned_daily:
                schedules.setPartitionedDaily(newScheduleSpec);
                break;

            case partitioned_monthly:
                schedules.setPartitionedMonthly(newScheduleSpec);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported scheduling group " + schedulingGroup);
        }

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }


    /**
     * Updates the configuration of incident grouping on an existing table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param incidentGrouping   New configuration of the table's incident grouping
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableIncidentGrouping", notes = "Updates the configuration of incident grouping on a table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's incident grouping configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableIncidentGrouping(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("New configuration of the table's incident grouping")
            @RequestBody Optional<TableIncidentGroupingSpec> incidentGrouping) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
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
        if (incidentGrouping.isPresent()) {
            tableSpec.setIncidentGrouping(incidentGrouping.get());
        } else {
            tableSpec.setIncidentGrouping(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the list of labels of an existing table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param labelSetSpec   New list of labels or an empty optional to clear the list of labels.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableLabels", notes = "Updates the list of assigned labels of an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's labels successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableLabels(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("List of labels to attach (replace) on a table or an empty object to clear the list of labels on a table")
            @RequestBody Optional<LabelSetSpec> labelSetSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
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

        // TODO: validate the tableSpec
        TableSpec tableSpec = tableWrapper.getSpec();
        if (labelSetSpec.isPresent()) {
            tableSpec.setLabels(labelSetSpec.get());
        } else {
            tableSpec.setLabels(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the list of comments of an existing table.
     * @param connectionName   Connection name.
     * @param schemaName       Schema name.
     * @param tableName        Table name.
     * @param commentsListSpec New list of comments or an empty optional to clear the list of comments.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableComments", notes = "Updates the list of comments on an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table's comments successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableComments(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("List of comments to attach (replace) on a table or an empty object to clear the list of comments on a table")
            @RequestBody Optional<CommentsListSpec> commentsListSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
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

        // TODO: validate the tableSpec
        TableSpec tableSpec = tableWrapper.getSpec();
        if (commentsListSpec.isPresent()) {
            tableSpec.setComments(commentsListSpec.get());
        } else {
            tableSpec.setComments(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    protected <T extends AbstractRootChecksContainerSpec> boolean updateTableGenericChecks(
            Consumer<TableSpec> tableSpecUpdater,
            String connectionName,
            String schemaName,
            String tableName) {
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

        // TODO: validate the tableSpec
        TableSpec tableSpec = tableWrapper.getSpec();
        tableSpecUpdater.accept(tableSpec);
        userHomeContext.flush();

        return true;
    }

    /**
     * Updates the configuration of table level data quality profiling checks of an existing table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param tableProfilingCheckCategoriesSpec New configuration of the data quality profiling checks on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableProfilingChecks", notes = "Updates the list of table level data quality profiling checks on an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality profiling checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableProfilingChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of table level data quality profiling checks to store or an empty object to remove all data quality profiling checks on the table level (column level profiling checks are preserved).")
            @RequestBody Optional<TableProfilingCheckCategoriesSpec> tableProfilingCheckCategoriesSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                spec -> {
                    if (tableProfilingCheckCategoriesSpec.isPresent()) {
                        spec.setTableCheckRootContainer(tableProfilingCheckCategoriesSpec.get());
                    } else {
                        spec.setProfilingChecks(new TableProfilingCheckCategoriesSpec()); // it is never empty...
                    }
                },
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * Updates the configuration of daily table level data quality recurring of an existing table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param tableDailyRecurringSpec     New configuration of the daily data quality recurring on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableRecurringChecksDaily", notes = "Updates the list of daily table level data quality recurring on an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily table level data quality recurring successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableDailyRecurringChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of daily table level data quality recurring to store or an empty object to remove all data quality recurring on the table level (column level recurring are preserved).")
            @RequestBody Optional<TableDailyRecurringCheckCategoriesSpec> tableDailyRecurringSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                spec -> {
                    TableRecurringChecksSpec recurringSpec = spec.getRecurringChecks();

                    if (tableDailyRecurringSpec.isPresent()) {
                        recurringSpec.setDaily(tableDailyRecurringSpec.get());
                    } else {
                        recurringSpec.setDaily(null);
                    }
                },
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * Updates the configuration of monthly table level data quality recurring of an existing table.
     * @param connectionName                Connection name.
     * @param schemaName                    Schema name.
     * @param tableName                     Table name.
     * @param tableMonthlyRecurringSpec     New configuration of the monthly data quality recurring on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableRecurringChecksMonthly", notes = "Updates the list of monthly table level data quality recurring on an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly table level data quality recurring successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableRecurringChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of monthly table level data quality recurring to store or an empty object to remove all data quality recurring on the table level (column level recurring are preserved).")
            @RequestBody Optional<TableMonthlyRecurringCheckCategoriesSpec> tableMonthlyRecurringSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                spec -> {
                    TableRecurringChecksSpec recurringSpec = spec.getRecurringChecks();

                    if (tableMonthlyRecurringSpec.isPresent()) {
                        recurringSpec.setMonthly(tableMonthlyRecurringSpec.get());
                    } else {
                        recurringSpec.setMonthly(null);
                    }
                },
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * Updates the configuration of daily table level data quality partitioned checks of an existing table.
     * @param connectionName                  Connection name.
     * @param schemaName                      Schema name.
     * @param tableName                       Table name.
     * @param tableDailyPartitionedSpec New configuration of the daily data quality partitioned checks on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitionedChecksDaily", notes = "Updates the list of daily table level data quality partitioned checks on an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Daily table level data quality recurring successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTablePartitionedChecksDaily(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of daily table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).")
            @RequestBody Optional<TableDailyPartitionedCheckCategoriesSpec> tableDailyPartitionedSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                spec -> {
                    TablePartitionedChecksRootSpec PartitionedSpec = spec.getPartitionedChecks();
                    
                    if (tableDailyPartitionedSpec.isPresent()) {
                        PartitionedSpec.setDaily(tableDailyPartitionedSpec.get());
                    } else {
                        PartitionedSpec.setDaily(null);
                    }
                },
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * Updates the configuration of monthly table level data quality partitioned checks of an existing table.
     * @param connectionName                  Connection name.
     * @param schemaName                      Schema name.
     * @param tableName                       Table name.
     * @param tableMonthlyPartitionedSpec New configuration of the monthly data quality partitioned checks on the table level.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitionedChecksMonthly", notes = "Updates the list of monthly table level data quality partitioned checks on an existing table.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Monthly table level data quality partitioned checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTablePartitionedChecksMonthly(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Configuration of monthly table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).")
            @RequestBody Optional<TableMonthlyPartitionedCheckCategoriesSpec> tableMonthlyPartitionedSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecks(
                spec -> {
                    TablePartitionedChecksRootSpec PartitionedSpec = spec.getPartitionedChecks();

                    if (tableMonthlyPartitionedSpec.isPresent()) {
                        PartitionedSpec.setMonthly(tableMonthlyPartitionedSpec.get());
                    } else {
                        PartitionedSpec.setMonthly(null);
                    }
                },
                connectionName,
                schemaName,
                tableName
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        } else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    protected <T extends AbstractRootChecksContainerSpec> boolean updateTableGenericChecksModel(
            Function<TableSpec, T> tableSpecToRootCheck,
            String connectionName,
            String schemaName,
            String tableName,
            Optional<CheckContainerModel> checkContainerModel) {
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
        T checksToUpdate = tableSpecToRootCheck.apply(tableSpec);
        if (checksToUpdate == null) {
            return false;
        }

        if (checkContainerModel.isPresent()) {
            this.modelToSpecCheckMappingService.updateCheckContainerSpec(checkContainerModel.get(), checksToUpdate);
            tableSpec.setTableCheckRootContainer(checksToUpdate);
        } else {
            // we cannot just remove all checks because the model is a patch, no changes in the patch means no changes to the object
        }

        userHomeContext.flush();
        return true;
    }

    /**
     * Updates the data quality profiling check specification on an existing table from a check model with a patch of changes.
     * @param connectionName           Connection name.
     * @param schemaName               Schema name.
     * @param tableName                Table name.
     * @param checkContainerModel      New configuration of the data quality checks on the table level provided as a model. The model may contain only a subset of data quality dimensions or checks. Only those profiling checks that are present in the model are updated, the others are preserved without any changes.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableProfilingChecksModel", notes = "Updates the data quality profiling checks from a model that contains a patch with changes.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality profiling checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableProfilingChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Model with the changes to be applied to the data quality profiling checks configuration.")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecksModel(
                TableSpec::getProfilingChecks,
                connectionName,
                schemaName,
                tableName,
                checkContainerModel
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }
        else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * Updates the data quality recurring specification on an existing table for a given time scale from a check model with a patch of changes.
     * @param connectionName           Connection name.
     * @param schemaName               Schema name.
     * @param tableName                Table name.
     * @param timeScale                Time scale.
     * @param checkContainerModel      New configuration of the data quality recurring on the table level provided as a model. The model may contain only a subset of data quality dimensions or checks. Only those recurring that are present in the model are updated, the others are preserved without any changes.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/{timeScale}/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTableRecurringChecksModel", notes = "Updates the data quality recurring from a model that contains a patch with changes.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality recurring successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTableRecurringChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality recurring configuration.")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecksModel(
                spec -> spec.getTableCheckRootContainer(CheckType.recurring, timeScale, true),
                connectionName,
                schemaName,
                tableName,
                checkContainerModel
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }
        else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * Updates the data quality partitioned checks specification on an existing table for a given time scale from a check model with a patch of changes.
     * @param connectionName           Connection name.
     * @param schemaName               Schema name.
     * @param tableName                Table name.
     * @param timeScale                Time scale.
     * @param checkContainerModel      New configuration of the data quality partitioned checks on the table level provided as a model. The model may contain only a subset of data quality dimensions or checks. Only those partitioned checks that are present in the model are updated, the others are preserved without any changes.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateTablePartitionedChecksModel", notes = "Updates the data quality partitioned checks from a model that contains a patch with changes.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Table level data quality partitioned checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found or invalid time scale"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateTablePartitionedChecksModel(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Model with the changes to be applied to the data quality partitioned checks configuration.")
            @RequestBody Optional<CheckContainerModel> checkContainerModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        boolean success = this.updateTableGenericChecksModel(
                spec -> spec.getTableCheckRootContainer(CheckType.partitioned, timeScale, true),
                connectionName,
                schemaName,
                tableName,
                checkContainerModel
        );

        if (success) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }
        else {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * Deletes a table metadata.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return Deferred operations job id.
     */
    @DeleteMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}", produces = "application/json")
    @ApiOperation(value = "deleteTable", notes = "Deletes a table", response = DqoQueueJobId.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Table successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> deleteTable(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName) {
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

        PushJobResult<DeleteStoredDataQueueJobResult> backgroundJob = this.tableService.deleteTable(
                connectionName, tableWrapper.getPhysicalTableName());

        return new ResponseEntity<>(Mono.just(backgroundJob.getJobId()), HttpStatus.OK); // 200
    }
}
