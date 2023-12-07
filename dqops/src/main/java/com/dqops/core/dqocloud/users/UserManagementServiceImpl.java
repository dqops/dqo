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

import com.dqops.cloud.rest.api.AccountUsersApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.cloud.rest.model.DqoUserModel;
import com.dqops.core.dqocloud.apikey.DqoCloudInvalidKeyException;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User management service that manages users in a multi-user cloud instance.
 */
@Component
public class UserManagementServiceImpl implements UserManagementService {
    private DqoCloudApiClientFactory dqoCloudApiClientFactory;

    /**
     * Dependency injection constructor.
     * @param dqoCloudApiClientFactory DQOps Cloud client factory.
     */
    @Autowired
    public UserManagementServiceImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
    }

    /**
     * Retrieves a list of users for this instance.
     * @param userPrincipal User principal.
     * @return List of users for this DQOps instance.
     */
    @Override
    public Collection<DqoCloudUserModel> listUsers(DqoUserPrincipal userPrincipal) {
        try {
            userPrincipal.throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.VIEW);

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userPrincipal.getIdentity());
            AccountUsersApi accountUsersApi = new AccountUsersApi(authenticatedClient);
            List<DqoUserModel> cloudUserList = accountUsersApi.listAccountUsers();

            List<DqoCloudUserModel> users =
                    cloudUserList.stream()
                            .map(cloudUserModel -> new DqoCloudUserModel() {{
                                setEmail(cloudUserModel.getEmail());
                                setAccountRole(DqoUserRole.convertFromApiEnum(cloudUserModel.getAccountRole()));
                            }})
                            .collect(Collectors.toList());
            return users;
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud API Key.");
            }

            throw new DqoCloudUserManagementException("Failed to retrieve a list of users, error: " + httpClientErrorException.getMessage(), httpClientErrorException);
        }
    }

    /**
     * Retrieves a user from the list of users known to DQOps Cloud, given a user email.
     * @param userPrincipal Caller principal, requires a VIEWER permission to run.
     * @param email User's email.
     * @return User's model or null when the user was not found.
     */
    @Override
    public DqoCloudUserModel getUserByEmail(DqoUserPrincipal userPrincipal, String email) {
        try {
            userPrincipal.throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.VIEW);

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userPrincipal.getIdentity());
            AccountUsersApi accountUsersApi = new AccountUsersApi(authenticatedClient);
            DqoUserModel cloudUserModel = accountUsersApi.getAccountUser(email);

            DqoCloudUserModel dqoCloudUserModel = new DqoCloudUserModel() {{
                setEmail(cloudUserModel.getEmail());
                setAccountRole(DqoUserRole.convertFromApiEnum(cloudUserModel.getAccountRole()));
            }};

            return dqoCloudUserModel;
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().value() == 404) {
                return null; // user not found
            }

            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud API Key.");
            }

            throw new DqoCloudUserManagementException("Failed to retrieve a user, error: " + httpClientErrorException.getMessage(), httpClientErrorException);
        }
    }

    /**
     * Creates a user in DQOps Cloud. An optional password may be passed.
     * @param userPrincipal Caller principal, requires an ADMIN role to run.
     * @param userModel User model that is created.
     * @param password User's password, optional.
     */
    @Override
    public void createUser(DqoUserPrincipal userPrincipal, DqoCloudUserModel userModel, String password) {
        try {
            userPrincipal.throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.MANAGE_ACCOUNT);

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userPrincipal.getIdentity());
            AccountUsersApi accountUsersApi = new AccountUsersApi(authenticatedClient);
            DqoUserModel dqoUserModel = new DqoUserModel() {{
                setEmail(userModel.getEmail());
                setAccountRole(userModel.getAccountRole().convertToApiEnum());
                setNewPassword(password);
            }};

            accountUsersApi.createAccountUser(dqoUserModel);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(412))) {
                throw new DqoUserLimitExceededException("The user limit was exceeded for the account, cannot create more users.");
            }

            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud API Key.");
            }

            throw new DqoCloudUserManagementException("Failed to create a user, error: " + httpClientErrorException.getMessage(), httpClientErrorException);
        }
    }

    /**
     * Update a user in DQOps Cloud. Supports changing the role.
     * @param userPrincipal Caller principal, requires an ADMIN role to run.
     * @param userModel User model that is updated.
     */
    @Override
    public void updateUser(DqoUserPrincipal userPrincipal, DqoCloudUserModel userModel) {
        try {
            userPrincipal.throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.MANAGE_ACCOUNT);

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userPrincipal.getIdentity());
            AccountUsersApi accountUsersApi = new AccountUsersApi(authenticatedClient);
            DqoUserModel dqoUserModel = new DqoUserModel() {{
                setEmail(userModel.getEmail());
                setAccountRole(userModel.getAccountRole().convertToApiEnum());
            }};

            accountUsersApi.updateAccountUser(userModel.getEmail(), dqoUserModel);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().value() == 404) {
                throw new DqoUserNotFoundException("User " + userModel.getEmail() + " not found");
            }

            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud API Key.");
            }

            throw new DqoCloudUserManagementException("Failed to update a user, error: " + httpClientErrorException.getMessage(), httpClientErrorException);
        }
    }

    /**
     * Deletes a user in DQOps Cloud, given a user email.
     * @param userPrincipal Caller principal, requires an ADMIN permission to run.
     * @param email User's email.
     */
    @Override
    public void deleteUser(DqoUserPrincipal userPrincipal, String email) {
        try {
            userPrincipal.throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.MANAGE_ACCOUNT);

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userPrincipal.getIdentity());
            AccountUsersApi accountUsersApi = new AccountUsersApi(authenticatedClient);
            accountUsersApi.deleteAccountUser(email);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().value() == 404) {
                throw new DqoUserNotFoundException("User " + email + " not found");
            }

            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud API Key.");
            }

            throw new DqoCloudUserManagementException("Failed to delete a user, error: " + httpClientErrorException.getMessage(), httpClientErrorException);
        }
    }

    /**
     * Changes the password of a user. Only an account administrator can change the password of any user in a multi-user installation,
     * but each user can change his own password when the user's email matches the email that identifies the user.
     * @param userPrincipal Caller principal, requires an ADMIN permission to run.
     * @param email User's email.
     * @param newPassword New password.
     */
    @Override
    public void changePassword(DqoUserPrincipal userPrincipal, String email, String newPassword) {
        try {
            if (!Objects.equals(userPrincipal.getName(), email)) {
                userPrincipal.throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.MANAGE_ACCOUNT);
            }

            ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userPrincipal.getIdentity());
            AccountUsersApi accountUsersApi = new AccountUsersApi(authenticatedClient);
            accountUsersApi.changeAccountUserPassword(email, newPassword);
        }
        catch (HttpClientErrorException httpClientErrorException) {
            if (httpClientErrorException.getStatusCode().value() == 404) {
                throw new DqoUserNotFoundException("User " + email + " not found");
            }

            if (httpClientErrorException.getStatusCode().is4xxClientError()) {
                throw new DqoCloudInvalidKeyException("Invalid API Key, run \"cloud login\" in DQOps shell to receive a current DQOps Cloud API Key.");
            }

            throw new DqoCloudUserManagementException("Failed to change the user's password, error: " + httpClientErrorException.getMessage(), httpClientErrorException);
        }
    }
}
