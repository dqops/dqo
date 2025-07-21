/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
        this.connectionName = connectionName;
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
