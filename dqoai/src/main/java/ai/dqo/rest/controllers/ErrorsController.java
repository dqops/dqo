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
import ai.dqo.data.errors.services.ErrorsDataService;
import ai.dqo.data.errors.services.ErrorsDetailedParameters;
import ai.dqo.data.errors.services.models.ErrorsDetailedDataModel;
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
 * Controller over the errors on tables and columns.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Errors", description = "Returns the errors related to check executions on tables and columns.")
public class ErrorsController {
    private UserHomeContextFactory userHomeContextFactory;
    private ErrorsDataService errorsDataService;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param errorsDataService Errors data service.
     */
    @Autowired
    public ErrorsController(UserHomeContextFactory userHomeContextFactory,
                            ErrorsDataService errorsDataService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.errorsDataService = errorsDataService;
    }

    /**
     * Retrieves the errors related to check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/errors")
    @ApiOperation(value = "getTableProfilingErrors", notes = "Returns the errors related to the most recent check executions for all table level data quality profiling checks on a table",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for table level data quality profiling checks on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableProfilingErrors(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(value = "Data stream name", required = false) @RequestParam(required = false) Optional<String> dataStreamName,
            @ApiParam(value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.PROFILING, null);
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the most recent recurring executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/recurring/{timeScale}/errors")
    @ApiOperation(value = "getTableRecurringErrors", notes = "Returns the errors related to the most recent table level recurring executions for the recurring at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent recurring executions for the recurring at a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableRecurringErrors(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Data stream name", required = false) @RequestParam(required = false) Optional<String> dataStreamName,
            @ApiParam(value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec recurring = tableSpec.getTableCheckRootContainer(CheckType.RECURRING, timeScale);
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                recurring, new ErrorsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/errors")
    @ApiOperation(value = "getTablePartitionedErrors", notes = "Returns errors related to the recent table level partitioned checks executions for a requested time scale", response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The errors related to partitioned check executions for a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTablePartitionedErrors(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Data stream name", required = false) @RequestParam(required = false) Optional<String> dataStreamName,
            @ApiParam(value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec partitionedChecks = tableSpec.getTableCheckRootContainer(CheckType.PARTITIONED, timeScale);
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                partitionedChecks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/errors")
    @ApiOperation(value = "getColumnProfilingErrors", notes = "Returns the errors related to the recent check executions for all column level data quality profiling checks on a column",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for column level data quality profiling checks on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnProfilingErrors(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam(value = "Data stream name", required = false) @RequestParam(required = false) Optional<String> dataStreamName,
            @ApiParam(value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.PROFILING, null, false);
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to recurring executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/recurring/{timeScale}/errors")
    @ApiOperation(value = "getColumnRecurringErrors", notes = "Returns errors related to the recent column level recurring executions for the recurring at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors for the recurring at a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnRecurringErrors(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Data stream name", required = false) @RequestParam(required = false) Optional<String> dataStreamName,
            @ApiParam(value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec recurring = columnSpec.getColumnCheckRootContainer(CheckType.RECURRING, timeScale, false);
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                recurring, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data-stream name.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/errors")
    @ApiOperation(value = "getColumnPartitionedErrors", notes = "Returns the errors related to the recent column level partitioned checks executions for a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors related to the recent partitioned check executions for a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnPartitionedErrors(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(value = "Data stream name", required = false) @RequestParam(required = false) Optional<String> dataStreamName,
            @ApiParam(value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd) {
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

        AbstractRootChecksContainerSpec partitionedChecks = columnSpec.getColumnCheckRootContainer(CheckType.PARTITIONED, timeScale, false);
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();
        dataStreamName.ifPresent(loadParams::setDataStreamName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                partitionedChecks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }
}
