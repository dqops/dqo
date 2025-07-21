/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.*;

public class LinkageStore<T> implements Map<T, Path> {
    private final Map<T, Path> linkage = new LinkedHashMap<>();

    @Override
    public int size() {
        return linkage.size();
    }

    @Override
    public boolean isEmpty() {
        return linkage.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return linkage.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return linkage.containsValue(value);
    }

    @Override
    public Path get(Object key) {
        return linkage.get(key);
    }

    @Nullable
    @Override
    public Path put(T key, Path value) {
        if (linkage.containsKey(key)) {
            throw new IllegalArgumentException("Linkage already exists for " + key.toString());
        }
        return linkage.put(key, value);
    }

    @Override
    public Path remove(Object key) {
        throw new UnsupportedOperationException("Linkage cannot be removed");
    }

    @Override
    public void putAll(@NotNull Map<? extends T, ? extends Path> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Linkages cannot be removed");
    }

    @NotNull
    @Override
    public Set<T> keySet() {
        return linkage.keySet();
    }

    @NotNull
    @Override
    public Collection<Path> values() {
        return linkage.values();
    }

    @NotNull
    @Override
    public Set<Entry<T, Path>> entrySet() {
        return linkage.entrySet();
    }
}
