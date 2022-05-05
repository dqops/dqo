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
package ai.dqo.cli;

import ai.dqo.cli.commands.DqoRootCliCommand;
import ai.dqo.cli.completion.InputCapturingCompleter;
import ai.dqo.cli.exceptions.CommandExecutionErrorHandler;
import ai.dqo.cli.terminal.CommandHelpStrategy;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.core.configuration.DqoCoreConfigurationProperties;
import org.jline.console.SystemRegistry;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.keymap.KeyMap;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.widget.TailTipWidgets;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

/**
 * Configuration class to configure the terminal and line reader beans.
 */
@Configuration
public class CliConfiguration {
    /**
     * Creates a default jline3 parser.
     * @return JLine parser.
     */
    @Bean(name = "defaultParser")
    public Parser defaultParser() {
        return new DefaultParser();
    }

    /**
     * Creates a configuration of built-ins.
     * @return Builtin operations.
     */
    @Bean(name = "builtins")
    public Builtins builtins() {
        Supplier<Path> workDir = () -> Paths.get(System.getProperty("user.dir"));
        Builtins builtins = new Builtins(workDir, null, null);
        builtins.rename(Builtins.Command.TTOP, "top");
        builtins.alias("zle", "widget");
        builtins.alias("bindkey", "keymap");
        return builtins;
    }

    /**
     * Configures a picocli command line using the root command.
     * @param rootShellCommand Root cli command.
     * @param factory Picocli command factory (default).
     * @param terminal Terminal.
     * @param terminalWriter Terminal writer.
     * @param coreConfigurationProperties Core configuration properties.
     * @return Command line.
     */
    @Bean(name = "commandLine")
    public CommandLine commandLine(DqoRootCliCommand rootShellCommand,
								   CommandLine.IFactory factory,
								   Terminal terminal,
								   TerminalWriter terminalWriter,
								   DqoCoreConfigurationProperties coreConfigurationProperties) {
        PicocliCommands.PicocliCommandsFactory shellCommandFactory = new PicocliCommands.PicocliCommandsFactory(factory);
        shellCommandFactory.setTerminal(terminal);
        CommandLine commandLine = new CommandLine(rootShellCommand, factory);
        commandLine.setExecutionExceptionHandler(new CommandExecutionErrorHandler(terminalWriter, coreConfigurationProperties));
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
//        CommandHelpStrategy helpExecutionStrategy = new CommandHelpStrategy(commandLine.getExecutionStrategy(), terminalWriter);
//        commandLine.setExecutionStrategy(helpExecutionStrategy);
        return commandLine;
    }

    /**
     * Creates a system registry of cli commands.
     * @param terminal Terminal
     * @param defaultParser Default parser.
     * @param builtins Builtins
     * @return System registry.
     */
    @Bean(name = "systemRegistry")
    public SystemRegistry systemRegistry(Terminal terminal,
										 @Qualifier("defaultParser") Parser defaultParser,
										 Builtins builtins,
										 CommandLine commandLine) {
        Supplier<Path> workDir = () -> Paths.get(System.getProperty("user.dir"));

        PicocliCommands picocliShellCommands = new PicocliCommands(commandLine);
        SystemRegistry systemRegistry = new SystemRegistryImpl(defaultParser, terminal, workDir, null);
        systemRegistry.setCommandRegistries(builtins, picocliShellCommands);
        systemRegistry.register("help", picocliShellCommands);
        return systemRegistry;
    }

    /**
     * Creates a default jline3 line reader.
     * @return Line reader.
     */
    @Bean(name = "cliLineReader")
    public LineReader cliLineReader(Terminal terminal,
									@Qualifier("defaultParser") Parser defaultParser,
									SystemRegistry systemRegistry,
									Builtins builtins) {
        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new InputCapturingCompleter(systemRegistry.completer()))
                .parser(defaultParser)
                .variable(LineReader.LIST_MAX, 50)   // max tab completion candidates
                .build();

        builtins.setLineReader(lineReader);
        TailTipWidgets widgets = new TailTipWidgets(lineReader, systemRegistry::commandDescription, 4, TailTipWidgets.TipType.COMPLETER);
        widgets.enable();
        KeyMap<Binding> keyMap = lineReader.getKeyMaps().get("main");
        keyMap.bind(new Reference("tailtip-toggle"), KeyMap.alt("s"));

        return lineReader;
    }
}
