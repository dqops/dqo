package com.dqops.metadata.storage.localfiles.defaultobservabilitychecks;

import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.reflection.DefaultFieldValue;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * The configuration of default data quality checks that are activated for all imported tables and columns.
 * The default observability checks are stored in the *$DQO_USER_HOME/settings/default.dqodefaultchecks.yaml* file in the DQOps user's home folder.
 */
public class DefaultObservabilityChecksYaml implements InvalidYamlStatusHolder {
    @JsonPropertyDescription("DQOps YAML schema version")
    @DefaultFieldValue(ApiVersion.CURRENT_API_VERSION)
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;

    @JsonPropertyDescription("File type")
    @DefaultFieldValue("default_checks")
    private SpecificationKind kind = SpecificationKind.default_checks;

    @JsonPropertyDescription("The configuration object with the definition of the default data observability checks")
    private DefaultObservabilityChecksSpec spec = new DefaultObservabilityChecksSpec();

    @JsonIgnore
    private String yamlParsingError;

    public DefaultObservabilityChecksYaml() {
    }

    public DefaultObservabilityChecksYaml(DefaultObservabilityChecksSpec spec) {
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
    public DefaultObservabilityChecksSpec getSpec() {
        return spec;
    }

    /**
     * Sets a data source specification.
     * @param spec Data source specification.
     */
    public void setSpec(DefaultObservabilityChecksSpec spec) {
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
