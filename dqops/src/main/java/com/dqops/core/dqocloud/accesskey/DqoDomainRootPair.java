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

package com.dqops.core.dqocloud.accesskey;

import com.dqops.core.synchronization.contract.DqoRoot;

import java.util.Objects;

/**
 * A key object that contains the data domain name and the DQOps root folder. Used as a key in some hash tables.
 */
public class DqoDomainRootPair {
    private String dataDomain;
    private DqoRoot dqoRoot;

    /**
     * Creates a data domain root folder key.
     * @param dataDomain Data domain name.
     * @param dqoRoot Folder root.
     */
    public DqoDomainRootPair(String dataDomain, DqoRoot dqoRoot) {
        this.dataDomain = dataDomain;
        this.dqoRoot = dqoRoot;
    }

    /**
     * Returns the name of the data domain.
     * @return Data domain name.
     */
    public String getDataDomain() {
        return dataDomain;
    }

    /**
     * Returns the folder type in the DQOps user home folder.
     * @return Folder type.
     */
    public DqoRoot getDqoRoot() {
        return dqoRoot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DqoDomainRootPair that = (DqoDomainRootPair) o;

        if (!Objects.equals(dataDomain, that.dataDomain)) return false;
        return dqoRoot == that.dqoRoot;
    }

    @Override
    public int hashCode() {
        int result = dataDomain != null ? dataDomain.hashCode() : 0;
        result = 31 * result + (dqoRoot != null ? dqoRoot.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DqoDomainRootPair{" +
                "dataDomain='" + dataDomain + '\'' +
                ", dqoRoot=" + dqoRoot +
                '}';
    }
}
