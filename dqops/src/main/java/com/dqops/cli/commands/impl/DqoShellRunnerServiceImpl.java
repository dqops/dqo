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
package com.dqops.cli.commands.impl;

import com.dqops.cli.completion.InputCapturingCompleter;
import lombok.extern.slf4j.Slf4j;
import org.jline.console.SystemRegistry;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.MaskingCallback;
import org.jline.reader.UserInterruptException;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Root DQOps shell runner. Separated into a different class to avoid circular dependencies in IoC.
 */
@Component
@Slf4j
public class DqoShellRunnerServiceImpl implements DqoShellRunnerService {
    /**
     * Prompt string shown in the shell.
     */
    public static final String DQO_PROMPT = "dqo> ";

    private final SystemRegistry systemRegistry;
    private final LineReader cliLineReader;

    @Autowired
    public DqoShellRunnerServiceImpl(SystemRegistry systemRegistry, @Qualifier("cliLineReader") LineReader cliLineReader) {
        this.systemRegistry = systemRegistry;
        this.cliLineReader = cliLineReader;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        String prompt = new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(255, 153,0)) // DQOps Orange
                .append(DQO_PROMPT)
                .toAnsi();
        String rightPrompt = null;

        // start the shell and process input until the user quits with Ctrl-D
        String line = null;
        while (true) {
            try {
				systemRegistry.cleanUp();

                try {
                    InputCapturingCompleter.enable();
                    line = cliLineReader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
                }
                finally {
                    InputCapturingCompleter.disable();
                }

				systemRegistry.execute(line);
            } catch (UserInterruptException e) {
                // Ignore
                continue;
            } catch (EndOfFileException e) {
                log.info("EndOfFile reached, closing...");
                return 0;
            } catch (Exception e) {
                log.error("Command failed: " + line + ", error message: " + e.getMessage(), e);
				systemRegistry.trace(e);
            }
        }
    }
}
