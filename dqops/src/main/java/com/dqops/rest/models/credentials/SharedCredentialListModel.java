/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rest.models.credentials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Shared credentials list model with the basic information about the credential.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SharedCredentialListModel", description = "Shared credentials list model with the basic information about the credential.")
public class SharedCredentialListModel {
    /**
     * Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user's home folder.
     */
    @JsonPropertyDescription("Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user's home folder.")
    private String credentialName;

    /**
     * Credential type.
     */
    @JsonPropertyDescription("Credential type that is based on the detected format of the file. If the file can be parsed as a valid utf-8 string, it is assumed that the credential is a text. Otherwise, it is a binary file that can only be retrieved as a base64 value.")
    private CredentialType type;

    /**
     * Boolean flag that decides if the current user can update or delete the shared credential file.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete the shared credential file.")
    private boolean canEdit;

    /**
     * Boolean flag that decides if the current user can see the actual credential or download the credential file.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user see or download the credential file.")
    private boolean canAccessCredential;
}
