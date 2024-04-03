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

import com.dqops.cli.ApplicationShutdownManager;
import com.dqops.cli.CliExitCodeGenerator;
import com.dqops.cli.CliInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Main command runner that parses the CLI command line arguments and executes a selected command.
 */
@Profile("!test & cli")
@Component
@Slf4j
public class CliMainCommandRunner implements CommandLineRunner {
    /**
     * Special exit code returned only by the {@link com.dqops.cli.commands.run.RunCliCommand} command
     * to indicate that the application should continue working, even after the command finished because we are simply not able to wait for the exit.
     */
    public static final int DO_NOT_EXIT_AFTER_COMMAND_FINISHED_EXIT_CODE = Integer.MIN_VALUE + 99;

    private final CliExitCodeGenerator cliExitCodeGenerator;
    private CliInitializer cliInitializer;
    private ApplicationShutdownManager applicationShutdownManager;
    private final CommandLine commandLine;

    @Autowired
    public CliMainCommandRunner(CommandLine commandLine,
                                CliExitCodeGenerator cliExitCodeGenerator,
                                CliInitializer cliInitializer,
                                ApplicationShutdownManager applicationShutdownManager) {
        this.commandLine = commandLine;
        this.cliExitCodeGenerator = cliExitCodeGenerator;
        this.cliInitializer = cliInitializer;
        this.applicationShutdownManager = applicationShutdownManager;
    }

    /**
     * Parses the command line, instantiates a command, fills the command with parameters from the arguments and starts it.
     * @param args Command line arguments.
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        this.cliInitializer.initializeApp(args);

        try {
            int errorCode = commandLine.execute(args);
            if (errorCode != DO_NOT_EXIT_AFTER_COMMAND_FINISHED_EXIT_CODE) {
                this.cliExitCodeGenerator.setExitCode(errorCode);
                this.applicationShutdownManager.initiateShutdown(errorCode); // to stop the web server
            }
        }
        catch (Exception ex) {
            log.error("Application shutdown failed: " + ex.getMessage(), ex);
			this.cliExitCodeGenerator.setExitCode(1);
        }
    }
}
