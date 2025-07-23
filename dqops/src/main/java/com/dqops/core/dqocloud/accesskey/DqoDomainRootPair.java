/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
