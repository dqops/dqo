package ai.dqo.cli.commands.connection.table;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.TabularOutputFormat;
import ai.dqo.cli.commands.connection.impl.ConnectionService;
import ai.dqo.cli.commands.status.CliOperationStatus;
import ai.dqo.cli.terminal.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "connection table list" 3nd level cli command.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "list", description = "List tables for connection")
public class ConnectionTableListCliCommand extends BaseCommand implements ICommand {
	private final ConnectionService connectionService;
	private final TerminalReader terminalReader;
	private final TerminalWriter terminalWriter;
	private final TerminalTableWritter terminalTableWritter;
	private final FileWritter fileWritter;

	@Autowired
	public ConnectionTableListCliCommand(ConnectionService connectionService,
										 TerminalReader terminalReader,
										 TerminalWriter terminalWriter,
										 TerminalTableWritter terminalTableWritter,
										 FileWritter fileWritter) {
		this.connectionService = connectionService;
		this.terminalWriter = terminalWriter;
		this.terminalReader = terminalReader;
		this.terminalTableWritter = terminalTableWritter;
		this.fileWritter = fileWritter;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name", required = false)
	private String connection;

	@CommandLine.Option(names = {"-s", "--schema"}, description = "Schema name", required = false)
	private String schema;

	@CommandLine.Option(names = {"-t", "--table"}, description = "Table name", required = false)
	private String table;

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

		if (Strings.isNullOrEmpty(this.schema)) {
			throwRequiredParameterMissingIfHeadless("--schema");
			this.schema = this.terminalReader.prompt("Schema name (--schema)", null, false);
		}

		CliOperationStatus cliOperationStatus = this.connectionService.loadTableList(connection, schema, table, this.getOutputFormat());
		if (cliOperationStatus.isSuccess()) {
			if (this.getOutputFormat() == TabularOutputFormat.TABLE) {
				if (this.isWriteToFile()) {
					TableBuilder tableBuilder = new TableBuilder(new TablesawDatasetTableModel(cliOperationStatus.getTable()));
					tableBuilder.addInnerBorder(BorderStyle.oldschool);
					tableBuilder.addHeaderBorder(BorderStyle.oldschool);
					String renderedTable = tableBuilder.build().render(this.terminalWriter.getTerminalWidth() - 1);
					CliOperationStatus cliOperationStatus2 = this.fileWritter.writeStringToFile(renderedTable);
					this.terminalWriter.writeLine(cliOperationStatus2.getMessage());
				} else {
					this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
				}
			} else {
				if (this.isWriteToFile()) {
					CliOperationStatus cliOperationStatus2 = this.fileWritter.writeStringToFile(cliOperationStatus.getMessage());
					this.terminalWriter.writeLine(cliOperationStatus2.getMessage());
				}
				else {
					this.terminalWriter.write(cliOperationStatus.getMessage());
				}
			}
			return 0;
		} else {
			this.terminalWriter.writeLine(cliOperationStatus.getMessage());
			return -1;
		}
	}
}
