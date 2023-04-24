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

package ai.dqo.metadata.incidents;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.data.incidents.factory.IncidentStatus;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Configuration of Webhook URLs used for new or updated incident's notifications.
 * Specifies the URLs of webhooks where the notification messages are sent.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class IncidentWebhookNotificationsSpec  extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<IncidentWebhookNotificationsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Webhook URL where the notification messages describing new incidents are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String openIncidentWebhookUrl;

    @JsonPropertyDescription("Webhook URL where the notification messages describing acknowledged messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String acknowledgedIncidentWebhookUrl;

    @JsonPropertyDescription("Webhook URL where the notification messages describing resolved messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String resolvedIncidentWebhookUrl;

    @JsonPropertyDescription("Webhook URL where the notification messages describing muted messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String mutedIncidentWebhookUrl;

    /**
     * Returns the URL where notifications of new incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getOpenIncidentWebhookUrl() {
        return openIncidentWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of new incidents are posted.
     * @param openIncidentWebhookUrl Webhook url.
     */
    public void setOpenIncidentWebhookUrl(String openIncidentWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.openIncidentWebhookUrl, openIncidentWebhookUrl));
        this.openIncidentWebhookUrl = openIncidentWebhookUrl;
    }

    /**
     * Returns the URL where notifications of acknowledged incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getAcknowledgedIncidentWebhookUrl() {
        return acknowledgedIncidentWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of acknowledged incidents are posted.
     * @param acknowledgedIncidentWebhookUrl Webhook url.
     */
    public void setAcknowledgedIncidentWebhookUrl(String acknowledgedIncidentWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.acknowledgedIncidentWebhookUrl, acknowledgedIncidentWebhookUrl));
        this.acknowledgedIncidentWebhookUrl = acknowledgedIncidentWebhookUrl;
    }

    /**
     * Returns the URL where notifications of resolved incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getResolvedIncidentWebhookUrl() {
        return resolvedIncidentWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of resolved incidents are posted.
     * @param resolvedIncidentWebhookUrl Webhook url.
     */
    public void setResolvedIncidentWebhookUrl(String resolvedIncidentWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.resolvedIncidentWebhookUrl, resolvedIncidentWebhookUrl));
        this.resolvedIncidentWebhookUrl = resolvedIncidentWebhookUrl;
    }

    /**
     * Returns the URL where notifications of muted incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getMutedIncidentWebhookUrl() {
        return mutedIncidentWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of muted incidents are posted.
     * @param mutedIncidentWebhookUrl Webhook url.
     */
    public void setMutedIncidentWebhookUrl(String mutedIncidentWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.mutedIncidentWebhookUrl, mutedIncidentWebhookUrl));
        this.mutedIncidentWebhookUrl = mutedIncidentWebhookUrl;
    }

    /**
     * Returns a webhook url for an incident status.
     * @param incidentStatus Incident status.
     * @return Webhook URL for incident status.
     */
    public String getWebhookUrlForStatus(IncidentStatus incidentStatus) {
        switch (incidentStatus) {
            case open:
                return this.openIncidentWebhookUrl;

            case acknowledged:
                return this.acknowledgedIncidentWebhookUrl;

            case resolved:
                return this.resolvedIncidentWebhookUrl;

            case muted:
                return this.mutedIncidentWebhookUrl;

            default:
                throw new NoSuchElementException("Unsupported incident status: " + incidentStatus);
        }
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public IncidentWebhookNotificationsSpec deepClone() {
        IncidentWebhookNotificationsSpec cloned = (IncidentWebhookNotificationsSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public IncidentWebhookNotificationsSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        IncidentWebhookNotificationsSpec cloned = this.deepClone();
        cloned.openIncidentWebhookUrl = secretValueProvider.expandValue(cloned.openIncidentWebhookUrl);
        cloned.acknowledgedIncidentWebhookUrl = secretValueProvider.expandValue(cloned.acknowledgedIncidentWebhookUrl);
        cloned.resolvedIncidentWebhookUrl = secretValueProvider.expandValue(cloned.resolvedIncidentWebhookUrl);
        cloned.mutedIncidentWebhookUrl = secretValueProvider.expandValue(cloned.mutedIncidentWebhookUrl);
        return cloned;
    }
}
