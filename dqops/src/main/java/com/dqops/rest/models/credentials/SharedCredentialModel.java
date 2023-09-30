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

package com.dqops.rest.models.credentials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Shared credentials full model used to create and update the credential. Contains one of two forms of the credential's value: a text or a base64 binary value.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "SharedCredentialModel", description = "Shared credentials full model used to create and update the credential. Contains one of two forms of the credential's value: a text or a base64 binary value.")
public class SharedCredentialModel {
    /**
     * Credential name. It is the name of a file in the .credentials/ folder inside the DQO user's home folder.
     */
    @JsonPropertyDescription("Credential name. It is the name of a file in the .credentials/ folder inside the DQO user's home folder.")
    private String credentialName;

    /**
     * Credential type.
     */
    @JsonPropertyDescription("Credential type that is based on the detected format of the file. If the file could be parsed as a valid utf-8 string then it is assumed that the credential is a text. Otherwise it is a binary file that could be retrieved only as a base64 value.")
    private CredentialType type;

    /**
     * Credential value as a text.
     */
    @JsonPropertyDescription("Credential's value as a text. Only one value (the text_value or binary_value) should be not empty.")
    private String textValue;

    /**
     * Credential's value for a binary credential that is stored as a base64 value.
     */
    @JsonPropertyDescription("Credential's value for a binary credential that is stored as a base64 value. Only one value (the text_value or binary_value) should be not empty.")
    private String binaryValue;
}
