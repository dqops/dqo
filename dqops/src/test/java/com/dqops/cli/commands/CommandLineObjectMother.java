/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands;

import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.BeanFactory;
import picocli.CommandLine;

/**
 * Command line object mother.
 */
public class CommandLineObjectMother {
    /**
     * Returns the default command line (configured) that is extracted from the spring bean factory.
     * @return Command line.
     */
    public static CommandLine getCommandLine() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(CommandLine.class);
    }

    /**
     * Parses the given command line and returns a user command object filled with the parameters.
     * @param args Arguments.
     * @param <T> User command type.
     * @return User command object.
     */
    public static <T> T parseCommand(Class<? extends T> userObject, String... args) {
        CommandLine commandLine = getCommandLine();
        CommandLine.ParseResult parseResult = commandLine.parseArgs(args);
        CommandLine lastCommandSpec = parseResult.asCommandLineList().get(parseResult.asCommandLineList().size() - 1);
        Object userCommandObject = lastCommandSpec.getCommandSpec().userObject();
        Assertions.assertInstanceOf(userObject, userCommandObject);
        return (T)userCommandObject;
    }
}
