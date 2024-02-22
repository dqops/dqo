package com.dqops.connectors.duckdb;

import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.JsonSerializerImpl;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class DuckdbSecretManager {

    private static DuckdbSecretManager secretsManager;
    private final Set<HashCode> secrets;

    public DuckdbSecretManager() {
        secrets = new HashSet<HashCode>();
    }

    public static synchronized DuckdbSecretManager getInstance() {
        if (secretsManager == null) {
            secretsManager = new DuckdbSecretManager();
        }
        return secretsManager;
    }

    public synchronized void ensureCreated(ConnectionSpec connectionSpec, AbstractJdbcSourceConnection sourceConnection){
        HashCode secretHash = calculateHash64(connectionSpec);
        if(secrets.contains(secretHash)){
            return;
        }
        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, secretHash);
        sourceConnection.executeQuery(createSecretQuery, JobCancellationToken.createDummyJobCancellationToken(), null, false);
        secrets.add(secretHash);
    }

    public static HashCode calculateHash64(ConnectionSpec connectionSpec) {
        JsonSerializer jsonSerializer = new JsonSerializerImpl();
        String serialized = jsonSerializer.serialize(connectionSpec);
        HashFunction hashFunction = Hashing.farmHashFingerprint64();
        HashCode hash = hashFunction.hashString(serialized, StandardCharsets.UTF_8);
        return hash;
    }

}
