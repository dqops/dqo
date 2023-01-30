package ai.dqo.data.statistics.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Statistics collector types (target types) enumeration. The values are stored in the "collector_type" column in the statistics results parquet file.
 */
public enum StatisticsCollectorTarget {
    @JsonProperty("table")
    table,

    @JsonProperty("column")
    column
}
