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

package com.dqops.data.checkresults.statuscache;

import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;

/**
 * Object that stores the current table status for a table. It also knows when the status was loaded, if it was loaded,
 * or a load was queued.
 */
public class CurrentTableStatusCacheEntry {
    private CurrentTableStatusKey key;
    private volatile CurrentTableStatusEntryStatus status;
    private volatile TableCurrentDataQualityStatusModel statusModel;

    /**
     * Creates a new entry.
     * @param key Key that identifies the table.
     * @param status Current status.
     */
    public CurrentTableStatusCacheEntry(CurrentTableStatusKey key, CurrentTableStatusEntryStatus status) {
        this.key = key;
        this.status = status;
    }

    /**
     * Returns the key that identifies the table.
     * @return Key.
     */
    public CurrentTableStatusKey getKey() {
        return key;
    }

    /**
     * Returns the current status of the entry - is it loading or loaded.
     * @return Current status.
     */
    public CurrentTableStatusEntryStatus getStatus() {
        return status;
    }

    /**
     * Sets the new status.
     * @param status New status.
     */
    public void setStatus(CurrentTableStatusEntryStatus status) {
        this.status = status;
    }

    /**
     * Returns the current model with the table status.
     * @return Current table status.
     */
    public TableCurrentDataQualityStatusModel getStatusModel() {
        return statusModel;
    }

    /**
     * Sets the current model. If it is not empty, the status is also changed to loaded.
     * @param statusModel Status model.
     */
    public void setStatusModel(TableCurrentDataQualityStatusModel statusModel) {
        this.statusModel = statusModel;
        this.status = CurrentTableStatusEntryStatus.LOADED;
    }
}
