package ai.dqo.connectors.bigquery;

/**
 * Runs 'gcloud auth application-default login' to log in the user to GCP.
 */
public interface GCloudLoginService {
    /**
     * Starts gcloud auth application-default login to log in the user to GCP and generate a local credentials.
     * This operations halts the execution and waits until the "gcloud auth application-default login" finishes.
     *
     * @return True when it was a success, false otherwise.
     */
    boolean authenticateUserForApplicationDefaultCredentials();
}
