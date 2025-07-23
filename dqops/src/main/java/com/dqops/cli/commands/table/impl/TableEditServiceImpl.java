/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.table.impl;

import com.dqops.cli.edit.EditorLaunchService;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
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
    private final DqoUserPrincipalProvider userPrincipalProvider;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context factory.
     * @param editorLaunchService Editor launcher.
     * @param terminalWriter Terminal writer.
     * @param userPrincipalProvider User principal provider.
     */
    @Autowired
    public TableEditServiceImpl(UserHomeContextFactory userHomeContextFactory,
                                EditorLaunchService editorLaunchService,
                                TerminalWriter terminalWriter,
                                DqoUserPrincipalProvider userPrincipalProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.editorLaunchService = editorLaunchService;
        this.terminalWriter = terminalWriter;
        this.userPrincipalProvider = userPrincipalProvider;
    }

    /**
     * Finds a table and opens the default text editor to edit the yaml file.
     * @param connectionName Connection name.
     * @param fullTableName Full table name e.g. schema.table.
     * @return Error code: 0 when the table was found, -1 when the connection or table was not found.
     */
    public int launchEditorForTable(String connectionName, String fullTableName) {
        DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), true);
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
