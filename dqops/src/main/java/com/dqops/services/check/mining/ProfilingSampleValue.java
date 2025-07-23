/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.mining;

import java.time.Instant;

/**
 * Container of a sample value that was captured by the value sampling profiler.
 */
public class ProfilingSampleValue {
    private Object value;
    private long count;
    private Instant instantValue;

    /**
     * Creates an object that contains a sample value.
     * @param value Sample value, using a java object of type of the collected value.
     * @param count The count of occurrences of this value.
     * @param instantValue The value converted to an instant, but only if this value is a valid date, datetime, instant (timestamp), or can be converted.
     */
    public ProfilingSampleValue(Object value, long count, Instant instantValue) {
        this.value = value;
        this.count = count;
        this.instantValue = instantValue;
    }

    /**
     * Returns the sample value, using a java object of type of the collected value.
     * @return Sample value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the count of occurrences of a sample value.
     * @return The sample value, converted to a string.
     */
    public long getCount() {
        return count;
    }

    /**
     * Returns the sample value converted to an instant, but only when it was possible to convert it to an instant (it was a timestamp, date, datetime or an object convertible to these types).
     * @return Sample value as a Java Instant class.
     */
    public Instant getInstantValue() {
        return instantValue;
    }
}
