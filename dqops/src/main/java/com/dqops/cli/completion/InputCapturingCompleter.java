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
package com.dqops.cli.completion;

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

/**
 * Special command line completer that can inform completers what are the other parameters in the command line and enable dynamic completion
 * that takes into account previously filled command parameters (like the connection name).
 */
public class InputCapturingCompleter implements Completer {
    private static boolean disabled;

    private final Completer nestedCompleter;

    /**
     * Creates a capturing completer that calls the given nested completer.
     * @param nestedCompleter Nested completer.
     */
    public InputCapturingCompleter(Completer nestedCompleter) {
        this.nestedCompleter = nestedCompleter;
    }

    /**
     * Enables the completer.
     */
    public static void enable() {
		disabled = false;
    }

    /**
     * Disables any command completion.
     */
    public static void disable() {
		disabled = true;
    }

    /**
     * Completes a line in the command line by trying to parse the current command, create a command object and let the completers work.
     * @param lineReader Line reader.
     * @param parsedLine Current line.
     * @param list List of completion candidates (output).
     */
    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        if (disabled) {
            return;
        }

        try {
            CurrentCompletionLine.setCurrentLine(parsedLine);
			this.nestedCompleter.complete(lineReader, parsedLine, list);
        }
        finally {
            CurrentCompletionLine.setCurrentLine(null);
        }
    }
}
