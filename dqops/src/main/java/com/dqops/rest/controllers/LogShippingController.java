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
import com.dqops.rest.models.platform.ExternalLogEntry;
import com.dqops.rest.models.platform.SpringErrorPayload;
import com.dqops.utils.threading.CompletableFutureRunner;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * REST API controller that accepts logs received from the web application. The logs are logged locally.
 */
@RestController
@RequestMapping("/api/logs")
@ResponseStatus(HttpStatus.OK)
@Api(value = "LogShipping", description = "Log shipping controller that accepts logs sent from a web application or external tools and aggregates them in the local DQOps instance logs.")
@Slf4j
public class LogShippingController {
    public LogShippingController() {
    }

    /**
     * Logs a debug severity log entry on the server.
     * @param logEntry Log entry to be logged on the serer.
     * @return Empty response.
     */
    @PostMapping(value = "/debug", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "logDebug", notes = "Logs an information message in the server's logs as a debug severity log entry.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Log entry was logged on the server", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<Void>>> logDebug(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Log entry")
            @RequestBody ExternalLogEntry logEntry) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (log.isDebugEnabled()) {
                String fullLogMessage = createLogMessage(logEntry);
                log.debug(fullLogMessage);
            }

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Logs an info severity log entry on the server as an info severity log entry.
     * @param logEntry Log entry to be logged on the serer.
     * @return Empty response.
     */
    @PostMapping(value = "/info",consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "logInfo", notes = "Logs an information message in the server's logs as an info severity log entry.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Log entry was logged on the server", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<Void>>> logInfo(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Log entry")
            @RequestBody ExternalLogEntry logEntry) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (log.isInfoEnabled()) {
                String fullLogMessage = createLogMessage(logEntry);
                log.info(fullLogMessage);
            }

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Logs an info severity log entry on the server as a warn severity log entry.
     * @param logEntry Log entry to be logged on the serer.
     * @return Empty response.
     */
    @PostMapping(value = "/warn", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "logWarn", notes = "Logs an information message in the server's logs as a warn severity log entry.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Log entry was logged on the server", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<Void>>> logWarn(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Log entry")
            @RequestBody ExternalLogEntry logEntry) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (log.isWarnEnabled()) {
                String fullLogMessage = createLogMessage(logEntry);
                log.warn(fullLogMessage);
            }

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Logs an info severity log entry on the server as an error severity log entry.
     * @param logEntry Log entry to be logged on the serer.
     * @return Empty response.
     */
    @PostMapping(value = "/error", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "logError", notes = "Logs an information message in the server's logs as an error severity log entry.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Log entry was logged on the server", response = Void.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Mono<Void>>> logError(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Log entry")
            @RequestBody ExternalLogEntry logEntry) {
        return Mono.fromFuture(CompletableFutureRunner.supplyAsync(() -> {
            if (log.isErrorEnabled()) {
                String fullLogMessage = createLogMessage(logEntry);
                log.error(fullLogMessage);
            }

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }

    /**
     * Creates a log message by concatenating all log entry elements.
     * @param logEntry Log entry.
     * @return Full log entry message.
     */
    @NotNull
    protected String createLogMessage(ExternalLogEntry logEntry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("External log entry");
        if (!Strings.isNullOrEmpty(logEntry.getWindowLocation())) {
            stringBuilder.append(" at window.location=");
            stringBuilder.append(logEntry.getWindowLocation());
        }
        if (!Strings.isNullOrEmpty(logEntry.getMessage())) {
            stringBuilder.append(", message: ");
            stringBuilder.append(logEntry.getMessage());
        }
        String fullLogMessage = stringBuilder.toString();
        return fullLogMessage;
    }
}
