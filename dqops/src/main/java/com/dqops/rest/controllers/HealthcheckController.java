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

import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.rest.server.authentication.AuthenticateWithDqoCloudWebFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * REST api controller to perform a health check test.
 */
@RestController
@RequestMapping("/")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Healthcheck", description = "Health check operations for checking if the DQOps service is up and operational. Used for monitoring by load balancers.")
public class HealthcheckController {
    private DqoJobQueue jobQueue;
    private ParentDqoJobQueue parentJobQueue;

    @Autowired
    public HealthcheckController(DqoJobQueue jobQueue,
                                 ParentDqoJobQueue parentJobQueue) {
        this.jobQueue = jobQueue;
        this.parentJobQueue = parentJobQueue;
    }

    @GetMapping(value = AuthenticateWithDqoCloudWebFilter.HEALTHCHECK_REQUEST_PATH, produces = "application/json")
    @ApiOperation(value = "isHealthy", notes = "Checks if the DQOps instance is healthy and operational. Returns a text \"OK\" and a HTTP status code 200 when the service is active and can accept jobs, " +
            " or returns a text \"UNAVAILABLE\" and a HTTP status code 503 when the service is still starting or is shutting down.", response = String.class)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class),
            @ApiResponse(code = 503, message = "DQOps instance is not healthy or is still starting", response = String.class)
    })
    public ResponseEntity<Mono<String>> isHealthy() {
        boolean isHealthy = this.jobQueue.isStarted() && this.parentJobQueue.isStarted();
        if (isHealthy) {
            return new ResponseEntity<>(Mono.just("OK"), HttpStatus.OK);
        }

        return new ResponseEntity<>(Mono.just("UNAVAILABLE"), HttpStatus.SERVICE_UNAVAILABLE); // 503
    }
}
