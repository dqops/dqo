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

import org.springframework.stereotype.Service;

@Service
public class UrlFormatterImpl implements UrlFormatter {
    /**
     * Formats a String to an ANSI hyperlink format.
     *
     * @param urlText     URL to be linked.
     * @param visibleText Text to be displayed when hyperlinked.
     */
    @Override
    public String getUrlAnsiString(String urlText, String visibleText) {
        AnsiCode ansiCode = AnsiCode.hyperlink;
        return ansiCode.on() + urlText + AnsiCode.CUTOFF +
                visibleText + ansiCode.off() + AnsiCode.CUTOFF;
    }
}
