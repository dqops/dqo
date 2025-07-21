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

import org.springframework.boot.ExitCodeGenerator;

/**
 * Exit code generator that caches the exit code that should be returned.
 */
public interface CliExitCodeGenerator extends ExitCodeGenerator {
    /**
     * Stores the exit code that will be returned to the operating system.
     * @param exitCode Exit code.
     */
    void setExitCode(int exitCode);
}
