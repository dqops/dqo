package com.dqops.metadata.storage.localfiles.credentials.azure;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;

/**
 * The default azure credentials container
 */
@Data
@Builder
public class AzureCredential {

    @JsonPropertyDescription("The identity name. Depending on the authentication method it can be a username or a client id.")
    private String user;

    @JsonPropertyDescription("The password. Depending on the authentication method it can also be a secret key.")
    private String password;

    @JsonPropertyDescription("Property that indicates which SQL authentication method to use for the connection. Check the Azure documentation for the SQL Server JDBC Driver for more details.")
    private String authentication;
}
