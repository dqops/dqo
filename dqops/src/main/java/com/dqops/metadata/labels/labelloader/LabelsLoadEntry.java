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

package com.dqops.metadata.labels.labelloader;

/**
 * Object that stores the information of a queued or an ongoing refresh of labels for a table or a connection.
 */
public final class LabelsLoadEntry {
    private LabelRefreshKey key;
    private final Object lock = new Object();
    private LabelRefreshStatus status;

    /**
     * Creates a new entry.
     * @param key Key that identifies the table.
     * @param status Current status.
     */
    public LabelsLoadEntry(LabelRefreshKey key, LabelRefreshStatus status) {
        this.key = key;
        this.status = status;
    }

    /**
     * Returns the key that identifies the table or connection.
     * @return Key.
     */
    public LabelRefreshKey getKey() {
        return key;
    }

    /**
     * Returns the current status of the entry - is it loading or loaded.
     * @return Current status.
     */
    public LabelRefreshStatus getStatus() {
        synchronized (this.lock) {
            return status;
        }
    }

    /**
     * Sets the new status.
     * @param status New status.
     */
    public void setStatus(LabelRefreshStatus status) {
        synchronized (this.lock) {
            this.status = status;
        }
    }
}
