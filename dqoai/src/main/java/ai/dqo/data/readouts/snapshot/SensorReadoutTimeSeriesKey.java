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
package ai.dqo.data.readouts.snapshot;

/**
 * Time series key extracted from historic sensor readouts for a single time series. A key identifies a single time series
 * and is used as a hashing key in a hash table.
 * A single time series is a subset of sensor readouts for a single check (which maps 1-to-1 to a sensor) and a data stream level id.
 */
public class SensorReadoutTimeSeriesKey {
    private final long checkHashId;
    private final long dimensionId;

    /**
     * Creates a time series key.
     * @param checkHashId Check hash id.
     * @param dataStreamId Data stream hash id.
     */
    public SensorReadoutTimeSeriesKey(long checkHashId, long dataStreamId) {
        this.checkHashId = checkHashId;
        this.dimensionId = dataStreamId;
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
    public long getDimensionId() {
        return dimensionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorReadoutTimeSeriesKey that = (SensorReadoutTimeSeriesKey) o;

        if (checkHashId != that.checkHashId) return false;
        return dimensionId == that.dimensionId;
    }

    @Override
    public int hashCode() {
        int result = (int) (checkHashId ^ (checkHashId >>> 32));
        result = 31 * result + (int) (dimensionId ^ (dimensionId >>> 32));
        return result;
    }
}
