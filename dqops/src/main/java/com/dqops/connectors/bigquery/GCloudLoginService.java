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
