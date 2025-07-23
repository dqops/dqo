/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.cli.terminal;

/**
 * Terminal factory test stub.
 */
public class TerminalFactoryStub implements TerminalFactory {
    private final TerminalReader reader;
    private final TerminalWriter writer;

    public TerminalFactoryStub(TerminalReader terminalReader, TerminalWriter terminalWriter) {
        this.reader = terminalReader;
        this.writer = terminalWriter;
    }

    public TerminalReader getReader() {
        return reader;
    }

    public TerminalWriter getWriter() {
        return writer;
    }
}
