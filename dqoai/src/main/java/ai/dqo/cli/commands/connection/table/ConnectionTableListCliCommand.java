package ai.dqo.cli.commands.connection.table;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.connection.impl.ConnectionService;
import ai.dqo.cli.commands.status.CliOperationStatus;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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


	@Autowired
	public ConnectionTableListCliCommand(ConnectionService connectionService,
									TerminalReader terminalReader,
									TerminalWriter terminalWriter,
								 	TerminalTableWritter terminalTableWritter) {
		this.connectionService = connectionService;
		this.terminalWriter = terminalWriter;
		this.terminalReader = terminalReader;
		this.terminalTableWritter = terminalTableWritter;
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

		CliOperationStatus cliOperationStatus = this.connectionService.loadTableList(connection, schema, table);
		if (cliOperationStatus.isSuccess()) {
			this.terminalTableWritter.writeTable(cliOperationStatus.getTable(), true);
			return 0;
		} else {
			this.terminalWriter.writeLine(cliOperationStatus.getMessage());
			return -1;
		}
	}
}
