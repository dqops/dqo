/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
