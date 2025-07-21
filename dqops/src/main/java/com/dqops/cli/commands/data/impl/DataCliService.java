/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.cli.commands.data.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;

/**
 * Service handling operations related to stored data from CLI.
 */
public interface DataCliService {

    /**
     * Delete data stored in .data folder (check results, sensor readouts, etc.).
     * @param deleteStoredDataQueueJobParameters Parameters including filters that narrow the scope of operation. Connection name is required.
     * @return CliOperationStatus to display in CLI.
     */
    CliOperationStatus deleteStoredData(DeleteStoredDataQueueJobParameters deleteStoredDataQueueJobParameters);
}
