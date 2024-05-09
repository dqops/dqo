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
package com.dqops.cli;

import com.dqops.cli.commands.DqoRootCliCommand;
import com.dqops.cli.completion.InputCapturingCompleter;
import com.dqops.core.configuration.DqoCliTerminalConfigurationProperties;
import com.dqops.cli.exceptions.CommandExecutionErrorHandler;
import com.dqops.cli.terminal.*;
import com.dqops.cli.terminal.ansi.UrlFormatter;
import com.dqops.core.configuration.DqoCoreConfigurationProperties;
import com.dqops.utils.StaticBeanFactory;
import org.jline.builtins.ConfigurationPath;
import org.jline.builtins.SyntaxHighlighter;
import org.jline.console.SystemRegistry;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.SystemHighlighter;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.keymap.KeyMap;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.widget.TailTipWidgets;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

import java.io.PushbackInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Configuration class to configure the terminal and line reader beans.
 */
@Lazy(false)
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
        Builtins builtins = new Builtins(workDir, new ConfigurationPath(null, null), null);
        builtins.rename(Builtins.Command.TTOP, "top");
        builtins.alias("zle", "widget");
        builtins.alias("bindkey", "keymap");
        return builtins;
    }

    /**
     * Creates a configuration of terminal writer based on application's running mode.
     * @param dqoCliTerminalConfigurationProperties Properties for CLI if running in one-shot mode.
     * @return Terminal writer applicable to the application's running mode.
     */
    @Lazy
    @Bean(name = "terminalWriter")
    public TerminalWriter terminalWriter(DqoCliTerminalConfigurationProperties dqoCliTerminalConfigurationProperties) {
        if (CliApplication.isRunningOneShotMode()) {
            return new TerminalWriterSystemImpl(dqoCliTerminalConfigurationProperties.getWidth());
        }

        Terminal terminal = StaticBeanFactory.getBeanFactory().getBean(Terminal.class);
        UrlFormatter urlFormatter = StaticBeanFactory.getBeanFactory().getBean(UrlFormatter.class);
        return new TerminalWriterImpl(terminal, urlFormatter);
    }

    /**
     * Configures a picocli command line using the root command, based on the application's running mode.
     * @param rootShellCommand Root cli command.
     * @param factory Picocli command factory (default).
     * @param terminalFactory Terminal reader and writer factory, used to delay the instance creation.
     * @param coreConfigurationProperties DQOps Core configuration properties.
     * @return Command line.
     */
    @Bean(name = "commandLine")
    public CommandLine commandLine(DqoRootCliCommand rootShellCommand,
                                   CommandLine.IFactory factory,
                                   TerminalFactory terminalFactory,
                                   DqoCoreConfigurationProperties coreConfigurationProperties) {
        PicocliCommands.PicocliCommandsFactory shellCommandFactory = new PicocliCommands.PicocliCommandsFactory(factory);
        if (!CliApplication.isRunningOneShotMode()) {
            Terminal terminal = StaticBeanFactory.getBeanFactory().getBean(Terminal.class);
            shellCommandFactory.setTerminal(terminal);
        }

        CommandLine commandLine = new CommandLine(rootShellCommand, factory);
        commandLine.setExecutionExceptionHandler(new CommandExecutionErrorHandler(terminalFactory, coreConfigurationProperties));
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
                .highlighter(new SystemHighlighter(
                        SyntaxHighlighter.build("classpath:dqo_cli.nanorc"),  // For optional future use.
                        SyntaxHighlighter.build("classpath:dqo_cli.nanorc"),  // The editable file is in
                        SyntaxHighlighter.build("classpath:dqo_cli.nanorc")   // resources/org/jline/builtins/dqo_cli.nanorc
                ))
                .terminal(terminal)
                .completer(new InputCapturingCompleter(systemRegistry.completer()))
                .parser(defaultParser)
                .variable(LineReader.LIST_MAX, 50)  // max tab completion candidates
                .variable(LineReader.COMPLETION_STYLE_STARTING, "fg:yellow")  // starting part highlight of the completer widget
                .build();

        builtins.setLineReader(lineReader);
        TailTipWidgets widgets = new TailTipWidgets(lineReader, systemRegistry::commandDescription, 4, TailTipWidgets.TipType.COMPLETER);
        widgets.enable();
        KeyMap<Binding> keyMap = lineReader.getKeyMaps().get("main");
        keyMap.bind(new Reference("tailtip-toggle"), KeyMap.alt("s"));

        return lineReader;
    }

    /**
     * Creates a configuration of terminal reader based on application's running mode.
     * @param terminalWriter Configured terminal writer.
     * @return Terminal reader applicable to the application's running mode.
     */
    @Lazy
    @Bean(name = "terminalReader")
    public TerminalReader terminalReader(TerminalWriter terminalWriter) {
        if (CliApplication.isRunningOneShotMode()) {
            System.setIn(new PushbackInputStream(System.in));
            return new TerminalReaderSystemImpl(terminalWriter);
        }

        LineReader cliLineReader = (LineReader) StaticBeanFactory.getBeanFactory().getBean("cliLineReader");
        return new TerminalReaderImpl(terminalWriter, cliLineReader);
    }

    /**
     * Creates an empty hashmap.
     * @return Empty hashmap.
     */
    @Bean(name = "java.util.Map")
    public Map createEmptyMap() {
        return new LinkedHashMap();
    }
}
