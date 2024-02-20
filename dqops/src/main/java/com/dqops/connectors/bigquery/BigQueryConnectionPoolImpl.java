/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.connectors.bigquery;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.jdbc.JdbConnectionPoolCreateException;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileContent;
import com.dqops.metadata.storage.localfiles.credentials.DefaultCloudCredentialFileNames;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.common.base.MoreObjects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * BigQuery connection pool that supports multiple connections.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BigQueryConnectionPoolImpl implements BigQueryConnectionPool {
    /**
     * Data sources cache.
     */
    private final Cache<BigQueryParametersSpec, BigQueryInternalConnection> bigQueryServiceCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(1, TimeUnit.HOURS) // after 1h, we want to re-authenticate
                    .build();

    /**
     * Returns or creates a BigQuery service for the given connection specification.
     * @param connectionSpec Connection specification (should be not mutable).
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return BigQuery service.
     */
    public BigQueryInternalConnection getBigQueryService(ConnectionSpec connectionSpec, SecretValueLookupContext secretValueLookupContext) {
        assert connectionSpec != null;
        assert connectionSpec.getProviderType() == ProviderType.bigquery;
        assert connectionSpec.getBigquery() != null;

        try {
            final BigQueryParametersSpec clonedBigQueryConnectionSpec = connectionSpec.getBigquery().deepClone();
            final String connectionName = connectionSpec.getConnectionName();
            return this.bigQueryServiceCache.get(clonedBigQueryConnectionSpec, () -> createBigQueryService(
                    clonedBigQueryConnectionSpec, connectionName, secretValueLookupContext));
        } catch (ExecutionException e) {
            throw new JdbConnectionPoolCreateException("Cannot create a BigQuery connection for " + connectionSpec.getConnectionName(), e);
        }
    }

    /**
     * Creates a big query service.
     * @param bigQueryParametersSpec Connection specification for a BigQuery connection.
     * @param connectionName Connection name, used for error reporting.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Connection specification.
     */
    public BigQueryInternalConnection createBigQueryService(BigQueryParametersSpec bigQueryParametersSpec,
                                                            String connectionName,
                                                            SecretValueLookupContext secretValueLookupContext) {
        try {
            GoogleCredentials googleCredentials = null;
            switch (bigQueryParametersSpec.getAuthenticationMode()) {
                case google_application_credentials:
                    SharedCredentialWrapper defaultCredentialsSharedSecret =
                            secretValueLookupContext != null && secretValueLookupContext.getUserHome() != null &&
                            secretValueLookupContext.getUserHome().getCredentials() != null ?
                            secretValueLookupContext.getUserHome().getCredentials()
                            .getByObjectName(DefaultCloudCredentialFileNames.GCP_APPLICATION_DEFAULT_CREDENTIALS_JSON_NAME, true) : null;
                    if (defaultCredentialsSharedSecret != null && defaultCredentialsSharedSecret.getObject() != null) {
                        String keyContent = defaultCredentialsSharedSecret.getObject().getTextContent();

                        if (Objects.equals(keyContent.replace("\r\n", "\n"), DefaultCloudCredentialFileContent.GCP_APPLICATION_DEFAULT_CREDENTIALS_JSON_INITIAL_CONTENT)) {
                            throw new DqoRuntimeException("The .credentials/" + DefaultCloudCredentialFileNames.GCP_APPLICATION_DEFAULT_CREDENTIALS_JSON_NAME +
                                    " file contains default (fake) credentials. Please replace the content of the file with a valid GCP Service Account JSON key.");
                        }

                        try (InputStream keyReaderStream = new ByteArrayInputStream(keyContent.getBytes(StandardCharsets.UTF_8))) {
                            googleCredentials = GoogleCredentials.fromStream(keyReaderStream);
                        }
                    } else {
                        googleCredentials = GoogleCredentials.getApplicationDefault();
                    }
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
                    throw new ConnectorOperationFailedException("Unsupported authentication mode for BigQuery connection " + connectionName +
                            ", mode: " + bigQueryParametersSpec.getAuthenticationMode());
            }

            String defaultProjectFromCredentials = null;

            if (googleCredentials instanceof UserCredentials) {
                UserCredentials userCredentials = (UserCredentials)googleCredentials;
                defaultProjectFromCredentials = userCredentials.getQuotaProjectId();
            }
            if(googleCredentials instanceof ServiceAccountCredentials) {
                ServiceAccountCredentials serviceAccountCredentials = (ServiceAccountCredentials) googleCredentials;
                defaultProjectFromCredentials = serviceAccountCredentials.getProjectId();
            }

            String effectiveJobProjectId = null;
            switch (bigQueryParametersSpec.getJobsCreateProject()) {
                case create_jobs_in_source_project:
                    effectiveJobProjectId = bigQueryParametersSpec.getSourceProjectId();
                    break;

                case create_jobs_in_default_project_from_credentials:
                    effectiveJobProjectId = defaultProjectFromCredentials;
                    break;

                case create_jobs_in_selected_billing_project_id:
                    effectiveJobProjectId = bigQueryParametersSpec.getBillingProjectId();
                    break;

                default:
                    effectiveJobProjectId = bigQueryParametersSpec.getSourceProjectId();
            }

            String effectiveQuotaProjectId =
                    bigQueryParametersSpec.getQuotaProjectId() != null ? bigQueryParametersSpec.getQuotaProjectId() :
                            effectiveJobProjectId != null ? effectiveJobProjectId :
                                    bigQueryParametersSpec.getSourceProjectId(); // fallback - use the source project

            BigQueryOptions.Builder builder = BigQueryOptions.newBuilder()
                    .setCredentials(googleCredentials)
                    .setProjectId(effectiveJobProjectId)
                    .setQuotaProjectId(effectiveQuotaProjectId);

            BigQueryOptions bigQueryOptions = builder.build();
            BigQuery service = bigQueryOptions.getService();

            BigQueryInternalConnection bigQueryInternalConnection = new BigQueryInternalConnection(service, effectiveJobProjectId, effectiveQuotaProjectId);
            return bigQueryInternalConnection;
        }
        catch (Exception ex) {
            throw new ConnectorOperationFailedException("Failed to open a BigQuery connection " + connectionName + ", error: " + ex.getMessage(), ex);
        }
    }
}
