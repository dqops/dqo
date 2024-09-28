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

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.scheduler.defaults.DefaultSchedulesProvider;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.dictionaries.CommonColumnModel;
import com.dqops.rest.models.metadata.ConnectionModel;
import com.dqops.rest.models.metadata.ConnectionSpecificationModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.CheckService;
import com.dqops.services.check.models.AllChecksPatchParameters;
import com.dqops.services.check.models.BulkCheckDeactivateParameters;
import com.dqops.services.locking.RestApiLockService;
import com.dqops.services.metadata.ConnectionService;
import com.dqops.utils.threading.CompletableFutureRunner;
import com.google.common.base.Strings;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST api controller to return a list of connections.
 */
@RestController
@RequestMapping("/api/connections")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Connections", description = "Operations for adding/updating/deleting the configuration of data sources managed by DQOps.")
public class ConnectionsController {
    private final ConnectionService connectionService;
    private final UserHomeContextFactory userHomeContextFactory;
    private final DefaultSchedulesProvider defaultSchedulesProvider;
    private final RestApiLockService lockService;
    private final JobSchedulerService jobSchedulerService;
    private final CheckService checkService;

    @Autowired
    public ConnectionsController(ConnectionService connectionService,
                                 CheckService checkService,
                                 UserHomeContextFactory userHomeContextFactory,
                                 DefaultSchedulesProvider defaultSchedulesProvider,
                                 RestApiLockService lockService,
                                 JobSchedulerService jobSchedulerService) {
        this.connectionService = connectionService;
        this.checkService = checkService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.defaultSchedulesProvider = defaultSchedulesProvider;
        this.lockService = lockService;
        this.jobSchedulerService = jobSchedulerService;
    }

    /**
     * Returns a list of connections.
     * @return List of connections.
     */
    @GetMapping(produces = "application/json")
    @ApiOperation(value = "getAllConnections", notes = "Returns a list of connections (data sources)", response = ConnectionModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ConnectionModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<ConnectionModel>>> getAllConnections(@AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            boolean isOperator = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);
            Stream<ConnectionModel> modelStream = connections.toList().stream()
                    .map(cw -> ConnectionModel.fromConnectionSpecification(cw.getName(), cw.getSpec(), isEditor, isOperator))
                    .sorted(Comparator.comparing(model -> model.getConnectionName()));

            return new ResponseEntity<>(Flux.fromStream(modelStream), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a full connection specification for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Full connection model with the connection name and the connection specification.
     */
    @GetMapping(value = "/{connectionName}", produces = "application/json")
    @ApiOperation(value = "getConnection", notes = "Return the full details of a connection given the connection name", response = ConnectionSpecificationModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection returned", response = ConnectionSpecificationModel.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<ConnectionSpecificationModel>>> getConnection(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ConnectionSpec connectionSpec = connectionWrapper.getSpec();
            ConnectionSpecificationModel connectionSpecificationModel = new ConnectionSpecificationModel() {{
                setConnectionName(connectionName);
                setConnectionHash(connectionSpec.getHierarchyId() != null ? connectionSpec.getHierarchyId().hashCode64() : null);
                setSpec(connectionSpec);
                setCanEdit(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
                setYamlParsingError(connectionSpec.getYamlParsingError());
            }};

            return new ResponseEntity<>(Mono.just(connectionSpecificationModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a connection basic information for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection basic model with the connection name and the connection parameters.
     */
    @GetMapping(value = "/{connectionName}/basic", produces = "application/json")
    @ApiOperation(value = "getConnectionBasic", notes = "Return the basic details of a connection given the connection name", response = ConnectionModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection basic information returned", response = ConnectionModel.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<ConnectionModel>>> getConnectionBasic(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            ConnectionSpec connectionSpec = connectionWrapper.getSpec();
            boolean isEditor = principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT);
            boolean isOperator = principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE);
            ConnectionModel connectionModel = ConnectionModel.fromConnectionSpecification(
                    connectionName, connectionSpec, isEditor, isOperator);

            return new ResponseEntity<>(Mono.just(connectionModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a named schedule of a connection for a requested connection identified by the connection name.
     * @param connectionName  Connection name.
     * @param schedulingGroup Scheduling group.
     * @return Connection's schedule specification.
     */
    @GetMapping(value = "/{connectionName}/schedules/{schedulingGroup}", produces = "application/json")
    @ApiOperation(value = "getConnectionSchedulingGroup", notes = "Return the schedule for a connection for a scheduling group", response = MonitoringScheduleSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's schedule returned", response = MonitoringScheduleSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<MonitoringScheduleSpec>>> getConnectionSchedulingGroup(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunScheduleGroup schedulingGroup) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();

            DefaultSchedulesSpec schedules = connectionSpec.getSchedules();
            if (schedules == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.OK); // 200
            }

            MonitoringScheduleSpec schedule = schedules.getScheduleForCheckSchedulingGroup(schedulingGroup);

            return new ResponseEntity<>(Mono.justOrEmpty(schedule), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a list of comments of a connection for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection's list of comments.
     */
    @GetMapping(value = "/{connectionName}/comments", produces = "application/json")
    @ApiOperation(value = "getConnectionComments", notes = "Return the comments for a connection", response = CommentsListSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's comments returned", response = CommentsListSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<CommentsListSpec>>> getConnectionComments(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();

            CommentsListSpec comments = connectionSpec.getComments();

            return new ResponseEntity<>(Mono.justOrEmpty(comments), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves a list of labels of a connection for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection's list of labels.
     */
    @GetMapping(value = "/{connectionName}/labels", produces = "application/json")
    @ApiOperation(value = "getConnectionLabels", notes = "Return the labels for a connection", response = LabelSetSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's labels returned", response = LabelSetSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<LabelSetSpec>>> getConnectionLabels(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();

            LabelSetSpec labels = connectionSpec.getLabels();

            return new ResponseEntity<>(Mono.justOrEmpty(labels), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the default data grouping configuration of a connection for a requested connection identified by the connection name.
     * @param connectionName Connection name.
     * @return Connection's default data grouping configuration.
     */
    @GetMapping(value = "/{connectionName}/defaultgroupingconfiguration", produces = "application/json")
    @ApiOperation(value = "getConnectionDefaultGroupingConfiguration", notes = "Return the default data grouping configuration for a connection",
            response = DataGroupingConfigurationSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's default data grouping configuration returned", response = DataGroupingConfigurationSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<DataGroupingConfigurationSpec>>> getConnectionDefaultGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();

            DataGroupingConfigurationSpec dataGroupingConfiguration = connectionSpec.getDefaultGroupingConfiguration();

            return new ResponseEntity<>(Mono.justOrEmpty(dataGroupingConfiguration), HttpStatus.OK); // 200
        }));
    }

    /**
     * Retrieves the configuration of data quality incident grouping and incident notifications.
     * @param connectionName Connection name.
     * @return Incident grouping and notification settings.
     */
    @GetMapping(value = "/{connectionName}/incidentgrouping", produces = "application/json")
    @ApiOperation(value = "getConnectionIncidentGrouping", notes = "Retrieves the configuration of data quality incident grouping and incident notifications",
            response = ConnectionIncidentGroupingSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection's incident grouping configuration returned", response = ConnectionIncidentGroupingSpec.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<ConnectionIncidentGroupingSpec>>> getConnectionIncidentGrouping(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }
            ConnectionSpec connectionSpec = connectionWrapper.getSpec();

            ConnectionIncidentGroupingSpec incidentGrouping = connectionSpec.getIncidentGrouping();

            return new ResponseEntity<>(Mono.justOrEmpty(incidentGrouping), HttpStatus.OK); // 200
        }));
    }

    /**
     * Finds common column names that are used on one or more tables. The columns are sorted in descending order by column name.
     * @param connectionName Connection name.
     * @return Sorted collection of most common columns.
     */
    @GetMapping(value = "/{connectionName}/commoncolumns", produces = "application/json")
    @ApiOperation(value = "getConnectionCommonColumns", notes = "Finds common column names that are used on one or more tables. " +
            "The list of columns is sorted in descending order by column name.", response = CommonColumnModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of common columns within a connection returned", response = CommonColumnModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<CommonColumnModel>>> getConnectionCommonColumns(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
            if (connectionWrapper == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<TableWrapper> tableWrapperList = connectionWrapper.getTables().toList();
            Map<String, CommonColumnModel> foundColumns = new LinkedHashMap<>();
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
        }));
    }

    /**
     * Creates (adds) a new connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createConnection", notes = "Creates a new connection", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New connection successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Connection with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createConnection(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Connection specification") @RequestBody ConnectionSpec connectionSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) || connectionSpec == null || connectionSpec.getProviderType() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper existingConnectionWrapper = connections.getByObjectName(connectionName, true);
            if (existingConnectionWrapper != null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a connection with this name already exists
            }

            ConnectionWrapper connectionWrapper = connections.createAndAddNew(connectionName);
            connectionWrapper.setSpec(connectionSpec);
            if (connectionSpec.getSchedules() == null) {
                connectionSpec.setSchedules(this.defaultSchedulesProvider.createMonitoringSchedulesSpecForNewConnection(userHome));
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Creates (adds) a new connection given the basic information.
     * @param connectionName Connection name.
     * @param connectionModel Basic connection model.
     * @return Empty response.
     */
    @PostMapping(value = "/{connectionName}/basic", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createConnectionBasic", notes = "Creates a new connection given the basic information.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New connection successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Connection with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createConnectionBasic(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Basic connection model") @RequestBody ConnectionModel connectionModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) || connectionModel == null || connectionModel.getProviderType() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();

            ConnectionList connections = userHome.getConnections();
            ConnectionWrapper existingConnectionWrapper = connections.getByObjectName(connectionName, true);
            if (existingConnectionWrapper != null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a connection with this name already exists
            }

            ConnectionWrapper connectionWrapper = connections.createAndAddNew(connectionName);
            ConnectionSpec connectionSpec = new ConnectionSpec();
            connectionModel.copyToConnectionSpecification(connectionSpec);
            if (connectionSpec.getSchedules() == null) {
                connectionSpec.setSchedules(this.defaultSchedulesProvider.createMonitoringSchedulesSpecForNewConnection(userHome));
            }
            connectionWrapper.setSpec(connectionSpec);
            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Updates an existing connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnection", notes = "Updates an existing connection", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateConnection(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Connection specification") @RequestBody ConnectionSpec connectionSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
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
                    });
        }));
    }

    /**
     * Updates the basic details of an existing connection.
     * @param connectionName       Connection name.
     * @param connectionModel Connection basic model.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/basic", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnectionBasic", notes = "Updates the basic information of a connection", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's basic parameters successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateConnectionBasic(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Connection basic details") @RequestBody ConnectionModel connectionModel) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (!Objects.equals(connectionName, connectionModel.getConnectionName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 400 - connection name mismatch
            }

            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        ConnectionSpec existingConnectionSpec = connectionWrapper.getSpec();
                        connectionModel.copyToConnectionSpecification(existingConnectionSpec);
                        // TODO: some validation should be executed before flushing

                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of a check run schedule for a scheduling group (named schedule) of an existing connection.
     * @param connectionName        Connection name.
     * @param monitoringScheduleSpec Schedule specification.
     * @param schedulingGroup       Scheduling group.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/schedules/{schedulingGroup}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnectionSchedulingGroup",
            notes = "Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's schedule successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateConnectionSchedulingGroup(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunScheduleGroup schedulingGroup,
            @ApiParam("Monitoring schedule definition to store") @RequestBody MonitoringScheduleSpec monitoringScheduleSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        ConnectionSpec existingConnectionSpec = connectionWrapper.getSpec();

                        DefaultSchedulesSpec schedules = existingConnectionSpec.getSchedules();
                        if (schedules == null) {
                            schedules = new DefaultSchedulesSpec();
                            existingConnectionSpec.setSchedules(schedules);
                        }

                        switch (schedulingGroup) {
                            case profiling:
                                schedules.setProfiling(monitoringScheduleSpec);
                                break;

                            case monitoring_daily:
                                schedules.setMonitoringDaily(monitoringScheduleSpec);
                                break;

                            case monitoring_monthly:
                                schedules.setMonitoringMonthly(monitoringScheduleSpec);
                                break;

                            case partitioned_daily:
                                schedules.setPartitionedDaily(monitoringScheduleSpec);
                                break;

                            case partitioned_monthly:
                                schedules.setPartitionedMonthly(monitoringScheduleSpec);
                                break;

                            default:
                                throw new UnsupportedOperationException("Unsupported scheduling group " + schedulingGroup);
                        }

                        boolean scheduleChanged = existingConnectionSpec.isDirty();
                        userHomeContext.flush();

                        if (scheduleChanged) {
                            this.jobSchedulerService.triggerMetadataSynchronization();
                        }

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the list of labels of an existing connection.
     * @param connectionName Connection name.
     * @param labelSetSpec   List of labels.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/labels", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnectionLabels", notes = "Updates the list of labels of a connection", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's labels successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateConnectionLabels(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("List of labels") @RequestBody LabelSetSpec labelSetSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        connectionWrapper.getSpec().setLabels(labelSetSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the list of comments of an existing connection.
     * @param connectionName   Connection name.
     * @param commentsListSpec List of comments.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/comments", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnectionComments", notes = "Updates (replaces) the list of comments of a connection", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's comments successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> updateConnectionComments(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("List of comments") @RequestBody CommentsListSpec commentsListSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        connectionWrapper.getSpec().setComments(commentsListSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the default data grouping configuration of an existing connection.
     * @param connectionName         Connection name.
     * @param dataGroupingConfigurationSpec New default data grouping mapping for a connection.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/defaultgroupingconfiguration", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnectionDefaultGroupingConfiguration", notes = "Updates the default data grouping connection of a connection", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's default data grouping configuration successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateConnectionDefaultGroupingConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Default data grouping configuration to be assigned to a connection")
                @RequestBody DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        connectionWrapper.getSpec().setDefaultGroupingConfiguration(dataGroupingConfigurationSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Updates the configuration of incident grouping and incident notifications.
     * @param connectionName   Connection name.
     * @param incidentGroupingSpec New configuration of the incident grouping.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/incidentgrouping", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnectionIncidentGrouping",
            notes = "Updates (replaces) configuration of incident grouping and notifications on a connection (data source) level.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Connection's incident configuration successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateConnectionIncidentGrouping(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Incident grouping and notification configuration") @RequestBody ConnectionIncidentGroupingSpec incidentGroupingSpec) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        connectionWrapper.getSpec().setIncidentGrouping(incidentGroupingSpec);
                        userHomeContext.flush();

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Activates a named check on this connection in the locations specified by filter.
     * Allows for configuring the rules for particular alert levels.
     * @param connectionName        Connection name.
     * @param checkName             Check name.
     * @param updatePatchParameters Check search filters and template model with the configurations.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/checks/{checkName}/bulkactivate", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "bulkActivateConnectionChecks", notes = "Activates all named check on this connection in the locations specified by filter", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Checks enabled in bulk", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> bulkActivateConnectionChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check name") @PathVariable String checkName,
            @ApiParam("Check search filters and rules configuration")
            @RequestBody AllChecksPatchParameters updatePatchParameters) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {

            if (updatePatchParameters == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST); // 400 - update patch parameters not supplied
            }

            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        updatePatchParameters.getCheckSearchFilters().setConnection(connectionName);
                        updatePatchParameters.getCheckSearchFilters().setCheckName(checkName);
                        checkService.activateOrUpdateAllChecks(updatePatchParameters, principal);

                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Deactivates (deletes) a named check on this connection in the locations specified by filter.
     * @param connectionName        Connection name.
     * @param checkName             Check name.
     * @param bulkDeactivateParameters Check search filters and table/column selectors.
     * @return Empty response.
     */
    @PutMapping(value = "/{connectionName}/checks/{checkName}/bulkdeactivate", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "bulkDeactivateConnectionChecks", notes = "Deactivates (deletes) all named check on this connection in the locations specified by filter", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Checks disabled", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.OPERATE})
    public Mono<ResponseEntity<Mono<Void>>> bulkDeactivateConnectionChecks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Check name") @PathVariable String checkName,
            @ApiParam("Check search filters and table/column selectors.")
            @RequestBody BulkCheckDeactivateParameters bulkDeactivateParameters) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404 - the connection was not found
                        }

                        bulkDeactivateParameters.getCheckSearchFilters().setConnection(connectionName);
                        bulkDeactivateParameters.getCheckSearchFilters().setCheckName(checkName);

                        checkService.deleteChecks(bulkDeactivateParameters, principal);
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
                    });
        }));
    }

    /**
     * Deletes a connection.
     * @param connectionName Connection name to delete.
     * @return Deferred operations job id.
     */
    @DeleteMapping(value = "/{connectionName}", produces = "application/json")
    @ApiOperation(value = "deleteConnection", notes = "Deletes a connection",
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Connection successfully deleted", response = DqoQueueJobId.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<DqoQueueJobId>>> deleteConnection(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return this.lockService.callSynchronouslyOnConnection(connectionName,
                    () -> {
                        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                        UserHome userHome = userHomeContext.getUserHome();

                        ConnectionList connections = userHome.getConnections();
                        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
                        if (connectionWrapper == null) {
                            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                        }

                        PushJobResult<DeleteStoredDataResult> backgroundJob = this.connectionService.deleteConnection(connectionName, principal);
                        return new ResponseEntity<>(Mono.just(backgroundJob.getJobId()), HttpStatus.OK); // 200
                    });
        }));
    }
}
