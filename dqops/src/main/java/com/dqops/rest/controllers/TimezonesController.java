/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.controllers;

import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.utils.datetime.TimeZoneUtility;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * REST api controller to return a list of available time zones.
 */
@RestController
@RequestMapping("/api/timezones")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Timezones", description = "Operations for returning time zone names and codes supported by DQOps.")
public class TimezonesController {

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "getAvailableZoneIds", notes = "Returns a list of available time zone ids", response = String[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<String>>> getAvailableZoneIds(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            return new ResponseEntity<>(Flux.fromIterable(TimeZoneUtility.getAvailableZoneIds()), HttpStatus.OK);
        }));
    }
}
