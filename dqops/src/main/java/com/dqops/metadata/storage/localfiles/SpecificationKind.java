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
package com.dqops.metadata.storage.localfiles;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * YAML file type.
 */
public enum SpecificationKind {
    @JsonProperty("source")
    source,

    @JsonProperty("table")
    table,

    @JsonProperty("sensor")
    sensor,

    @JsonProperty("provider_sensor")
    provider_sensor,

    @JsonProperty("rule")
    rule,

    @JsonProperty("check")
    check,

    @JsonProperty("settings")
    settings,

    @JsonProperty("file_index")
    file_index,

    @JsonProperty("dashboards")
    dashboards,

    @JsonProperty("default_schedules")
    default_schedules,

    @Deprecated
    @JsonProperty("default_checks")
    default_checks,

    @JsonProperty("default_table_checks")
    default_table_checks,

    @JsonProperty("default_column_checks")
    default_column_checks,

    @JsonProperty("default_notifications")
    default_notifications;
}
