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

import com.google.cloud.bigquery.BigQuery;

/**
 * Internal BigQuery connection object that stores both the BigQuery service instance and the GCP project names used to execute jobs.
 */
public class BigQueryInternalConnection {
    private String connectionName;
    private BigQuery bigQueryClient;
    private String billingProjectId;
    private String quotaProjectId;

    /**
     * Creates a holder of a BigQuery client.
     * @param connectionName Connection name, used for logging.
     * @param bigQueryClient Big query client instance.
     * @param billingProjectId GCP project id where the jobs are started.
     * @param quotaProjectId GCP quota project id.
     */
    public BigQueryInternalConnection(String connectionName, BigQuery bigQueryClient, String billingProjectId, String quotaProjectId) {
        this.bigQueryClient = bigQueryClient;
        this.billingProjectId = billingProjectId;
        this.quotaProjectId = quotaProjectId;
    }

    /**
     * Connection name. Used for logging.
     * @return Connection name (data source name in DQOps).
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Returns a configured BigQuery client instance.
     * @return Big query client instance.
     */
    public BigQuery getBigQueryClient() {
        return bigQueryClient;
    }

    /**
     * Returns the GCP billing project id that is used to run jobs.
     * @return GCP project id where the jobs are started.
     */
    public String getBillingProjectId() {
        return billingProjectId;
    }

    /**
     * Returns the GCP quota project id.
     * @return GCP quota project id.
     */
    public String getQuotaProjectId() {
        return quotaProjectId;
    }
}
