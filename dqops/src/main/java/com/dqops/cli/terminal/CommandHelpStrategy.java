/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal;

import com.dqops.cli.commands.BaseCommand;
import picocli.CommandLine;

import java.util.List;

/**
 * CLI execution strategy that captures executions that will show the help.
 */
public class CommandHelpStrategy implements CommandLine.IExecutionStrategy {
    private CommandLine.IExecutionStrategy chainedExecutionStrategy;
    private TerminalWriter terminalWriter;

    /**
     * Creates a command help strategy that captures CLI command execution and prints the help text when --help option was used.
     * @param chainedExecutionStrategy Nested (original) command execution strategy that is called when the help was not requested.
     * @param terminalWriter Terminal writer to print out the command help.
     */
    public CommandHelpStrategy(CommandLine.IExecutionStrategy chainedExecutionStrategy,
                               TerminalWriter terminalWriter) {
        this.chainedExecutionStrategy = chainedExecutionStrategy;
        this.terminalWriter = terminalWriter;
    }

    @Override
    public int execute(CommandLine.ParseResult parseResult) throws CommandLine.ExecutionException, CommandLine.ParameterException {
        List<CommandLine> commandLines = parseResult.asCommandLineList();
        if (commandLines == null || commandLines.size() == 0) {
            return this.chainedExecutionStrategy.execute(parseResult);
        }

        for (CommandLine commandLine : commandLines) {
            Object userCommand = commandLine.getCommandSpec().userObject();
            if (userCommand instanceof BaseCommand) {
                BaseCommand baseCommand = (BaseCommand)userCommand;
                if (baseCommand.isHelp()) {
                    this.terminalWriter.writeCommandSynopsis(commandLine);
                    return 0;
                }
            }
        }

        return this.chainedExecutionStrategy.execute(parseResult);
    }
}
