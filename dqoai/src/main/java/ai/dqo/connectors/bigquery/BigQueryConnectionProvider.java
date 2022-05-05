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

import ai.dqo.cli.exceptions.CliRequiredParameterMissingException;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.AbstractSqlConnectionProvider;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.metadata.sources.ColumnTypeSnapshotSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.UserCredentials;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.columns.Column;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Big query provider.
 */
@Component("bigquery-provider")
@Scope("singleton")
public class BigQueryConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final ProviderDialectSettings dialectSettings = new ProviderDialectSettings("`", "`", "``", false);

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     */
    @Autowired
    public BigQueryConnectionProvider(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a connection to a target data source.
     *
     * @param connectionSpec Connection specification.
     * @param openConnection Open the connection after creating.
     * @return Connection object.
     */
    @Override
    public BigQuerySourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection) {
        BigQuerySourceConnection connection = this.beanFactory.getBean(BigQuerySourceConnection.class);
        connection.setConnectionSpec(connectionSpec);
        if (openConnection) {
            connection.open();
        }
        return connection;
    }

    /**
     * Returns the dialect settings for the provider. The dialect settings has information about supported SQL features, identifier quoting, etc.
     *
     * @param connectionSpec Connection specification if the settings are database version specific.
     * @return Provider dialect settings.
     */
    @Override
    public ProviderDialectSettings getDialectSettings(ConnectionSpec connectionSpec) {
        return this.dialectSettings;
    }

    /**
     * Tries to retrieve the current (billing) GCP project from the application default credentials. Maybe the user has already
     * run gcloud auth application-default login
     * @return Default GCP project.
     */
    public static String tryGetCurrentGcpProject() {
        try {
            GoogleCredentials defaultCredentials = GoogleCredentials.getApplicationDefault();
            if (defaultCredentials instanceof UserCredentials) {
                UserCredentials userCredentials = (UserCredentials)defaultCredentials;
                return userCredentials.getQuotaProjectId();
            }
            if(defaultCredentials instanceof ServiceAccountCredentials) {
                ServiceAccountCredentials serviceAccountCredentials = (ServiceAccountCredentials) defaultCredentials;
                return serviceAccountCredentials.getQuotaProjectId();
            }

            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }

    /**
     * Delegates the connection configuration to the provider.
     *
     * @param connectionSpec Connection specification to fill.
     * @param isHeadless     When true and some required parameters are missing then throws an exception {@link CliRequiredParameterMissingException},
     *                       otherwise prompts the user to fill the answer.
     * @param terminalReader Terminal reader that may be used to prompt the user.
     * @param terminalWriter Terminal writer that should be used to write any messages.
     */
    @Override
    public void promptForConnectionParameters(ConnectionSpec connectionSpec, boolean isHeadless, TerminalReader terminalReader, TerminalWriter terminalWriter) {
        HashMap<String, String> connectionProperties = connectionSpec.getProperties();
        BigQueryParametersSpec bigquerySpec = connectionSpec.getBigquery();
        if (bigquerySpec == null) {
            bigquerySpec = new BigQueryParametersSpec();
            connectionSpec.setBigquery(bigquerySpec);
        }

        if (connectionProperties.containsKey("bigquery-source-project-id")) {
            bigquerySpec.setSourceProjectId(connectionProperties.get("bigquery-source-project-id"));
            connectionProperties.remove("bigquery-source-project-id");
        }
        if (Strings.isNullOrEmpty(bigquerySpec.getSourceProjectId())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=bigquery-source-project-id");
            }

            String sourceProjectId = tryGetCurrentGcpProject();
            String defaultGcpProject = sourceProjectId != null ? sourceProjectId : "${GCP_PROJECT}";
            bigquerySpec.setSourceProjectId(terminalReader.prompt("Source GCP project ID (-P=bigquery-source-project-id)", defaultGcpProject, false));
        }

        if (connectionProperties.containsKey("bigquery-billing-project-id")) {
            bigquerySpec.setBillingProjectId(connectionProperties.get("bigquery-billing-project-id"));
            connectionProperties.remove("bigquery-billing-project-id");
        }
        if (Strings.isNullOrEmpty(bigquerySpec.getBillingProjectId())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=bigquery-billing-project-id");
            }

            String billingProjectId = tryGetCurrentGcpProject();
            String defaultGcpProject = billingProjectId != null ? billingProjectId : "${GCP_PROJECT}";
            bigquerySpec.setBillingProjectId(terminalReader.prompt("Billing GCP project ID (-P=bigquery-billing-project-id)", defaultGcpProject, false));
        }

        if (connectionProperties.containsKey("bigquery-authentication-mode")) {
            String authenticationModeText = connectionProperties.get("bigquery-authentication-mode");
            try {
                BigQueryAuthenticationMode bigQueryAuthenticationMode = BigQueryAuthenticationMode.valueOf(authenticationModeText);
                bigquerySpec.setAuthenticationMode(bigQueryAuthenticationMode);
            }
            catch (Exception ex) {
                throw new CliRequiredParameterMissingException("-P=bigquery-authentication-mode");  // TODO: we could throw a specific error that tells the user that the value was incorrect
            }
            connectionProperties.remove("bigquery-authentication-mode");
        }
        if (bigquerySpec.getAuthenticationMode() == null) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=bigquery-authentication-mode");
            }

            BigQueryAuthenticationMode authenticationMode = terminalReader.promptEnum("GCP Authentication Mode", BigQueryAuthenticationMode.class, BigQueryAuthenticationMode.google_application_credentials, false);
            bigquerySpec.setAuthenticationMode(authenticationMode);
        }

        if (connectionProperties.containsKey("bigquery-json-key-content")) {
            bigquerySpec.setJsonKeyContent(connectionProperties.get("bigquery-json-key-content"));
            connectionProperties.remove("bigquery-json-key-content");
        }
        if (bigquerySpec.getAuthenticationMode() == BigQueryAuthenticationMode.json_key_content &&
                Strings.isNullOrEmpty(bigquerySpec.getJsonKeyContent())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=bigquery-json-key-content");
            }

            bigquerySpec.setJsonKeyContent(terminalReader.prompt("JSON key content (-P=bigquery-json-key-content)", "${JSON_KEY_CONTENT}", false));
        }

        if (connectionProperties.containsKey("bigquery-json-key-path")) {
            bigquerySpec.setJsonKeyPath(connectionProperties.get("bigquery-json-key-path"));
            connectionProperties.remove("bigquery-json-key-path");
        }
        if (bigquerySpec.getAuthenticationMode() == BigQueryAuthenticationMode.json_key_path &&
                Strings.isNullOrEmpty(bigquerySpec.getJsonKeyPath())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=bigquery-json-key-path");
            }

            bigquerySpec.setJsonKeyPath(terminalReader.prompt("JSON key path (-P=bigquery-json-key-path)", "${GOOGLE_APPLICATION_CREDENTIALS}", false));
        }

        if (connectionProperties.containsKey("bigquery-quota-project-id")) {
            bigquerySpec.setQuotaProjectId(connectionProperties.get("bigquery-quota-project-id"));
            connectionProperties.remove("bigquery-quota-project-id");
        }
        if (Strings.isNullOrEmpty(bigquerySpec.getQuotaProjectId())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("-P=bigquery-quota-project-id");
            }

            bigquerySpec.setQuotaProjectId(terminalReader.prompt("GCP quota (billing) project ID (-P=bigquery-quota-project-id)", bigquerySpec.getBillingProjectId(), true));
        }
    }

    /**
     * Proposes a physical (provider specific) column type that is able to store the data of the given Tablesaw column.
     *
     * @param dataColumn Tablesaw column with data that should be stored.
     * @return Column type snapshot.
     */
    @Override
    public ColumnTypeSnapshotSpec proposePhysicalColumnType(Column<?> dataColumn) {
        ColumnType columnType = dataColumn.type();

        if (columnType == ColumnType.SHORT) {
            return new ColumnTypeSnapshotSpec("SMALLINT");
        }
        else if (columnType == ColumnType.INTEGER) {
            return new ColumnTypeSnapshotSpec("INTEGER");
        }
        else if (columnType == ColumnType.LONG) {
            return new ColumnTypeSnapshotSpec("BIGINT");
        }
        else if (columnType == ColumnType.FLOAT) {
            return new ColumnTypeSnapshotSpec("FLOAT64");
        }
        else if (columnType == ColumnType.BOOLEAN) {
            return new ColumnTypeSnapshotSpec("BOOL");
        }
        else if (columnType == ColumnType.STRING) {
            return new ColumnTypeSnapshotSpec("STRING");
        }
        else if (columnType == ColumnType.DOUBLE) {
            return new ColumnTypeSnapshotSpec("FLOAT64");
        }
        else if (columnType == ColumnType.LOCAL_DATE) {
            return new ColumnTypeSnapshotSpec("DATE");
        }
        else if (columnType == ColumnType.LOCAL_TIME) {
            return new ColumnTypeSnapshotSpec("TIME");
        }
        else if (columnType == ColumnType.LOCAL_DATE_TIME) {
            return new ColumnTypeSnapshotSpec("DATETIME");
        }
        else if (columnType == ColumnType.INSTANT) {
            return new ColumnTypeSnapshotSpec("TIMESTAMP");
        }
        else if (columnType == ColumnType.TEXT) {
            return new ColumnTypeSnapshotSpec("STRING");
        }
        else {
            throw new NoSuchElementException("Unsupported column type: " + columnType.name());
        }
    }
}
