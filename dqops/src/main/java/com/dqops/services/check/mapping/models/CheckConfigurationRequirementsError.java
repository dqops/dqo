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
package com.dqops.services.check.mapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Enumeration of errors on a check, such as an event timestamp column is not configured on a table.
 */
public enum CheckConfigurationRequirementsError {
    @JsonProperty("missing_event_timestamp_column")
    @JsonPropertyDescription("Configuration of the event timestamp column that identifies the timestamp of the event (transaction, click, operation, etc.) is missing and must be configured at the table level.")
    missing_event_timestamp_column,

    @JsonProperty("missing_ingestion_timestamp_column")
    @JsonPropertyDescription("Configuration of the ingestion timestamp column that identifies the timestamp when the row was loaded is missing and must be configured at the table level.")
    missing_ingestion_timestamp_column,

    @JsonProperty("missing_event_and_ingestion_columns")
    @JsonPropertyDescription("Configuration of both the event timestamp column that identifies the timestamp of the event (transaction, click, operation, etc.) and the ingestion timestamp column that identifies the timestamp when the row was loaded are missing and must be configured at the table level.")
    missing_event_and_ingestion_columns,

    @JsonProperty("missing_time_period_partitioning_column")
    @JsonPropertyDescription("Configuration of column that is used to partition the table by a time period (day, month, etc.) is missing and must be configured at the table level.")
    missing_time_period_partitioning_column,
}
