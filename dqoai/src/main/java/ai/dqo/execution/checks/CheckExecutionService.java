/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.checks;


import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.execution.checks.scheduled.ScheduledChecksCollection;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.metadata.search.CheckSearchFilters;

/**
 * Service that executes data quality checks.
 */
public interface CheckExecutionService {
    /**
     * Executes data quality checks. Reports progress and saves the results.
     * @param checkExecutionContext Check execution context with access to the user home and dqo home.
     * @param checkSearchFilters Check search filters to find the right checks.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    CheckExecutionSummary executeChecks(CheckExecutionContext checkExecutionContext,
                       CheckSearchFilters checkSearchFilters,
                       CheckExecutionProgressListener progressListener,
                       boolean dummySensorExecution);

    /**
     * Executes scheduled data quality checks. A list of checks divided by tables must be provided.
     * @param checkExecutionContext Check execution context with access to the user home and dqo home.
     * @param targetSchedule Target schedule to match, when finding checks that should be executed.
     * @param progressListener Progress listener that receives progress calls.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    CheckExecutionSummary executeChecksForSchedule(CheckExecutionContext checkExecutionContext,
                                        RunChecksSchedule targetSchedule,
                                        CheckExecutionProgressListener progressListener);
}
