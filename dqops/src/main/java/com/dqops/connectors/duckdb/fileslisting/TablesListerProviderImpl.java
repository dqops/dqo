package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.duckdb.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.aws.AwsTablesLister;
import com.dqops.connectors.duckdb.fileslisting.azure.AzureTablesLister;
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

    @Autowired
    public TablesListerProviderImpl(LocalSystemTablesLister localSystemTablesLister,
                                    AwsTablesLister awsTablesLister,
                                    AzureTablesLister azureTablesLister) {
        this.localSystemTablesLister = localSystemTablesLister;
        this.awsTablesLister = awsTablesLister;
        this.azureTablesLister = azureTablesLister;
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
            default:
                throw new RuntimeException("This type of storageType is not supported: " + storageType);
        }
    }

}
