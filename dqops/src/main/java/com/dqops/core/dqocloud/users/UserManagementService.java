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
    Collection<DqoCloudUserModel> listUsers(DqoUserPrincipal userPrincipal);

    /**
     * Retrieves a user from the list of users known to DQOps Cloud, given a user email.
     *
     * @param userPrincipal Caller principal, requires a VIEWER permission to run.
     * @param email         User's email.
     * @return User's model or null when the user was not found.
     */
    DqoCloudUserModel getUserByEmail(DqoUserPrincipal userPrincipal, String email);

    /**
     * Creates a user in DQOps Cloud. An optional password may be passed.
     *
     * @param userPrincipal Caller principal, requires an ADMIN role to run.
     * @param userModel     User model that is created.
     * @param password      User's password, optional.
     * @throws DqoUserLimitExceededException when the user limit was exceeded for the account.
     */
    void createUser(DqoUserPrincipal userPrincipal, DqoCloudUserModel userModel, String password);

    /**
     * Update a user in DQOps Cloud. Supports changing the role.
     *
     * @param userPrincipal Caller principal, requires an ADMIN role to run.
     * @param userModel     User model that is updated.
     */
    void updateUser(DqoUserPrincipal userPrincipal, DqoCloudUserModel userModel);

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
