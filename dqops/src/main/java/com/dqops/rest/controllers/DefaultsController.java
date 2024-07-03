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
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.SpecToModelCheckMappingService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * REST Api controller that manages the default settings.
 */
@RestController
@RequestMapping("/api/defaults")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Defaults", description = "Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.")
public class DefaultsController {
    private ExecutionContextFactory executionContextFactory;
    private SpecToModelCheckMappingService specToModelCheckMappingService;
    private ModelToSpecCheckMappingService modelToSpecCheckMappingService;

    @Autowired
    public DefaultsController(
            ExecutionContextFactory executionContextFactory,
            SpecToModelCheckMappingService specToModelCheckMappingService,
            ModelToSpecCheckMappingService modelToSpecCheckMappingService) {
        this.executionContextFactory = executionContextFactory;
        this.specToModelCheckMappingService = specToModelCheckMappingService;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
    }

    /**
     * Returns the spec for the default schedule configuration for given scheduling group.
     * @return Schedule spec for given scheduling group.
     */
    @GetMapping(value = "/defaultschedule/{schedulingGroup}", produces = "application/json")
    @ApiOperation(value = "getDefaultSchedules", notes = "Returns spec to show and edit the default configuration of schedules.",
            response = MonitoringScheduleSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MonitoringScheduleSpec.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<MonitoringScheduleSpec>>> getDefaultSchedule(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunScheduleGroup schedulingGroup) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            MonitoringScheduleSpec defaultMonitoringScheduleSpec = null;

            if (userHome == null
                    || userHome.getDefaultSchedules() == null
                    || userHome.getDefaultSchedules().getSpec() == null
            ) {
                defaultMonitoringScheduleSpec = new MonitoringScheduleSpec();
            } else {
                defaultMonitoringScheduleSpec = userHome.getDefaultSchedules().getSpec()
                        .getScheduleForCheckSchedulingGroup(schedulingGroup);
            }

            if (defaultMonitoringScheduleSpec == null) {
                defaultMonitoringScheduleSpec = new MonitoringScheduleSpec();
            }

            return new ResponseEntity<>(Mono.just(defaultMonitoringScheduleSpec), HttpStatus.OK);
        }));
    }

    /**
     * Updates the configuration of default schedules for given scheduling group.
     * @param newMonitoringScheduleSpec New configuration of the default schedules
     * @return Empty response.
     */
    @PutMapping(value = "/defaultschedule/{schedulingGroup}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultSchedules",
            notes = "New configuration of the default schedules.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of schedules successfully updated.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultSchedules(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Spec with default schedules changes to be applied to the default configuration.")
            @RequestBody Optional<MonitoringScheduleSpec> newMonitoringScheduleSpec,
            @ApiParam("Check scheduling group (named schedule)") @PathVariable CheckRunScheduleGroup schedulingGroup) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();

            DefaultSchedulesSpec defaultSchedulesSpec = null;

            if (userHome == null
                    || userHome.getDefaultSchedules() == null
                    || userHome.getDefaultSchedules().getSpec() == null
            ) {
                defaultSchedulesSpec = new DefaultSchedulesSpec();
            } else {
                defaultSchedulesSpec = userHome.getDefaultSchedules().getSpec();
            }

            if (newMonitoringScheduleSpec.isPresent()) {
                defaultSchedulesSpec.setScheduleForCheckSchedulingGroup(newMonitoringScheduleSpec.get(), schedulingGroup);
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Returns the spec for the default notification webhooks.
     * @return Notification webhooks spec.
     */
    @GetMapping(value = "/defaultwebhooks", produces = "application/json")
    @ApiOperation(value = "getDefaultWebhooks", notes = "Returns spec to show and edit the default configuration of webhooks.",
            response = IncidentWebhookNotificationsSpec.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IncidentWebhookNotificationsSpec.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<IncidentWebhookNotificationsSpec>>> getDefaultWebhooks(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();
            IncidentWebhookNotificationsSpec defaultNotificationWebhooks = null;

            if (userHome == null
                    || userHome.getDefaultNotificationWebhook() == null
                    || userHome.getDefaultNotificationWebhook().getSpec() == null
            ) {
                defaultNotificationWebhooks = new IncidentWebhookNotificationsSpec();
            } else {
                defaultNotificationWebhooks = userHome.getDefaultNotificationWebhook().getSpec();
            }

            return new ResponseEntity<>(Mono.just(defaultNotificationWebhooks), HttpStatus.OK);
        }));
    }

    /**
     * Updates the configuration of default notification webhooks
     * @param newIncidentWebhookNotificationsSpec New configuration of the default notification webhooks
     * @return Empty response.
     */
    @PutMapping(value = "/defaultwebhooks", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDefaultWebhooks",
            notes = "New configuration of the default webhooks.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The default configuration of notification webhooks successfully updated.", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = String.class),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateDefaultWebhooks(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Spec with default notification webhooks changes to be applied to the default configuration")
            @RequestBody Optional<IncidentWebhookNotificationsSpec> newIncidentWebhookNotificationsSpec) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserDomainIdentity userDomainIdentity = principal.getDataDomainIdentity();
            ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity, false);
            UserHomeContext userHomeContext = executionContext.getUserHomeContext();

            UserHome userHome = userHomeContext.getUserHome();

            if (newIncidentWebhookNotificationsSpec.isPresent()) {
                userHome.getDefaultNotificationWebhook().setSpec(newIncidentWebhookNotificationsSpec.get());
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

}
