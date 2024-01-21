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
package com.dqops.cli.commands.settings.editor;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.settings.impl.SettingsCliService;
import com.dqops.cli.completion.completedcommands.IEditorNameCommand;
import com.dqops.cli.completion.completers.EditorNameCompleter;
import com.dqops.cli.completion.completers.EditorPathCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Cli command to set a new editor in settings.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "set", header = "Set editor settings", description = "Set the settings for the editor. It allows user to set the editor to use a specific output format.")
public class SettingsEditorSetCliCommand extends BaseCommand implements ICommand, IEditorNameCommand {
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public SettingsEditorSetCliCommand() {
	}

	@Autowired
	public SettingsEditorSetCliCommand(SettingsCliService settingsCliService,
									   TerminalReader terminalReader,
									   TerminalWriter terminalWriter) {
		this.settingsCliService = settingsCliService;
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

		CliOperationStatus cliOperationStatus = this.settingsCliService.setSettingsEditor(editorName, editorPath);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
