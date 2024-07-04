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
package com.dqops.core.secrets;

/**
 * Static class with constants: secret names to retrieve various configuration properties from the Secret Manager.
 */
public class DevelopmentCredentialsSecretNames {
    /**
     * Property to retrieve the BigQuery GCP project ID.
     */
    public static final String BIGQUERY_PROJECT = "${sm://bigquery-project}";

    /**
     * Property to retrieve the BigQuery testable dataset.
     */
    public static final String BIGQUERY_DATASET = "${sm://bigquery-dataset}";

    /**
     * Property to retrieve the Snowflake account name.
     */
    public static final String SNOWFLAKE_ACCOUNT = "${sm://snowflake-account}";

    /**
     * Property to retrieve the Snowflake database name.
     */
    public static final String SNOWFLAKE_DATABASE = "${sm://snowflake-database}";

    /**
     * Property to retrieve the Snowflake warehouse name.
     */
    public static final String SNOWFLAKE_WAREHOUSE = "${sm://snowflake-warehouse}";

    /**
     * Property to retrieve the Snowflake user name.
     */
    public static final String SNOWFLAKE_USER = "${sm://snowflake-user}";

    /**
     * Property to retrieve the Snowflake password.
     */
    public static final String SNOWFLAKE_PASSWORD = "${sm://snowflake-password}";

    /**
     * Secret name that holds the api key.
     */
    public static final String TESTABLE_API_KEY = "${sm://testable-api-key}";

    /**
     * Property to retrieve the Redshift host.
     */
    public static final String REDSHIFT_HOST = "${sm://redshift-host}";

    /**
     * Property to retrieve the Redshift port.
     */
    public static final String REDSHIFT_PORT = "${sm://redshift-port}";

    /**
     * Property to retrieve the Redshift database.
     */
    public static final String REDSHIFT_DATABASE = "${sm://redshift-database}";

    /**
     * Property to retrieve the Redshift user name.
     */
    public static final String REDSHIFT_USERNAME = "${sm://redshift-username}";

    /**
     * Property to retrieve the Redshift user password.
     */
    public static final String REDSHIFT_PASSWORD = "${sm://redshift-password}";

    /**
     * Property to retrieve the Azure Blob Storage connection string.
     */
    public static final String AZURE_STORAGE_CONNECTION_STRING = "${sm://azure-storage-connection-string}";

    /**
     * Property to retrieve the AWS Access Key ID to testing storage.
     */
    public static final String AWS_S3_ACCESS_KEY_ID = "${sm://aws-s3-access-key-id}";

    /**
     * Property to retrieve the AWS Secret Access Key to testing storage.
     */
    public static final String AWS_S3_SECRET_ACCESS_KEY = "${sm://aws-s3-secret-access-key}";

    /**
     * Property to retrieve the AWS Region to testing storage.
     */
    public static final String AWS_S3_REGION = "${sm://aws-s3-region}";

    /**
     * Property to retrieve the Azure Service Principal Tenant ID
     */
    public static final String AZURE_SERVICE_PRINCIPAL_TENANT_ID = "${sm://azure-service-principal-tenant-id}";

    /**
     * Property to retrieve the Azure Service Principal Client ID
     */
    public static final String AZURE_SERVICE_PRINCIPAL_CLIENT_ID = "${sm://azure-service-principal-client-id}";

    /**
     * Property to retrieve the Azure Service Principal Client Secret
     */
    public static final String AZURE_SERVICE_PRINCIPAL_CLIENT_SECRET = "${sm://azure-service-principal-client-secret}";

    /**
     * Property to retrieve the Azure Storage Account name
     */
    public static final String AZURE_STORAGE_ACCOUNT_NAME = "${sm://azure-storage-account-name}";

    /**
     * Property to retrieve the Interoperability access key for dqo-ai-testing gcp bucket
     */
    public static final String GCS_DQO_AI_TESTING_INTEROPERABILITY_ACCESS_KEY = "${sm://gcs-dqo-ai-testing-interoperability-access-key}";

    /**
     * Property to retrieve the Interoperability secret for dqo-ai-testing gcp bucket
     */
    public static final String GCS_DQO_AI_TESTING_INTEROPERABILITY_SECRET = "${sm://gcs-dqo-ai-testing-interoperability-secret}";

    /**
     * Property to retrieve the Azure Databricks host
     */
    public static final String AZURE_DATABRICKS_HOST = "${sm://azure-databricks-host}";

    /**
     * Property to retrieve the Azure Databricks http path
     */
    public static final String AZURE_DATABRICKS_HTTP_PATH = "${sm://azure-databricks-http-path}";

    /**
     * Property to retrieve the Azure Databricks access token
     */
    public static final String AZURE_DATABRICKS_ACCESS_TOKEN = "${sm://azure-databricks-access-token}";

}
