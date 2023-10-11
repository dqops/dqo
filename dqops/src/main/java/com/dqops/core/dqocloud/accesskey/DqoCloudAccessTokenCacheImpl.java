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
package com.dqops.core.dqocloud.accesskey;

import com.dqops.cloud.rest.model.TenantAccessTokenModel;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.google.auth.oauth2.AccessToken;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * DQOps Cloud access token cache that creates new GCP access tokens when they are about to expire.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class DqoCloudAccessTokenCacheImpl implements DqoCloudAccessTokenCache {
    public static final int REFRESH_ACCESS_TOKEN_BEFORE_EXPIRATION_SECONDS = 900; // refresh the access token if it is about to expire in 900 seconds

    private DqoCloudCredentialsProvider dqoCloudCredentialsProvider;
    private final HashMap<DqoRoot, Supplier<DqoCloudCredentials>> rootCredentialSuppliers = new LinkedHashMap<>();
    private final Object lock = new Object();

    @Autowired
    public DqoCloudAccessTokenCacheImpl(DqoCloudCredentialsProvider dqoCloudCredentialsProvider) {
        this.dqoCloudCredentialsProvider = dqoCloudCredentialsProvider;
    }

    /**
     * Returns a current GCP bucket access token used to perform read/write operations on a customer's storage bucket for a given root folder.
     * @param dqoRoot DQOps Root folder.
     * @return Up-to-date access token.
     */
    @Override
    public DqoCloudCredentials getCredentials(DqoRoot dqoRoot) {
        Supplier<DqoCloudCredentials> credentialsSupplier = null;

        synchronized (this.lock) {
            credentialsSupplier = this.rootCredentialSuppliers.get(dqoRoot);
            if (credentialsSupplier == null) {
                final Object supplierLock = new Object();

                credentialsSupplier = Suppliers.memoize(() -> {
                    if (log.isDebugEnabled()) {
                        log.debug("Requesting a new access token for " + dqoRoot);
                    }

                    synchronized (supplierLock) {
                        if (log.isDebugEnabled()) {
                            log.debug("Requesting new tenant access token to access the folder " + dqoRoot);
                        }

                        TenantAccessTokenModel tenantAccessTokenModel = this.dqoCloudCredentialsProvider.issueTenantAccessToken(dqoRoot);
                        if (tenantAccessTokenModel != null) {
                            AccessToken accessToken = this.dqoCloudCredentialsProvider.createAccessToken(tenantAccessTokenModel);
                            return new DqoCloudCredentials(tenantAccessTokenModel, accessToken);
                        }

                        return null;
                    }
                });

                this.rootCredentialSuppliers.put(dqoRoot, credentialsSupplier);
            }
        }

        DqoCloudCredentials dqoCloudCredentials = credentialsSupplier.get();
        if (dqoCloudCredentials == null) {
            return null;
        }

        Date testedExpirationDate = new Date(System.currentTimeMillis() + REFRESH_ACCESS_TOKEN_BEFORE_EXPIRATION_SECONDS * 1000L);
        boolean accessTokenNotExpired = dqoCloudCredentials.getAccessToken().getExpirationTime().after(testedExpirationDate);
        if (accessTokenNotExpired) {
            return dqoCloudCredentials;
        }

        synchronized (this.lock) {
            Supplier<DqoCloudCredentials> currentCredentialsSupplier = this.rootCredentialSuppliers.get(dqoRoot);
            if (credentialsSupplier == currentCredentialsSupplier) {
                final Object supplierLock = new Object();

                credentialsSupplier = Suppliers.memoize(() -> {
                    if (log.isDebugEnabled()) {
                        log.debug("Requesting a new access token for " + dqoRoot);
                    }

                    synchronized (supplierLock) {
                        if (log.isDebugEnabled()) {
                            log.debug("Requesting new tenant access token to access the folder " + dqoRoot +
                                    ", because the the current access token will expire at " + dqoCloudCredentials.getAccessToken().getExpirationTime());
                        }
                        TenantAccessTokenModel tenantAccessTokenModel = this.dqoCloudCredentialsProvider.issueTenantAccessToken(dqoRoot);
                        AccessToken accessToken = this.dqoCloudCredentialsProvider.createAccessToken(tenantAccessTokenModel);
                        return new DqoCloudCredentials(tenantAccessTokenModel, accessToken);
                    }
                });

                this.rootCredentialSuppliers.put(dqoRoot, credentialsSupplier);
            } else {
                credentialsSupplier = currentCredentialsSupplier;
            }
        }

        return credentialsSupplier.get();
    }

    /**
     * Invalidates the cache. Called when all the keys should be abandoned, because the API key has changed.
     */
    @Override
    public void invalidate() {
        synchronized (this.lock) {
            this.rootCredentialSuppliers.clear();
        }
    }
}
