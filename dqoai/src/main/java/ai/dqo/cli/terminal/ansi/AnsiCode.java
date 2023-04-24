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
package ai.dqo.cli.terminal.ansi;

import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine.Help.Ansi.IStyle;

/**
 * Enumeration of selected ANSI codes used throughout the application.
 * Certain styling codes are available in {@link Ansi}, others are here.
 */
public enum AnsiCode implements IStyle {
    hyperlink("]8;;", "]8;;");

    private final String startToken;
    private final String endToken;

    public static final String ESC = "\u001B";
    public static final String CUTOFF = ESC + "\\";

    AnsiCode(String startToken, String endToken) {
        this.startToken = startToken;
        this.endToken = endToken;
    }

    public String on() {
        return ESC + this.startToken;
    }

    public String off() {
        return ESC + this.endToken;
    }
}
