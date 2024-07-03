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
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoPermissionNames;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.credentials.CredentialType;
import com.dqops.rest.models.credentials.SharedCredentialListModel;
import com.dqops.rest.models.credentials.SharedCredentialModel;
import com.dqops.rest.models.platform.SpringErrorPayload;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Shared credentials controller for managing credentials that are stored in the shared .credentials folder in the DQOps user's home folder.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "SharedCredentials", description = "Operations for managing shared credentials in DQOps. Credentials that are stored in the shared .credentials folder in the DQOps user's home folder.")
public class SharedCredentialsController {
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Dependency injection controller.
     * @param userHomeContextFactory Factory of the DQOps user home context.
     */
    @Autowired
    public SharedCredentialsController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of all shared credentials.
     * @return List of all shared credentials.
     */
    @GetMapping(value = "/credentials", produces = "application/json")
    @ApiOperation(value = "getAllSharedCredentials", notes = "Returns a list of all shared credentials that are present in the DQOps user's home .credentials/ folder.",
            response = SharedCredentialListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SharedCredentialListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public Mono<ResponseEntity<Flux<SharedCredentialListModel>>> getAllSharedCredentials(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();

            List<SharedCredentialWrapper> sharedCredentialWrappers = userHome.getCredentials().toList();
            List<SharedCredentialListModel> credentialListModelList = sharedCredentialWrappers.stream()
                    .map(wrapper -> new SharedCredentialListModel() {{
                        setCredentialName(wrapper.getCredentialName());
                        setType(wrapper.getObject().getByteContent() != null ? CredentialType.binary : CredentialType.text);
                        setCanAccessCredential(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
                        setCanEdit(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
                    }})
                    .collect(Collectors.toList());

            credentialListModelList.sort(Comparator.comparing(model -> model.getCredentialName()));

            return new ResponseEntity<>(Flux.fromStream(credentialListModelList.stream()), HttpStatus.OK);
        }));
    }

    /**
     * Returns the full credential including the secret values that are stored inside it.
     * @param credentialName Credential file name.
     * @return Shared credential model with the details.
     */
    @GetMapping(value = "/credentials/{credentialName}", produces = "application/json")
    @ApiOperation(value = "getSharedCredential", notes = "Returns a shared credential content", response = SharedCredentialModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SharedCredentialModel.class),
            @ApiResponse(code = 404, message = "Shared credential not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<SharedCredentialModel>>> getSharedCredential(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Shared credential file name") @PathVariable String credentialName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(credentialName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            SharedCredentialWrapper sharedCredentialWrapper = userHome.getCredentials().getByObjectName(credentialName, true);

            if (sharedCredentialWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            FileContent credentialFileContent = sharedCredentialWrapper.getObject();
            SharedCredentialModel sharedCredentialModel = new SharedCredentialModel();
            sharedCredentialModel.setCredentialName(credentialName);

            if (credentialFileContent.getByteContent() != null) {
                sharedCredentialModel.setType(CredentialType.binary);
                String base64Credential = new String(Base64.getEncoder().encode(credentialFileContent.getByteContent()));
                sharedCredentialModel.setBinaryValue(base64Credential);
            } else {
                sharedCredentialModel.setType(CredentialType.text);
                sharedCredentialModel.setTextValue(credentialFileContent.getTextContent());
            }

            return new ResponseEntity<>(Mono.just(sharedCredentialModel), HttpStatus.OK);
        }));
    }

    /**
     * Downloads a shared credential file.
     * @param credentialName Credential file name.
     * @return Shared credential's file content.
     */
    @GetMapping(value = "/credentials/{credentialName}/download", produces = "application/octet-stream")
    @ApiOperation(value = "downloadSharedCredential", notes = "Downloads a shared credential's file", response = byte[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = byte[].class),
            @ApiResponse(code = 404, message = "Shared credential not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<byte[]>>> downloadSharedCredential(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Shared credential file name") @PathVariable String credentialName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(credentialName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), true);
            UserHome userHome = userHomeContext.getUserHome();
            SharedCredentialWrapper sharedCredentialWrapper = userHome.getCredentials().getByObjectName(credentialName, true);

            if (sharedCredentialWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            FileContent credentialFileContent = sharedCredentialWrapper.getObject();
            byte[] binaryFileContent = null;

            if (credentialFileContent.getByteContent() != null) {
                binaryFileContent = credentialFileContent.getByteContent();
            } else {
                binaryFileContent = credentialFileContent.getTextContent().getBytes(StandardCharsets.UTF_8);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(credentialName).build());

            return new ResponseEntity<>(Mono.just(binaryFileContent), headers, HttpStatus.OK);
        }));
    }

    /**
     * Creates (adds) a new shared credential, which creates a file in the DQOps user's home .credentials/ folder named as the credential and with the content that is provided in this call
     * @param sharedCredentialModel Shared credential model to save.
     * @return Empty response.
     */
    @PostMapping(value = "/credentials", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createSharedCredential",
            notes = "Creates (adds) a new shared credential, which creates a file in the DQOps user's home .credentials/ folder named as the credential " +
                    "and with the content that is provided in this call.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New shared credential successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Shared credential with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> createSharedCredential(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Shared credential model") @RequestBody SharedCredentialModel sharedCredentialModel) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            if (Strings.isNullOrEmpty(sharedCredentialModel.getCredentialName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();
            SharedCredentialList credentialList = userHome.getCredentials();
            SharedCredentialWrapper existingSharedCredential = credentialList.getByObjectName(sharedCredentialModel.getCredentialName(), true);

            if (existingSharedCredential != null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
            }

            if (sharedCredentialModel.getType() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            SharedCredentialWrapper sharedCredentialWrapper = credentialList.createAndAddNew(sharedCredentialModel.getCredentialName());
            switch (sharedCredentialModel.getType()) {
                case binary:
                    if (sharedCredentialModel.getBinaryValue() == null || sharedCredentialModel.getTextValue() != null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
                    }
                    byte[] base64DecodeValue = Base64.getDecoder().decode(sharedCredentialModel.getBinaryValue());
                    sharedCredentialWrapper.setObject(new FileContent(base64DecodeValue));
                    break;

                case text:
                    if (sharedCredentialModel.getTextValue() == null || sharedCredentialModel.getBinaryValue() != null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
                    }
                    sharedCredentialWrapper.setObject(new FileContent(sharedCredentialModel.getTextValue()));
                    break;

                default:
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
        }));
    }

    /**
     * Updates an existing shared credential.
     * @param sharedCredentialModel Shared credential model to save.
     * @param credentialName Credential file name.
     * @return Empty response.
     */
    @PutMapping(value = "/credential/{credentialName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateSharedCredential", notes = "Updates an existing shared credential, replacing the credential's file content.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Shared credential successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Shared credential not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> updateSharedCredential(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Shared credential model") @RequestBody SharedCredentialModel sharedCredentialModel,
            @ApiParam("Credential file name that will be updated") @PathVariable String credentialName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(credentialName) || sharedCredentialModel == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            if (!Objects.equals(credentialName, sharedCredentialModel.getCredentialName())) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();
            SharedCredentialList sharedCredentialList = userHome.getCredentials();
            SharedCredentialWrapper sharedCredentialWrapper = sharedCredentialList.getByObjectName(credentialName, true);

            if (sharedCredentialWrapper == null || sharedCredentialWrapper.getObject() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
            }

            if (sharedCredentialModel.getType() == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            switch (sharedCredentialModel.getType()) {
                case binary:
                    if (sharedCredentialModel.getBinaryValue() == null || sharedCredentialModel.getTextValue() != null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
                    }
                    byte[] base64DecodeValue = Base64.getDecoder().decode(sharedCredentialModel.getBinaryValue());
                    sharedCredentialWrapper.setObject(new FileContent(base64DecodeValue));
                    break;

                case text:
                    if (sharedCredentialModel.getTextValue() == null || sharedCredentialModel.getBinaryValue() != null) {
                        return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
                    }
                    sharedCredentialWrapper.setObject(new FileContent(sharedCredentialModel.getTextValue()));
                    break;

                default:
                    return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
        }));
    }

    /**
     * Deletes a shared credential file.
     * @param credentialName  Shared credential file name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/credentials/{credentialName}", produces = "application/json")
    @ApiOperation(value = "deleteSharedCredential", notes = "Deletes a shared credential file from the DQOps user's home .credentials/ folder.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shared credential file successfully deleted", response = Void.class),
            @ApiResponse(code = 404, message = "Shared credential file not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public Mono<ResponseEntity<Mono<Void>>> deleteSharedCredential(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Full shared credential name") @PathVariable String credentialName) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {

            if (Strings.isNullOrEmpty(credentialName)) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
            }

            UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity(), false);
            UserHome userHome = userHomeContext.getUserHome();

            SharedCredentialList sharedCredentialList = userHome.getCredentials();
            SharedCredentialWrapper sharedCredentialWrapper = sharedCredentialList.getByObjectName(credentialName, true);

            if (sharedCredentialWrapper == null) {
                return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
            }

            sharedCredentialWrapper.markForDeletion();
            userHomeContext.flush();

            return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
        }));
    }
}
