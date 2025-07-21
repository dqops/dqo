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

import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.JsonSerializerImpl;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import tech.tablesaw.api.Table;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * DuckDB secret manager that handles of the secrets creation in DuckDB.
 */
@Slf4j
public class DuckdbSecretManager {

    private static DuckdbSecretManager secretsManager;

    public DuckdbSecretManager() {
    }

    /**
     * Returns a static shared instance of the DuckDB secrets manager.
     * @return DuckDB secrets manager.
     */
    public static synchronized DuckdbSecretManager getInstance() {
        if (secretsManager == null) {
            secretsManager = new DuckdbSecretManager();
        }
        return secretsManager;
    }

    /**
     * Creates a secret for a connection spec.
     *
     * @param connectionSpec Connection spec with credential details.
     * @param sourceConnection The source connection that executes a secret creation query.
     */
    public synchronized void createSecrets(ConnectionSpec connectionSpec, AbstractJdbcSourceConnection sourceConnection){
        DuckdbParametersSpec duckdb = connectionSpec.getDuckdb();

        List<String> scopes = duckdb.getScopes();
        scopes.forEach(scope -> {

            String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, scope);
            try {
                Table tableResult = sourceConnection.executeQuery(createSecretQuery, JobCancellationToken.createDummyJobCancellationToken(), null, false);
                Boolean success = (Boolean) tableResult.column(tableResult.columnIndex("success")).get(0);
                if(!success){
                    log.error("Creation of a new DuckDB secret key for storage " + connectionSpec.getDuckdb().getStorageType() + " failed !");
                }
            } catch (Exception e){
                if(!e.getMessage().contains("already exists")) { // Temporary secret with name xxx already exists
                    log.error("Cannot create a secret for the " + connectionSpec.getConnectionName() + " connection.");
                }
            }
        });
    }

    /**
     * Calculates a 64-bit hash of all the files.
     * @return 64-bit farm hash.
     */
    public static HashCode calculateHash64(ConnectionSpec connectionSpec) {
        JsonSerializer jsonSerializer = new JsonSerializerImpl();
        String serialized = jsonSerializer.serialize(connectionSpec);
        HashFunction hashFunction = Hashing.farmHashFingerprint64();
        Thread thread = Thread.currentThread();
        if(thread != null){
            String threadName = Thread.currentThread().getName();
            HashCode hash = hashFunction.hashString(threadName + serialized, StandardCharsets.UTF_8);
            return hash;
        } else {
            HashCode hash = hashFunction.hashString(serialized, StandardCharsets.UTF_8);
            return hash;
        }
    }

}
