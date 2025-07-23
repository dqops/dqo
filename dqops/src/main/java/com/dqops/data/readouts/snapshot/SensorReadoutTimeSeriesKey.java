/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.snapshot;

/**
 * Time series key extracted from historic sensor readouts for a single time series. A key identifies a single time series
 * and is used as a hashing key in a hash table.
 * A single time series is a subset of sensor readouts for a single check (which maps 1-to-1 to a sensor) and a data stream level id.
 */
public class SensorReadoutTimeSeriesKey {
    private final long checkHashId;
    private final long dataStreamHash;

    /**
     * Creates a time series key.
     * @param checkHashId Check hash id.
     * @param dataStreamId Data stream hash id.
     */
    public SensorReadoutTimeSeriesKey(long checkHashId, long dataStreamId) {
        this.checkHashId = checkHashId;
        this.dataStreamHash = dataStreamId;
    }

    /**
     * Returns the check hash id.
     * @return Check hash id.
     */
    public long getCheckHashId() {
        return checkHashId;
    }

    /**
     * Returns the data series ID, which is a hash of the data stream level values.
     * @return Combined data stream id (hash).
     */
    public long getDataStreamHash() {
        return dataStreamHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorReadoutTimeSeriesKey that = (SensorReadoutTimeSeriesKey) o;

        if (checkHashId != that.checkHashId) return false;
        return dataStreamHash == that.dataStreamHash;
    }

    @Override
    public int hashCode() {
        int result = (int) (checkHashId ^ (checkHashId >>> 32));
        result = 31 * result + (int) (dataStreamHash ^ (dataStreamHash >>> 32));
        return result;
    }
}
