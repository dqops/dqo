/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.collections;

import java.util.List;
import java.util.function.Predicate;

/**
 * Helper class to operate on lists.
 */
public class ListFindUtils {
    /**
     * Finds an element in the list that matches the given predicate.
     * @param list List of items.
     * @param predicate Predicate to match.
     * @return Found element or null when the element was not found.
     * @param <T>
     */
    public static <T> T findElement(List<T> list, Predicate<T> predicate) {
        for (T item : list) {
            if (predicate.test(item)) {
                return item;
            }
        }

        return null;
    }
}
