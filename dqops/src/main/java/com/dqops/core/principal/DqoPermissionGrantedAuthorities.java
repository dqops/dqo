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

import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * Container of singletons that stores Spring Security "Granted Authorities" privileges (permissions) supported by DQOps.
 */
public final class DqoPermissionGrantedAuthorities {
    /**
     * Permission to manage the account - permissions, etc. Perform actions that only a person assigned the "ADMIN" role can perform.
     */
    public static final SimpleGrantedAuthority MANAGE_ACCOUNT = new SimpleGrantedAuthority(DqoPermissionNames.MANAGE_ACCOUNT);

    /**
     * Permission to edit data sources and definitions.
     */
    public static final SimpleGrantedAuthority EDIT = new SimpleGrantedAuthority(DqoPermissionNames.EDIT);

    /**
     * Permission to configure checks, run checks, manage incidents, delete data.
     */
    public static final SimpleGrantedAuthority OPERATE = new SimpleGrantedAuthority(DqoPermissionNames.OPERATE);

    /**
     * Permission to view the definition of data sources, check configuration, check results, incidents, data quality dashboards.
     */
    public static final SimpleGrantedAuthority VIEW = new SimpleGrantedAuthority(DqoPermissionNames.VIEW);

    private static final List<GrantedAuthority> ADMIN_ROLE_AUTHORITIES =
            List.of(new SimpleGrantedAuthority("ROLE_admin"), MANAGE_ACCOUNT, EDIT, OPERATE, VIEW);

    private static final List<GrantedAuthority> EDITOR_ROLE_AUTHORITIES =
            List.of(new SimpleGrantedAuthority("ROLE_editor"), EDIT, OPERATE, VIEW);

    private static final List<GrantedAuthority> OPERATOR_ROLE_AUTHORITIES =
            List.of(new SimpleGrantedAuthority("ROLE_operator"), OPERATE, VIEW);

    private static final List<GrantedAuthority> VIEWER_ROLE_AUTHORITIES =
            List.of(new SimpleGrantedAuthority("ROLE_viewer"), VIEW);

    private static final List<GrantedAuthority> NONE_ROLE_AUTHORITIES =
            List.of(new SimpleGrantedAuthority("ROLE_none"));


    /**
     * Returns a list of granted authorities (permissions) for a given Cloud DQOps role.
     * @param role Assigned role.
     * @return List of all effective roles.
     */
    public static List<GrantedAuthority> getPrivilegesForRole(DqoUserRole role) {
        if (role == null) {
            return NONE_ROLE_AUTHORITIES;
        }

        switch (role) {
            case ADMIN:
                return ADMIN_ROLE_AUTHORITIES;

            case EDITOR:
                return EDITOR_ROLE_AUTHORITIES;

            case OPERATOR:
                return OPERATOR_ROLE_AUTHORITIES;

            case VIEWER:
                return VIEWER_ROLE_AUTHORITIES;

            case NONE:
                return NONE_ROLE_AUTHORITIES;

            default:
                throw new DqoRuntimeException("Cannot map permissions, unknown role: " + role);
        }
    }
}
