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
package com.dqops.metadata.comments;

import com.dqops.metadata.basespecs.AbstractDirtyTrackingSpecList;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;

/**
 * List of comments.
 */
public class CommentsListSpec extends AbstractDirtyTrackingSpecList<CommentSpec> {
    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public CommentsListSpec deepClone() {
        CommentsListSpec cloned = new CommentsListSpec();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId().clone());
        }

        for (CommentSpec comment : this) {
            cloned.add(comment.deepClone());
        }

        cloned.clearDirty(false);
        return cloned;
    }
}
