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
import java.util.HashSet;
import java.util.Set;

/**
 * DuckDB secret manager that handles of the secrets creation in DuckDB.
 */
@Slf4j
public class DuckdbSecretManager {

    private static DuckdbSecretManager secretsManager;
    private final Set<HashCode> secrets;

    public DuckdbSecretManager() {
        secrets = new HashSet<HashCode>();
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
     * Creates a secret for a connection spec which secret has not been created yet or when the connection spec has changed.
     *
     * @param connectionSpec Connection spec which hash is compared for the creation of a new secret.
     * @param sourceConnection The source connection that executes a query with that creates a new secret.
     */
    public synchronized void ensureCreated(ConnectionSpec connectionSpec, AbstractJdbcSourceConnection sourceConnection){
        HashCode secretHash = calculateHash64(connectionSpec);
        if(secrets.contains(secretHash)){
            return;
        }
        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, secretHash);
        Table tableResult = sourceConnection.executeQuery(createSecretQuery, JobCancellationToken.createDummyJobCancellationToken(), null, false);
        Boolean success = (Boolean) tableResult.column(tableResult.columnIndex("success")).get(0);
        if(success){
            secrets.add(secretHash);
        } else {
            log.error("Creation of a new DuckDB secret key for storage " + connectionSpec.getDuckdb().getStorageType() + " failed !");
        }
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
