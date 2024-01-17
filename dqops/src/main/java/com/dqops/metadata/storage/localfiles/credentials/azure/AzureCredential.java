package com.dqops.metadata.storage.localfiles.credentials.azure;

import lombok.Builder;
import lombok.Data;

// todo: descriptions
@Data
@Builder
public class AzureCredential {

    private String user;

    private String password;

    /**
     * Property that indicates which SQL authentication method to use for the connection.
     * Check the SQL Server JDBC Driver for more details.
     */
    private String authentication;
}
