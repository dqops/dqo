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
package com.dqops.cli.commands.rule;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.commands.RuleFileExtension;
import com.dqops.cli.commands.rule.impl.RuleCliService;
import com.dqops.cli.completion.completers.RuleExtensionCompleter;
import com.dqops.cli.completion.completers.RuleNameCompleter;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * "rule edit" 2nd level CLI command that edits rule template.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "edit", header = "Edit rule that matches a given condition", description = "This command can be used to update the rule. It is important to use caution when using this command, as it can impact the execution of data quality checks.")
public class RuleEditCliCommand extends BaseCommand implements ICommand {
	private RuleCliService ruleCliService;
	private TerminalReader terminalReader;
	private TerminalWriter terminalWriter;

	public RuleEditCliCommand() {
	}

	@Autowired
	public RuleEditCliCommand(RuleCliService ruleCliService, TerminalReader terminalReader, TerminalWriter terminalWriter) {
		this.ruleCliService = ruleCliService;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
	}

	@CommandLine.Option(names = {"-f", "--ext"}, description = "File type",
			completionCandidates = RuleExtensionCompleter.class)
	private RuleFileExtension ext;

	@CommandLine.Option(names = {"-r", "--rule"}, description = "Rule name",
			completionCandidates = RuleNameCompleter.class)
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
			throwRequiredParameterMissingIfHeadless("--rule");
			this.name = this.terminalReader.prompt("Rule name (--rule)", null, false);
		}

		CliOperationStatus cliOperationStatus = this.ruleCliService.editTemplate(name, ext);

		if (cliOperationStatus.isSuccess()) {
			return 0;
		}
		this.terminalWriter.writeLine(cliOperationStatus.getMessage());
		return -1;
	}
}
