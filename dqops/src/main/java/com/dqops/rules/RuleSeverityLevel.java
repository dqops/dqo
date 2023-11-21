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
package com.dqops.rules;

/**
 * Rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.
 */
public enum RuleSeverityLevel {
    valid(0),
    warning(1),
    error(2),
    fatal(3);

    /**
     * Numeric severity level.
     */
    public final int level;

    RuleSeverityLevel(int level) {
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
    public static RuleSeverityLevel fromSeverityLevel(int severity) {
        switch (severity) {
            case 0:
                return valid;
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
}
