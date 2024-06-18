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
