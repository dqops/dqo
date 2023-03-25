/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.scheduler.synchronization;

import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;

/**
 * File synchronization service used by the job scheduler to synchronize the data (after checks were executed) and metadata.
 */
public interface SchedulerFileSynchronizationService {
    /**
     * Synchronizes the whole user home, both the metadata (checks, rules, sensors) and the parquet data files. Should be called in the job that updates the metadata.
     *
     * @param synchronizationReportingMode File system synchronization mode.
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    boolean synchronizeAll(FileSystemSynchronizationReportingMode synchronizationReportingMode, boolean forceRefreshNativeTable);

    /**
     * Synchronizes only the data files (parquet files). Should be called in the job that executes the data quality checks.
     *
     * @param synchronizationReportingMode File system synchronization mode.
     * @param forceRefreshNativeTable True when the native table should be forcibly refreshed even if there are no changes.
     * @return true when synchronization was successful, false - when it failed, no API Key was provided or the cloud synchronization is simply disabled
     */
    boolean synchronizeData(FileSystemSynchronizationReportingMode synchronizationReportingMode, boolean forceRefreshNativeTable);
}
