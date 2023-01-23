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
package ai.dqo.core.jobqueue.jobs.data;

import java.util.Objects;

/**
 * Concurrency target (connection name) that is used to limit the number of concurrent operations.
 */
public class DeleteStoredDataQueueJobConcurrencyTarget {
    private String connectionName;

    public DeleteStoredDataQueueJobConcurrencyTarget() {
    }

    /**
     * Creates a concurrency object used to limit concurrent operations to a single connection at a time (DOP: 1)
     * @param connectionName Connection name.
     */
    public DeleteStoredDataQueueJobConcurrencyTarget(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets the connection name.
     * @param connectionName Connection name.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteStoredDataQueueJobConcurrencyTarget that = (DeleteStoredDataQueueJobConcurrencyTarget) o;

        return Objects.equals(connectionName, that.connectionName);
    }

    @Override
    public int hashCode() {
        return connectionName != null ? connectionName.hashCode() : 0;
    }
}
