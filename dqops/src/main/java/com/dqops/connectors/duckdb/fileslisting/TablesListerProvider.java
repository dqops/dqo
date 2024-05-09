package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.duckdb.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.azure.AzureTablesLister;

/**
 * Factory for the table listers based on file system.
 */
public class TablesListerProvider {

    /**
     * Created the table lister depending on the storage type.
     * @param storageType The storage type supported in duckdb
     * @return TableLister
     */
    public static TablesLister createTablesLister(DuckdbStorageType storageType){

        if(storageType == null || storageType.equals(DuckdbStorageType.local)){
            return new LocalSystemTablesLister();
        }

        switch (storageType){
            case s3:
                return new AwsTablesLister();
            case azure:
                return new AzureTablesLister();
            default:
                throw new RuntimeException("This type of storageType is not supported: " + storageType);
        }
    }

}
