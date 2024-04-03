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

import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.AbstractSqlConnectionProvider;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.UserCredentials;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.columns.Column;

import java.util.NoSuchElementException;

/**
 * Big query provider.
 */
@Component("bigquery-provider")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BigQueryConnectionProvider extends AbstractSqlConnectionProvider {
    private final BeanFactory beanFactory;
    private final BigQueryProviderDialectSettings dialectSettings;

    /**
     * Injection constructor.
     * @param beanFactory Bean factory used to create the connection.
     * @param dialectSettings Dialect settings.
     */
    @Autowired
    public BigQueryConnectionProvider(BeanFactory beanFactory,
                                      BigQueryProviderDialectSettings dialectSettings) {
        this.beanFactory = beanFactory;
        this.dialectSettings = dialectSettings;
    }

    /**
     * Creates a connection to a target data source.
     *
     * @param connectionSpec Connection specification.
     * @param openConnection Open the connection after creating.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Connection object.
     */
    @Override
    public BigQuerySourceConnection createConnection(ConnectionSpec connectionSpec,
                                                     boolean openConnection,
                                                     SecretValueLookupContext secretValueLookupContext) {
        BigQuerySourceConnection connection = this.beanFactory.getBean(BigQuerySourceConnection.class);
        connection.setConnectionSpec(connectionSpec);
        if (openConnection) {
            connection.open(secretValueLookupContext);
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
     * Formats a constant for the target database.
     *
     * @param constant   Constant to be formatted.
     * @param columnType Column type snapshot.
     * @return Formatted constant.
     */
    @Override
    public String formatConstant(Object constant, ColumnTypeSnapshotSpec columnType) {
        if(constant instanceof Boolean){
            Boolean asBoolean = (Boolean)constant;
            return asBoolean ? "true" : "false";
        }
        return super.formatConstant(constant, columnType);
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
        BigQueryParametersSpec bigquerySpec = connectionSpec.getBigquery();
        if (bigquerySpec == null) {
            bigquerySpec = new BigQueryParametersSpec();
            connectionSpec.setBigquery(bigquerySpec);
        }

        if (Strings.isNullOrEmpty(bigquerySpec.getSourceProjectId())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--bigquery-source-project-id\"");
            }

            String sourceProjectId = tryGetCurrentGcpProject();
            String defaultGcpProject = sourceProjectId != null ? sourceProjectId : "${GCP_PROJECT}";
            bigquerySpec.setSourceProjectId(terminalReader.prompt("Source GCP project ID (--bigquery-source-project-id\")", defaultGcpProject, false));
        }

        if (bigquerySpec.getJobsCreateProject() == null || bigquerySpec.getJobsCreateProject() == BigQueryJobsCreateProject.create_jobs_in_source_project) {
            if (bigquerySpec.getJobsCreateProject() == null && isHeadless) {
                throw new CliRequiredParameterMissingException("--bigquery-jobs-create-project");
            }

            BigQueryJobsCreateProject jobsCreateProject = terminalReader.promptEnum("GCP project with bigquery.jobs.create permission selection mode (--bigquery-jobs-create-project)",
                    BigQueryJobsCreateProject.class, BigQueryJobsCreateProject.create_jobs_in_selected_billing_project_id, false);
            bigquerySpec.setJobsCreateProject(jobsCreateProject);
        }

        if (bigquerySpec.getJobsCreateProject() == BigQueryJobsCreateProject.create_jobs_in_selected_billing_project_id && Strings.isNullOrEmpty(bigquerySpec.getBillingProjectId())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--bigquery-billing-project-id");
            }

            String billingProjectId = tryGetCurrentGcpProject();
            String defaultProjectMessage = billingProjectId != null ? " (" + billingProjectId + ")" : "";
            bigquerySpec.setBillingProjectId(terminalReader.prompt("Billing GCP project ID (--bigquery-billing-project-id), leave null to use the default GCP project from credentials" + defaultProjectMessage, null, true));
        }

        if (bigquerySpec.getAuthenticationMode() == null || bigquerySpec.getAuthenticationMode() == BigQueryAuthenticationMode.google_application_credentials) {
            if (bigquerySpec.getAuthenticationMode() == null && isHeadless) {
                throw new CliRequiredParameterMissingException("--bigquery-authentication-mode");
            }

            BigQueryAuthenticationMode authenticationMode = terminalReader.promptEnum("GCP Authentication Mode (--bigquery-authentication-mode)", BigQueryAuthenticationMode.class, BigQueryAuthenticationMode.google_application_credentials, false);
            bigquerySpec.setAuthenticationMode(authenticationMode);
        }

        if (bigquerySpec.getAuthenticationMode() == BigQueryAuthenticationMode.google_application_credentials &&
                bigquerySpec.getJobsCreateProject() == BigQueryJobsCreateProject.create_jobs_in_default_project_from_credentials) {
            // checking if the default credentials are present
            String billingProjectId = tryGetCurrentGcpProject();
            if (billingProjectId == null) {
                // the credentials are not present, we can ask the user to perform a login
                if (terminalReader.promptBoolean("Default GCP credentials are not available, run 'gcloud auth application-default login' to log in to GCP?", true)) {
                    GCloudLoginService gCloudLoginService = this.beanFactory.getBean(GCloudLoginService.class);
                    gCloudLoginService.authenticateUserForApplicationDefaultCredentials();
                }
            }
        }

        if (bigquerySpec.getAuthenticationMode() == BigQueryAuthenticationMode.json_key_content &&
                Strings.isNullOrEmpty(bigquerySpec.getJsonKeyContent())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--bigquery-json-key-content");
            }

            bigquerySpec.setJsonKeyContent(terminalReader.prompt("JSON key content (--bigquery-json-key-content)", "${JSON_KEY_CONTENT}", false));
        }

        if (bigquerySpec.getAuthenticationMode() == BigQueryAuthenticationMode.json_key_path &&
                Strings.isNullOrEmpty(bigquerySpec.getJsonKeyPath())) {
            if (isHeadless) {
                throw new CliRequiredParameterMissingException("--bigquery-json-key-path");
            }

            bigquerySpec.setJsonKeyPath(terminalReader.prompt("JSON key path (--bigquery-json-key-path)", "${GOOGLE_APPLICATION_CREDENTIALS}", false));
        }

//        if (Strings.isNullOrEmpty(bigquerySpec.getQuotaProjectId())) {
//            if (isHeadless) {
//                throw new CliRequiredParameterMissingException("--bigquery-quota-project-id");
//            }
//
//            String billingProjectId = bigquerySpec.getBillingProjectId() != null ? bigquerySpec.getBillingProjectId() : tryGetCurrentGcpProject();
//            String defaultProjectMessage = billingProjectId != null ? " (" + billingProjectId + ")" : "";
//            bigquerySpec.setQuotaProjectId(terminalReader.prompt("GCP quota (billing) project ID (--bigquery-quota-project-id), leave blank to use the default GCP project from credentials" + defaultProjectMessage, null, true));
//        }
    }

    /**
     * Proposes a physical (provider specific) column type that is able to store the data of the given Tablesaw column.
     *
     * @param connectionSpec Connection specification if the settings are database version specific.
     * @param dataColumn Tablesaw column with data that should be stored.
     * @return Column type snapshot.
     */
    @Override
    public ColumnTypeSnapshotSpec proposePhysicalColumnType(ConnectionSpec connectionSpec, Column<?> dataColumn) {
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
