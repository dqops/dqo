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
package ai.dqo.metadata.dashboards;

import ai.dqo.metadata.basespecs.AbstractDirtyTrackingSpecList;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;

import java.util.Objects;

/**
 * List of dashboard folders.
 */
public class DashboardsFolderListSpec extends AbstractDirtyTrackingSpecList<DashboardsFolderSpec> implements Cloneable {
    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DashboardsFolderListSpec clone() {
        DashboardsFolderListSpec cloned = new DashboardsFolderListSpec();
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(cloned.getHierarchyId().clone());
        }

        for (DashboardsFolderSpec folderSpec : this) {
            cloned.add(folderSpec.clone());
        }

        if (!this.isDirty()) {
            cloned.clearDirty(false);
        }

        return cloned;
    }

    /**
     * Finds a folder by name.
     * @param folderName Folder specification when the folder was found or null when the folder is missing.
     * @return Folder specification or null.
     */
    public DashboardsFolderSpec getFolderByName(String folderName) {
        for (DashboardsFolderSpec folder : this) {
            if (Objects.equals(folderName, folder.getFolderName())) {
                return folder;
            }
        }

        return null;
    }
}
