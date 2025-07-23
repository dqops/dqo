/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
