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
package com.dqops.utils.reflection;

import org.github.jamm.MemoryMeter;

/**
 * JVM object memory size utility that measures the real JVM object size, including the whole object tree.
 */
public class ObjectMemorySizeUtility {
    private static final MemoryMeter memoryMeter = MemoryMeter.builder()
            .build();

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
