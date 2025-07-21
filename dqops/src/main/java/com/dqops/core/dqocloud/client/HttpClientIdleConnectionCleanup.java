/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.client;

import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Scheduled component that closes idle connections.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HttpClientIdleConnectionCleanup {
    private final PoolingHttpClientConnectionManager httpClientConnectionManager;
    private final DqoCloudConfigurationProperties dqoCloudConfigurationProperties;

    /**
     * Default injection constructor.
     * @param httpClientConnectionManager HTTP request factory.
     */
    @Autowired
    public HttpClientIdleConnectionCleanup(PoolingHttpClientConnectionManager httpClientConnectionManager,
                                           DqoCloudConfigurationProperties dqoCloudConfigurationProperties) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
    }

    /**
     * Called by the scheduler. Closes idle connections.
     */
    @Scheduled(fixedDelay = 30000L)
    public void closeIdleConnections() {
        TimeValue idleTime = TimeValue.of(this.dqoCloudConfigurationProperties.getIdleTimeoutSeconds(), TimeUnit.SECONDS);
        this.httpClientConnectionManager.closeIdle(idleTime);
    }
}
