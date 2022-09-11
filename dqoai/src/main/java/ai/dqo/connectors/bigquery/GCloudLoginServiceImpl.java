package ai.dqo.connectors.bigquery;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Runs 'gcloud auth application-default login' to log in the user to GCP.
 */
@Component
public class GCloudLoginServiceImpl implements GCloudLoginService {
    private static final Logger LOG = LoggerFactory.getLogger(GCloudLoginServiceImpl.class);

    /**
     * Starts gcloud auth application-default login to log in the user to GCP and generate a local credentials.
     * This operation halts the execution and waits until the "gcloud auth application-default login" finishes.
     * @return True when it was a success, false otherwise.
     */
    @Override
    public boolean authenticateUserForApplicationDefaultCredentials() {
        try {
            CommandLine commandLine = CommandLine.parse("gcloud auth application-default login");
            DefaultExecutor executor = new DefaultExecutor();
            int result = executor.execute(commandLine);
            return result == 0;
        }
        catch (Exception ex) {
            LOG.error("Failed to call 'gcloud auth application-default login' because " + ex.getMessage(), ex);
            return false;
        }
    }
}
