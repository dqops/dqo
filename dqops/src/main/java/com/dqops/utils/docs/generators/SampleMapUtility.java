/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.utils.docs.generators;

import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.docs.DocumentationReflectionServiceImpl;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import jakarta.validation.constraints.NotNull;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SampleMapUtility {
    private static DocumentationReflectionService reflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());

    public static <K, V> @NotNull Map<K, V> generateMap(@NotNull List<K> keys, @NotNull List<V> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException(String.format("Cannot generate map, keys and values list differ in length (K = %d, V = %d)",
                    keys.size(),
                    values.size()));
        }

        return IntStream.range(0, keys.size())
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(keys.get(i), values.get(i)))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        (key, value) -> value,
                        LinkedHashMap::new));
    }
}
