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
 * Interface implemented by objects that are tracking if the object was frozen for modifications and is read-only.
 */
public interface ReadOnlyStatus {
    /**
     * Check if the object is frozen (read only). A read-only object cannot be modified.
     * @return True when the object is read-only and trying to apply a change will return an error.
     */
    boolean isReadOnly();

    /**
     * Sets the read-only flag on the current object, and optionally on child objects.
     * @param propagateToChildren When true, makes also the child objects as read-only.
     */
    void makeReadOnly(boolean propagateToChildren);
}
