package com.dqops.metadata.storage.localfiles.defaultschedules;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;

/**
 * The configuration of default schedules for running data quality checks.
 * The default schedules are stored in the settings/defaultschedules.dqoschedules.yaml file in the DQOps user's home folder.
 */
public class DefaultSchedulesYaml {

    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.DEFAULT_SCHEDULES;
    private DefaultSchedulesSpec spec = new DefaultSchedulesSpec();

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

}
