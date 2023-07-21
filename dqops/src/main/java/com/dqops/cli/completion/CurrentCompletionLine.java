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
