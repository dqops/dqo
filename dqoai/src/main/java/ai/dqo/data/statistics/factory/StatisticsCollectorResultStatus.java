package ai.dqo.data.statistics.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Profiler result status enumeration: success - when the statistics collector (a basic profiler) managed to obtain a requested metric, error - when an error was returned. The error is stored in another field.
 * The values are stored in the "status" column in the statistics results parquet file.
 */
public enum StatisticsCollectorResultStatus {
    @JsonProperty("success")
    success,

    @JsonProperty("error")
    error
}
