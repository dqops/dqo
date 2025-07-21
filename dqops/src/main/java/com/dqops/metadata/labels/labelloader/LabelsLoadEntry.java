/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
