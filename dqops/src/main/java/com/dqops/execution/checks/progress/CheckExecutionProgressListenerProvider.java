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
package com.dqops.execution.checks.progress;

/**
 * Returns a correct implementation of the check run progress listener that prints the progress to the screen.
 */
public interface CheckExecutionProgressListenerProvider {
    /**
     * Returns a check execution progress listener for the requested reporting level.
     *
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the checks to the console.
     * @return Check execution progress listener.
     */
    CheckExecutionProgressListener getProgressListener(CheckRunReportingMode reportingMode, boolean writeSummaryToConsole);
}
