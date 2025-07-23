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

import org.jline.reader.ParsedLine;

/**
 * Provides access to the current line that is completed in the shell.
 */
public class CurrentCompletionLine {
    private static ParsedLine currentLine;

    /**
     * Returns the current line that was parsed.
     * @return Current line.
     */
    public static ParsedLine getCurrentLine() {
        return currentLine;
    }

    /**
     * Stores the current line for the completer to pick.
     * @param currentLine Current completer line.
     */
    public static void setCurrentLine(ParsedLine currentLine) {
        CurrentCompletionLine.currentLine = currentLine;
    }
}
