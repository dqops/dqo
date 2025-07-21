/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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

    @JsonProperty("connection_similarity_index")
    connection_similarity_index,

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
