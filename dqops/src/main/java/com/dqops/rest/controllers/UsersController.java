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

import autovalue.shaded.com.google.common.base.Strings;
import com.dqops.core.dqocloud.apikey.DqoCloudInvalidKeyException;
import com.dqops.core.dqocloud.users.DqoCloudUserModel;
import com.dqops.core.dqocloud.users.DqoUserLimitExceededException;
import com.dqops.core.dqocloud.users.DqoUserNotFoundException;
import com.dqops.core.dqocloud.users.UserManagementService;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * REST API controller for managing users in a multi-user DQOps installations.
 */
@RestController
@RequestMapping("/api/")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Users", description = "User management service")
public class UsersController {
    private UserManagementService userManagementService;

    @Autowired
    public UsersController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    /**
     * Returns a flat list of all users.
     * @return List of all users.
     */
    @GetMapping(value = "users", produces = "application/json")
    @ApiOperation(value = "getAllUsers", notes = "Returns a list of all users.",
            response = DqoCloudUserModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DqoCloudUserModel[].class),
            @ApiResponse(code = 403, message = "DQOps instance is not authenticated to DQOps Cloud"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<DqoCloudUserModel>> getAllUsers(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        try {
            Collection<DqoCloudUserModel> dqoCloudUserModels = this.userManagementService.listUsers(principal);
            Stream<DqoCloudUserModel> sortedStream = dqoCloudUserModels.stream()
                    .sorted(Comparator.comparing(model -> model.getEmail()));
            return new ResponseEntity<>(Flux.fromStream(sortedStream), HttpStatus.OK);
        }
        catch (DqoCloudInvalidKeyException ex) {
            return new ResponseEntity<>(Flux.empty(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Returns the model of a user identified by email.
     * @param email User's email.
     * @return User's model that describes the user's role.
     */
    @GetMapping(value = "users/{email}", produces = "application/json")
    @ApiOperation(value = "getUser", notes = "Returns the user model that describes the role of a user identified by an email", response = DqoCloudUserModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DqoCloudUserModel.class),
            @ApiResponse(code = 403, message = "DQOps instance is not authenticated to DQOps Cloud"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<DqoCloudUserModel>> getUser(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("User's email") @PathVariable String email) {

        if (Strings.isNullOrEmpty(email)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            DqoCloudUserModel userByEmail = this.userManagementService.getUserByEmail(principal, email);
            if (userByEmail == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(Mono.just(userByEmail), HttpStatus.OK);
        }
        catch (DqoCloudInvalidKeyException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Creates (adds) a new user to a multi-user account.
     * @param userModel User model to add.
     * @return Empty response.
     */
    @PostMapping(value = "users", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createUser", notes = "Creates (adds) a new user to a multi-user account.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New user successfully added", response = Void.class),
            @ApiResponse(code = 400, message = "User limit exceeded"),
            @ApiResponse(code = 403, message = "DQOps instance is not authenticated to DQOps Cloud"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.MANAGE_ACCOUNT})
    public ResponseEntity<Mono<Void>> createUser(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("User model") @RequestBody DqoCloudUserModel userModel) {
        if (userModel == null || Strings.isNullOrEmpty(userModel.getEmail())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            this.userManagementService.createUser(principal, userModel, null);
        }
        catch (DqoCloudInvalidKeyException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.FORBIDDEN);
        }
        catch (DqoUserLimitExceededException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST); // 400
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates user to a multi-user account.
     * @param principal User principal that identifies the caller.
     * @param email User's email.
     * @param userModel User model to update.
     * @return Empty response.
     */
    @PutMapping(value = "users/{email}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateUser", notes = "Updates a user in a multi-user account. The user's email cannot be changed.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully updated", response = Void.class),
            @ApiResponse(code = 403, message = "DQOps instance is not authenticated to DQOps Cloud"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields or the email does not match"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.MANAGE_ACCOUNT})
    public ResponseEntity<Mono<Void>> updateUser(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("User's email") @PathVariable String email,
            @ApiParam("User model") @RequestBody DqoCloudUserModel userModel) {
        if (userModel == null || Strings.isNullOrEmpty(email) ||
                !Objects.equals(email, userModel.getEmail())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            this.userManagementService.updateUser(principal, userModel);
        }
        catch (DqoCloudInvalidKeyException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.FORBIDDEN);
        }
        catch (DqoUserLimitExceededException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST); // 400
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a user from a multi-user account.
     * @param email User's email.
     * @return Empty model.
     */
    @DeleteMapping(value = "users/{email}", produces = "application/json")
    @ApiOperation(value = "deleteUser", notes = "Deletes a user from a multi-user account.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted", response = Void.class),
            @ApiResponse(code = 403, message = "DQOps instance is not authenticated to DQOps Cloud"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 406, message = "User's email is not provided"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.MANAGE_ACCOUNT})
    public ResponseEntity<Mono<Void>> deleteUser(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("User's email") @PathVariable String email) {

        if (Strings.isNullOrEmpty(email)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            this.userManagementService.deleteUser(principal, email);
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
        }
        catch (DqoUserNotFoundException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }
        catch (DqoCloudInvalidKeyException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Changes the user's password.
     * @param principal User principal that identifies the caller.
     * @param email User's email.
     * @param password New password.
     * @return Empty response.
     */
    @PutMapping(value = "users/{email}/password", consumes = "text/plain", produces = "application/json")
    @ApiOperation(value = "changeUserPassword", notes = "Changes the password of a user identified by the email.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Password successfully updated", response = Void.class),
            @ApiResponse(code = 403, message = "DQOps instance is not authenticated to DQOps Cloud"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields or the required fields are empty"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.MANAGE_ACCOUNT})
    public ResponseEntity<Mono<Void>> changeUserPassword(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("User's email") @PathVariable String email,
            @ApiParam("New Password") @RequestBody String password) {
        if (Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(password)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            this.userManagementService.changePassword(principal, email, password);
        }
        catch (DqoCloudInvalidKeyException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.FORBIDDEN);
        }
        catch (DqoUserLimitExceededException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST); // 400
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Changes the current user's password.
     * @param principal User principal that identifies the caller.
     * @param password New password.
     * @return Empty response.
     */
    @PutMapping(value = "mypassword", consumes = "text/plain", produces = "application/json")
    @ApiOperation(value = "changeCallerPassword", notes = "Changes the password of the calling user. When the user is identified by the DQOps local API key, " +
            "it is the user whose email is stored in the DQOps API Key.",
            response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Password successfully updated", response = Void.class),
            @ApiResponse(code = 403, message = "DQOps instance is not authenticated to DQOps Cloud"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields or the required fields are empty"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Mono<Void>> changeCallerPassword(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("New Password") @RequestBody String password) {
        try {
            this.userManagementService.changePassword(principal, principal.getName(), password);
        }
        catch (DqoCloudInvalidKeyException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.FORBIDDEN);
        }
        catch (DqoUserLimitExceededException ex) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.BAD_REQUEST); // 400
        }

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }
}
