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
