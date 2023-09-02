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

package com.dqops.rest.server.authentication;

/**
 * Constants with DQO user role names.
 */
public final class DqoRoleNames {
    /**
     * Administrator role, who can perform any action in DQO.
     */
    public static final String ADMIN = "admin";

    /**
     * Power user role who can manage connections, import tables, define data quality checks, run data quality checks, manage results.
     */
    public static final String EDITOR = "editor";

    /**
     * Limited user who cannot manage connections, but the user can configure and run data quality run checks, preview and delete data quality results, use data quality dashboards, manage incidents.
     */
    public static final String OPERATOR = "operator";

    /**
     * End user who can only preview the definition of data quality checks without making any changes. The user can also use data quality dashboards.
     */
    public static final String VIEWER = "viewer";
}
