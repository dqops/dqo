package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.duckdb.DuckdbStorageType;

public class TablesListerProvider {

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
