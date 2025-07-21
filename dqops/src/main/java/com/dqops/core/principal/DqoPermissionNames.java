/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.principal;

/**
 * Constants with DQOps privileges (permission) names.
 */
public final class DqoPermissionNames {
    /**
     * Permission to manage the account - permissions, etc. Perform actions that only a person assigned the "ADMIN" role can perform.
     */
    public static final String MANAGE_ACCOUNT = "manage_account";

    /**
     * Permission to edit data sources and definitions.
     */
    public static final String EDIT = "edit";

    /**
     * Permission to configure checks, run checks, manage incidents, delete data.
     */
    public static final String OPERATE = "operate";

    /**
     * Permission to view the definition of data sources, check configuration, check results, incidents, data quality dashboards.
     */
    public static final String VIEW = "view";
}
