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

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Exit code generator that caches the exit code that should be returned.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CliExitCodeGeneratorImpl implements CliExitCodeGenerator {
    private int exitCode;

    /**
     * Returns the exit code that is returned back to the operating system when the application quits.
     * @return Process exit code.
     */
    @Override
    public int getExitCode() {
        return this.exitCode;
    }

    /**
     * Stores the exit code that will be returned to the operating system.
     *
     * @param exitCode Exit code.
     */
    @Override
    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
}
