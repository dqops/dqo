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
    SOURCE,

    @JsonProperty("table")
    TABLE,

    @JsonProperty("sensor")
    SENSOR,

    @JsonProperty("provider_sensor")
    PROVIDER_SENSOR,

    @JsonProperty("rule")
    RULE,

    @JsonProperty("check")
    CHECK,

    @JsonProperty("settings")
    SETTINGS,

    @JsonProperty("file_index")
    FILE_INDEX,

    @JsonProperty("dashboards")
    DASHBOARDS,

    @JsonProperty("default_schedules")
    MONITORING_SCHEDULES,

    @JsonProperty("default_data_observability_checks")
    OBSERVABILITY_CHECK_SETTINGS,

    @JsonProperty("default_incident_webhooks_notifications")
    NOTIFICATION_WEBHOOKS

}
