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
package ai.dqo.cli.commands;

import ai.dqo.BaseTest;
import ai.dqo.cli.commands.connection.ConnectionListCliCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CliMainCommandRunnerTests extends BaseTest {
    @Test
    void run_whenConnectionsList_thenCallsCommand() throws Exception {
        ConnectionListCliCommand command = CommandLineObjectMother.parseCommand(ConnectionListCliCommand.class,
                "connection", "list");

        Assertions.assertInstanceOf(ConnectionListCliCommand.class, command);
    }
}
