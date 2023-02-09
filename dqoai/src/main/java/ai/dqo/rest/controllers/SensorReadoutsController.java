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
import ai.dqo.checks.CheckType;
import ai.dqo.data.readouts.services.SensorReadoutsDataService;
import ai.dqo.data.readouts.services.SensorReadoutsDetailedParameters;
import ai.dqo.data.readouts.services.models.SensorReadoutsDetailedDataModel;
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
import java.util.Optional;

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
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Data stream name") @RequestParam Optional<String> dataStreamName,
            @ApiParam("Month start boundary") @RequestParam Optional<LocalDate> monthStart,
            @ApiParam("Month end boundary") @RequestParam Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.ADHOC, null);
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

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
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/readouts")
    @ApiOperation(value = "getTableCheckpointsSensorReadouts", notes = "Returns the complete results of the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getTableCheckpointsSensorReadouts(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @RequestParam Optional<String> dataStreamName,
            @ApiParam("Month start boundary") @RequestParam Optional<LocalDate> monthStart,
            @ApiParam("Month end boundary") @RequestParam Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec checkpointPartition = tableSpec.getTableCheckRootContainer(CheckType.CHECKPOINT, timeScale);
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checkpointPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the most recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/readouts")
    @ApiOperation(value = "getTablePartitionedSensorReadouts", notes = "Returns a complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale", response = SensorReadoutsDetailedDataModel[].class)
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
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @RequestParam Optional<String> dataStreamName,
            @ApiParam("Month start boundary") @RequestParam Optional<LocalDate> monthStart,
            @ApiParam("Month end boundary") @RequestParam Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec partitionedCheckPartition = tableSpec.getTableCheckRootContainer(CheckType.PARTITIONED, timeScale);
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                partitionedCheckPartition, new SensorReadoutsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete sensor readouts of recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Data stream name") @RequestParam Optional<String> dataStreamName,
            @ApiParam("Month start boundary") @RequestParam Optional<LocalDate> monthStart,
            @ApiParam("Month end boundary") @RequestParam Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.ADHOC, null);
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

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
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent sensor readouts.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/readouts")
    @ApiOperation(value = "getColumnCheckpointsSensorReadouts", notes = "Returns a complete view of the sensor readouts for recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = SensorReadoutsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts of recent checkpoint executions for the checkpoints at a requested time scale on a column returned",
                    response = SensorReadoutsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<SensorReadoutsDetailedDataModel>> getColumnCheckpointsSensorReadouts(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @RequestParam Optional<String> dataStreamName,
            @ApiParam("Month start boundary") @RequestParam Optional<LocalDate> monthStart,
            @ApiParam("Month end boundary") @RequestParam Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec checkpointPartition = columnSpec.getColumnCheckRootContainer(CheckType.CHECKPOINT, timeScale);
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checkpointPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of sensor readouts for recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam("Data stream name") @RequestParam Optional<String> dataStreamName,
            @ApiParam("Month start boundary") @RequestParam Optional<LocalDate> monthStart,
            @ApiParam("Month end boundary") @RequestParam Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec partitionedCheckPartition = columnSpec.getColumnCheckRootContainer(CheckType.PARTITIONED, timeScale);
        SensorReadoutsDetailedParameters loadParams = new SensorReadoutsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        SensorReadoutsDetailedDataModel[] sensorReadoutsDetailedDataModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                partitionedCheckPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsDetailedDataModels), HttpStatus.OK); // 200
    }
}
