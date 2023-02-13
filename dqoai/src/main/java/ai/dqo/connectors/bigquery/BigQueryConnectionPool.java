/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors.bigquery;

import ai.dqo.metadata.sources.ConnectionSpec;

/**
 * BigQuery connection pool that supports multiple connections.
 */
public interface BigQueryConnectionPool {
    /**
     * Returns or creates a BigQuery service for the given connection specification.
     * @param connectionSpec Connection specification (should be not mutable).
     * @return BigQuery service.
     */
    BigQueryInternalConnection getBigQueryService(ConnectionSpec connectionSpec);
}
