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
package com.dqops.data.checkresults.services.models;

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
