package ai.dqo.data.profilingresults.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of possible profiling scopes. "table" - a whole table was profiled, "data_stream" - a single data stream was profiled.
 */
public enum ProfilerDataScope {
    /**
     * The profile is analyzed for the whole table.
     */
    @JsonProperty("table")
    table,

    /**
     * The profile is analyzed for each data stream.
     */
    @JsonProperty("data_stream")
    data_stream
}
