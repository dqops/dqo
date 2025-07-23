/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
