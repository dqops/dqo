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
 * Provides instances of the {@link TerminalReader} and {@link TerminalWriter} that are created when needed.
 */
public interface TerminalFactory {
    /**
     * Returns the instance of the terminal reader.
     *
     * @return Terminal reader.
     */
    TerminalReader getReader();

    /**
     * Returns the instance of the terminal writer.
     *
     * @return Terminal writer.
     */
    TerminalWriter getWriter();
}
