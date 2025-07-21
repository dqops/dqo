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

import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
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
     * Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user's home folder.
     */
    @JsonPropertyDescription("Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user's home folder.")
    private String credentialName;

    /**
     * Credential type.
     */
    @JsonPropertyDescription("Credential type that is based on the detected format of the file. If the file can be parsed as a valid utf-8 string, then it is assumed that the credential is a text. Otherwise, it is a binary file that can only be retrieved as a base64 value.")
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

    public static class SharedCredentialModelSampleFactory implements SampleValueFactory<SharedCredentialModel> {
        @Override
        public SharedCredentialModel createSample() {
            return new SharedCredentialModel() {{
                setCredentialName(SampleStringsRegistry.getCredential());
                setType(CredentialType.text);
                setTextValue(getCredentialName() + "_text_value");
            }};
        }
    }
}
