/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.utility;

import com.dqops.cli.commands.ICommand;
import com.dqops.cli.terminal.TerminalWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Clear screen ("cls") command.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "cls", header = "Clear the screen", description = "Clear the terminal screen or console, removing all the previous commands and outputs of the commands that were executed on the console. It allows the user to start with a clean slate for the next set of commands or outputs.")
public class ClearScreenCliCommand implements ICommand {
    private TerminalWriter terminalWriter;

    public ClearScreenCliCommand() {
    }

    /**
     * Default injection constructor.
     * @param terminalWriter Terminal writer.
     */
    @Autowired
    public ClearScreenCliCommand(TerminalWriter terminalWriter) {
        this.terminalWriter = terminalWriter;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        this.terminalWriter.clearScreen();
        return 0;
    }
}
