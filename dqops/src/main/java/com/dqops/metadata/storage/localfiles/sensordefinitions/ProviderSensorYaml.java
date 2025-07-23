/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.sensordefinitions;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.reflection.DefaultFieldValue;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Provider specific data quality sensor definition YAML schema for a data quality sensor configuration specification.
 */
public class ProviderSensorYaml implements InvalidYamlStatusHolder {
    @JsonPropertyDescription("DQOps YAML schema version")
    @DefaultFieldValue(ApiVersion.CURRENT_API_VERSION)
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;

    @JsonPropertyDescription("File type")
    @DefaultFieldValue("provider_sensor")
    private SpecificationKind kind = SpecificationKind.provider_sensor;

    @JsonPropertyDescription("Custom data quality data source specific sensor specification object with definition of a custom sensor for that type of the data source")
    private ProviderSensorDefinitionSpec spec = new ProviderSensorDefinitionSpec();

    @JsonIgnore
    private String yamlParsingError;

    /**
     * Creates an empty yaml object.
     */
    public ProviderSensorYaml() {
    }

    /**
     * Create a root yaml object given a specification object.
     * @param spec Specification object.
     */
    public ProviderSensorYaml(ProviderSensorDefinitionSpec spec) {
        this.spec = spec;
    }

    /**
     * Current api version. The current version is dqo/v1
     * @return Current api version.
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Sets the api version for the serialized file.
     * @param apiVersion Api version.
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Returns the YAML file kind.
     * @return Yaml file kind.
     */
    public SpecificationKind getKind() {
        return kind;
    }

    /**
     * Sets YAML file kind.
     * @param kind YAML file kind.
     */
    public void setKind(SpecificationKind kind) {
        this.kind = kind;
    }

    /**
     * Returns a provider sensor configuration specification.
     * @return Provider sensor configuration specification.
     */
    public ProviderSensorDefinitionSpec getSpec() {
        return spec;
    }

    /**
     * Sets a provider sensor configuration specification.
     * @param spec Provider sensor configuration specification.
     */
    public void setSpec(ProviderSensorDefinitionSpec spec) {
        this.spec = spec;
    }

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        if (this.spec != null) {
            this.spec.setYamlParsingError(yamlParsingError);
        }
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }
}
