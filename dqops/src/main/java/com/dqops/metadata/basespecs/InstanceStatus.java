/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
