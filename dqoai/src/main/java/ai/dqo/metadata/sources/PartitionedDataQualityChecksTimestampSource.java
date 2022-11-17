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
