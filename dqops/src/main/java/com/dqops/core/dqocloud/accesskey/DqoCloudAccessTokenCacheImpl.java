/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.dqocloud.accesskey;

import com.dqops.cloud.rest.model.TenantAccessTokenModel;
import com.dqops.core.principal.UserDomainIdentity;
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
    private final HashMap<DqoDomainRootPair, Supplier<DqoCloudCredentials>> rootCredentialSuppliers = new LinkedHashMap<>();
    private final Object lock = new Object();

    @Autowired
    public DqoCloudAccessTokenCacheImpl(DqoCloudCredentialsProvider dqoCloudCredentialsProvider) {
        this.dqoCloudCredentialsProvider = dqoCloudCredentialsProvider;
    }

    /**
     * Returns a current GCP bucket access token used to perform read/write operations on a customer's storage bucket for a given root folder.
     * @param dqoRoot DQOps Root folder.
     * @param userIdentity Calling user identity, used to identify the data domain.
     * @return Up-to-date access token.
     */
    @Override
    public DqoCloudCredentials getCredentials(DqoRoot dqoRoot, UserDomainIdentity userIdentity) {
        Supplier<DqoCloudCredentials> credentialsSupplier = null;
        DqoDomainRootPair domainRootPair = new DqoDomainRootPair(userIdentity.getDataDomainCloud(), dqoRoot);

        synchronized (this.lock) {
            credentialsSupplier = this.rootCredentialSuppliers.get(domainRootPair);
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

                        TenantAccessTokenModel tenantAccessTokenModel = this.dqoCloudCredentialsProvider.issueTenantAccessToken(dqoRoot, userIdentity);
                        if (tenantAccessTokenModel != null) {
                            AccessToken accessToken = this.dqoCloudCredentialsProvider.createAccessToken(tenantAccessTokenModel);
                            return new DqoCloudCredentials(tenantAccessTokenModel, accessToken);
                        }

                        return null;
                    }
                });

                this.rootCredentialSuppliers.put(domainRootPair, credentialsSupplier);
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
            Supplier<DqoCloudCredentials> currentCredentialsSupplier = this.rootCredentialSuppliers.get(domainRootPair);
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
                        TenantAccessTokenModel tenantAccessTokenModel = this.dqoCloudCredentialsProvider.issueTenantAccessToken(dqoRoot, userIdentity);
                        AccessToken accessToken = this.dqoCloudCredentialsProvider.createAccessToken(tenantAccessTokenModel);
                        return new DqoCloudCredentials(tenantAccessTokenModel, accessToken);
                    }
                });

                this.rootCredentialSuppliers.put(domainRootPair, credentialsSupplier);
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
