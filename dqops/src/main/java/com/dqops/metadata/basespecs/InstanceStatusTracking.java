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
 * Implement by objects that are tracking their model status (added, deleted). Provides methods to retrieve the status
 * or change the status.
 */
public interface InstanceStatusTracking {
    /**
     * Returns the status of the node.
     * @return Object status (added, modified, deleted, etc).
     */
    InstanceStatus getStatus();

    /**
     * Changes the status of the model.
     * @param status New status.
     */
    void setStatus(InstanceStatus status);

    /**
     * Marks the object for deletion. The status changes to {@link InstanceStatus#TO_BE_DELETED}.
     */
    void markForDeletion();
}
