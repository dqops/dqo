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
