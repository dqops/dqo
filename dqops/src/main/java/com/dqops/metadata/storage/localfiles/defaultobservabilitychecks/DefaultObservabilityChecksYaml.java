package com.dqops.metadata.storage.localfiles.defaultobservabilitychecks;

import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.storage.localfiles.SpecificationKind;

/**
 * The configuration of default data quality checks that are activated for all imported tables and columns.
 * The default observability checks are stored in the settings/defaultchecks.dqochecks.yaml file in the DQO user's home folder.
 */
public class DefaultObservabilityChecksYaml {
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.DEFAULT_CHECKS;
    private DefaultObservabilityChecksSpec spec = new DefaultObservabilityChecksSpec();

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

}
