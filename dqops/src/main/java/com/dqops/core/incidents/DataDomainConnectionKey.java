/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.incidents;

import java.util.Objects;

/**
 * A key object used in a hashtable with incident loaders, identified by the connection name and data domain name.
 */
public class DataDomainConnectionKey {
    private String dataDomain;
    private String connection;

    /**
     * Creates a key object.
     * @param dataDomain Data domain name.
     * @param connection Connection name.
     */
    public DataDomainConnectionKey(String dataDomain, String connection) {
        this.dataDomain = dataDomain;
        this.connection = connection;
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
    public String getConnection() {
        return connection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataDomainConnectionKey that = (DataDomainConnectionKey) o;

        if (!Objects.equals(dataDomain, that.dataDomain)) return false;
        return Objects.equals(connection, that.connection);
    }

    @Override
    public int hashCode() {
        int result = dataDomain != null ? dataDomain.hashCode() : 0;
        result = 31 * result + (connection != null ? connection.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataDomainConnectionKey{" +
                "dataDomain='" + dataDomain + '\'' +
                ", connection='" + connection + '\'' +
                '}';
    }
}
