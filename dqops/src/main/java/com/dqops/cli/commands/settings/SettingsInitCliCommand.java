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
package com.dqops.cli.commands.settings;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.settings.impl.EditorFinderService;
import com.dqops.cli.commands.settings.impl.EditorInformation;
import com.dqops.cli.commands.settings.impl.SettingsService;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Cli command to init settings yaml.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "init", header = "Initialize settings file in UserHome directory",
		description = "Settings file in your UserHome directory. This file stores configuration options for the DQOps.")
public class SettingsInitCliCommand extends BaseCommand implements ICommand {
	private SettingsService settingsService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private EditorFinderService editorFinderService;

	public SettingsInitCliCommand() {
	}

	@Autowired
	public SettingsInitCliCommand(SettingsService settingsService,
								  TerminalReader terminalReader,
								  TerminalWriter terminalWriter,
								  EditorFinderService editorFinderService) {
		this.settingsService = settingsService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.editorFinderService = editorFinderService;
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 */
	@Override
	public Integer call() throws Exception {
		ArrayList<EditorInformation> editors = editorFinderService.detectEditors();

		EditorInformation editor;

		if (editors.size() == 0) {
			editors = editorFinderService.getAllEditors();
			this.terminalWriter.writeLine("We did not find any editors on your computer, which one do you want to set?(choose number from 1 to 4)");

			for (int i = 1; i <= editors.size(); i++) {
				this.terminalWriter.writeLine("[" + i + "] " + editors.get(i - 1).name);
			}

			int index = parseInt(this.terminalReader.prompt("Which editor do You want?", null, false));
			editor = editors.get(index - 1);

			String editorPath = (this.terminalReader.prompt("Paste editor path here", null, false));
			editor.path = editorPath;
		}
		else {
			this.terminalWriter.writeLine("We found these editors on your computer, which one do you want to set?(choose number from 1 to "
					+ editors.size() + ")");

			for (int i = 1; i <= editors.size(); i++) {
				this.terminalWriter.writeLine("[" + i + "] " + editors.get(i - 1).name);
			}

			int index = parseInt(this.terminalReader.prompt("Which editor do you want?", null, false));

			editor = editors.get(index - 1);
		}

		CliOperationStatus cliOperationStatus = this.settingsService.initSettings(editor.name, editor.path);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
