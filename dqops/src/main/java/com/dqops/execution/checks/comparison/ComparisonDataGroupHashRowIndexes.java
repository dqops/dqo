/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.checks.comparison;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import tech.tablesaw.api.DoubleColumn;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Object that contains provides row indexes for each data group hash.
 */
public class ComparisonDataGroupHashRowIndexes {
    private final Long2IntOpenHashMap rowIndexesPerGroupHash;
    private final LocalDateTime timePeriod;
    private final DoubleColumn valueColumn;

    public ComparisonDataGroupHashRowIndexes(LocalDateTime timePeriod, DoubleColumn valueColumn) {
        this.rowIndexesPerGroupHash = new Long2IntOpenHashMap();
        this.rowIndexesPerGroupHash.defaultReturnValue(-1);
        this.timePeriod = timePeriod;
        this.valueColumn = valueColumn;
    }

    /**
     * Adds a row index (row number from the results) to the hash table.
     * @param dataGroupHash Data group hash.
     * @param rowIndex Row index.
     */
    public void addRowIndex(long dataGroupHash, int rowIndex) {
        this.rowIndexesPerGroupHash.put(dataGroupHash, rowIndex);
    }

    /**
     * Checks if the hash table contains the result for the data group hash.
     * @param dataGroupHash Data group hash.
     * @return True when the value for the data group is present, false if it is not (we have a mismatch of compared values).
     */
    public boolean containsDataGroup(long dataGroupHash) {
        return this.rowIndexesPerGroupHash.containsKey(dataGroupHash);
    }

    /**
     * Returns the row index (in the results) for the data group hash.
     * Note: first call the containsDataGroup to see if the data is present.
     * @param dataGroupHash Data group hash.
     * @return Row index.
     */
    public int getRowIndexForDataGroup(long dataGroupHash) {
        return this.rowIndexesPerGroupHash.get(dataGroupHash);
    }

    /**
     * Returns the time period.
     * @return Time period.
     */
    public LocalDateTime getTimePeriod() {
        return timePeriod;
    }

    /**
     * Streams all values stored in the hash map.
     * @return Stream of values.
     */
    public Stream<ComparedValue> streamValues() {
        return this.rowIndexesPerGroupHash.long2IntEntrySet()
                .stream()
                .map(entry -> {
                    int rowIndex = entry.getIntValue();
                    return new ComparedValue(timePeriod, entry.getLongKey(), valueColumn.get(rowIndex), rowIndex);
                });
    }

    /**
     * Collects row indexes that do not have a matching value in the <code>comparedTableHolder</code> (which is the data from the compared table).
     * @param comparedTableHolder Compared table data.
     * @param lookupMissRowIndexes Target integer array list to add (append) row indexes in the current table (the reference table).
     */
    public void collectRowIndexesNotMatching(ComparisonDataHolder comparedTableHolder, IntArrayList lookupMissRowIndexes) {
        for (Long2IntMap.Entry entry : this.rowIndexesPerGroupHash.long2IntEntrySet()) {
            if (!comparedTableHolder.containsValue(this.timePeriod, entry.getLongKey())) {
                lookupMissRowIndexes.add(entry.getIntValue());
            }
        }
    }
}
