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

import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.FilteredNotificationSpecMap;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.metadata.FilteredNotificationModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.locking.RestApiLockService;
import io.swagger.annotations.*;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * REST api controller to manage the filtered notification configurations on a connection.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "FilteredNotificationsConfigurations", description = "Operations for managing the configuration of filtered notifications on a connection level in DQOps.")
public class FilteredNotificationsController {
    private UserHomeContextFactory userHomeContextFactory;
    private RestApiLockService lockService;

    /**
     * Creates an instance of a controller by injecting dependencies.
     * @param userHomeContextFactory      User home context factory.
     * @param lockService                 Lock service.
     */
    @Autowired
    public FilteredNotificationsController(UserHomeContextFactory userHomeContextFactory,
                                           RestApiLockService lockService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.lockService = lockService;
    }

    /**
     * Returns a list of named filtered notification configurations on the connection.
     * @param connectionName Connection name.
     * @return List of basic models of filtered notification configurations on the connection.
     */
    @GetMapping(value = "/connections/{connectionName}/filterednotifications", produces = "application/json")
    @ApiOperation(value = "getConnectionFilteredNotificationsConfigurations", notes = "Returns the list of filtered notification configurations on a connection",
            response = FilteredNotificationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FilteredNotificationModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<FilteredNotificationModel>>> getConnectionFilteredNotificationsConfigurations(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMap(userHomeContext, connectionName);
            if (filteredNotifications == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<FilteredNotificationModel> filteredNotificationListModel =
                    filteredNotifications.entrySet().stream()
                            .map(stringFilteredNotificationSpecEntry -> FilteredNotificationModel.fromFilteredNotificationMapEntry(
                                    stringFilteredNotificationSpecEntry.getKey(),
                                    stringFilteredNotificationSpecEntry.getValue()))
                            .collect(Collectors.toList());

            return new ResponseEntity<>(Flux.fromIterable(filteredNotificationListModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns the configuration of a specific filtered notification configuration.
     * @param connectionName Connection name.
     * @param filteredNotificationName Filtered notification name.
     * @return Model of the filtered notification configuration.
     */
    @GetMapping(value = "/connections/{connectionName}/filterednotification/{filteredNotificationName}", produces = "application/json")
    @ApiOperation(value = "getConnectionFilteredNotificationConfiguration", notes = "Returns a model of the filtered notification configuration",
            response = FilteredNotificationModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FilteredNotificationModel.class),
            @ApiResponse(code = 404, message = "Connection or filtered notification not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<FilteredNotificationModel>>> getConnectionFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Filtered notification name") @PathVariable String filteredNotificationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMap(userHomeContext, connectionName);
            if (filteredNotifications == null || !filteredNotifications.containsKey(filteredNotificationName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            FilteredNotificationSpec filteredNotificationSpec = filteredNotifications.get(filteredNotificationName);

            FilteredNotificationModel filteredNotificationModel = FilteredNotificationModel
                .fromFilteredNotificationMapEntry(filteredNotificationName, filteredNotificationSpec);

            return new ResponseEntity<>(Mono.just(filteredNotificationModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Update a specific filtered notification configuration using a new model.
     * Remark: PUT method is used, because renaming the filtered notification configuration would break idempotence.
     * @param connectionName Connection name.
     * @param filteredNotificationName Filtered notification name up until now.
     * @param filteredNotificationModel Filtered notification model.
     * @return Empty response.
     */
    @PutMapping(value = "/connections/{connectionName}/filterednotification/{filteredNotificationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateConnectionFilteredNotificationConfiguration", notes = "Updates a filtered notification configuration according to the provided model", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Filtered notification configuration successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection or filtered notification not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Filtered notification configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateConnectionFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Filtered notification name") @PathVariable String filteredNotificationName,
            @ApiParam("Filtered notification model") @RequestBody FilteredNotificationModel filteredNotificationModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                Strings.isNullOrEmpty(filteredNotificationName) ||
                filteredNotificationModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnConnection(connectionName, () -> {
                UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMap(userHomeContext, connectionName);
                if (filteredNotifications == null || !filteredNotifications.containsKey(filteredNotificationName)) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                String newName = filteredNotificationModel.getName();
                if (Strings.isNullOrEmpty(newName)) {
                    newName = filteredNotificationName;
                }

                if (!Objects.equals(newName, filteredNotificationName) && filteredNotifications.containsKey(newName)) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a filtered notification configuration with this name already exists
                }

                FilteredNotificationSpec newSpec = filteredNotificationModel.toSpec();
                filteredNotifications.put(newName, newSpec);
                if (!newName.equals(filteredNotificationName)) {
                    // If renaming actually happened.
                    filteredNotifications.remove(filteredNotificationName);
                }

                userHomeContext.flush();
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            });
        }));
    }

    /**
     * Creates (adds) a new named filtered notification configuration.
     * @param connectionName  Connection name.
     * @param filteredNotificationModel Filtered notification model.
     * @return Empty response.
     */
    @PostMapping(value = "/connections/{connectionName}/filterednotification", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createConnectionFilteredNotificationConfiguration", notes = "Creates a new filtered notification configuration on a connection level", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New data filtered notification successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Filtered notification configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createConnectionFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Filtered notification model") @RequestBody FilteredNotificationModel filteredNotificationModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName) ||
                filteredNotificationModel == null ||
                Strings.isNullOrEmpty(filteredNotificationModel.getName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnConnection(connectionName, () -> {
                UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMap(userHomeContext, connectionName);
                if (filteredNotifications == null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                if (filteredNotifications.containsKey(filteredNotificationModel.getName())) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a filtered notification configuration with this name already exists
                }

                filteredNotifications.put(filteredNotificationModel.getName(), filteredNotificationModel.toSpec());

                userHomeContext.flush();
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
            });
        }));
    }

    /**
     * Deletes a specific filtered notification configuration.
     * @param connectionName Connection name.
     * @param filteredNotificationName Filtered notification name up until now.
     * @return Empty response.
     */
    @DeleteMapping(value = "/connections/{connectionName}/filterednotification/{filteredNotificationName}", produces = "application/json")
    @ApiOperation(value = "deleteConnectionFilteredNotificationConfiguration", notes = "Deletes a filtered notification configuration from a connection", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Filtered notification configuration removed", response = Void.class),
            @ApiResponse(code = 404, message = "Connection or filtered notification not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> deleteConnectionFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Connection name") @PathVariable String connectionName,
            @ApiParam("Filtered notification name") @PathVariable String filteredNotificationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(connectionName)     ||
                    Strings.isNullOrEmpty(filteredNotificationName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            return this.lockService.callSynchronouslyOnConnection(connectionName, () -> {
                UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
                FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMap(userHomeContext, connectionName);
                if (filteredNotifications == null) {
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
                }

                filteredNotifications.remove(filteredNotificationName);

                userHomeContext.flush();
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
            });
        }));
    }


    /**
     * Returns a list of named filtered notification configurations on the connection.
     * @return List of basic models of filtered notification configurations on the connection.
     */
    @GetMapping(value = "/default/filterednotifications", produces = "application/json")
    @ApiOperation(value = "getDefaultFilteredNotificationsConfigurations", notes = "Returns the list of filtered notification configurations on default notifications",
            response = FilteredNotificationModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FilteredNotificationModel[].class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<FilteredNotificationModel>>> getDefaultFilteredNotificationsConfigurations(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMapFromDefaults(userHomeContext);
            if (filteredNotifications == null) {
                return new ResponseEntity<>(Flux.empty(), HttpStatus.NOT_FOUND); // 404
            }

            List<FilteredNotificationModel> filteredNotificationListModel =
                    filteredNotifications.entrySet().stream()
                            .map(stringFilteredNotificationSpecEntry -> FilteredNotificationModel.fromFilteredNotificationMapEntry(
                                    stringFilteredNotificationSpecEntry.getKey(),
                                    stringFilteredNotificationSpecEntry.getValue()))
                            .collect(Collectors.toList());

            return new ResponseEntity<>(Flux.fromIterable(filteredNotificationListModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Returns the configuration of a specific filtered notification configuration.
     * @param filteredNotificationName Filtered notification name.
     * @return Model of the filtered notification configuration.
     */
    @GetMapping(value = "/default/filterednotification/{filteredNotificationName}", produces = "application/json")
    @ApiOperation(value = "getDefaultFilteredNotificationConfiguration", notes = "Returns a model of the filtered notification from default notifications",
            response = FilteredNotificationModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FilteredNotificationModel.class),
            @ApiResponse(code = 404, message = "Connection or filtered notification not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<FilteredNotificationModel>>> getDefaultFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Filtered notification name") @PathVariable String filteredNotificationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMapFromDefaults(userHomeContext);
            if (filteredNotifications == null || !filteredNotifications.containsKey(filteredNotificationName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            FilteredNotificationSpec filteredNotificationSpec = filteredNotifications.get(filteredNotificationName);

            FilteredNotificationModel filteredNotificationModel = FilteredNotificationModel
                    .fromFilteredNotificationMapEntry(filteredNotificationName, filteredNotificationSpec);

            return new ResponseEntity<>(Mono.just(filteredNotificationModel), HttpStatus.OK); // 200
        }));
    }

    /**
     * Update a specific filtered notification configuration using a new model.
     * Remark: PUT method is used, because renaming the filtered notification configuration would break idempotence.
     * @param filteredNotificationName Filtered notification name up until now.
     * @param filteredNotificationModel Filtered notification model.
     * @return Empty response.
     */
    @PutMapping(value = "/default/filterednotification/{filteredNotificationName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultFilteredNotificationConfiguration", notes = "Updates a filtered notification configuration on default notifications according to the provided model", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Filtered notification configuration successfully updated", response = Void.class),
            @ApiResponse(code = 404, message = "Connection or filtered notification not found"),
            @ApiResponse(code = 406, message = "Incorrect request"),
            @ApiResponse(code = 409, message = "Filtered notification configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Filtered notification name") @PathVariable String filteredNotificationName,
            @ApiParam("Filtered notification model") @RequestBody FilteredNotificationModel filteredNotificationModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(filteredNotificationName) ||
                filteredNotificationModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMapFromDefaults(userHomeContext);
            if (filteredNotifications == null || !filteredNotifications.containsKey(filteredNotificationName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            String newName = filteredNotificationModel.getName();
            if (Strings.isNullOrEmpty(newName)) {
                newName = filteredNotificationName;
            }

            if (!Objects.equals(newName, filteredNotificationName) && filteredNotifications.containsKey(newName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a filtered notification configuration with this name already exists
            }

            FilteredNotificationSpec newSpec = filteredNotificationModel.toSpec();
            filteredNotifications.put(newName, newSpec);
            if (!newName.equals(filteredNotificationName)) {
                // If renaming actually happened.
                filteredNotifications.remove(filteredNotificationName);
            }

            userHomeContext.flush();
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Creates (adds) a new named filtered notification configuration.
     * @param filteredNotificationModel Filtered notification model.
     * @return Empty response.
     */
    @PostMapping(value = "/default/filterednotification", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDefaultFilteredNotificationConfiguration", notes = "Creates a new filtered notification configuration at default notifications", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New data filtered notification successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"), // TODO: returned when the validation failed
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Filtered notification configuration with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createDefaultFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Filtered notification model") @RequestBody FilteredNotificationModel filteredNotificationModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (filteredNotificationModel == null ||
                Strings.isNullOrEmpty(filteredNotificationModel.getName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMapFromDefaults(userHomeContext);
            if (filteredNotifications == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            if (filteredNotifications.containsKey(filteredNotificationModel.getName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT); // 409 - a filtered notification configuration with this name already exists
            }

            filteredNotifications.put(filteredNotificationModel.getName(), filteredNotificationModel.toSpec());

            userHomeContext.flush();
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED); // 201
        }));
    }

    /**
     * Deletes a specific filtered notification configuration.
     * @param filteredNotificationName Filtered notification name up until now.
     * @return Empty response.
     */
    @DeleteMapping(value = "/default/filterednotification/{filteredNotificationName}", produces = "application/json")
    @ApiOperation(value = "deleteDefaultFilteredNotificationConfiguration", notes = "Deletes a filtered notification configuration from default notifications", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Filtered notification configuration removed", response = Void.class),
            @ApiResponse(code = 404, message = "Connection not found"),
            @ApiResponse(code = 406, message = "Invalid request"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> deleteDefaultFilteredNotificationConfiguration(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Filtered notification name") @PathVariable String filteredNotificationName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(filteredNotificationName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE); // 406
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            FilteredNotificationSpecMap filteredNotifications = this.readIncidentNotificationMapFromDefaults(userHomeContext);
            if (filteredNotifications == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            filteredNotifications.remove(filteredNotificationName);

            userHomeContext.flush();
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }


    /**
     * Reads the filtered notification specification of a certain connection.
     * @param userHomeContext User-home context.
     * @param connectionName  Connection name.
     * @return FilteredNotificationSpecMap of the requested connection. Null if not found.
     */
    protected FilteredNotificationSpecMap readIncidentNotificationMap(UserHomeContext userHomeContext,
                                                                      String connectionName) {
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return null;
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionIncidentGroupingSpec incidentGrouping = connectionSpec.getIncidentGrouping();
        if (incidentGrouping == null) {
            return null;
        }
        IncidentNotificationSpec incidentNotification = incidentGrouping.getIncidentNotification();
        if (incidentNotification == null) {
            incidentNotification = new IncidentNotificationSpec();
        }
        FilteredNotificationSpecMap filteredNotificationMap = incidentNotification.getFilteredNotifications();
        if (filteredNotificationMap == null) {
            incidentNotification.setFilteredNotifications(new FilteredNotificationSpecMap());
        }
        return filteredNotificationMap;
    }

    /**
     * Reads the filtered notification specification of a certain connection.
     * @param userHomeContext User-home context.
     * @return FilteredNotificationSpecMap of the default notifications.
     */
    protected FilteredNotificationSpecMap readIncidentNotificationMapFromDefaults(UserHomeContext userHomeContext) {
        UserHome userHome = userHomeContext.getUserHome();
        IncidentNotificationSpec spec = userHome.getDefaultIncidentNotifications().getSpec();
        FilteredNotificationSpecMap filteredNotificationMap = spec.getFilteredNotifications();
        return filteredNotificationMap;
    }

}
