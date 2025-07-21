/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.users;

import com.dqops.core.principal.DqoUserPrincipal;

import java.util.Collection;

/**
 * User management service that manages users in a multi-user cloud instance.
 */
public interface UserManagementService {
    /**
     * Retrieves a list of users for this instance.
     *
     * @param userPrincipal User principal.
     * @return List of users for this DQOps instance.
     */
    Collection<DqoUserRolesModel> listUsers(DqoUserPrincipal userPrincipal);

    /**
     * Retrieves a user from the list of users known to DQOps Cloud, given a user email.
     *
     * @param userPrincipal Caller principal, requires a VIEWER permission to run.
     * @param email         User's email.
     * @return User's model or null when the user was not found.
     */
    DqoUserRolesModel getUserByEmail(DqoUserPrincipal userPrincipal, String email);

    /**
     * Creates a user in DQOps Cloud. An optional password may be passed.
     *
     * @param userPrincipal Caller principal, requires an ADMIN role to run.
     * @param userModel     User model that is created.
     * @param password      User's password, optional.
     * @throws DqoUserLimitExceededException when the user limit was exceeded for the account.
     */
    void createUser(DqoUserPrincipal userPrincipal, DqoUserRolesModel userModel, String password);

    /**
     * Update a user in DQOps Cloud. Supports changing the role.
     *
     * @param userPrincipal Caller principal, requires an ADMIN role to run.
     * @param userModel     User model that is updated.
     */
    void updateUser(DqoUserPrincipal userPrincipal, DqoUserRolesModel userModel);

    /**
     * Deletes a user in DQOps Cloud, given a user email.
     *
     * @param userPrincipal Caller principal, requires an ADMIN permission to run.
     * @param email         User's email.
     */
    void deleteUser(DqoUserPrincipal userPrincipal, String email);

    /**
     * Changes the password of a user. Only an account administrator can change the password of any user in a multi-user installation,
     * but each user can change his own password when the user's email matches the email that identifies the user.
     *
     * @param userPrincipal Caller principal, requires an ADMIN permission to run.
     * @param email         User's email.
     * @param newPassword   New password.
     */
    void changePassword(DqoUserPrincipal userPrincipal, String email, String newPassword);
}
