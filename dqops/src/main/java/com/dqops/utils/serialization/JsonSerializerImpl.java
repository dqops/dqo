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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Json serializer and deserializer.
 */
@Component
public class JsonSerializerImpl implements JsonSerializer {
    private final ObjectMapper mapper;

    /**
     * Creates and configures a json serializer.
     */
    public JsonSerializerImpl() {
		this.mapper = new ObjectMapper();
		this.mapper.findAndRegisterModules();
		this.mapper.registerModule(new JavaTimeModule());
        this.mapper.registerModule(new Jdk8Module());
        this.mapper.registerModule(new BlackbirdModule());
        this.mapper.registerModule(new DeserializationAwareModule());  // our custom module to notify objects that they were deserialized
		this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		this.mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);

		this.mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);

        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Serializes an object as a JSON text.
     * @param source Source object to be serialized.
     * @return Object serialized as a JSON string.
     * @throws JsonSerializationException
     */
    @Override
    public String serialize(Object source) {
        try {
            return this.mapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("JSON serialization failed", e);
        }
    }

    /**
     * Serializes an object as a JSON text, using a pretty print writer.
     * @param source Source object to be serialized.
     * @return Object serialized as a JSON string, formatted.
     * @throws JsonSerializationException
     */
    @Override
    public String serializePrettyPrint(Object source) {
        try {
            return this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("JSON serialization failed", e);
        }
    }

    /**
     * Deserializes a JSON string to a class instance.
     * @param json Json file as a string.
     * @param clazz Target class type.
     * @param <T> Return class type (the same as the target class type).
     * @return Deserialized object instance.
     * @throws JsonSerializationException
     */
    public <T> T deserialize(String json, Class<T> clazz) {
        try {
            return this.mapper.readValue(json, clazz);
        }
        catch (JsonProcessingException e) {
            throw new JsonSerializationException("JSON deserialization failed", e);
        }
    }

    /**
     * Deserializes multiple jsons from a stream reader.
     * @param reader Input stream reader.
     * @param clazz Target class.
     * @param <T> Json class object (target type).
     * @return List of deserialized objects.
     */
    public <T> List<T> deserializeMultiple(InputStreamReader reader, Class<T> clazz) {
        try {
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                JsonFactory jsonFactory = new JsonFactory();
                JsonParser jsonParser = jsonFactory.createParser(bufferedReader);
                MappingIterator<T> mappingIterator = this.mapper.readValues(jsonParser, clazz);
                return mappingIterator.readAll();
            }
        }
        catch (IOException e) {
            throw new JsonSerializationException("JSON deserialization failed", e);
        }
    }

    /**
     * Returns a configured JSON mapper with the correct configuration.
     * @return Configured JSON object mapper.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
