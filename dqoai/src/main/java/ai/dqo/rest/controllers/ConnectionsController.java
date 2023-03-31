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

import ai.dqo.core.jobqueue.DqoQueueJobId;
import ai.dqo.core.jobqueue.PushJobResult;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.scheduling.CheckRunRecurringScheduleGroup;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.scheduling.RecurringSchedulesSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rest.models.dictionaries.CommonColumnModel;
import ai.dqo.rest.models.metadata.ConnectionBasicModel;
import ai.dqo.rest.models.metadata.ConnectionModel;
import ai.dqo.rest.models.platform.SpringErrorPayload;
import ai.dqo.services.check.CheckService;
import ai.dqo.services.check.models.UIAllChecksPatchParameters;
import ai.dqo.services.metadata.ConnectionService;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to return a list of connections.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Connections", description = "Connection management")
public class ConnectionsController {
    private final ConnectionService connectionService;
    private final UserHomeContextFactory userHomeContextFactory;
    private final CheckService checkService;

    @Autowired
    public ConnectionsController(ConnectionService connectionService,
                                 CheckService checkService,
                                 UserHomeContextFactory userHomeContextFactory) {
        this.connectionService = connectionService;
        this.checkService = checkService;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of connections.
     * @return List of connections.
     */
    @GetMapping
    @ApiOperation(value = "getAllConnections", notes = "Returns a list of connection", response = ConnectionBasicModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ConnectionBasicModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<ConnectionBasicModel>> getAllConnections() {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        Stream<ConnectionBasicModel> modelStream = connections.toList().stream()
                .map(cw -> ConnectionBasicModel.fromConnectionSpecification(cw.getName(), cw.getSpec()));

        return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a full connection specification for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Full connection model with the connection name and the connection specification.
     */
    @GetMapping("/{connectionName}")
    @ApiOperation(value = "getConnection", notes = "Return the full details of a connection given the connection name", response = ConnectionModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection returned", response = ConnectionModel.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ConnectionModel>> getConnection(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionModel connectionModel = new ConnectionModel() {{
            setConnectionName(connectionName);
            setConnectionHash(connectionSpec.getHierarchyId() != null ? connectionSpec.getHierarchyId().hashCode64() : null);
            setSpec(connectionSpec);
        }};

        return new ResponseEntity<>(Mono.just(connectionModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a connection basic information for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection basic model with the connection name and the connection parameters.
     */
    @GetMapping("/{connectionName}/basic")
    @ApiOperation(value = "getConnectionBasic", notes = "Return the basic details of a connection given the connection name", response = ConnectionBasicModel.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection basic information returned", response = ConnectionBasicModel.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<ConnectionBasicModel>> getConnectionBasic(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionBasicModel connectionBasicModel = ConnectionBasicModel.fromConnectionSpecification(connectionName, connectionSpec);

        return new ResponseEntity<>(Mono.just(connectionBasicModel), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a named schedule of a connection for a requested connection identified by the connection name.
     * @param connectionName  Connection name.
     * @param schedulingGroup Scheduling group.
     * @return Connection's schedule specification.
     */
    @GetMapping("/{connectionName}/schedules/{schedulingGroup}")
    @ApiOperation(value = "getConnectionSchedulingGroup", notes = "Return the schedule for a connection for a scheduling group", response = RecurringScheduleSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's schedule returned", response = RecurringScheduleSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<RecurringScheduleSpec>> getConnectionSchedulingGroup(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunRecurringScheduleGroup schedulingGroup) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();

        RecurringSchedulesSpec schedules = connectionSpec.getSchedules();
        if (schedules == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
        }

        RecurringScheduleSpec schedule = schedules.getScheduleForCheckSchedulingGroup(schedulingGroup);

        return new ResponseEntity<>(Mono.justOrEmpty(schedule), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a list of comments of a connection for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection's list of comments.
     */
    @GetMapping("/{connectionName}/comments")
    @ApiOperation(value = "getConnectionComments", notes = "Return the comments for a connection", response = CommentsListSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's comments returned", response = CommentsListSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<CommentsListSpec>> getConnectionComments(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();

        CommentsListSpec comments = connectionSpec.getComments();

        return new ResponseEntity<>(Mono.justOrEmpty(comments), HttpStatus.OK); // 200
    }

    /**
     * Retrieves a list of labels of a connection for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection's list of labels.
     */
    @GetMapping("/{connectionName}/labels")
    @ApiOperation(value = "getConnectionLabels", notes = "Return the labels for a connection", response = LabelSetSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's labels returned", response = LabelSetSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<LabelSetSpec>> getConnectionLabels(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();

        LabelSetSpec labels = connectionSpec.getLabels();

        return new ResponseEntity<>(Mono.justOrEmpty(labels), HttpStatus.OK); // 200
    }

    /**
     * Retrieves the default data streams configuration of a connection for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection's default data streams configuration.
     */
    @GetMapping("/{connectionName}/defaultdatastreamsmapping")
    @ApiOperation(value = "getConnectionDefaultDataStreamsMapping", notes = "Return the default data streams mapping for a connection", response = DataStreamMappingSpec.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's default data streams mapping returned", response = DataStreamMappingSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DataStreamMappingSpec>> getConnectionDefaultDataStreamsMapping(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();

        DataStreamMappingSpec dataStreamsSpec = connectionSpec.getDefaultDataStreamMapping();

        return new ResponseEntity<>(Mono.justOrEmpty(dataStreamsSpec), HttpStatus.OK); // 200
    }

    /**
     * Finds common column names that are used on one or more tables. The columns are sorted in descending order by column name.
     * @param connectionName Connection name.
     * @return Sorted collection of most common columns.
     */
    @GetMapping("/{connectionName}/commoncolumns")
    @ApiOperation(value = "getConnectionCommonColumns", notes = "Finds common column names that are used on one or more tables. " +
            "The list of columns is sorted in descending order by column name.", response = CommonColumnModel[].class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of common columns within a connection returned", response = CommonColumnModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Flux<CommonColumnModel>> getConnectionCommonColumns(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
        }

        List<TableWrapper> tableWrapperList = connectionWrapper.getTables().toList();
        Map<String, CommonColumnModel> foundColumns = new HashMap<>();
        for (TableWrapper tableWrapper : tableWrapperList) {
            ColumnSpecMap columns = tableWrapper.getSpec().getColumns();
            for (String columnName : columns.keySet()) {
                CommonColumnModel commonColumnModel = foundColumns.get(columnName);
                if (commonColumnModel == null) {
                    commonColumnModel = new CommonColumnModel(columnName, 1);
                    foundColumns.put(columnName, commonColumnModel);
                } else {
                    commonColumnModel.setTablesCount(commonColumnModel.getTablesCount() + 1);
                }
            }
        }

        List<CommonColumnModel> sortedCommonColumnList = foundColumns.values().stream()
                .sorted(Comparator.comparing(CommonColumnModel::getColumnName))
                .collect(Collectors.toList());

        return new ResponseEntity<>(Flux.fromIterable(sortedCommonColumnList), HttpStatus.OK); // 200
    }

    /**
     * Creates (adds) a new connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Empty response.
     */
    @PostMapping("/{connectionName}")
    @ApiOperation(value = "createConnection", notes = "Creates a new connection")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New connection successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Connection with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createConnection(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Connection specification") @RequestBody ConnectionSpec connectionSpec) {
        if (Strings.isNullOrEmpty(connectionName) || connectionSpec == null || connectionSpec.getProviderType() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper existingConnectionWrapper = connections.getByObjectName(connectionName, true);
        if (existingConnectionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a connection with this name already exists
        }

        ConnectionWrapper connectionWrapper = connections.createAndAddNew(connectionName);
        connectionWrapper.setSpec(connectionSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Creates (adds) a new connection given teh basic information.
     * @param connectionName Connection name.
     * @param connectionBasicModel Basic connection model.
     * @return Empty response.
     */
    @PostMapping("/{connectionName}/basic")
    @ApiOperation(value = "createConnectionBasic", notes = "Creates a new connection given the basic information.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New connection successfully created"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Connection with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> createConnectionBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Basic connection model") @RequestBody ConnectionBasicModel connectionBasicModel) {
        if (Strings.isNullOrEmpty(connectionName) || connectionBasicModel == null || connectionBasicModel.getProviderType() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper existingConnectionWrapper = connections.getByObjectName(connectionName, true);
        if (existingConnectionWrapper != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a connection with this name already exists
        }

        ConnectionWrapper connectionWrapper = connections.createAndAddNew(connectionName);
        ConnectionSpec connectionSpec = new ConnectionSpec();
        connectionBasicModel.copyToConnectionSpecification(connectionSpec);
        connectionWrapper.setSpec(connectionSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
    }

    /**
     * Updates an existing connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}")
    @ApiOperation(value = "updateConnection", notes = "Updates an existing connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateConnection(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Connection specification") @RequestBody ConnectionSpec connectionSpec) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        // TODO: validate the connectionSpec
        connectionWrapper.setSpec(connectionSpec);
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the basic details of an existing connection.
     * @param connectionName       Connection name.
     * @param connectionBasicModel Connection basic model.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/basic")
    @ApiOperation(value = "updateConnectionBasic", notes = "Updates the basic information of a connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's basic parameters successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateConnectionBasic(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Connection basic details") @RequestBody ConnectionBasicModel connectionBasicModel) {
        if (!Objects.equals(connectionName, connectionBasicModel.getConnectionName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 400 - connection name mismatch
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        ConnectionSpec existingConnectionSpec = connectionWrapper.getSpec();
        connectionBasicModel.copyToConnectionSpecification(existingConnectionSpec);
        // TODO: some validation should be executed before flushing

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the configuration of a check run schedule for a scheduling group (named schedule) of an existing connection.
     * @param connectionName        Connection name.
     * @param recurringScheduleSpec Schedule specification.
     * @param schedulingGroup       Scheduling group.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/schedules/{schedulingGroup}")
    @ApiOperation(value = "updateConnectionSchedulingGroup", notes = "Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's schedule successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateConnectionSchedulingGroup(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunRecurringScheduleGroup schedulingGroup,
            @ApiParam("Recurring schedule definition to store") @RequestBody Optional<RecurringScheduleSpec> recurringScheduleSpec) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        ConnectionSpec existingConnectionSpec = connectionWrapper.getSpec();
        RecurringSchedulesSpec schedules = existingConnectionSpec.getSchedules();
        if (schedules == null) {
            schedules = new RecurringSchedulesSpec();
            existingConnectionSpec.setSchedules(schedules);
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
     * Updates the list of labels of an existing connection.
     * @param connectionName Connection name.
     * @param labelSetSpec   List of labels.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/labels")
    @ApiOperation(value = "updateConnectionLabels", notes = "Updates the list of labels of a connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's labels successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateConnectionLabels(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("List of labels") @RequestBody Optional<LabelSetSpec> labelSetSpec) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        ConnectionSpec existingConnectionSpec = connectionWrapper.getSpec();
        if (labelSetSpec.isPresent()) {
            existingConnectionSpec.setLabels(labelSetSpec.get());
        } else {
            existingConnectionSpec.setLabels(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the list of comments of an existing connection.
     * @param connectionName   Connection name.
     * @param commentsListSpec List of comments.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/comments")
    @ApiOperation(value = "updateConnectionComments", notes = "Updates (replaces) the list of comments of a connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's comments successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateConnectionComments(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("List of comments") @RequestBody Optional<CommentsListSpec> commentsListSpec) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        ConnectionSpec existingConnectionSpec = connectionWrapper.getSpec();
        if (commentsListSpec.isPresent()) {
            existingConnectionSpec.setComments(commentsListSpec.get());
        } else {
            existingConnectionSpec.setComments(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Updates the default data streams mapping of an existing connection.
     * @param connectionName         Connection name.
     * @param dataStreamsMappingSpec New default data streams mapping for a connection.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/defaultdatastreamsmapping")
    @ApiOperation(value = "updateConnectionDefaultDataStreamsMapping", notes = "Updates the default data streams mapping of a connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's default data streams mapping successfully updated"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> updateConnectionDefaultDataStreamsMapping(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Default data streams mapping to be assigned to a connection")
                @RequestBody Optional<DataStreamMappingSpec> dataStreamsMappingSpec) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        ConnectionSpec existingConnectionSpec = connectionWrapper.getSpec();
        if (dataStreamsMappingSpec.isPresent()) {
            existingConnectionSpec.setDefaultDataStreamMapping(dataStreamsMappingSpec.get());
        }
        else {
            existingConnectionSpec.setDefaultDataStreamMapping(null);
        }
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Enables a named check on this connection in the locations specified by filter.
     * Allows for configuring the rules for particular alert levels.
     * @param connectionName        Connection name.
     * @param checkName             Check name.
     * @param updatePatchParameters Check search filters and rules configuration.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/checks/{checkName}/enable")
    @ApiOperation(value = "enableConnectionChecks", notes = "Enables a named check on this connection in the locations specified by filter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Checks enabled"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> enableConnectionChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check name") @PathVariable String checkName,
            @ApiParam("Check search filters and rules configuration")
            @RequestBody UIAllChecksPatchParameters updatePatchParameters) {
        if (updatePatchParameters == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST); // 400 - update patch parameters not supplied
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        updatePatchParameters.getCheckSearchFilters().setConnectionName(connectionName);
        updatePatchParameters.getCheckSearchFilters().setCheckName(checkName);
        checkService.updateAllChecksPatch(updatePatchParameters);

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Disables a named check on this connection in the locations specified by filter.
     * @param connectionName        Connection name.
     * @param checkName             Check name.
     * @param checkSearchFilters    Optional search filters.
     * @return Empty response.
     */
    @PutMapping("/{connectionName}/checks/{checkName}/disable")
    @ApiOperation(value = "disableConnectionChecks", notes = "Disables a named check on this connection in the locations specified by filter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Checks disabled"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<?>> disableConnectionChecks(
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check name") @PathVariable String checkName,
            @ApiParam("Optional check search filters")
            @RequestBody Optional<CheckSearchFilters> checkSearchFilters) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
        }

        CheckSearchFilters filters = checkSearchFilters.orElseGet(CheckSearchFilters::new);
        filters.setConnectionName(connectionName);
        filters.setCheckName(checkName);

        checkService.disableChecks(filters);
        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }

    /**
     * Deletes a connection.
     * @param connectionName Connection name to delete.
     * @return Deferred operations job id.
     */
    @DeleteMapping("/{connectionName}")
    @ApiOperation(value = "deleteConnection", notes = "Deletes a connection")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    public ResponseEntity<Mono<DqoQueueJobId>> deleteConnection(
            @ApiParam("Connection name") @PathVariable String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        PushJobResult<DeleteStoredDataQueueJobResult> backgroundJob = this.connectionService.deleteConnection(
                connectionWrapper, userHomeContext);
        return new ResponseEntity<>(Mono.just(backgroundJob.getJobId()), HttpStatus.OK); // 200
    }
}
