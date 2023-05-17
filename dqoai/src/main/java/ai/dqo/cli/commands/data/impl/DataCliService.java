/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.cli.commands.data.impl;

import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;

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
