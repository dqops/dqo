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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.List;

/**
 * Json serializer and deserializer.
 */
@Component
public interface JsonSerializer {
    /**
     * Serializes an object as a JSON text.
     * @param source Source object to be serialized.
     * @return Object serialized as a JSON string.
     * @throws JsonSerializationException
     */
    String serialize(Object source);

    /**
     * Serializes an object as a JSON text, using a pretty print writer.
     * @param source Source object to be serialized.
     * @return Object serialized as a JSON string, formatted.
     * @throws JsonSerializationException
     */
     String serializePrettyPrint(Object source);

    /**
     * Deserializes a JSON string to a class instance.
     * @param json Json file as a string.
     * @param clazz Target class type.
     * @param <T> Return class type (the same as the target class type).
     * @return Deserialized object instance.
     * @throws JsonSerializationException
     */
    <T> T deserialize(String json, Class<T> clazz);

    /**
     * Returns a configured JSON mapper with the correct configuration.
     * @return Configured JSON object mapper.
     */
    ObjectMapper getMapper();

    /**
     * Deserializes multiple JSONs from a stream reader.
     * @param reader Input stream reader.
     * @param clazz Target class.
     * @param <T> Json class object (target type).
     * @return List of deserialized objects.
     */
    <T> List<T> deserializeMultiple(InputStreamReader reader, Class<T> clazz);
}
