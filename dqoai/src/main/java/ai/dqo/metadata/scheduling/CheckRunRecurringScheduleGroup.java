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
package ai.dqo.metadata.scheduling;

/**
 * The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.
 */
public enum CheckRunRecurringScheduleGroup {
    /**
     * Schedule for profiling checks.
     */
    profiling,

    /**
     * Schedule for recurring checks that should be executed daily because they capture daily snapshot of a data quality metrics.
     */
    recurring_daily,

    /**
     * Schedule for recurring checks that should be executed monthly because they capture monthly snapshot of a data quality metrics.
     */
    recurring_monthly,

    /**
     * Schedule for partition checks for daily partitioned data.
     */
    partitioned_daily,

    /**
     * Schedule for partition checks for monthly partitioned data.
     */
    partitioned_monthly;
}
