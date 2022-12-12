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
package ai.dqo.metadata.sources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identifies which timestamp column is used to perform date/time partitioned data quality checks,
 * it could be the event timestamp column or the ingestion timestamp column.
 */
public enum PartitionedDataQualityChecksTimestampSource {
    /**
     * Date/time partitioned data quality checks are calculated for rows grouped by the event timestamp, rounded to the valid boundary (daily, weekly, etc).
     */
    @JsonProperty("event_timestamp")
    event_timestamp,

    /**
     * Date/time partitioned data quality checks are calculated for rows grouped by the ingestion timestamp, rounded to the valid boundary (daily, weekly, etc).
     */
    @JsonProperty("ingestion_timestamp")
    ingestion_timestamp
}
