/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
