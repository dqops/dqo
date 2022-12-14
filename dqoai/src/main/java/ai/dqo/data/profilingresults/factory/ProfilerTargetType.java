package ai.dqo.data.profilingresults.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Profiler types (target types) enumeration. The values are stored in the "profiler_type" column in the profiler results parquet file.
 */
public enum ProfilerTargetType {
    @JsonProperty("table")
    table,

    @JsonProperty("column")
    column
}
