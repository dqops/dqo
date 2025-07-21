/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.reflection;

import org.github.jamm.FieldFilter;
import org.github.jamm.Filters;
import org.github.jamm.MemoryMeter;
import org.github.jamm.MemoryMeterStrategy;
import org.github.jamm.listeners.NoopMemoryMeterListener;
import org.github.jamm.strategies.MemoryMeterStrategies;

/**
 * JVM object memory size utility that measures the real JVM object size, including the whole object tree.
 */
public class ObjectMemorySizeUtility {
    private static final MemoryMeter memoryMeter;

    static {
        MemoryMeterStrategy memoryMeterStrategy = MemoryMeterStrategies.getInstance().getStrategy(MemoryMeter.BEST);
        FieldFilter fieldFilters = Filters.getFieldFilters(true, false, true);
        memoryMeter = new MemoryMeter(memoryMeterStrategy, Filters.IGNORE_KNOWN_SINGLETONS, fieldFilters, NoopMemoryMeterListener.FACTORY);
    }

    /**
     * Measure the size of the object, including the whole nested object tree. Include the JVM memory heap overhead for the object headers.
     * @param obj Object to measure.
     * @return The total size of the object on the JVM heap.
     */
    public static long measureDeep(Object obj) {
        long objectSizeBytes = memoryMeter.measureDeep(obj);
        return objectSizeBytes;
    }
}
