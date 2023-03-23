/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.commands.settings.apikey;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.settings.impl.SettingsService;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.google.common.base.Strings;
import org.apache.commons.codec.DecoderException;
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
	private SettingsService settingsService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private DqoCloudApiKeyProvider apiKeyProvider;

	public SettingsApiKeySetCliCommand() {
	}

	@Autowired
	public SettingsApiKeySetCliCommand(SettingsService settingsService,
									   TerminalReader terminalReader,
									   TerminalWriter terminalWriter,
									   DqoCloudApiKeyProvider apiKeyProvider) {
		this.settingsService = settingsService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.apiKeyProvider = apiKeyProvider;
	}

	@CommandLine.Parameters(index = "0", description = "DQO Cloud Api key")
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
			this.key = this.terminalReader.prompt("Api key", null, false);
		}

		try {
			this.apiKeyProvider.decodeApiKey(this.key);
		}
		catch (Exception ex) {
			this.terminalWriter.writeLine("Invalid Cloud DQO API key: " + ex.getMessage());
			return -1;
		}

		CliOperationStatus cliOperationStatus = this.settingsService.setApiKey(key);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
