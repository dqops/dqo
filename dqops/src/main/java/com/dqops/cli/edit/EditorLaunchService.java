/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
