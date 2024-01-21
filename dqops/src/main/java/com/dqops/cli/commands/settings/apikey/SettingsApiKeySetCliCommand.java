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
package com.dqops.cli.commands.settings.apikey;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.settings.impl.SettingsCliService;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to set a api key in settings.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "set", header = "Set API key", description = "Set the API key used for accessing external services. This key is used to authenticate requests to the service.")
public class SettingsApiKeySetCliCommand extends BaseCommand implements ICommand {
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private DqoCloudApiKeyProvider apiKeyProvider;

	public SettingsApiKeySetCliCommand() {
	}

	@Autowired
	public SettingsApiKeySetCliCommand(SettingsCliService settingsCliService,
                                       TerminalReader terminalReader,
                                       TerminalWriter terminalWriter,
                                       DqoCloudApiKeyProvider apiKeyProvider) {
		this.settingsCliService = settingsCliService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.apiKeyProvider = apiKeyProvider;
	}

	@CommandLine.Parameters(index = "0", description = "DQOps Cloud Api key")
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.key)) {
			throwRequiredParameterMissingIfHeadless("key");
			this.key = this.terminalReader.prompt("DQOps Cloud Api key", null, false);
		}

		try {
			this.apiKeyProvider.decodeApiKey(this.key);
		}
		catch (Exception ex) {
			this.terminalWriter.writeLine("Invalid DQOps Cloud API key: " + ex.getMessage());
			return -1;
		}

		CliOperationStatus cliOperationStatus = this.settingsCliService.setApiKey(key);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
