/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
