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

/**
 * Field reflection service that returns cached objects used to access fields on a class.
 */
public interface ReflectionService {
    /**
     * Returns a cached reflection info for a given class type.
     *
     * @param targetClass Target class type to introspect for fields.
     * @return Class info with a list of fields that will be serialized to YAML.
     */
    ClassInfo getClassInfoForClass(Class<?> targetClass);
}
