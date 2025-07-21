/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
