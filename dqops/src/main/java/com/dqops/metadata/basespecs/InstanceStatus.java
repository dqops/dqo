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
package com.dqops.metadata.basespecs;

/**
 * Model instance status: added, transient, modified, deleted. The status indicates the state of a {@link AbstractElementWrapper}.
 */
public enum InstanceStatus {
    /**
     * The object was never loaded (no earlier calls to getSpec method)
     */
    NOT_TOUCHED,

    /**
     * The object was requested in the getSpec operation, but is during loading.
     */
    LOAD_IN_PROGRESS,

    /**
     * The object was added and will be written as a new file during flush.
     */
    ADDED,

    /**
     * The object was loaded from the disk (even if the file was missing and a null value was stored). No changes were applied to it.
     */
    UNCHANGED,

    /**
     * The object is marked as modified. An earlier version of the object is stored on the disk, but a newer version is already in memory and when the flush() is called, the new content will override the existing file.
     */
    MODIFIED,

    /**
     * The file is marked for deletion. The file is still present on the disk, but it will be deleted during the next flush operation.
     */
    TO_BE_DELETED,

    /**
     * The file was deleted and this node is just an orphan node of an already deleted object.
     */
    DELETED
}
