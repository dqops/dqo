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

package com.dqops.core.principal;

/**
 * Constants with DQO privileges (permission) names.
 */
public final class DqoPermissionNames {
    /**
     * Permission to manage the account - permissions, etc. Perform actions that only a person assigned the "ADMIN" role can perform.
     */
    public static final String ADMIN = "admin";

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
