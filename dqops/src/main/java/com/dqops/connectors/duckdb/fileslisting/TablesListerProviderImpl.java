/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.aws.AwsTablesLister;
import com.dqops.connectors.duckdb.fileslisting.azure.AzureTablesLister;
import com.dqops.connectors.duckdb.fileslisting.google.GcsTablesLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for the table listers based on file system.
 */
@Component
public class TablesListerProviderImpl implements TablesListerProvider {

    private final LocalSystemTablesLister localSystemTablesLister;
    private final AwsTablesLister awsTablesLister;
    private final AzureTablesLister azureTablesLister;
    private final GcsTablesLister gcsTablesLister;

    @Autowired
    public TablesListerProviderImpl(LocalSystemTablesLister localSystemTablesLister,
                                    AwsTablesLister awsTablesLister,
                                    AzureTablesLister azureTablesLister,
                                    GcsTablesLister gcsTablesLister) {
        this.localSystemTablesLister = localSystemTablesLister;
        this.awsTablesLister = awsTablesLister;
        this.azureTablesLister = azureTablesLister;
        this.gcsTablesLister = gcsTablesLister;
    }

    /**
     * Created the table lister depending on the storage type.
     * @param storageType The storage type supported in duckdb.
     * @return TableLister
     */
    public TablesLister createTablesLister(DuckdbStorageType storageType){

        if(storageType == null || storageType.equals(DuckdbStorageType.local)){
            return localSystemTablesLister;
        }

        switch (storageType){
            case s3:
                return awsTablesLister;
            case azure:
                return azureTablesLister;
            case gcs:
                return gcsTablesLister;
            default:
                throw new RuntimeException("This type of storageType is not supported: " + storageType);
        }
    }

}
