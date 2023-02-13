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
import ai.dqo.data.ruleresults.services.CheckResultsDetailedParameters;
import ai.dqo.data.ruleresults.services.RuleResultsDataService;
import ai.dqo.data.ruleresults.services.models.CheckResultsDetailedDataModel;
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
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

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
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checkpointPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the most recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/results")
    @ApiOperation(value = "getTablePartitionedChecksResults", notes = "Returns a complete view of the recent table level partitioned checks executions for a requested time scale", response = CheckResultsDetailedDataModel[].class)
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
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                partitionedCheckPartition, new CheckResultsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the complete results of the recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

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
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                checkpointPartition, loadParams);
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
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
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
        CheckResultsDetailedParameters loadParams = new CheckResultsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        CheckResultsDetailedDataModel[] checkResultsDetailedDataModels = this.ruleResultsDataService.readCheckStatusesDetailed(
                partitionedCheckPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(checkResultsDetailedDataModels), HttpStatus.OK); // 200
    }
}
