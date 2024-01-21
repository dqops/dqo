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
