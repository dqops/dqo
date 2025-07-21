/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.table;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.table.impl.TableEditService;
import com.dqops.cli.completion.completedcommands.IConnectionNameCommand;
import com.dqops.cli.completion.completers.ConnectionNameCompleter;
import com.dqops.cli.completion.completers.FullTableNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to edit a table.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "edit", header = "Edit table that matches a given condition", description = "Edit the table or tables that match the filter conditions specified in the options. It allows the user to modify the details of an existing table in the application.")
public class TableEditCliCommand extends BaseCommand implements ICommand, IConnectionNameCommand {
    private TerminalReader terminalReader;
    private TerminalWriter terminalWriter;
    private TableEditService tableEditService;

    public TableEditCliCommand() {
    }

    /**
     * Default injection constructor.
     * @param terminalReader Terminal reader.
     * @param terminalWriter Terminal writer.
     * @param tableEditService Table edit service.
     */
    @Autowired
    public TableEditCliCommand(TerminalReader terminalReader,
							   TerminalWriter terminalWriter,
							   TableEditService tableEditService) {
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.tableEditService = tableEditService;
    }

    @CommandLine.Option(names = {"-c", "--connection"}, description = "Connection Name", completionCandidates = ConnectionNameCompleter.class)
    private String connection;

    @CommandLine.Option(names = {"-t", "--table", "--full-table-name"},
            description = "Full table name (schema.table), supports wildcard patterns 'sch*.tab*'", completionCandidates = FullTableNameCompleter.class)
    private String table;


    /**
     * Returns the table name.
     * @return Table name.
     */
    public String getTable() {
        return this.table;
    }

    /**
     * Sets the table name.
     * @param name Table name.
     */
    public void setTable(String name) {
		this.table = name;
    }

    /**
     * Returns the connection name.
     * @return Connection name.
     */
    public String getConnection() {
        return connection;
    }

    /**
     * Sets the connection name.
     * @param connection Connection name.
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        if (Strings.isNullOrEmpty(this.connection)) {
			throwRequiredParameterMissingIfHeadless("--connection");
			this.connection = this.terminalReader.prompt("Connection name (--connection)", null, false);
        }

        if (Strings.isNullOrEmpty(this.table)) {
			throwRequiredParameterMissingIfHeadless("--table");
			this.table = this.terminalReader.prompt("Full table name (schema.table), supports wildcard patterns 'sch*.tab*'", null, false);
        }

        return this.tableEditService.launchEditorForTable(this.connection, this.table);
    }
}
