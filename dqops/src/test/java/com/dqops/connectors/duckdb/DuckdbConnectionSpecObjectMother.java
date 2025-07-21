/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.duckdb;

import com.dqops.connectors.ProviderType;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.config.DuckdbReadMode;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.storage.azure.AzureAuthenticationMode;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable DuckDB connection spec that provides access to the database.
 */
public class DuckdbConnectionSpecObjectMother {

    /**
     * Creates a default connection spec to DuckDB that works on files placed in memory.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForInMemory() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.duckdb);
			setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.in_memory);
            }});
        }};

        return connectionSpec;
    }

    /**
     * Creates a default connection spec to DuckDB.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForFiles(DuckdbFilesFormatType duckdbFilesFormatType) {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.duckdb);
            setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.in_memory);
                setFilesFormatType(duckdbFilesFormatType);
                setStorageType(DuckdbStorageType.local);
            }});
        }};

        return connectionSpec;
    }

    /**
     * Creates a default connection spec to DuckDB with s3 setup.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForFilesOnS3(DuckdbFilesFormatType duckdbFilesFormatType) {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.duckdb);
            setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.in_memory);
                setFilesFormatType(duckdbFilesFormatType);
                setStorageType(DuckdbStorageType.s3);
                setUser("aws_example_key_id");
                setPassword("aws_example_secret");
                setRegion("eu-central-1");
            }});
        }};

        return connectionSpec;
    }

    /**
     * Creates a default connection spec to DuckDB with Azure setup.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForFilesOnAzure(DuckdbFilesFormatType duckdbFilesFormatType,
                                                       AzureAuthenticationMode azureAuthenticationMode) {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.duckdb);
            setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.in_memory);
                setFilesFormatType(duckdbFilesFormatType);
                setStorageType(DuckdbStorageType.azure);
                setAzureAuthenticationMode(azureAuthenticationMode);
            }});
        }};

        return connectionSpec;
    }

    /**
     * Creates a default connection spec to DuckDB with Google Cloud Storage setup.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForFilesOnGoogle(DuckdbFilesFormatType duckdbFilesFormatType) {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.duckdb);
            setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.in_memory);
                setFilesFormatType(duckdbFilesFormatType);
                setStorageType(DuckdbStorageType.gcs);
                setUser("gcs_example_key_id");
                setPassword("gcs_example_secret");
            }});
        }};

        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable DuckDB database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "main";
    }

}
