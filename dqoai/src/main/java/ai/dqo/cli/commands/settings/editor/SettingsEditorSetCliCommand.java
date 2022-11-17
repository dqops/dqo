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
package ai.dqo.cli.commands.settings.editor;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.commands.settings.impl.SettingsService;
import ai.dqo.cli.completion.completedcommands.IEditorNameCommand;
import ai.dqo.cli.completion.completers.EditorNameCompleter;
import ai.dqo.cli.completion.completers.EditorPathCompleter;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to set a new editor to settings.
 */
@Component
@Scope("prototype")
@CommandLine.Command(name = "set", description = "Set editor settings")
public class SettingsEditorSetCliCommand extends BaseCommand implements ICommand, IEditorNameCommand {
	private final SettingsService settingsService;
	private final TerminalReader terminalReader;
	private final TerminalWriter terminalWriter;

	@Autowired
	public SettingsEditorSetCliCommand(SettingsService settingsService,
									  TerminalReader terminalReader,
									  TerminalWriter terminalWriter) {
		this.settingsService = settingsService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
	}

	@CommandLine.Option(names = {"-n", "--name"}, description = "Editor name",
			completionCandidates = EditorNameCompleter.class)
	private String editorName;

	@CommandLine.Option(names = {"-p", "--path"}, description = "Editor path", required = false,
			completionCandidates = EditorPathCompleter.class)
	private String editorPath;

	public String getEditorName() {
		return editorName;
	}

	public void setEditorName(String name) {
		this.editorName = name;
	}

	public String getEditorPath() {
		return editorPath;
	}

	public void setEditorPath(String path) {
		this.editorPath = path;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		if (Strings.isNullOrEmpty(this.editorName)) {
			throwRequiredParameterMissingIfHeadless("--name");
			this.editorName = this.terminalReader.prompt("Editor name (--name)", null, false);
		}

		if (Strings.isNullOrEmpty(this.editorPath)) {
			throwRequiredParameterMissingIfHeadless("--path");
			this.editorPath = this.terminalReader.prompt("Editor path (--path)", null, false);
		}

		CliOperationStatus cliOperationStatus = this.settingsService.setSettingsEditor(editorName, editorPath);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
