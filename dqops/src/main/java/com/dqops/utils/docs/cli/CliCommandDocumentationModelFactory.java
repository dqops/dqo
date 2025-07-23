/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.cli;

import picocli.CommandLine;

/**
 * Documentation factory that creates documentation of a single CLI command.
 */
public interface CliCommandDocumentationModelFactory {
    /**
     * Generates a documentation for a single command. May return null for commands that are just intermediate nodes in the command tree
     * and are not callable.
     *
     * @param commandLine Command line for the command.
     * @return Command documentation or null if the command is not callable.
     */
    CliCommandDocumentationModel makeDocumentationForCommand(CommandLine commandLine);
}
