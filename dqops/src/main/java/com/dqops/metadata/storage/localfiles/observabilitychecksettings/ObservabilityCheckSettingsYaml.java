package com.dqops.metadata.storage.localfiles.observabilitychecksettings;

import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.storage.localfiles.SpecificationKind;

public class ObservabilityCheckSettingsYaml {

    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.OBSERVABILITY_CHECK_SETTINGS;
    private DefaultObservabilityCheckSettingsSpec spec = new DefaultObservabilityCheckSettingsSpec();

    public ObservabilityCheckSettingsYaml() {
    }

    public ObservabilityCheckSettingsYaml(DefaultObservabilityCheckSettingsSpec spec) {
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
    public DefaultObservabilityCheckSettingsSpec getSpec() {
        return spec;
    }

    /**
     * Sets a data source specification.
     * @param spec Data source specification.
     */
    public void setSpec(DefaultObservabilityCheckSettingsSpec spec) {
        this.spec = spec;
    }

}
