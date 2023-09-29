package com.dqops.metadata.storage.localfiles.webhooks;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;

/**
 * DQO incident webhook notification YAML schema for a default notification webhooks specification.
 */
public class DefaultIncidentWebhookNotificationsYaml {

    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.NOTIFICATION_WEBHOOKS;
    private IncidentWebhookNotificationsSpec spec = new IncidentWebhookNotificationsSpec();

    public DefaultIncidentWebhookNotificationsYaml() {
    }

    public DefaultIncidentWebhookNotificationsYaml(IncidentWebhookNotificationsSpec spec) {
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

}
