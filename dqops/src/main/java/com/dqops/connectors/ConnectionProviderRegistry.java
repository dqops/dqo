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
package com.dqops.connectors;


/**
 * Connection registry that returns connection providers for dialects.
 */
public interface ConnectionProviderRegistry {
    /**
     * Returns a connection provider for the given dialect.
     * @param dialect Dialect name.
     * @return Connection provider for the given dialect.
     */
    ConnectionProvider getConnectionProvider(String dialect);

    /**
     * Returns a connection provider for the given dialect.
     * @param dialect Dialect name.
     * @return Connection provider for the given dialect.
     */
    ConnectionProvider getConnectionProvider(ProviderType dialect);
}
