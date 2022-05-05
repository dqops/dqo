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
package ai.dqo.cli.commands.table.impl;

import ai.dqo.cli.edit.EditorLaunchService;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that launches an editor to edit the table specification.
 */
@Service
public class TableEditServiceImpl implements TableEditService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final EditorLaunchService editorLaunchService;
    private final TerminalWriter terminalWriter;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param editorLaunchService Editor launcher.
     * @param terminalWriter Terminal writer.
     */
    @Autowired
    public TableEditServiceImpl(UserHomeContextFactory userHomeContextFactory,
								EditorLaunchService editorLaunchService,
								TerminalWriter terminalWriter) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.editorLaunchService = editorLaunchService;
        this.terminalWriter = terminalWriter;
    }

    /**
     * Finds a table and opens the default text editor to edit the yaml file.
     * @param connectionName Connection name.
     * @param fullTableName Full table name e.g. schema.table.
     * @return Error code: 0 when the table was found, -1 when the connection or table was not found.
     */
    public int launchEditorForTable(String connectionName, String fullTableName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
			this.terminalWriter.writeLine(String.format("Connection '%s' not found", connectionName));
            return -1;
        }

        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(fullTableName);
        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(physicalTableName, true);
        if (tableWrapper == null) {
			this.terminalWriter.writeLine(String.format("Table '%s' not found in connection '%s'", fullTableName, connectionName));
            return -1;
        }

		this.editorLaunchService.openEditorForTable(tableWrapper);

        return 0;
    }
}
