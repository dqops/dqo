/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
