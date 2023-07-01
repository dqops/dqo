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
package com.dqops.cli.edit;

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;

/**
 * Service called from "edit" command line commands to open an editor for editing .yaml or .py files.
 */
public interface EditorLaunchService {
    /**
     * Opens an editor for a file that contains the given target object.
     * @param targetObjectId Target object id.
     */
    void openEditorFor(HierarchyId targetObjectId);

    /**
     * Opens an editor for a connection specification yaml.
     * @param connectionWrapper Connection wrapper.
     */
    void openEditorForConnection(ConnectionWrapper connectionWrapper);

    /**
     * Opens an editor for a table specification yaml.
     * @param tableWrapper Table wrapper.
     */
    void openEditorForTable(TableWrapper tableWrapper);

    /**
     * Launches a text editor for a file given.
     * @param pathToTextFile Path to the text file that will be edited (it could be .yaml, .py)
     */
    void launchEditorForFile(String pathToTextFile);
}
