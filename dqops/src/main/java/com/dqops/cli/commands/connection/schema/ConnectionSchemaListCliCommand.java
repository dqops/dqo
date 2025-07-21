/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.connection.schema;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.cli.commands.connection.impl.ConnectionCliService;
import com.dqops.cli.terminal.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "connection schema list" 3nd level cli command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "list", header = "List schemas in the specified connection", description = "It allows the user to view the summary of all schemas in a selected connection.")
public class ConnectionSchemaListCliCommand extends BaseCommand implements ICommand {
	private ConnectionCliService connectionCliService;
	private TerminalFactory terminalFactory;
	private TerminalTableWritter terminalTableWritter;
	private FileWriter fileWriter;

	public ConnectionSchemaListCliCommand() {
	}

	@Autowired
	public ConnectionSchemaListCliCommand(ConnectionCliService connectionCliService,
										  TerminalFactory terminalFactory,
                                          TerminalTableWritter terminalTableWritter,
                                          FileWriter fileWriter) {
		this.connectionCliService = connectionCliService;
		this.terminalFactory = terminalFactory;
		this.terminalTableWritter = terminalTableWritter;
		this.fileWriter = fileWriter;
	}

	@CommandLine.Option(names = {"-n", "--name"}, description = "Connection name filter", required = false)
	private String name;

	@CommandLine.Option(names = {"-d", "--dimension"}, description = "Dimension filter", required = false)
	private String[] dimensions;

	@CommandLine.Option(names = {"-l", "--label"}, description = "Label filter", required = false)
	private String[] labels;

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.name)) {
			throwRequiredParameterMissingIfHeadless("--name");
			this.name = this.terminalFactory.getReader().prompt("Connection name (--name)", null, false);
		}

		CliOperationStatus cliOperationStatus= this.connectionCliService.loadSchemaList(this.name, this.getOutputFormat(), dimensions, labels);
		if (cliOperationStatus.isSuccess()) {
			if (this.getOutputFormat() == TabularOutputFormat.TABLE) {
				if (this.isWriteToFile()) {
					TableBuilder tableBuilder = new TableBuilder(new TablesawDatasetTableModel(cliOperationStatus.getTable()));
					tableBuilder.addInnerBorder(BorderStyle.oldschool);
					tableBuilder.addHeaderBorder(BorderStyle.oldschool);
					String renderedTable = tableBuilder.build().render(this.terminalFactory.getWriter().getTerminalWidth() - 1);
					CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(renderedTable);
					this.terminalFactory.getWriter().writeLine(cliOperationStatus2.getMessage());
				} else {
					this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
				}
			} else {
				if (this.isWriteToFile()) {
					CliOperationStatus cliOperationStatus2 = this.fileWriter.writeStringToFile(cliOperationStatus.getMessage());
					this.terminalFactory.getWriter().writeLine(cliOperationStatus2.getMessage());
				}
				else {
					this.terminalFactory.getWriter().write(cliOperationStatus.getMessage());
				}
			}
			return 0;
		} else {
			this.terminalFactory.getWriter().writeLine(cliOperationStatus.getMessage());
			return -1;
		}
	}
}
