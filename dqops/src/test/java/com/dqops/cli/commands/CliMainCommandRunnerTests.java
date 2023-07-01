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
