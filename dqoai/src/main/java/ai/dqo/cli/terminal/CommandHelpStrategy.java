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
package ai.dqo.cli.terminal;

import ai.dqo.cli.commands.BaseCommand;
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
