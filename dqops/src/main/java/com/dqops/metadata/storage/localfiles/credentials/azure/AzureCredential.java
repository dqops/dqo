/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.storage.localfiles.credentials.azure;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;

/**
 * The default azure credentials container for service principal authentication.
 */
@Data
@Builder
public class AzureCredential {

    @JsonPropertyDescription("Tenant ID.")
    private String tenantId;

    @JsonPropertyDescription("Client ID.")
    private String clientId;

    @JsonPropertyDescription("Client Secret.")
    private String clientSecret;

    @JsonPropertyDescription("Storage Account Name.")
    private String accountName;
}
