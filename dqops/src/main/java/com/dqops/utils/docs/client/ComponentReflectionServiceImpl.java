/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.client;

import com.dqops.utils.reflection.TargetClassSearchUtility;
import joptsimple.internal.Strings;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ComponentReflectionServiceImpl implements ComponentReflectionService {
    private final Path projectDir;
    private Map<String, Class<?>> reflectedClasses = null;

    public ComponentReflectionServiceImpl(Path projectDir) {
        this.projectDir = projectDir;
    }

    public Class<?> getClassFromClassSimpleName(String className) {
        if (reflectedClasses == null) {
            reflectedClasses = initReflectedClasses();
        }

        return reflectedClasses.get(className);
    }

    private Map<String, Class<?>> initReflectedClasses() {
        List<Class<?>> classList = TargetClassSearchUtility.findClasses("com.dqops", projectDir, Object.class);
        Map<String, Class<?>> result = new LinkedHashMap<>();

        for (Class<?> clazz : classList) {
            String className = clazz.getSimpleName();
            if (!Strings.isNullOrEmpty(className) && !result.containsKey(className)) {
                result.put(className, clazz);
            }
        }

        return result;
    }
}
