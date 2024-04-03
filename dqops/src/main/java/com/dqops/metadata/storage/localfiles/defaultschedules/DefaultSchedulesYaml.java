package com.dqops.metadata.storage.localfiles.defaultschedules;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.reflection.DefaultFieldValue;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * The configuration of default schedules for running data quality checks.
 * The default schedules are stored in the *$DQO_USER_HOME/settings/defaultschedules.dqoschedules.yaml* file in the DQOps user's home folder.
 */
public class DefaultSchedulesYaml implements InvalidYamlStatusHolder {
    @JsonPropertyDescription("DQOps YAML schema version")
    @DefaultFieldValue(ApiVersion.CURRENT_API_VERSION)
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;

    @JsonPropertyDescription("File type")
    @DefaultFieldValue("default_schedules")
    private SpecificationKind kind = SpecificationKind.default_schedules;

    @JsonPropertyDescription("The configuration object with the definition of the default schedules")
    private DefaultSchedulesSpec spec = new DefaultSchedulesSpec();

    @JsonIgnore
    private String yamlParsingError;

    public DefaultSchedulesYaml() {
    }

    public DefaultSchedulesYaml(DefaultSchedulesSpec spec) {
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
     * Returns a data source specification.
     * @return Data source specification.
     */
    public DefaultSchedulesSpec getSpec() {
        return spec;
    }

    /**
     * Sets a data source specification.
     * @param spec Data source specification.
     */
    public void setSpec(DefaultSchedulesSpec spec) {
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
