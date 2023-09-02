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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Container of singletons that identify roles as Spring Security authorities.
 */
public final class DqoRoleAuthorities {
    /**
     * Administrator role, who can perform any action in DQO.
     */
    public static final SimpleGrantedAuthority ADMIN = new SimpleGrantedAuthority(DqoRoleNames.ADMIN);

    /**
     * Power user role who can manage connections, import tables, define data quality checks, run data quality checks, manage results.
     */
    public static final SimpleGrantedAuthority EDITOR = new SimpleGrantedAuthority(DqoRoleNames.EDITOR);

    /**
     * Limited user who cannot manage connections, but the user can configure and run data quality run checks, preview and delete data quality results, use data quality dashboards, manage incidents.
     */
    public static final SimpleGrantedAuthority OPERATOR = new SimpleGrantedAuthority(DqoRoleNames.OPERATOR);

    /**
     * End user who can only preview the definition of data quality checks without making any changes. The user can also use data quality dashboards.
     */
    public static final SimpleGrantedAuthority VIEWER = new SimpleGrantedAuthority(DqoRoleNames.VIEWER);

    /**
     * Returns a known authority (role holder) for a known role.
     * @param roleName Role name.
     * @return Granted authority for the role or null when the role name does not match any role.
     */
    public static SimpleGrantedAuthority getAuthorityForRole(String roleName) {
        switch (roleName) {
            case DqoRoleNames.ADMIN:
                return ADMIN;

            case DqoRoleNames.EDITOR:
                return EDITOR;

            case DqoRoleNames.OPERATOR:
                return OPERATOR;

            case DqoRoleNames.VIEWER:
                return VIEWER;

            default:
                return null;
        }
    }

    /**
     * Returns a list of roles that are nested inside the given role that was assigned.
     * For example, the ADMIN role includes also EDITOR, OPERATOR and VIEWER roles.
     * @param assignedRole Assigned role.
     * @return List of all effective roles.
     */
    public static List<GrantedAuthority> getExpandedRoles(SimpleGrantedAuthority assignedRole) {
        List<GrantedAuthority> allRoles = new ArrayList<>();
        if (VIEWER.equals(assignedRole)) {
            allRoles.add(VIEWER);
            return allRoles;
        }

        if (OPERATOR.equals(assignedRole)) {
            allRoles.add(VIEWER);
            allRoles.add(OPERATOR);
            return allRoles;
        }

        if (EDITOR.equals(assignedRole)) {
            allRoles.add(VIEWER);
            allRoles.add(OPERATOR);
            allRoles.add(EDITOR);
            return allRoles;
        }

        if (ADMIN.equals(assignedRole)) {
            allRoles.add(VIEWER);
            allRoles.add(OPERATOR);
            allRoles.add(EDITOR);
            allRoles.add(ADMIN);
            return allRoles;
        }

        return allRoles;
    }
}
