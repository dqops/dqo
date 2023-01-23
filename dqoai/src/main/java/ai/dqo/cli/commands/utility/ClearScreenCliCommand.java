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
package ai.dqo.cli.commands.utility;

import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.terminal.TerminalWriter;
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
@CommandLine.Command(name = "cls", description = "Clears the screen")
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
