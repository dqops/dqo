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
    @JsonProperty("run_checks")
    RUN_CHECKS,

    @JsonProperty("run_checks_on_table")
    RUN_CHECKS_ON_TABLE,

    @JsonProperty("collect_statistics")
    COLLECT_STATISTICS,

    @JsonProperty("collect_statistics_on_table")
    COLLECT_STATISTICS_ON_TABLE,

    @JsonProperty("queue_thread_shutdown")
    QUEUE_THREAD_SHUTDOWN,

    @JsonProperty("synchronize_folder")
    SYNCHRONIZE_FOLDER,

    @JsonProperty("synchronize_multiple_folders")
    SYNCHRONIZE_MULTIPLE_FOLDERS,

    @JsonProperty("run_scheduled_checks_by_cron")
    RUN_SCHEDULED_CHECKS_CRON,

    @JsonProperty("import_schema")
    IMPORT_SCHEMA,

    @JsonProperty("import_selected_tables")
    IMPORT_TABLES,

    @JsonProperty("delete_stored_data")
    DELETE_STORED_DATA,

    @JsonProperty("repair_stored_data")
    REPAIR_STORED_DATA,
}
