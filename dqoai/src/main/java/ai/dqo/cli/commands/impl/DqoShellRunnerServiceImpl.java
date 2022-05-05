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
package ai.dqo.cli.commands.impl;

import ai.dqo.cli.completion.InputCapturingCompleter;
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
 * Root DQO shell runner. Separated into a different class to avoid circular dependencies in IoC.
 */
@Component
public class DqoShellRunnerServiceImpl implements DqoShellRunnerService {
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
        String prompt = new AttributedStringBuilder().append("dqo.ai> ", AttributedStyle.DEFAULT.foreground(4)).toAnsi();
        String rightPrompt = null;
//        boolean completeInWord = cliLineReader.isSet(LineReader.Option.COMPLETE_IN_WORD);

        // start the shell and process input until the user quits with Ctrl-D
        String line;
        while (true) {
            try {
				systemRegistry.cleanUp();
//                cliLineReader.setAutosuggestion(LineReader.SuggestionType.COMPLETER);
//                cliLineReader.option(LineReader.Option.COMPLETE_IN_WORD, completeInWord);
//                cliLineReader.option(LineReader.Option.AUTO_MENU, true);
//                cliLineReader.option(LineReader.Option.AUTO_MENU_LIST, false);

                InputCapturingCompleter.enable();
                line = cliLineReader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);

//                cliLineReader.setAutosuggestion(LineReader.SuggestionType.NONE);
//                cliLineReader.option(LineReader.Option.COMPLETE_IN_WORD, false);
//                cliLineReader.option(LineReader.Option.AUTO_MENU, false);
//                cliLineReader.option(LineReader.Option.AUTO_MENU_LIST, false);
                InputCapturingCompleter.disable();
				systemRegistry.execute(line);
            } catch (UserInterruptException e) {
                // Ignore
                continue;
            } catch (EndOfFileException e) {
                return 0;
            } catch (Exception e) {
				systemRegistry.trace(e);
            }
        }
    }
}
