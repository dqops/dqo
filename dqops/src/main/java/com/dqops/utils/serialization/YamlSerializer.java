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
package com.dqops.utils.serialization;

import java.nio.file.Path;

/**
 * Yaml serializer and deserializer.
 */
public interface YamlSerializer {
    /**
     * Serializes an object as a YAML text.
     * @param source Source object to be serialized.
     * @return Object serialized as a YAML text.
     * @throws YamlSerializationException
     */
    String serialize(Object source);

    /**
     * Deserializes a YAML string to a class instance.
     * @param yaml Yaml as a string.
     * @param clazz Target class type.
     * @param <T> Return class type (the same as the target class type).
     * @return Deserialized object instance.
     * @throws YamlSerializationException
     */
    <T> T deserialize(String yaml, Class<T> clazz);

    /**
     * Deserializes a YAML string to a class instance.
     * @param yaml Yaml as a string.
     * @param clazz Target class type.
     * @param filePathForMessage Additional file path to the file that is parsed. It is just added to the error message if parsing fails.
     * @param <T> Return class type (the same as the target class type).
     * @return Deserialized object instance.
     * @throws YamlSerializationException
     */
    <T> T deserialize(String yaml, Class<T> clazz, Path filePathForMessage);
}
