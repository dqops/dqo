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

package com.dqops.core.scheduler.synchronize;

/**
 * Enumeration of synchronization modes (selection of folders that are synchronized to the DQOps Cloud) during a scheduled synchronization.
 */
public enum ScheduledSynchronizationFolderSelectionMode {
    /**
     * Always synchronizes all folders, possibly downloading remote changes from the DQOps Cloud.
     */
    all,

    /**
     * Only synchronize folders with local changes.
     */
    locally_changed
}
