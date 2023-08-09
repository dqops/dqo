/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
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

package com.dqops.utils.docs.python;

import com.dqops.utils.docs.python.apimodel.ComponentModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.ReflectionService;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ComponentReflectionServiceImpl implements ComponentReflectionService {
    private final ReflectionService reflectionService;

    public ComponentReflectionServiceImpl(ReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    @Override
    public void fillComponentsDocsRefs(Iterable<ComponentModel> componentModels) {
        ClassPath classPath;
        try {
            classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't obtain class-path.", e);
        }

        Map<String, ComponentModel> componentClasses = new HashMap<>();
        for (ComponentModel componentModel : componentModels) {
            componentClasses.put(componentModel.getClassName(), componentModel);
        }

//        List<ClassInfo> classInfos = classPath
        List<Class<?>> classes = classPath
                .getAllClasses()
                .stream()
                .filter(clazz -> componentClasses.containsKey(clazz.getSimpleName()))
                .map(ClassPath.ClassInfo::load).collect(Collectors.toList());
        List<ClassInfo> classInfos = classes.stream()
                .map(reflectionService::getClassInfoForClass).collect(Collectors.toList());

        System.out.println("Done!");
    }
}
