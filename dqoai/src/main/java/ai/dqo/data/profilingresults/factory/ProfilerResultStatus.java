package ai.dqo.data.profilingresults.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Profiler result status enumeration: success - when the profiler managed to obtain a requested metric, error - when an error was returned. The error is stored in another field.
 * The values are stored in the "status" column in the profiler results parquet file.
 */
public enum ProfilerResultStatus {
    @JsonProperty("success")
    success,

    @JsonProperty("error")
    error
}
