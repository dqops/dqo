package com.dqops.metadata.storage.localfiles.defaultnotifications;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The default configuration of notifications. Notifications are published by calling webhooks defined in this object.
 * The default notification settings are stored in the *$DQO_USER_HOME/settings/defaultnotifications.dqonotifications.yaml* file in the DQOps user's home folder.
 */
public class DefaultNotificationsYaml  implements InvalidYamlStatusHolder {
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.DEFAULT_NOTIFICATIONS;
    private IncidentWebhookNotificationsSpec spec = new IncidentWebhookNotificationsSpec();

    @JsonIgnore
    private String yamlParsingError;

    public DefaultNotificationsYaml() {
    }

    public DefaultNotificationsYaml(IncidentWebhookNotificationsSpec spec) {
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
    public IncidentWebhookNotificationsSpec getSpec() {
        return spec;
    }

    /**
     * Sets a data source specification.
     * @param spec Data source specification.
     */
    public void setSpec(IncidentWebhookNotificationsSpec spec) {
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
