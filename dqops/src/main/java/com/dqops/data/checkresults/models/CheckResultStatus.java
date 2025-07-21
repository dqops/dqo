/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.models;

/**
 * Enumeration of check execution statuses. It is the highest severity or an error if the sensor cannot be executed due to a configuration issue.
 */
public enum CheckResultStatus {
    valid(0),
    warning(1),
    error(2),
    fatal(3),

    /**
     * Sensor or rule failed to execute due to an implementation issue.
     */
    execution_error(4);

    private int severity;

    /**
     * Initializes an enum with a matching severity value.
     * @param severity Severity value (0..4), where 4 is the execution error.
     */
    CheckResultStatus(int severity) {
        this.severity = severity;
    }

    /**
     * Returns the severity value (0..4), where 0..3 are check severity levels and 4 is an execution error.
     * @return Severity value.
     */
    public int getSeverity() {
        return severity;
    }

    /**
     * Finds the maximum severity level from two check results. Execution errors are not compared, because they are not issues.
     * @param first First severity level.
     * @param second Second severity level.
     * @return The maximum severity level detected.
     */
    public static CheckResultStatus max(CheckResultStatus first, CheckResultStatus second) {
        if (first == null || first == execution_error) {
            return second;
        } else  if (second == null || second == execution_error) {
            return first;
        }

        if (first.getSeverity() >= second.getSeverity()) {
            return first;
        }

        return second;
    }

    /**
     * Creates a result status object from a severity number.
     * @param severity Severity value (0..4).
     * @return Matching result status.
     */
    public static CheckResultStatus fromSeverity(int severity) {
        switch (severity) {
            case 0: return valid;
            case 1: return warning;
            case 2: return error;
            case 3: return fatal;
            case 4: return execution_error;
            default:
                throw new IllegalArgumentException("Severity " + severity + " is out of range");
        }
    }
}
