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
import com.dqops.metadata.dictionaries.DictionaryList;
import com.dqops.metadata.dictionaries.DictionaryWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rest.models.credentials.SharedCredentialModel;
import com.dqops.rest.models.dictionaries.DataDictionaryListModel;
import com.dqops.rest.models.dictionaries.DataDictionaryModel;
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
import java.util.stream.Collectors;

/**
 * Data dictionaries controller for managing dictonary CSV files that are stored in the dictionaries folder in the DQOps user's home folder.
 */
@RestController
@RequestMapping("/api")
@ResponseStatus(HttpStatus.OK)
@Api(value = "Dictionaries", description = "Operations for managing data dictionary CSV files in DQOps. Data dictionaries can be used in *accepted_values* data quality checks.")
public class DictionariesController {
    private UserHomeContextFactory userHomeContextFactory;

    /**
     * Dependency injection controller.
     * @param userHomeContextFactory Factory of the DQOps user home context.
     */
    @Autowired
    public DictionariesController(UserHomeContextFactory userHomeContextFactory) {
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Returns a list of all data dictionaries.
     * @return List of all data dictionaries.
     */
    @GetMapping(value = "/dictionaries", produces = "application/json")
    @ApiOperation(value = "getAllDictionaries", notes = "Returns a list of all data dictionary CSV files that are present in the DQOps user's home dictionaries/ folder.",
            response = DataDictionaryListModel[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataDictionaryListModel[].class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.VIEW})
    public ResponseEntity<Flux<DataDictionaryListModel>> getAllDictionaries(
            @AuthenticationPrincipal DqoUserPrincipal principal) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();

        List<DictionaryWrapper> dictionaryWrappers = userHome.getDictionaries().toList();
        List<DataDictionaryListModel> dictionaryListModelList = dictionaryWrappers.stream()
                .map(wrapper -> new DataDictionaryListModel() {{
                    setDictionaryName(wrapper.getDictionaryName());
                    setCanAccessDictionary(principal.hasPrivilege(DqoPermissionGrantedAuthorities.VIEW));
                    setCanEdit(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
                }})
                .collect(Collectors.toList());

        dictionaryListModelList.sort(Comparator.comparing(model -> model.getDictionaryName()));

        return new ResponseEntity<>(Flux.fromStream(dictionaryListModelList.stream()), HttpStatus.OK);
    }

    /**
     * Returns the full data dictionary CSV file content.
     * @param dictionaryName Data dictionary CSV file name.
     * @return Data dictionary model with the details.
     */
    @GetMapping(value = "/dictionaries/{dictionaryName}", produces = "application/json")
    @ApiOperation(value = "getDictionary", notes = "Returns the content of a data dictionary CSV file as a model object",
            response = DataDictionaryModel.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataDictionaryModel.class),
            @ApiResponse(code = 404, message = "Data dictionary not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<DataDictionaryModel>> getDictionary(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data dictionary CSV file name") @PathVariable String dictionaryName) {

        if (Strings.isNullOrEmpty(dictionaryName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();
        DictionaryWrapper dictionaryWrapper = userHome.getDictionaries().getByObjectName(dictionaryName, true);

        if (dictionaryWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        FileContent credentialFileContent = dictionaryWrapper.getObject();
        DataDictionaryModel sharedCredentialModel = new DataDictionaryModel();
        sharedCredentialModel.setDictionaryName(dictionaryName);
        sharedCredentialModel.setFileContent(credentialFileContent.getTextContent());

        return new ResponseEntity<>(Mono.just(sharedCredentialModel), HttpStatus.OK);
    }

    /**
     * Downloads a data dictionary CSV file.
     * @param dictionaryName Data dictionary file name.
     * @return Data dictionary CSV file content.
     */
    @GetMapping(value = "/dictionaries/{dictionaryName}/download", produces = "text/csv")
    @ApiOperation(value = "downloadDictionary", notes = "Downloads a data dictionary CSV file", response = byte[].class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = byte[].class),
            @ApiResponse(code = 404, message = "Data dictionary not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<byte[]>> downloadDictionary(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data dictionary CSV file name") @PathVariable String dictionaryName) {

        if (Strings.isNullOrEmpty(dictionaryName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();
        DictionaryWrapper dictionaryWrapper = userHome.getDictionaries().getByObjectName(dictionaryName, true);

        if (dictionaryWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        FileContent credentialFileContent = dictionaryWrapper.getObject();
        byte[] binaryFileContent = credentialFileContent.getTextContent().getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(dictionaryName).build());

        return new ResponseEntity<>(Mono.just(binaryFileContent), headers, HttpStatus.OK);
    }

    /**
     * Creates (adds) a new data dictionary file, which creates a file in the DQOps user's home dictionaries/ folder named as the data dictionary file and with the content that is provided in this call
     * @param dataDictionaryModel Data dictionary model to save.
     * @return Empty response.
     */
    @PostMapping(value = "/dictionaries", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "createDictionary",
            notes = "Creates (adds) a new data dictionary CSV file, which creates a file in the DQOps user's home dictionaries/ folder named as the dictionary " +
                    "and with the content that is provided in this call.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New data dictionary successfully created", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 406, message = "Rejected, missing required fields"),
            @ApiResponse(code = 409, message = "Data dictionary with the same name already exists"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> createDictionary(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data dictionary model") @RequestBody DataDictionaryModel dataDictionaryModel) {
        if (Strings.isNullOrEmpty(dataDictionaryModel.getDictionaryName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();
        DictionaryList dictionaryList = userHome.getDictionaries();
        DictionaryWrapper existingDictionary = dictionaryList.getByObjectName(dataDictionaryModel.getDictionaryName(), true);

        if (existingDictionary != null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.CONFLICT);
        }

        if (dataDictionaryModel.getFileContent() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        DictionaryWrapper dictionaryWrapper = dictionaryList.createAndAddNew(dataDictionaryModel.getDictionaryName());
        dictionaryWrapper.setObject(new FileContent(dataDictionaryModel.getFileContent()));

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    /**
     * Updates an existing data dictionary.
     * @param dataDictionaryModel Data dictionary model to save.
     * @param dictionaryName Data dictionary file name.
     * @return Empty response.
     */
    @PutMapping(value = "/dictionaries/{dictionaryName}", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "updateDictionary", notes = "Updates an existing data dictionary CSV file, replacing the dictionary's file content.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Data dictionary successfully updated", response = Void.class),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying"),
            @ApiResponse(code = 404, message = "Data dictionary not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> updateDictionary(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data dictionary model") @RequestBody DataDictionaryModel dataDictionaryModel,
            @ApiParam("Data dictionary file name that will be updated") @PathVariable String dictionaryName) {

        if (Strings.isNullOrEmpty(dictionaryName) || dataDictionaryModel == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        if (!Objects.equals(dictionaryName, dataDictionaryModel.getDictionaryName())) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();
        DictionaryList dictionaryList = userHome.getDictionaries();
        DictionaryWrapper dictionaryWrapper = dictionaryList.getByObjectName(dictionaryName, true);

        if (dictionaryWrapper == null || dictionaryWrapper.getObject() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND);
        }

        if (dataDictionaryModel.getFileContent() == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        dictionaryWrapper.setObject(new FileContent(dataDictionaryModel.getFileContent()));

        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a data dictionary file.
     * @param dictionaryName  Data dictionary file name.
     * @return Empty response.
     */
    @DeleteMapping(value = "/dictionaries/{dictionaryName}", produces = "application/json")
    @ApiOperation(value = "deleteDictionary", notes = "Deletes a data dictionary CSV file from the DQOps user's home dictionaries/ folder.", response = Void.class,
            authorizations = {
                    @Authorization(value = "authorization_bearer_api_key")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data dictionary CSV file successfully deleted", response = Void.class),
            @ApiResponse(code = 404, message = "Data dictionary file not found"),
            @ApiResponse(code = 500, message = "Internal Server Error", response = SpringErrorPayload.class)
    })
    @Secured({DqoPermissionNames.EDIT})
    public ResponseEntity<Mono<Void>> deleteDictionary(
            @AuthenticationPrincipal DqoUserPrincipal principal,
            @ApiParam("Data dictionary name") @PathVariable String dictionaryName) {

        if (Strings.isNullOrEmpty(dictionaryName)) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_ACCEPTABLE);
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(principal.getDataDomainIdentity());
        UserHome userHome = userHomeContext.getUserHome();

        DictionaryList dictionaryList = userHome.getDictionaries();
        DictionaryWrapper dictionaryWrapper = dictionaryList.getByObjectName(dictionaryName, true);

        if (dictionaryWrapper == null) {
            return new ResponseEntity<>(Mono.empty(), HttpStatus.NOT_FOUND); // 404
        }

        dictionaryWrapper.markForDeletion();
        userHomeContext.flush();

        return new ResponseEntity<>(Mono.empty(), HttpStatus.NO_CONTENT); // 204
    }
}
