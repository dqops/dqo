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
package ai.dqo.core.scheduler.quartz;

import org.quartz.JobKey;

/**
 * Predefined Quartz job keys for built-in jobs (synchronization, running checks).
 */
public final class JobKeys {
    /**
     * Predefined job that synchronizes the metadata.
     */
    public static final JobKey SYNCHRONIZE_METADATA = new JobKey(JobNames.SYNCHRONIZE_METADATA, null);

    /**
     * Predefined job that runs the data quality checks.
     */
    public static final JobKey RUN_CHECKS = new JobKey(JobNames.RUN_CHECKS, null);
}
