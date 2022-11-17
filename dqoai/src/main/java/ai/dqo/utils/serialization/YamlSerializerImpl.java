/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.serialization;

import ai.dqo.core.configuration.DqoConfigurationProperties;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Yaml serializer and deserializer.
 */
@Component
public class YamlSerializerImpl implements YamlSerializer {
    private final ObjectMapper mapper;
    private final DqoConfigurationProperties configurationProperties;

    /**
     * Creates a yaml serializer.
     * @param configurationProperties Configuration properties.
     */
    @Autowired
    public YamlSerializerImpl(DqoConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        yamlFactory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);

		this.mapper = new ObjectMapper(yamlFactory);
		this.mapper.findAndRegisterModules();
		this.mapper.registerModule(new JavaTimeModule());
        this.mapper.registerModule(new Jdk8Module());
        this.mapper.registerModule(new BlackbirdModule());
        this.mapper.registerModule(new DeserializationAwareModule());  // our custom module to notify objects that they were deserialized
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		this.mapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
		this.mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);

		this.mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
    }

    /**
     * Serializes an object as a YAML text.
     * @param source Source object to be serialized.
     * @return Object serialized as a YAML text.
     * @throws YamlSerializationException
     */
    public String serialize(Object source) {
        try {
            String className = source.getClass().getSimpleName();
            String schemaUrl = this.configurationProperties.getYamlSchemaServer();
			String yamlString = this.mapper.writeValueAsString(source);
            if (!schemaUrl.endsWith("/")) {
                schemaUrl += "/";
            }
            schemaUrl += className + "-schema.json";
            String yamlServerLine = "# yaml-language-server: $schema=" + schemaUrl + "\n";
            return yamlServerLine + yamlString;
        } catch (JsonProcessingException e) {
            throw new YamlSerializationException("YAML serialization failed", e);
        }
    }

    /**
     * Deserializes a YAML string to a class instance.
     * @param yaml Yaml as a string.
     * @param clazz Target class type.
     * @param <T> Return class type (the same as the target class type).
     * @return Deserialized object instance.
     * @throws YamlSerializationException
     */
    public <T> T deserialize(String yaml, Class<T> clazz) {
        try {
            return this.mapper.readValue(yaml, clazz);
        }
        catch (JacksonException e) {
            JsonLocation location = e.getLocation();
            String message = e.getOriginalMessage();

            if (location != null) {
                message += " at line " + location.getLineNr() + ", column " + location.getColumnNr();
            }

            throw new YamlSerializationException(message, e);
        }
    }

    /**
     * Deserializes a YAML string to a class instance.
     * @param yaml Yaml as a string.
     * @param clazz Target class type.
     * @param filePathForMessage Additional file path to the file that is parsed. It is just added to the error message if parsing fails.
     * @param <T> Return class type (the same as the target class type).
     * @return Deserialized object instance.
     * @throws YamlSerializationException
     */
    public <T> T deserialize(String yaml, Class<T> clazz, Path filePathForMessage) {
        try {
            return this.mapper.readValue(yaml, clazz);
        }
        catch (JacksonException e) {
            JsonLocation location = e.getLocation();
            String message = e.getOriginalMessage();

            if (location != null) {
                message += " at line " + location.getLineNr() + ", column " + location.getColumnNr();
            }

            if (filePathForMessage != null) {
                message += ", file path: " + filePathForMessage;
            }

            throw new YamlSerializationException(message, e);
        }
    }
}
