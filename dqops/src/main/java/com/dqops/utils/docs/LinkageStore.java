/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
