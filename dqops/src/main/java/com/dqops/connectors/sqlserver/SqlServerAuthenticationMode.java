package com.dqops.connectors.sqlserver;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Authentication mode to Microsoft SQL Server.
 */
public enum SqlServerAuthenticationMode {

    /**
     * Uses the SQL Server Authentication
     * The authentication is SqlPassword
     */
    @JsonProperty("sql_password")
    sql_password,   // todo: test the connection with use of it one more time

    /**
     * Uses the Azure Active Directory Authentication with Password
     * The authentication is ActiveDirectoryPassword.
     */
    @JsonProperty("active_directory_password")
    active_directory_password, // todo: test the connection with use of it one more time

    /**
     * Uses the Azure Active Directory Authentication with Managed Identity.
     * The authentication is ActiveDirectoryManagedIdentity
     */
    @JsonProperty("active_directory_managed_identity")
    active_directory_managed_identity, // todo: test the connection with use of it one more time

    /**
     * Uses the Azure Active Directory Authentication with Service Pricnipal
     * The authentication is ActiveDirectoryServicePrincipal
     */
    @JsonProperty("active_directory_service_principal")
    active_directory_service_principal, // todo: test the connection with use of it one more time

    /**
     * Uses the DQOps default credential from USER_HOME/.credentials/Azure_default_credentials
     * The authentication is acquired from the file.
     */
    @JsonProperty("default_credential")
    default_credential, // todo: test the connection with use of it one more time
}
