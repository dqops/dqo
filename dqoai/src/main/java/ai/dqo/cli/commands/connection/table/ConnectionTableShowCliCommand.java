package ai.dqo.cli.commands.connection.table;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.connection.impl.ConnectionService;
import ai.dqo.cli.commands.status.CliOperationStatus;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "connection table show" 3nd level cli command.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "show", description = "Show table for connection")
public class ConnectionTableShowCliCommand extends BaseCommand implements ICommand {
	private final ConnectionService connectionService;
	private final TerminalReader terminalReader;
	private final TerminalWriter terminalWriter;

	@Autowired
	public ConnectionTableShowCliCommand(ConnectionService connectionService,
										 TerminalReader terminalReader,
										 TerminalWriter terminalWriter) {
		this.connectionService = connectionService;
		this.terminalWriter = terminalWriter;
		this.terminalReader = terminalReader;
	}

	@CommandLine.Option(names = {"-c", "--connection"}, description = "Connection name", required = false)
	private String connection;

	@CommandLine.Option(names = {"-t", "--table"}, description = "Full table name", required = false)
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

		if (Strings.isNullOrEmpty(this.table)) {
			throwRequiredParameterMissingIfHeadless("--table");
			this.table = this.terminalReader.prompt("Full table name (--schema)", null, false);
		}

		CliOperationStatus cliOperationStatus = this.connectionService.showTableForConnection(connection, table);
		if (cliOperationStatus.isSuccess()) {
			this.terminalWriter.writeTable(cliOperationStatus.getTable(), true);
			return 0;
		} else {
			this.terminalWriter.writeLine(cliOperationStatus.getMessage());
			return -1;
		}
	}
}
