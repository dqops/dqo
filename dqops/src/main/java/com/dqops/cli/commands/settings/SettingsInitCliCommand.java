/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.settings.impl.EditorFinderService;
import com.dqops.cli.commands.settings.impl.EditorInformation;
import com.dqops.cli.commands.settings.impl.SettingsCliService;
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
	private SettingsCliService settingsCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;
	private EditorFinderService editorFinderService;

	public SettingsInitCliCommand() {
	}

	@Autowired
	public SettingsInitCliCommand(SettingsCliService settingsCliService,
                                  TerminalReader terminalReader,
                                  TerminalWriter terminalWriter,
                                  EditorFinderService editorFinderService) {
		this.settingsCliService = settingsCliService;
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

		CliOperationStatus cliOperationStatus = this.settingsCliService.initSettings(editor.name, editor.path);
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return cliOperationStatus.isSuccess() ? 0 : -1;
	}
}
