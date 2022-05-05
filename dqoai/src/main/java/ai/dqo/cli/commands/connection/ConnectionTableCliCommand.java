package ai.dqo.cli.commands.connection;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.connection.table.ConnectionTableListCliCommand;
import ai.dqo.cli.commands.connection.table.ConnectionTableShowCliCommand;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "table" 2st level cli command.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "table", description = "Show and list connection tables", subcommands = {
		ConnectionTableListCliCommand.class,
		ConnectionTableShowCliCommand.class
})
public class ConnectionTableCliCommand extends BaseCommand {
}
