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

}
