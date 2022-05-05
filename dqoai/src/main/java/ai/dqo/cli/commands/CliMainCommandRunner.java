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
package ai.dqo.cli.commands;

import ai.dqo.cli.CliExitCodeGenerator;
import ai.dqo.cli.CliInitializer;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Objects;

/**
 * Main command runner that parses the CLI command line arguments and executes a selected command.
 */
@Profile("!test & cli")
@Component
public class CliMainCommandRunner implements CommandLineRunner {
    private final CliExitCodeGenerator cliExitCodeGenerator;
    private CliInitializer cliInitializer;
    private final CommandLine commandLine;

    @Autowired
    public CliMainCommandRunner(CommandLine commandLine,
                                CliExitCodeGenerator cliExitCodeGenerator,
                                CliInitializer cliInitializer) {
        this.commandLine = commandLine;
        this.cliExitCodeGenerator = cliExitCodeGenerator;
        this.cliInitializer = cliInitializer;
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
			this.cliExitCodeGenerator.setExitCode(errorCode);
        }
        catch (Exception ex) {
			this.cliExitCodeGenerator.setExitCode(1);
        }
    }
}
