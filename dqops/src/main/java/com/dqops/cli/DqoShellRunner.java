/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
