package ai.dqo.connectors.bigquery;

import com.google.cloud.bigquery.BigQuery;

/**
 * Internal BigQuery connection object that stores both the BigQuery service instance and the GCP project names used to execute jobs.
 */
public class BigQueryInternalConnection {
    private BigQuery bigQueryClient;
    private String billingProjectId;
    private String quotaProjectId;

    /**
     * Creates a holder of a BigQuery client.
     * @param bigQueryClient Big query client instance.
     * @param billingProjectId GCP project id where the jobs are started.
     * @param quotaProjectId GCP quota project id.
     */
    public BigQueryInternalConnection(BigQuery bigQueryClient, String billingProjectId, String quotaProjectId) {
        this.bigQueryClient = bigQueryClient;
        this.billingProjectId = billingProjectId;
        this.quotaProjectId = quotaProjectId;
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
