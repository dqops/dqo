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
