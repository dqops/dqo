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

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Finds classes in a [project root dir]/target/classes folder that extend a base class.
 */
public class TargetClassSearchUtility {
    /**
     * Finds all classes inside the target/classes folder that are in the <code>packageName</code> package and are subclasses of <code>baseClass</code>.
     * @param packageName Package name.
     * @param projectDir Project root folder.
     * @param baseClass Base class.
     * @return List of matching classes.
     * @param <T> Base class type.
     */
    public static <T> List<Class<? extends T>> findClasses(String packageName, Path projectDir, Class<T> baseClass) {
        Path classesRootFolder = projectDir.resolve("target/classes");
        Path packageClassDir = classesRootFolder.resolve(packageName.replace('.', '/'));
        try {
            try (Stream<Path> pathStream = Files.walk(packageClassDir)) {
                List<Path> pathsToClassFiles = pathStream
                        .filter(path -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(".class"))
                        .collect(Collectors.toList());

                return pathsToClassFiles.stream()
                        .map(classesRootFolder::relativize)
                        .map(filePath -> filePath.toString()
                                .replace('\\', '.')
                                .replace('/', '.')
                                .replace(".class", ""))
                        .map(className -> (Class<? extends T>) tryLoadSubclass(className, baseClass))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    /**
     * Tries to load a class given a fully qualified class name, but only if it is assignable (a subclass) of the <code>baseClass</code> class.
     * @param className Fully qualified class name to be loaded.
     * @param baseClass Base class that the class must subclass.
     * @return Class object when the class was found and was a subclass of <code>ba</code> or null.
     * @param <T> Base class type.
     */
    public static <T> Class<? extends T> tryLoadSubclass(String className, Class<T> baseClass) {
        try {
            Class<?> classObject = Class.forName(className);
            if (baseClass.isAssignableFrom(classObject) && !Modifier.isAbstract(classObject.getModifiers())) {
                return (Class<? extends T>) classObject;
            }

            return null;
        }
        catch (ClassNotFoundException e) {
            System.err.println("Class " + className + " not found: " + e.getMessage());
            return null;
        }
        catch (NoClassDefFoundError e) {
            System.err.println("Class " + className + " depends on not found class: " + e.getCause().getMessage());
            return null;
        }
    }
}
