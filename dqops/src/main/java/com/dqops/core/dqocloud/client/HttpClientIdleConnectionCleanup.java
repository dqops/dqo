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
