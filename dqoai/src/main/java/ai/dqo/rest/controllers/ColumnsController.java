/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rest.controllers;

import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingService;
import ai.dqo.rest.models.checks.mapping.UiToSpecCheckMappingService;
import ai.dqo.rest.models.metadata.ColumnBasicModel;
import ai.dqo.rest.models.metadata.ColumnModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * REST api controller to manage the list of columns inside a table.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Columns", description = "Manages columns inside a table")
public class ColumnsController {
    private UserHomeContextFactory userHomeContextFactory;
    private SpecToUiCheckMappingService specToUiCheckMappingService;
    private UiToSpecCheckMappingService uiToSpecCheckMappingService;

    /**
     * Creates a columns rest controller.
     * @param userHomeContextFactory      User home context factory.
     * @param specToUiCheckMappingService Check mapper to convert the check specification to a UI model.
     * @param uiToSpecCheckMappingService Check mapper to convert the check UI model to a check specification.
     */
    @Autowired
    public ColumnsController(UserHomeContextFactory userHomeContextFactory,
                             SpecToUiCheckMappingService specToUiCheckMappingService,
                             UiToSpecCheckMappingService uiToSpecCheckMappingService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.specToUiCheckMappingService = specToUiCheckMappingService;
        this.uiToSpecCheckMappingService = uiToSpecCheckMappingService;
    }

    /**
     * Returns a list of columns inside a table.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @parqm tableName      Table name
     * @return List of columns inside a table.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns")
    @ApiOperation(value = "getColumns", notes = "Returns a list of columns inside a table", response = ColumnBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ColumnBasicModel[].class),
            @ApiResponse(code = 404, message = "Connection or table not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ColumnBasicModel>> getColumns(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND);
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
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
     * Retrieves the full column specification given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Column full specification for the requested column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
    @ApiOperation(value = "getColumn", notes = "Return the full column specification", response = ColumnModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column returned", response = ColumnModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnModel>> getColumn(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic")
    @ApiOperation(value = "getColumnBasic", notes = "Return the column specification", response = ColumnBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Column basic details returned", response = ColumnBasicModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnBasicModel>> getColumnBasic(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        ColumnBasicModel columnBasicModel = ColumnBasicModel.fromColumnSpecification(
                connectionName, tableWrapper.getPhysicalTableName(), columnName, columnSpec);

        return new ResponseEntity<>(Mono.just(columnBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the list of labels assigned to a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return List of labels assigned to a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels")
    @ApiOperation(value = "getColumnLabels", notes = "Return the list of labels assigned to a column", response = LabelSetSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of labels assigned to a column returned", response = LabelSetSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<LabelSetSpec>> getColumnLabels(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments")
    @ApiOperation(value = "getColumnComments", notes = "Return the list of comments assigned to a column", response = CommentsListSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of comments assigned to a column returned", response = CommentsListSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CommentsListSpec>> getColumnComments(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        CommentsListSpec comments = columnSpec.getComments();

        return new ResponseEntity<>(Mono.justOrEmpty(comments), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of an overridden schedule on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Overridden schedule configuration on a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/scheduleoverride")
    @ApiOperation(value = "getColumnScheduleOverride", notes = "Return the configuration of an overridden schedule on a column", response = RecurringScheduleSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of an overridden schedule on a column returned", response = RecurringScheduleSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RecurringScheduleSpec>> getColumnScheduleOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        RecurringScheduleSpec scheduleOverride = columnSpec.getScheduleOverride();

        return new ResponseEntity<>(Mono.justOrEmpty(scheduleOverride), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of an overridden time series on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Overridden time series configuration on a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/timeseriesoverride")
    @ApiOperation(value = "getColumnTimeSeriesOverride", notes = "Return the configuration of an overridden time series on a column", response = TimeSeriesConfigurationSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of an overridden time series on a column returned", response = TimeSeriesConfigurationSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<TimeSeriesConfigurationSpec>> getColumnTimeSeriesOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        TimeSeriesConfigurationSpec timeSeriesOverride = columnSpec.getTimeSeriesOverride();

        return new ResponseEntity<>(Mono.justOrEmpty(timeSeriesOverride), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of an overridden data streams mapping on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Overridden data streams configuration on a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/datastreamsoverride")
    @ApiOperation(value = "getColumnDataStreamsOverride", notes = "Return the configuration of overridden data streams on a column", response = DataStreamMappingSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of overridden data streams on a column returned", response = DataStreamMappingSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Deprecated
    public ResponseEntity<Mono<DataStreamMappingSpec>> getColumnDataStreamsOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        DataStreamMappingSpec dataStreamsOverride = columnSpec.getDataStreamsOverride();

        return new ResponseEntity<>(Mono.justOrEmpty(dataStreamsOverride), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the configuration of column level data quality checks on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return Column level data quality checks on a column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks")
    @ApiOperation(value = "getColumnChecks", notes = "Return the configuration of column level data quality checks on a column", response = ColumnAdHocCheckCategoriesSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Configuration of column level data quality checks on a column returned", response = ColumnAdHocCheckCategoriesSpec.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ColumnAdHocCheckCategoriesSpec>> getColumnChecks(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        ColumnAdHocCheckCategoriesSpec checks = columnSpec.getChecks();

        return new ResponseEntity<>(Mono.justOrEmpty(checks), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a UI friendly model of column level data quality checks on a column given a connection, table add column names.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnName     Column name.
     * @return UI friendly data quality check list on a requested column.
     */
    @GetMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checksui")
    @ApiOperation(value = "getColumnChecksUI", notes = "Return a UI friendly model of column level data quality checks on a column", response = UIAllChecksModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UI model of column level data quality checks on a column returned", response = UIAllChecksModel.class),
            @ApiResponse(code = 404, message = "Connection, table or column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<UIAllChecksModel>> getColumnChecksUI(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        ColumnAdHocCheckCategoriesSpec checks = columnSpec.getChecks();
        if (checks == null) {
            checks = new ColumnAdHocCheckCategoriesSpec();
        }
        UIAllChecksModel checksUiModel = this.specToUiCheckMappingService.createUiModel(checks);

        return new ResponseEntity<>(Mono.justOrEmpty(checksUiModel), HttpStatus.OK); // 200
    }

    /**
     * Creates (adds) a new column metadata.
     * @param connectionName Connection name.
     * @param schemaName     Schema name.
     * @param tableName      Table name.
     * @param columnSpec     Column specification.
     * @return Empty response.
     */
    @PostMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Column specification") @RequestBody ColumnSpec columnSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        TableWrapper tableWrapper = tables.getByObjectName(
                new PhysicalTableName(schemaName, tableName), true);
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
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Column specification") @RequestBody ColumnSpec columnSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic")
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Basic column information to store") @RequestBody ColumnBasicModel columnBasicModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        if (!Objects.equals(connectionName, columnBasicModel.getConnectionName()) ||
                !Objects.equals(columnName, columnBasicModel.getColumnName())) {
            return new ResponseEntity<>(Mono.justOrEmpty("Connection name and column name in the model must match the connection name and the column name in the url"),
                    HttpStatus.NOT_ACCEPTABLE); // 400 - wrong values
        }

        if (columnBasicModel.getTable() == null ||
                !Objects.equals(schemaName, columnBasicModel.getTable().getSchemaName()) ||
                !Objects.equals(tableName, columnBasicModel.getTable().getTableName())) {
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

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        columnBasicModel.copyToColumnSpecification(columnSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of the overridden schedule on a column.
     * @param connectionName        Connection name.
     * @param schemaName            Schema name.
     * @param tableName             Table name.
     * @param columnName            Column name.
     * @param recurringScheduleSpec Overridden schedule configuration to store or an empty optional to clear the schedule configuration on a column.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/scheduleoverride")
    @ApiOperation(value = "updateColumnScheduleOverride", notes = "Updates the overridden schedule configuration on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's overridden schedule configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnScheduleOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Overridden schedule configuration or an empty object to clear the schedule from the column level")
                @RequestBody Optional<RecurringScheduleSpec> recurringScheduleSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (recurringScheduleSpec.isPresent()) {
            columnSpec.setScheduleOverride(recurringScheduleSpec.get());
        } else {
            columnSpec.setScheduleOverride(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of the overridden data streams configuration on a column.
     * @param connectionName              Connection name.
     * @param schemaName                  Schema name.
     * @param tableName                   Table name.
     * @param columnName                  Column name.
     * @param dataStreamsConfigurationSpec Overridden data streams configuration to store or an empty optional to clear the data streams configuration on a column.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/datastreamsoverride")
    @ApiOperation(value = "updateColumnDataStreamsOverride", notes = "Updates the overridden data streams configuration on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's overridden data streams configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Deprecated
    public ResponseEntity<Mono<?>> updateColumnDataStreamsOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Overridden data streams configuration or an empty object to clear the customized data streams configuration from the column level")
            @RequestBody Optional<DataStreamMappingSpec> dataStreamsConfigurationSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (dataStreamsConfigurationSpec.isPresent()) {
            columnSpec.setDataStreamsOverride(dataStreamsConfigurationSpec.get());
        } else {
            columnSpec.setDataStreamsOverride(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of the overridden time series on a column.
     * @param connectionName              Connection name.
     * @param schemaName                  Schema name.
     * @param tableName                   Table name.
     * @param columnName                  Column name.
     * @param timeSeriesConfigurationSpec Overridden time series configuration to store or an empty optional to clear the time series configuration on a column.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/timeseriesoverride")
    @ApiOperation(value = "updateColumnTimeSeriesOverride", notes = "Updates the overridden time series configuration on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column's overridden time series configuration successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnTimeSeriesOverride(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Overridden time series configuration or an empty object to clear the customized time series from the column level")
            @RequestBody Optional<TimeSeriesConfigurationSpec> timeSeriesConfigurationSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (timeSeriesConfigurationSpec.isPresent()) {
            columnSpec.setTimeSeriesOverride(timeSeriesConfigurationSpec.get());
        } else {
            columnSpec.setTimeSeriesOverride(null);
        }
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
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels")
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column")
            @RequestBody Optional<LabelSetSpec> labelSetSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
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
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments")
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
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column")
            @RequestBody Optional<CommentsListSpec> commentsListSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
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
     * Updates the configuration of column level data quality checks configured on a column.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param columnCheckCategoriesSpec New configuration of the column level data quality checks to configure on a column or an empty optional to clear the list of checks.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checks")
    @ApiOperation(value = "updateColumnChecks", notes = "Updates configuration of column level data quality checks on a column.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnChecks(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "Configuration of column level data quality checks to configure on a column or an empty object to clear the list of assigned data quality checks on the column")
            @RequestBody Optional<ColumnAdHocCheckCategoriesSpec> columnCheckCategoriesSpec) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        if (columnCheckCategoriesSpec.isPresent()) {
            columnSpec.setChecks(columnCheckCategoriesSpec.get());
        } else {
            columnSpec.setChecks(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of column level data quality checks configured on a column from a UI friendly model.
     * @param connectionName            Connection name.
     * @param schemaName                Schema name.
     * @param tableName                 Table name.
     * @param columnName                Column name.
     * @param uiAllChecksModel          UI model of the column level data quality checks to be applied on the configuration of the data quality checks on a column. Only data quality dimensions and data quality checks that are present in the UI model are updated (patched).
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/checksui")
    @ApiOperation(value = "updateColumnChecksUI", notes = "Updates configuration of column level data quality checks on a column from a UI friendly model.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column level data quality checks successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 404, message = "Table not found"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateColumnChecksUI(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName,
            @Parameter(description = "UI model with the changes to be applied to the data quality checks configuration")
            @RequestBody Optional<UIAllChecksModel> uiAllChecksModel) {
        if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(schemaName) ||
                Strings.isNullOrEmpty(tableName) ||
                Strings.isNullOrEmpty(columnName)) {
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
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec columnSpec = columns.get(columnName);
        if (columnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        // TODO: validate the columnSpec
        ColumnAdHocCheckCategoriesSpec checks = columnSpec.getChecks();
        if (checks == null) {
            checks = new ColumnAdHocCheckCategoriesSpec();
        }

        if (uiAllChecksModel.isPresent()) {
            this.uiToSpecCheckMappingService.updateAllChecksSpecs(uiAllChecksModel.get(), checks);
            if (!checks.isDefault()) {
                columnSpec.setChecks(checks);
            }
        } else {
            // we cannot just remove all checks because the UI model is a patch, no changes in the patch means no changes to the object
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
     * @return Empty response.
     */
    @DeleteMapping("/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}")
    @ApiOperation(value = "deleteColumn", notes = "Deletes a column from the table")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Column successfully deleted"),
            @ApiResponse(code = 404, message = "Column not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> deleteColumn(
            @Parameter(description = "Connection name") @PathVariable String connectionName,
            @Parameter(description = "Schema name") @PathVariable String schemaName,
            @Parameter(description = "Table name") @PathVariable String tableName,
            @Parameter(description = "Column name") @PathVariable String columnName) {
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

        TableSpec tableSpec = tableWrapper.getSpec();
        ColumnSpecMap columns = tableSpec.getColumns();
        ColumnSpec existingColumnSpec = columns.get(columnName);
        if (existingColumnSpec == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the column was not found
        }

        columns.remove(columnName);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}
