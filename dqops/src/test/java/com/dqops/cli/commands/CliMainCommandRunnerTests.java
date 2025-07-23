/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands;

import com.dqops.BaseTest;
import com.dqops.cli.commands.connection.ConnectionListCliCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CliMainCommandRunnerTests extends BaseTest {
    @Test
    void run_whenConnectionsList_thenCallsCommand() throws Exception {
        ConnectionListCliCommand command = CommandLineObjectMother.parseCommand(ConnectionListCliCommand.class,
                "connection", "list");
        String value = "\"aome-name\"";
        String replaced = value.substring(1, value.length() - 1).replace("-", "_HYPHEN_").replace("/", "_HYPHEN_").replace("\\", "_BACKSLASH_");

        Assertions.assertInstanceOf(ConnectionListCliCommand.class, command);
    }
}
