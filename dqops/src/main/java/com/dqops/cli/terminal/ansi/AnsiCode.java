/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal.ansi;

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
