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

package com.dqops.core.similarity;

import java.util.Objects;

/**
 * Queue key of the similarity refresh service to identify a connection and its parent data domain.
 */
public final class SimilarityContainerQueueKey {
    private final String dataDomain;
    private final String connectionName;

    public SimilarityContainerQueueKey(String dataDomain, String connectionName) {
        this.dataDomain = dataDomain;
        this.connectionName = connectionName;
    }

    /**
     * Returns the data domain name.
     * @return Data domain name.
     */
    public String getDataDomain() {
        return dataDomain;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimilarityContainerQueueKey that = (SimilarityContainerQueueKey) o;

        if (!Objects.equals(dataDomain, that.dataDomain)) return false;
        return Objects.equals(connectionName, that.connectionName);
    }

    @Override
    public int hashCode() {
        int result = dataDomain != null ? dataDomain.hashCode() : 0;
        result = 31 * result + (connectionName != null ? connectionName.hashCode() : 0);
        return result;
    }
}
