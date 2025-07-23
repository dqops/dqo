/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.check;

/**
 * Enumeration of the issue severity level that will cause the "check run" command to fail.
 */
public enum CheckRunCommandFailThreshold {
    /**
     * Any warning detected or any higher severity level (error, fatal) causes the "check run" command to fail.
     */
    warning(1),

    /**
     * Any error detected or any higher severity level (fatal) causes the "check run" command to fail.
     */
    error(2),

    /**
     * Only fatal severity issues that were detected will cause the "check run" command to fail.
     */
    fatal(3),

    /**
     * Do not fail, just blindly run "check run" without returning an error code.
     */
    none(4);

    private int severityLevel;

    CheckRunCommandFailThreshold(int severityLevel) {
        this.severityLevel = severityLevel;
    }

    /**
     * Returns the severity level at which the command will fail.
     * @return Severity level to fail.
     */
    public int getSeverityLevel() {
        return severityLevel;
    }
}
