package ai.dqo.data.statistics.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of possible statistics scopes. "table" - a whole table was profiled, "data_stream" - a single data stream was profiled.
 */
public enum StatisticsDataScope {
    /**
     * The statistics (profile) is analyzed for the whole table.
     */
    @JsonProperty("table")
    table,

    /**
     * The statistics (profile) is analyzed for each data stream.
     */
    @JsonProperty("data_stream")
    data_stream
}
