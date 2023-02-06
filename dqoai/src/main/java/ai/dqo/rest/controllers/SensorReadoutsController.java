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
import ai.dqo.data.readouts.services.SensorReadoutsDataService;
import ai.dqo.data.readouts.services.SensorReadoutsDetailedParameters;
import ai.dqo.data.readouts.services.models.SensorReadoutsDetailedDataModel;
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
 * Controller over the sensor readouts on tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "SensorReadouts", description = "Returns the complete sensor readouts of executed checks on tables and columns.")
public class SensorReadoutsController {
    private UserHomeContextFactory userHomeContextFactory;
    private SensorReadoutsDataService sensorReadoutsDataService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param sensorReadoutsDataService Sensor readouts data service.
     */
    @Autowired
    public SensorReadoutsController(UserHomeContextFactory userHomeContextFactory,
                                    SensorReadoutsDataService sensorReadoutsDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.sensorReadoutsDataService = sensorReadoutsDataService;
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/readouts")
    @ApiOperation(value = "getTableAdHocSensorReadouts", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableAdHocSensorReadouts(
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
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/readouts/datastream/{dataStreamName}")
    @ApiOperation(value = "getTableAdHocSensorReadoutsOnDataStream", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableAdHocSensorReadoutsOnDataStream(
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
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/readouts/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableAdHocSensorReadoutsOnMonthRange", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableAdHocSensorReadoutsOnMonthRange(
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
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/readouts/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableAdHocSensorReadoutsOnDataStreamMonthRange", notes = "Returns the complete results of the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableAdHocSensorReadoutsOnDataStreamMonthRange(
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
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
            setDataStreamName(dataStreamName);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/readouts")
    @ApiOperation(value = "getTableSensorReadouts", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableSensorReadouts(
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

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, new SensorReadoutsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/readouts/datastream/{dataStreamName}")
    @ApiOperation(value = "getTableSensorReadoutsOnDataStream", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableSensorReadoutsOnDataStream(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters(){{
            setDataStreamName(dataStreamName);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/readouts/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableSensorReadoutsOnMonthRange", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableSensorReadoutsOnMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters(){{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/readouts/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableSensorReadoutsOnDataStreamMonthRange", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableSensorReadoutsOnDataStreamMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters(){{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete sensor readouts of recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return View of the most recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/readouts")
    @ApiOperation(value = "getTablePartitionedSensorReadouts", notes = "Returns a complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale",            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the sensor readouts for recent partitioned check executions for a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTablePartitionedSensorReadouts(
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

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, new SensorReadoutsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/readouts/datastream/{dataStreamName}")
    @ApiOperation(value = "getTablePartitionedSensorReadoutsOnDataStream", notes = "Returns the complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the sensor readouts for recent partitioned check executions for a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTablePartitionedSensorReadoutsOnDataStream(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters(){{
            setDataStreamName(dataStreamName);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/readouts/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTablePartitionedSensorReadoutsOnMonthRange", notes = "Returns the complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the sensor readouts for recent partitioned check executions for a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTablePartitionedSensorReadoutsOnMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters(){{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/readouts/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTablePartitionedSensorReadoutsOnDataStreamMonthRange", notes = "Returns the complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the sensor readouts for recent partitioned check executions for a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTablePartitionedSensorReadoutsOnDataStreamMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters(){{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete sensor readouts of recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/readouts")
    @ApiOperation(value = "getColumnAdHocSensorReadouts", notes = "Returns sensor results of the recent check executions for all column level data quality ad-hoc checks on a column",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnAdHocSensorReadouts(
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

        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();
        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data stream name.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/readouts/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnAdHocSensorReadoutsOnDataStream", notes = "Returns sensor results of the recent check executions for all column level data quality ad-hoc checks on a column",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnAdHocSensorReadoutsOnDataStream(
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

        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};
        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/readouts/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnAdHocSensorReadoutsOnMonthRange", notes = "Returns sensor results of the recent check executions for all column level data quality ad-hoc checks on a column",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnAdHocSensorReadoutsOnMonthRange(
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

        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};
        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/readouts/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnAdHocSensorReadoutsOnDataStreamMonthRange", notes = "Returns sensor results of the recent check executions for all column level data quality ad-hoc checks on a column",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnAdHocSensorReadoutsOnDataStreamMonthRange(
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

        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};
        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/readouts")
    @ApiOperation(value = "getColumnCheckpointSensorReadouts", notes = "Returns a complete view of the sensor readouts for recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts of recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnCheckpointSensorReadouts(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters();

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/readouts/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnCheckpointSensorReadoutsOnDataStream", notes = "Returns a complete view of the sensor readouts for recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts of recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnCheckpointSensorReadoutsOnDataStream(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/readouts/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnCheckpointSensorReadoutsOnMonthRange", notes = "Returns a complete view of the sensor readouts for recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts of recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnCheckpointSensorReadoutsOnMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/readouts/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnCheckpointSensorReadoutsOnDataStreamMonthRange", notes = "Returns a complete view of the sensor readouts for recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts of recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnCheckpointSensorReadoutsOnDataStreamMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete view of sensor readouts for recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/readouts")
    @ApiOperation(value = "getColumnPartitionedSensorReadouts", notes = "Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts for recent partitioned check executions for a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnPartitionedSensorReadouts(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters();

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of sensor readouts for recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/readouts/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnPartitionedSensorReadoutsOnDataStream", notes = "Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts for recent partitioned check executions for a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnPartitionedSensorReadoutsOnDataStream(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of sensor readouts for recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/readouts/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnPartitionedSensorReadoutsOnMonthRange", notes = "Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts for recent partitioned check executions for a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnPartitionedSensorReadoutsOnMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of sensor readouts for recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/readouts/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnPartitionedSensorReadoutsOnDataStreamMonthRange", notes = "Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts for recent partitioned check executions for a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnPartitionedSensorReadoutsOnDataStreamMonthRange(
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

        SensorReadoutsDetailedParameters loadParameters = new SensorReadoutsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }
}
