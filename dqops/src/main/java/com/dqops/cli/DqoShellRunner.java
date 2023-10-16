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
package com.dqops.cli;

import org.springframework.boot.ApplicationArguments;
import org.springframework.shell.ShellApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * DQOps shell runner that captures the cli commands passed over to the CLI, because DQOps does not use the Spring Boot Shell.
 */
@Component
public class DqoShellRunner implements ShellApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // just ignore, we will run the command anyway from CliMainCommandRunner, because we are using Picocli command parser, not the spring shell parser
    }
}
