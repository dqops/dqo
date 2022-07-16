package ai.dqo.cli.commands.sensor;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.SensorFileExtension;
import ai.dqo.cli.commands.sensor.impl.SensorService;
import ai.dqo.cli.commands.status.CliOperationStatus;
import ai.dqo.cli.completion.completers.ProviderTypeCompleter;
import ai.dqo.cli.completion.completers.SensorExtensionCompleter;
import ai.dqo.cli.completion.completers.SensorNameCompleter;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.ProviderType;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "sensor edit" 2nd level CLI command that edits sensor template.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "edit", description = "Run checks matching specified filters")
public class SensorEditCliCommand extends BaseCommand implements ICommand {
	private final SensorService sensorService;
	private final TerminalReader terminalReader;
	private final TerminalWriter terminalWriter;

	@Autowired
	public SensorEditCliCommand(SensorService sensorService, TerminalReader terminalReader, TerminalWriter terminalWriter) {
		this.sensorService = sensorService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
	}

	@CommandLine.Option(names = {"-p", "--provider"}, description = "Provider type",
			completionCandidates = ProviderTypeCompleter.class)
	private ProviderType provider;

	@CommandLine.Option(names = {"-f", "--ext"}, description = "File type",
			completionCandidates = SensorExtensionCompleter.class)
	private SensorFileExtension ext;

	@CommandLine.Option(names = {"-s", "--sensor"}, description = "Sensor name",
			completionCandidates = SensorNameCompleter.class)
	private String name;


	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 * @throws Exception if unable to compute a result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.name)) {
			throwRequiredParameterMissingIfHeadless("--sensor");
			this.name = this.terminalReader.prompt("Sensor name (--sensor)", null, false);
		}

		CliOperationStatus cliOperationStatus = this.sensorService.editTemplate(name, provider, ext);

		if (cliOperationStatus.isSuccess()) {
			return 0;
		}
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return -1;
	}
}
