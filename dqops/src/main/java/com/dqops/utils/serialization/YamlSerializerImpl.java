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

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.utils.logging.UserErrorLogger;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Yaml serializer and deserializer.
 */
@Component
@Slf4j
public class YamlSerializerImpl implements YamlSerializer {
    private final ObjectMapper mapper;
    private final DqoConfigurationProperties configurationProperties;
    private final UserErrorLogger userErrorLogger;

    /**
     * Creates a yaml serializer.
     * @param configurationProperties Configuration properties.
     * @param userErrorLogger User error logger.
     */
    @Autowired
    public YamlSerializerImpl(DqoConfigurationProperties configurationProperties,
                              UserErrorLogger userErrorLogger) {
        this.configurationProperties = configurationProperties;
        this.userErrorLogger = userErrorLogger;
        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        yamlFactory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);

		this.mapper = new ObjectMapper(yamlFactory);
		this.mapper.findAndRegisterModules();
		this.mapper.registerModule(new JavaTimeModule());
        this.mapper.registerModule(new Jdk8Module());
        this.mapper.registerModule(new BlackbirdModule());
        this.mapper.registerModule(new DeserializationAwareModule());  // our custom module to notify objects that they were deserialized
        this.mapper.registerModule(new LooseDeserializationModule());
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
            String message = e.getOriginalMessage().replace('"', '\'');

            if (location != null) {
                message += " at line " + location.getLineNr() + ", column " + location.getColumnNr();
            }

            throw new YamlDeserializationException(message, null);
        }
        catch (Exception ex) {
            throw new YamlDeserializationException("Failed to deserialize a YAML file, error: " + ex.getMessage(), null, ex);
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
            String message = e.getOriginalMessage().replace('"', '\'');

            if (location != null) {
                message += " at line " + location.getLineNr() + ", column " + location.getColumnNr();
            }

            if (filePathForMessage != null) {
                message += ", file path: " + filePathForMessage;
            }

            if (this.userErrorLogger != null) {
                this.userErrorLogger.logYaml("Failed to deserialize YAML, " + message + ", file: " + filePathForMessage, null);
            }

            try {
                T emptyInstance = clazz.getDeclaredConstructor().newInstance();
                if (emptyInstance instanceof InvalidYamlStatusHolder) {
                    InvalidYamlStatusHolder invalidYamlStatusHolder = (InvalidYamlStatusHolder)emptyInstance;
                    invalidYamlStatusHolder.setYamlParsingError(message);
                }
                return emptyInstance;
            }
            catch (Exception ex) {
                throw new YamlDeserializationException("Failed to instantiate a new object, because a YAML file was empty: " + message, filePathForMessage, e);
            }
        }
        catch (Exception ex) {
            log.warn("Failed to deserialize YAML, file: " + filePathForMessage + ", error: " + ex.getMessage(), ex);
            throw new YamlDeserializationException("Failed to deserialize a YAML file " + filePathForMessage + ", error: " + ex.getMessage(), filePathForMessage, ex);
        }
    }
}
