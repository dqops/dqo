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
package com.dqops.core.jobqueue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Job type that identifies a job by type.
 */
public enum DqoJobType {
    run_checks,
    run_checks_on_table,
    collect_statistics,
    collect_statistics_on_table,
    queue_thread_shutdown,
    synchronize_folder,
    synchronize_multiple_folders,
    run_scheduled_checks_cron,
    import_schema,
    import_tables,
    delete_stored_data,
    repair_stored_data,
}
