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
package ai.dqo.metadata.basespecs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for all specs in the tree. Provides basic dirty checking.
 */
@EqualsAndHashCode(callSuper = false)
public abstract class BaseDirtyTrackingSpec implements DirtyStatus {
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private boolean dirty;

    /**
     * Check if the object is dirty (has changes).
     * @return True when the object is dirty and has modifications.
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the dirty flag to true.
     */
    public void setDirty() {
		this.dirty = true;
    }

    /**
     * Sets the dirty flag to true if the parameter is true. Otherwise preserves the current dirty status.
     * @param hasChanged When true, sets the dirty flag to true.
     */
    @JsonIgnore
    public void setDirtyIf(boolean hasChanged) {
        if(hasChanged) {
			this.dirty = true;
        }
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    public void clearDirty(boolean propagateToChildren) {
		this.dirty = false;
    }
}
