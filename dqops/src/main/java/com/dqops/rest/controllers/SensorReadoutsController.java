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
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.data.checkresults.services.CheckResultsDetailedFilterParameters;
import com.dqops.data.readouts.services.SensorReadoutsDataService;
import com.dqops.data.readouts.services.SensorReadoutsDetailedFilterParameters;
import com.dqops.data.readouts.services.models.SensorReadoutsListModel;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.core.principal.DqoUserPrincipal;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent sensor readouts.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/readouts", produces = "application/json")
    @ApiOperation(value = "getTableProfilingSensorReadouts", notes = "Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table",
            response = SensorReadoutsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for table level data quality profiling checks on a table returned",
                    response = SensorReadoutsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<SensorReadoutsListModel>> getTableProfilingSensorReadouts(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

        AbstractRootChecksContainerSpec checks = tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false);
        SensorReadoutsDetailedFilterParameters loadParams = new SensorReadoutsDetailedFilterParameters();
        checkName.ifPresent(loadParams::setCheckName);
        category.ifPresent(loadParams::setCheckCategory);
        tableComparison.ifPresent(loadParams::setTableComparison);
        dataGroup.ifPresent(loadParams::setDataGroupName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);
        maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

        SensorReadoutsListModel[] sensorReadoutsListModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsListModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent monitoring executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent sensor readouts.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/readouts", produces = "application/json")
    @ApiOperation(value = "getTableMonitoringSensorReadouts", notes = "Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale",
            response = SensorReadoutsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view of the most recent monitoring executions for the monitoring at a requested time scale on a table returned",
                    response = SensorReadoutsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<SensorReadoutsListModel>> getTableMonitoringSensorReadouts(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

        AbstractRootChecksContainerSpec monitoringPartition = tableSpec.getTableCheckRootContainer(CheckType.monitoring, timeScale, false);
        SensorReadoutsDetailedFilterParameters loadParams = new SensorReadoutsDetailedFilterParameters();
        checkName.ifPresent(loadParams::setCheckName);
        category.ifPresent(loadParams::setCheckCategory);
        tableComparison.ifPresent(loadParams::setTableComparison);
        dataGroup.ifPresent(loadParams::setDataGroupName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);
        maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

        SensorReadoutsListModel[] sensorReadoutsListModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                monitoringPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsListModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent partitioned check executions on a table given a connection name, table name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the most recent partitioned checks results.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/readouts", produces = "application/json")
    @ApiOperation(value = "getTablePartitionedSensorReadouts", notes = "Returns a complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale",
            response = SensorReadoutsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The complete view of the sensor readouts for recent partitioned check executions for a requested time scale on a table returned",
                    response = SensorReadoutsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<SensorReadoutsListModel>> getTablePartitionedSensorReadouts(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

        AbstractRootChecksContainerSpec partitionedCheckPartition = tableSpec.getTableCheckRootContainer(CheckType.partitioned, timeScale, false);
        SensorReadoutsDetailedFilterParameters loadParams = new SensorReadoutsDetailedFilterParameters();
        checkName.ifPresent(loadParams::setCheckName);
        category.ifPresent(loadParams::setCheckCategory);
        tableComparison.ifPresent(loadParams::setTableComparison);
        dataGroup.ifPresent(loadParams::setDataGroupName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);
        maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

        SensorReadoutsListModel[] sensorReadoutsListModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                partitionedCheckPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsListModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent check executions on a column given a connection name, table name and column name.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent sensor readouts.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/readouts", produces = "application/json")
    @ApiOperation(value = "getColumnProfilingSensorReadouts", notes = "Returns sensor results of the recent check executions for all column level data quality profiling checks on a column",
            response = SensorReadoutsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Complete view sensor readouts of recent check runs for column level data quality profiling checks on a column returned",
                    response = SensorReadoutsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<SensorReadoutsListModel>> getColumnProfilingSensorReadouts(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

        AbstractRootChecksContainerSpec checks = columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);
        SensorReadoutsDetailedFilterParameters loadParams = new SensorReadoutsDetailedFilterParameters();
        checkName.ifPresent(loadParams::setCheckName);
        category.ifPresent(loadParams::setCheckCategory);
        tableComparison.ifPresent(loadParams::setTableComparison);
        dataGroup.ifPresent(loadParams::setDataGroupName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);
        maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

        SensorReadoutsListModel[] sensorReadoutsListModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                checks, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsListModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete sensor readouts of recent monitoring executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of the recent sensor readouts.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/readouts", produces = "application/json")
    @ApiOperation(value = "getColumnMonitoringSensorReadouts", notes = "Returns a complete view of the sensor readouts for recent column level monitoring executions for the monitoring at a requested time scale",
            response = SensorReadoutsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts of recent monitoring executions for the monitoring at a requested time scale on a column returned",
                    response = SensorReadoutsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<SensorReadoutsListModel>> getColumnMonitoringSensorReadouts(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

        AbstractRootChecksContainerSpec monitoringPartition = columnSpec.getColumnCheckRootContainer(CheckType.monitoring, timeScale, false);
        SensorReadoutsDetailedFilterParameters loadParams = new SensorReadoutsDetailedFilterParameters();
        checkName.ifPresent(loadParams::setCheckName);
        category.ifPresent(loadParams::setCheckCategory);
        tableComparison.ifPresent(loadParams::setTableComparison);
        dataGroup.ifPresent(loadParams::setDataGroupName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);
        maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

        SensorReadoutsListModel[] sensorReadoutsListModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                monitoringPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsListModels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the complete view of sensor readouts for recent partitioned check executions on a column given a connection name, table name, column name and a time scale.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @param timeScale      Time scale.
     * @param dataGroup      Data group.
     * @param monthStart     Month start boundary.
     * @param monthEnd       Month end boundary.
     * @return View of sensor readouts of the recent partitioned checks.
     */
    @GetMapping(value = "/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/readouts", produces = "application/json")
    @ApiOperation(value = "getColumnPartitionedSensorReadouts", notes = "Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale",
            response = SensorReadoutsListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "View of the sensor readouts for recent partitioned check executions for a requested time scale on a column returned",
                    response = SensorReadoutsListModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found or time scale invalid"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<SensorReadoutsListModel>> getColumnPartitionedSensorReadouts(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Schema name") @PathVariable String schemaName,
            @ApiParam("Table name") @PathVariable String tableName,
            @ApiParam("Column name") @PathVariable String columnName,
            @ApiParam("Time scale") @PathVariable CheckTimeScale timeScale,
            @ApiParam(name = "dataGroup", value = "Data group", required = false) @RequestParam(required = false) Optional<String> dataGroup,
            @ApiParam(name = "monthStart", value = "Month start boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthStart,
            @ApiParam(name = "monthEnd", value = "Month end boundary", required = false) @RequestParam(required = false) Optional<LocalDate> monthEnd,
            @ApiParam(name = "checkName", value = "Check name", required = false) @RequestParam(required = false) Optional<String> checkName,
            @ApiParam(name = "category", value = "Check category name", required = false) @RequestParam(required = false) Optional<String> category,
            @ApiParam(name = "tableComparison", value = "Table comparison name", required = false) @RequestParam(required = false) Optional<String> tableComparison,
            @ApiParam(name = "maxResultsPerCheck", value = "Maximum number of results per check, the default is " +
                    CheckResultsDetailedFilterParameters.DEFAULT_MAX_RESULTS_PER_CHECK, required = false) @RequestParam(required = false) Optional<Integer>  maxResultsPerCheck) {
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

        AbstractRootChecksContainerSpec partitionedCheckPartition = columnSpec.getColumnCheckRootContainer(CheckType.partitioned, timeScale, false);
        SensorReadoutsDetailedFilterParameters loadParams = new SensorReadoutsDetailedFilterParameters();
        checkName.ifPresent(loadParams::setCheckName);
        category.ifPresent(loadParams::setCheckCategory);
        tableComparison.ifPresent(loadParams::setTableComparison);
        dataGroup.ifPresent(loadParams::setDataGroupName);
        monthStart.ifPresent(loadParams::setStartMonth);
        monthEnd.ifPresent(loadParams::setEndMonth);
        maxResultsPerCheck.ifPresent(loadParams::setMaxResultsPerCheck);

        SensorReadoutsListModel[] sensorReadoutsListModels = this.sensorReadoutsDataService.readSensorReadoutsDetailed(
                partitionedCheckPartition, loadParams);
        return new ResponseEntity<>(Flux.fromArray(sensorReadoutsListModels), HttpStatus.OK); // 200
    }
}
