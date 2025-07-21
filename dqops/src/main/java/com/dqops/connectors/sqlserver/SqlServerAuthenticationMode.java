/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

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
    sql_password,

    /**
     * Uses the Azure Active Directory Authentication with Password
     * The authentication is ActiveDirectoryPassword.
     */
    @JsonProperty("active_directory_password")
    active_directory_password,

    /**
     * Uses the Azure Active Directory Authentication with Service Pricnipal
     * The authentication is ActiveDirectoryServicePrincipal
     */
    @JsonProperty("active_directory_service_principal")
    active_directory_service_principal,

    /**
     * Uses the Azure Active Directory Default
     * The authentication is ActiveDirectoryDefault
     */
    @JsonProperty("active_directory_default")
    active_directory_default
}
