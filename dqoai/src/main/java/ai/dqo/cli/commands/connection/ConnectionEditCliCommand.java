package ai.dqo.cli.commands.connection;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.connection.impl.ConnectionService;
import ai.dqo.cli.completion.completers.ConnectionNameCompleter;
import ai.dqo.cli.terminal.TerminalReader;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to edit a table.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "edit", description = "Edit connection which match filters")
public class ConnectionEditCliCommand extends BaseCommand implements ICommand {
	private final TerminalReader terminalReader;
	private final ConnectionService connectionService;

	/**
	 * Default injection constructor.
	 * @param terminalReader Terminal reader.
	 * @param connectionService Connection service.
	 */
	@Autowired
	public ConnectionEditCliCommand(TerminalReader terminalReader,
								ConnectionService connectionService) {
		this.terminalReader = terminalReader;
		this.connectionService = connectionService;
	}

	@CommandLine.Option(names = {"-n", "--connection"}, description = "Connection Name", completionCandidates = ConnectionNameCompleter.class)
	private String connection;

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

		return this.connectionService.launchEditorForConnection(this.connection);
	}
}
