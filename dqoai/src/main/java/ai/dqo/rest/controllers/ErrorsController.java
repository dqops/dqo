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
import ai.dqo.data.errors.services.ErrorsDataService;
import ai.dqo.data.errors.services.ErrorsDetailedParameters;
import ai.dqo.data.errors.services.models.ErrorsDetailedDataModel;
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
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/errors")
    @ApiOperation(value = "getTableAdHocErrors", notes = "Returns the errors related to the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableAdHocErrors(
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
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/errors/datastream/{dataStreamName}")
    @ApiOperation(value = "getTableAdHocErrorsOnDataStream", notes = "Returns the errors related to the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableAdHocErrorsOnDataStream(
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
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/errors/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableAdHocErrorsOnMonthRange", notes = "Returns the errors related to the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableAdHocErrorsOnMonthRange(
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
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to check executions on a table given a connection name and a table name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checks/errors/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableAdHocErrorsOnDataStreamMonthRange", notes = "Returns the errors related to the most recent check executions for all table level data quality ad-hoc checks on a table",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for table level data quality ad-hoc checks on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableAdHocErrorsOnDataStreamMonthRange(
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
        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
            setDataStreamName(dataStreamName);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the errors related to the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/errors")
    @ApiOperation(value = "getTableCheckpointErrors", notes = "Returns the errors related to the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableCheckpointErrors(
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

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, new ErrorsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/errors/datastream/{dataStreamName}")
    @ApiOperation(value = "getTableCheckpointErrorsOnDataStream", notes = "Returns the errors related to the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableCheckpointErrorsOnDataStream(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters(){{
            setDataStreamName(dataStreamName);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/errors/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableCheckpointErrorsOnMonthRange", notes = "Returns the errors related to the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableCheckpointErrorsOnMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters(){{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the most recent checkpoint executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/checkpoints/{timeScale}/errors/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTableCheckpointErrorsOnDataStreamMonthRange", notes = "Returns the errors related to the most recent table level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent checkpoint executions for the checkpoints at a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTableCheckpointErrorsOnDataStreamMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters(){{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the errors related to the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @return View of the errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/errors")
    @ApiOperation(value = "getTablePartitionedErrors", notes = "Returns errors related to the recent table level partitioned checks executions for a requested time scale",            response = ErrorsDetailedDataModel[].class)
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

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, new ErrorsDetailedParameters());
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/errors/datastream/{dataStreamName}")
    @ApiOperation(value = "getTablePartitionedErrorsOnDataStream", notes = "Returns the errors related to the recent table level partitioned checks executions for a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The errors related to partitioned check executions for a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTablePartitionedErrorsOnDataStream(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters(){{
            setDataStreamName(dataStreamName);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/errors/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTablePartitionedErrorsOnMonthRange", notes = "Returns the errors related to the recent table level partitioned checks executions for a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The errors related to partitioned check executions for a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTablePartitionedErrorsOnMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters(){{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to the recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitionedchecks/{timeScale}/errors/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getTablePartitionedErrorsOnDataStreamMonthRange", notes = "Returns the errors related to the recent table level partitioned checks executions for a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The errors related to partitioned check executions for a requested time scale on a table returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getTablePartitionedErrorsOnDataStreamMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters(){{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the errors related to check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/errors")
    @ApiOperation(value = "getColumnAdHocErrors", notes = "Returns the errors related to the recent check executions for all column level data quality ad-hoc checks on a column",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnAdHocErrors(
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

        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters();
        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data stream name.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/errors/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnAdHocErrorsOnDataStream", notes = "Returns the errors related to the recent check executions for all column level data quality ad-hoc checks on a column",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnAdHocErrorsOnDataStream(
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

        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};
        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/errors/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnAdHocErrorsOnMonthRange", notes = "Returns the errors related to the recent check executions for all column level data quality ad-hoc checks on a column",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnAdHocErrorsOnMonthRange(
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

        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};
        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks/errors/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnAdHocErrorsOnDataStreamMonthRange", notes = "Returns the errors related to the recent check executions for all column level data quality ad-hoc checks on a column",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Errors related to the most recent check runs for column level data quality ad-hoc checks on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnAdHocErrorsOnDataStreamMonthRange(
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

        ErrorsDetailedParameters loadParams = new ErrorsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};
        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the errors related to checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/errors")
    @ApiOperation(value = "getColumnCheckpointErrors", notes = "Returns errors related to the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors for the checkpoints at a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnCheckpointErrors(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters();

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/errors/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnCheckpointErrorsOnDataStream", notes = "Returns errors related to the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors for the checkpoints at a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnCheckpointErrorsOnDataStream(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/errors/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnCheckpointErrorsOnMonthRange", notes = "Returns errors related to the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors for the checkpoints at a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnCheckpointErrorsOnMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to checkpoint executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of the recent errors.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checkpoints/{timeScale}/errors/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnCheckpointErrorsOnDataStreamMonthRange", notes = "Returns errors related to the recent column level checkpoint executions for the checkpoints at a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors for the checkpoints at a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnCheckpointErrorsOnDataStreamMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }


    /**
     * Retrieves the errors related to recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters();

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @return View of errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/errors/datastream/{dataStreamName}")
    @ApiOperation(value = "getColumnPartitionedErrorsOnDataStream", notes = "Returns the errors related to the recent column level partitioned checks executions for a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors related to the recent partitioned check executions for a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnPartitionedErrorsOnDataStream(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters() {{
            setDataStreamName(dataStreamName);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/errors/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnPartitionedErrorsOnMonthRange", notes = "Returns the errors related to the recent column level partitioned checks executions for a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors related to the recent partitioned check executions for a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnPartitionedErrorsOnMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters() {{
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the errors related to recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataStreamName Data stream name.
     * @param monthStart     Month start.
     * @param monthEnd       Month end.
     * @return View of errors related to the recent partitioned checks results.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitionedchecks/{timeScale}/errors/datastream/{dataStreamName}/{monthStart}_{monthEnd}")
    @ApiOperation(value = "getColumnPartitionedErrorsOnDataStreamMonthRange", notes = "Returns the errors related to the recent column level partitioned checks executions for a requested time scale",
            response = ErrorsDetailedDataModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of errors related to the recent partitioned check executions for a requested time scale on a column returned",
                    response = ErrorsDetailedDataModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ErrorsDetailedDataModel>> getColumnPartitionedErrorsOnDataStreamMonthRange(
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

        ErrorsDetailedParameters loadParameters = new ErrorsDetailedParameters() {{
            setDataStreamName(dataStreamName);
            setStartMonth(monthStart);
            setEndMonth(monthEnd);
        }};

        ErrorsDetailedDataModel[] errorsDetailedDataModels = this.errorsDataService.readErrorsDetailed(
                tempCheckpointPartition, loadParameters);
        return new ResponseEntity<>(Flux.fromArray(errorsDetailedDataModels), HttpStatus.OK); // 200
    }
}
