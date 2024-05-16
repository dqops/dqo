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
