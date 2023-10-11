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
package com.dqops.utils.http;

import reactor.netty.resources.ConnectionProvider;

/**
 * Returns a shared, preconfigured HTTP Connection provider that maintains a pool of HTTP connections
 * used by a WebFlux web client in DQOps.
 */
public interface SharedHttpConnectionProvider {
    /**
     * Returns a shared HTTP connection provider (connection pool).
     *
     * @return HTTP connection provider (connection pool).
     */
    ConnectionProvider getConnectionProvider();
}
