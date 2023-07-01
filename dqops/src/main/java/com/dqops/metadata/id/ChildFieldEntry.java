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
package com.dqops.metadata.id;

/**
 * Hierarchy child node name and hierarchy node instance, returned from a child iterator on a HierarchyNode.
 */
public class ChildFieldEntry {
    private final String childName;
    private final GetHierarchyChildNodeFunc<HierarchyNode> getChildFunc;

    /**
     * Creates a child field node entry.
     * @param childName Child name. It is a field name or an entry name in an object map.
     * @param getChildFunc Lambda for extracting the child node.
     */
    public ChildFieldEntry(String childName, GetHierarchyChildNodeFunc<HierarchyNode> getChildFunc) {
        this.childName = childName;
        this.getChildFunc = getChildFunc;
    }

    /**
     * Returns the child node name (field name or a key name in an object map).
     * @return Child name.
     */
    public String getChildName() {
        return childName;
    }

    /**
     * Returns the child hierarchy node extraction lambda.
     * @return Lambda for extracting the child node.
     */
    public GetHierarchyChildNodeFunc<HierarchyNode> getGetChildFunc() {
        return getChildFunc;
    }
}
