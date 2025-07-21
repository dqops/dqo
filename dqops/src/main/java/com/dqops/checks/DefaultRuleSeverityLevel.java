/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks;

import com.dqops.rules.RuleSeverityLevel;

/**
 * Default rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.
 */
public enum DefaultRuleSeverityLevel {
    none(0),
    warning(1),
    error(2),
    fatal(3);

    /**
     * Numeric severity level.
     */
    public final int level;

    DefaultRuleSeverityLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the severity level as a number.
     * @return Severity level as a number.
     */
    public int getSeverity() {
        return level;
    }

    /**
     * Creates a rule severity level from a numeric severity level.
     * @param severity Rule severity level.
     * @return Severity level enum instance.
     */
    public static DefaultRuleSeverityLevel fromSeverityLevel(int severity) {
        switch (severity) {
            case 0:
                return none;
            case 1:
                return warning;
            case 2:
                return error;
            case 3:
                return fatal;
            case 4:
                return null; // execution error is excluded

            default:
                throw new IllegalArgumentException("Invalid severity level: " + severity);
        }
    }

    /**
     * Converts the default severity level to a rule severity level.
     * @return Rule severity level.
     */
    public RuleSeverityLevel toRuleSeverityLevel() {
        return RuleSeverityLevel.fromSeverityLevel(this.level);
    }
}
