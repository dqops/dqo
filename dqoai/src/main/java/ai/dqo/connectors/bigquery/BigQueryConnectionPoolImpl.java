/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors.bigquery;

import ai.dqo.connectors.ConnectorOperationFailedException;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.jdbc.JdbConnectionPoolCreateException;
import ai.dqo.metadata.sources.ConnectionSpec;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.common.base.MoreObjects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * BigQuery connection pool that supports multiple connections.
 */
@Component
@Scope("singleton")
public class BigQueryConnectionPoolImpl implements BigQueryConnectionPool {
    /**
     * Data sources cache.
     */
    private final Cache<ConnectionSpec, BigQuery> bigQueryServiceCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(1, TimeUnit.HOURS) // after 1h, we want to re-authenticate
                    .build();

    /**
     * Returns or creates a BigQuery service for the given connection specification.
     * @param connectionSpec Connection specification (should be not mutable).
     * @return BigQuery service.
     */
    public BigQuery getBigQueryService(ConnectionSpec connectionSpec) {
        assert connectionSpec != null;
        assert connectionSpec.getProviderType() == ProviderType.bigquery;
        assert connectionSpec.getBigquery() != null;

        try {
            final ConnectionSpec clonedConnectionSpec = connectionSpec.clone();
            return this.bigQueryServiceCache.get(clonedConnectionSpec, () -> createBigQueryService(clonedConnectionSpec));
        } catch (ExecutionException e) {
            throw new JdbConnectionPoolCreateException("Cannot create a BigQuery connection for " + connectionSpec.getConnectionName(), e);
        }
    }

    /**
     * Creates a big query service.
     * @param connectionSpec Connection specification for a BigQuery connection.
     * @return Connection specification.
     */
    public BigQuery createBigQueryService(ConnectionSpec connectionSpec) {
        try {
            BigQueryParametersSpec bigQueryParametersSpec = connectionSpec.getBigquery();
            GoogleCredentials googleCredentials = null;
            switch (bigQueryParametersSpec.getAuthenticationMode()) {
                case google_application_credentials:
                    googleCredentials = GoogleCredentials.getApplicationDefault();
                    break;
                case json_key_content:
                    byte[] keyContentBytes = bigQueryParametersSpec.getJsonKeyContent().getBytes(StandardCharsets.UTF_8);
                    try (ByteArrayInputStream contentFileStream = new ByteArrayInputStream(keyContentBytes)) {
                        googleCredentials = GoogleCredentials.fromStream(contentFileStream);
                    }
                    break;
                case json_key_path:
                    String jsonKeyPath = bigQueryParametersSpec.getJsonKeyPath();
                    byte[] jsonKeyBytes = Files.readAllBytes(Path.of(jsonKeyPath));
                    try (ByteArrayInputStream keyFileStream = new ByteArrayInputStream(jsonKeyBytes)) {
                        googleCredentials = GoogleCredentials.fromStream(keyFileStream);
                    }
                    break;
                default:
                    throw new ConnectorOperationFailedException("Unsupported authentication mode for BigQuery connection " + connectionSpec.getConnectionName() + ", mode: " + bigQueryParametersSpec.getAuthenticationMode());
            }

            String defaultProjectFromCredentials = null;

            if (googleCredentials instanceof UserCredentials) {
                UserCredentials userCredentials = (UserCredentials)googleCredentials;
                defaultProjectFromCredentials = userCredentials.getQuotaProjectId();
            }
            if(googleCredentials instanceof ServiceAccountCredentials) {
                ServiceAccountCredentials serviceAccountCredentials = (ServiceAccountCredentials) googleCredentials;
                defaultProjectFromCredentials = serviceAccountCredentials.getQuotaProjectId();
            }

            BigQueryOptions.Builder builder = BigQueryOptions.newBuilder()
                    .setCredentials(googleCredentials)
                    .setProjectId(MoreObjects.firstNonNull(bigQueryParametersSpec.getBillingProjectId(), defaultProjectFromCredentials))
                    .setQuotaProjectId(MoreObjects.firstNonNull(bigQueryParametersSpec.getQuotaProjectId(), defaultProjectFromCredentials));

            BigQueryOptions bigQueryOptions = builder.build();
            BigQuery service = bigQueryOptions.getService();
            return service;
        }
        catch (Exception ex) {
            throw new ConnectorOperationFailedException("Failed to open a BigQuery connection " + connectionSpec.getConnectionName(), ex);
        }
    }
}
