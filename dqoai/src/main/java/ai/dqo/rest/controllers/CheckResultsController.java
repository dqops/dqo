/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableCheckpointsSpec;
import ai.dqo.checks.table.checkpoints.TableDailyCheckpointCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableMonthlyCheckpointCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TableMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.data.ruleresults.services.CheckResultsDetailedParameters;
import ai.dqo.data.ruleresults.services.RuleResultsDataService;
import ai.dqo.data.ruleresults.services.models.CheckResultsDetailedDataModel;
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

import java.time.LocalDate;
import java.util.Objects;

/**
 * Controller over the check results on tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "CheckResults", description = "Returns the complete results of executed checks on tables and columns.")
public class CheckResultsController {
    private UserHomeContextFactory userHomeContextFactory;
    private RuleResultsDataService ruleResultsDataService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param ruleResultsDataService Rule results data service.
     */
    @Autowired
    public CheckResultsController(UserHomeContextFactory userHomeContextFactory,
                                  RuleResultsDataService ruleResultsDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.ruleResultsDataService = ruleResultsDataService;
    }

    /**
     * Retrieves the complete results of the recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/results")
    @ApiOperation(value = "getTableAdHocChecksResults", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableAdHocChecksResults(
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

        TableAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                tableSpec.getChecks(),
                () -> {
                    TableAdHocCheckCategoriesSpec container = new TableAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "checks"));
                    return container;
                });
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/results/datastream/{dataStreamName}")
    @ApiOperation(value = "getTableAdHocChecksResultsOnDataStream", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableAdHocChecksResultsOnDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream") @PathVariable String dataStreamName) {
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

        TableAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                tableSpec.getChecks(),
                () -> {
                    TableAdHocCheckCategoriesSpec container = new TableAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "checks"));
                    return container;
                });
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/results/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableAdHocChecksResultsOnMonthRange", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableAdHocChecksResultsOnMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        TableAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                tableSpec.getChecks(),
                () -> {
                    TableAdHocCheckCategoriesSpec container = new TableAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "checks"));
                    return container;
                });
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/results/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableAdHocChecksResultsOnDataStreamMonthRange", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableAdHocChecksResultsOnDataStreamMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        TableAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                tableSpec.getChecks(),
                () -> {
                    TableAdHocCheckCategoriesSpec container = new TableAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "checks"));
                    return container;
                });
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
            setDataStreamName(dataStreamName);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete results of the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/results")
    @ApiOperation(value = "getTableCheckpointsResults", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableCheckpointsResults(
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

        TableCheckpointsSpec checkpoints = tableSpec.getCheckpoints();

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            TableDailyCheckpointCategoriesSpec container = new TableDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            TableMonthlyCheckpointCategoriesSpec container = new TableMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, new CheckResultsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/results/datastream/{dataStreamName}")
    @ApiOperation(value = "getTableCheckpointsResultsOnDataStream", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableCheckpointsResultsOnDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
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

        TableCheckpointsSpec checkpoints = tableSpec.getCheckpoints();

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            TableDailyCheckpointCategoriesSpec container = new TableDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            TableMonthlyCheckpointCategoriesSpec container = new TableMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters(){{
            setDataStreamName(dataStreamName);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/results/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableCheckpointsResultsOnMonthRange", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableCheckpointsResultsOnMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        TableCheckpointsSpec checkpoints = tableSpec.getCheckpoints();

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            TableDailyCheckpointCategoriesSpec container = new TableDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            TableMonthlyCheckpointCategoriesSpec container = new TableMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters(){{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/results/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableCheckpointsResultsOnDataStreamMonthRange", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTableCheckpointsResultsOnDataStreamMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        TableCheckpointsSpec checkpoints = tableSpec.getCheckpoints();

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            TableDailyCheckpointCategoriesSpec container = new TableDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            TableMonthlyCheckpointCategoriesSpec container = new TableMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters(){{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete view of the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return View of the most recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/results")
    @ApiOperation(value = "getTablePartitionedChecksResults", notes = "Returns a complete view of the recent table level partitioned checks executions for a requested time scale",            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the most recent partitioned check executions for a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTablePartitionedChecksResults(
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

        final TablePartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        tableSpec.getPartitionedChecks(),
                        () -> {
                            TablePartitionedChecksRootSpec container = new TablePartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            TableDailyPartitionedCheckCategoriesSpec container = new TableDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            TableMonthlyPartitionedCheckCategoriesSpec container = new TableMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, new CheckResultsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/results/datastream/{dataStreamName}")
    @ApiOperation(value = "getTablePartitionedChecksResultsOnDataStream", notes = "Returns the complete view of the recent table level partitioned checks executions for a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the most recent partitioned check executions for a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTablePartitionedChecksResultsOnDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
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

        final TablePartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        tableSpec.getPartitionedChecks(),
                        () -> {
                            TablePartitionedChecksRootSpec container = new TablePartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            TableDailyPartitionedCheckCategoriesSpec container = new TableDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            TableMonthlyPartitionedCheckCategoriesSpec container = new TableMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters(){{
            setDataStreamName(dataStreamName);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/results/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTablePartitionedChecksResultsOnMonthRange", notes = "Returns the complete view of the recent table level partitioned checks executions for a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the most recent partitioned check executions for a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTablePartitionedChecksResultsOnMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        final TablePartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        tableSpec.getPartitionedChecks(),
                        () -> {
                            TablePartitionedChecksRootSpec container = new TablePartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            TableDailyPartitionedCheckCategoriesSpec container = new TableDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            TableMonthlyPartitionedCheckCategoriesSpec container = new TableMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters(){{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/results/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTablePartitionedChecksResultsOnDataStreamMonthRange", notes = "Returns the complete view of the recent table level partitioned checks executions for a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the most recent partitioned check executions for a requested time scale on a table returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getTablePartitionedChecksResultsOnDataStreamMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        final TablePartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        tableSpec.getPartitionedChecks(),
                        () -> {
                            TablePartitionedChecksRootSpec container = new TablePartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(tableSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            TableDailyPartitionedCheckCategoriesSpec container = new TableDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            TableMonthlyPartitionedCheckCategoriesSpec container = new TableMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters(){{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete results of the recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/results")
    @ApiOperation(value = "getColumnAdHocChecksResults", notes = "Returns an overview of the most recent check executions for all column level data quality ad-hoc checks on a column",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnAdHocChecksResults(
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

        ColumnAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                columnSpec.getChecks(),
                () -> {
                    ColumnAdHocCheckCategoriesSpec container = new ColumnAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checks"));
                    return container;
                });

        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();
        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data stream name.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/results/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnAdHocChecksResultsOnDataStream", notes = "Returns an overview of the most recent check executions for all column level data quality ad-hoc checks on a column",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnAdHocChecksResultsOnDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
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

        ColumnAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                columnSpec.getChecks(),
                () -> {
                    ColumnAdHocCheckCategoriesSpec container = new ColumnAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checks"));
                    return container;
                });

        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};
        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/results/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnAdHocChecksResultsOnMonthRange", notes = "Returns an overview of the most recent check executions for all column level data quality ad-hoc checks on a column",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnAdHocChecksResultsOnMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        ColumnAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                columnSpec.getChecks(),
                () -> {
                    ColumnAdHocCheckCategoriesSpec container = new ColumnAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checks"));
                    return container;
                });

        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};
        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete results of the recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent check results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/results/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnAdHocChecksResultsOnDataStreamMonthRange", notes = "Returns an overview of the most recent check executions for all column level data quality ad-hoc checks on a column",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnAdHocChecksResultsOnDataStreamMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        ColumnAdHocCheckCategoriesSpec checks = Objects.requireNonNullElseGet(
                columnSpec.getChecks(),
                () -> {
                    ColumnAdHocCheckCategoriesSpec container = new ColumnAdHocCheckCategoriesSpec();
                    container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checks"));
                    return container;
                });

        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};
        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete view of the most recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/results")
    @ApiOperation(value = "getColumnCheckpointsResults", notes = "Returns a complete view of the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnCheckpointsResults(
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

        final ColumnCheckpointsSpec checkpoints =
                Objects.requireNonNullElseGet(
                        columnSpec.getCheckpoints(),
                        () -> {
                            ColumnCheckpointsSpec container = new ColumnCheckpointsSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checkpoints"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            ColumnDailyCheckpointCategoriesSpec container = new ColumnDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            ColumnMonthlyCheckpointCategoriesSpec container = new ColumnMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters();

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of the most recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/results/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnCheckpointsResultsOnDataStream", notes = "Returns a complete view of the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnCheckpointsResultsOnDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
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

        final ColumnCheckpointsSpec checkpoints =
                Objects.requireNonNullElseGet(
                        columnSpec.getCheckpoints(),
                        () -> {
                            ColumnCheckpointsSpec container = new ColumnCheckpointsSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checkpoints"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            ColumnDailyCheckpointCategoriesSpec container = new ColumnDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            ColumnMonthlyCheckpointCategoriesSpec container = new ColumnMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of the most recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/results/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnCheckpointsResultsOnMonthRange", notes = "Returns a complete view of the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnCheckpointsResultsOnMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        final ColumnCheckpointsSpec checkpoints =
                Objects.requireNonNullElseGet(
                        columnSpec.getCheckpoints(),
                        () -> {
                            ColumnCheckpointsSpec container = new ColumnCheckpointsSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checkpoints"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            ColumnDailyCheckpointCategoriesSpec container = new ColumnDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            ColumnMonthlyCheckpointCategoriesSpec container = new ColumnMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of the most recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent checkpoint results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/results/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnCheckpointsResultsOnDataStreamMonthRange", notes = "Returns a complete view of the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnCheckpointsResultsOnDataStreamMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        final ColumnCheckpointsSpec checkpoints =
                Objects.requireNonNullElseGet(
                        columnSpec.getCheckpoints(),
                        () -> {
                            ColumnCheckpointsSpec container = new ColumnCheckpointsSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "checkpoints"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getDaily(),
                        () -> {
                            ColumnDailyCheckpointCategoriesSpec container = new ColumnDailyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        checkpoints.getMonthly(),
                        () -> {
                            ColumnMonthlyCheckpointCategoriesSpec container = new ColumnMonthlyCheckpointCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(checkpoints.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete view of recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/results")
    @ApiOperation(value = "getColumnPartitionedChecksResults", notes = "Returns an overview of the most recent column level partitioned checks executions for a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent partitioned check executions for a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnPartitionedChecksResults(
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

        final ColumnPartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        columnSpec.getPartitionedChecks(),
                        () -> {
                            ColumnPartitionedChecksRootSpec container = new ColumnPartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            ColumnDailyPartitionedCheckCategoriesSpec container = new ColumnDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            ColumnMonthlyPartitionedCheckCategoriesSpec container = new ColumnMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters();

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/results/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnPartitionedChecksResultsOnDataStream", notes = "Returns an overview of the most recent column level partitioned checks executions for a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent partitioned check executions for a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnPartitionedChecksResultsOnDataStream(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName) {
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

        final ColumnPartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        columnSpec.getPartitionedChecks(),
                        () -> {
                            ColumnPartitionedChecksRootSpec container = new ColumnPartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            ColumnDailyPartitionedCheckCategoriesSpec container = new ColumnDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            ColumnMonthlyPartitionedCheckCategoriesSpec container = new ColumnMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/results/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnPartitionedChecksResultsOnMonthRange", notes = "Returns an overview of the most recent column level partitioned checks executions for a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent partitioned check executions for a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnPartitionedChecksResultsOnMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        final ColumnPartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        columnSpec.getPartitionedChecks(),
                        () -> {
                            ColumnPartitionedChecksRootSpec container = new ColumnPartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            ColumnDailyPartitionedCheckCategoriesSpec container = new ColumnDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            ColumnMonthlyPartitionedCheckCategoriesSpec container = new ColumnMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/results/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnPartitionedChecksResultsOnDataStreamMonthRange", notes = "Returns an overview of the most recent column level partitioned checks executions for a requested time scale",
            response = CheckResultsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the recent partitioned check executions for a requested time scale on a column returned",
                    response = CheckResultsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CheckResultsDetailedDataModel>> getColumnPartitionedChecksResultsOnDataStreamMonthRange(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @PathVariable String dataStreamName,
            @ApiParam("Month start") @PathVariable LocalDate monthStart,
            @ApiParam("Month end") @PathVariable LocalDate monthEnd) {
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

        final ColumnPartitionedChecksRootSpec partitionedChecks =
                Objects.requireNonNullElseGet(
                        columnSpec.getPartitionedChecks(),
                        () -> {
                            ColumnPartitionedChecksRootSpec container = new ColumnPartitionedChecksRootSpec();
                            container.setHierarchyId(new HierarchyId(columnSpec.getHierarchyId(), "partitioned_checks"));
                            return container;
                        });

        AbstractRootChecksContainerSpec tempCheckpointPartition = null;
        switch (timeScale) {
            case daily:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getDaily(),
                        () -> {
                            ColumnDailyPartitionedCheckCategoriesSpec container = new ColumnDailyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;

            case monthly:
                tempCheckpointPartition = Objects.requireNonNullElseGet(
                        partitionedChecks.getMonthly(),
                        () -> {
                            ColumnMonthlyPartitionedCheckCategoriesSpec container = new ColumnMonthlyPartitionedCheckCategoriesSpec();
                            container.setHierarchyId(new HierarchyId(partitionedChecks.getHierarchyId(), timeScale.name()));
                            return container;
                        });
                break;
        }

        CheckResultsDetailedParameters loadParameters = new CheckResultsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }
}
