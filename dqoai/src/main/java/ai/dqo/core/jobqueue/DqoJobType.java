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
package ai.dqo.core.jobqueue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Job type that identifies a job by type.
 */
public enum DqoJobType {
    @JsonProperty("run checks")
    RUN_CHECKS,

    @JsonProperty("run checks on table")
    RUN_CHECKS_ON_TABLE,

    @JsonProperty("collect statistics")
    COLLECT_STATISTICS,

    @JsonProperty("collect statistics on table")
    COLLECT_STATISTICS_ON_TABLE,

    @JsonProperty("queue thread shutdown")
    QUEUE_THREAD_SHUTDOWN,

    @JsonProperty("synchronize folder")
    SYNCHRONIZE_FOLDER,

    @JsonProperty("synchronize multiple folders")
    SYNCHRONIZE_MULTIPLE_FOLDERS,

    @JsonProperty("run scheduled checks by cron")
    RUN_SCHEDULED_CHECKS_CRON,

    @JsonProperty("import schema")
    IMPORT_SCHEMA,

    @JsonProperty("import selected tables")
    IMPORT_TABLES,

    @JsonProperty("delete stored data")
    DELETE_STORED_DATA,

    @JsonProperty("repair stored data")
    REPAIR_STORED_DATA,
}
