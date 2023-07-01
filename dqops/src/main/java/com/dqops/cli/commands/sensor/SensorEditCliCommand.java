/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.cli.commands.sensor;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.SensorFileExtension;
import com.dqops.cli.commands.sensor.impl.SensorService;
import com.dqops.cli.completion.completers.ProviderTypeCompleter;
import com.dqops.cli.completion.completers.SensorExtensionCompleter;
import com.dqops.cli.completion.completers.SensorNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.ProviderType;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "sensor edit" 2nd level CLI command that edits sensor template.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "edit", header = "Edit sensor that matches a given condition", description = "Allows you to modify the properties of a custom sensor that matches certain condition.")
public class SensorEditCliCommand extends BaseCommand implements ICommand {
	private SensorService sensorService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public SensorEditCliCommand() {
	}

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
