package ai.dqo.metadata.groupings;

import ai.dqo.metadata.sources.TableSpec;

/**
 * Interface implemented on specification nodes that are providing the value of a configured time series.
 * Checkpoint and partitioned check category roots (daily, weekly, ) are special kind of roots.
 */
public interface TimeSeriesConfigurationProvider {
    /**
     * Returns time series configuration for the given group of checks.
     * @param tableSpec Parent table specification - used to get the details about the time partitioning column.
     * @return Time series configuration.
     */
    TimeSeriesConfigurationSpec getTimeSeriesConfiguration(TableSpec tableSpec);
}
