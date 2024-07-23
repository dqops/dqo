/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.metadata.incidents;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Configuration of addresses used for new or updated incident's notifications.
 * Specifies the Webhook URL or email address where the notification messages are sent.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class IncidentNotificationSpec extends AbstractSpec implements Cloneable, InvalidYamlStatusHolder {
    private static final ChildHierarchyNodeFieldMapImpl<IncidentNotificationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Notification address(es) where the notification messages describing new incidents are pushed using a HTTP POST request (for webhook address) or an SMTP (for email address). The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String incidentOpenedWebhookUrl;

    @JsonPropertyDescription("Notification address(es) where the notification messages describing acknowledged messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email address). The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String incidentAcknowledgedWebhookUrl;

    @JsonPropertyDescription("Notification address(es) where the notification messages describing resolved messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email address). The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String incidentResolvedWebhookUrl;

    @JsonPropertyDescription("Notification address(es) where the notification messages describing muted messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email address). The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String incidentMutedWebhookUrl;

    @JsonIgnore
    private String yamlParsingError;

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
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

    /**
     * Returns the URL where notifications of new incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getIncidentOpenedWebhookUrl() {
        return incidentOpenedWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of new incidents are posted.
     * @param incidentOpenedWebhookUrl Webhook url.
     */
    public void setIncidentOpenedWebhookUrl(String incidentOpenedWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.incidentOpenedWebhookUrl, incidentOpenedWebhookUrl));
        this.incidentOpenedWebhookUrl = incidentOpenedWebhookUrl;
    }

    /**
     * Returns the URL where notifications of acknowledged incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getIncidentAcknowledgedWebhookUrl() {
        return incidentAcknowledgedWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of acknowledged incidents are posted.
     * @param incidentAcknowledgedWebhookUrl Webhook url.
     */
    public void setIncidentAcknowledgedWebhookUrl(String incidentAcknowledgedWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.incidentAcknowledgedWebhookUrl, incidentAcknowledgedWebhookUrl));
        this.incidentAcknowledgedWebhookUrl = incidentAcknowledgedWebhookUrl;
    }

    /**
     * Returns the URL where notifications of resolved incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getIncidentResolvedWebhookUrl() {
        return incidentResolvedWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of resolved incidents are posted.
     * @param incidentResolvedWebhookUrl Webhook url.
     */
    public void setIncidentResolvedWebhookUrl(String incidentResolvedWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.incidentResolvedWebhookUrl, incidentResolvedWebhookUrl));
        this.incidentResolvedWebhookUrl = incidentResolvedWebhookUrl;
    }

    /**
     * Returns the URL where notifications of muted incidents are pushed using a HTTP POST request.
     * @return Webhook url.
     */
    public String getIncidentMutedWebhookUrl() {
        return incidentMutedWebhookUrl;
    }

    /**
     * Sets an url to a HTTP webhook where notifications of muted incidents are posted.
     * @param incidentMutedWebhookUrl Webhook url.
     */
    public void setIncidentMutedWebhookUrl(String incidentMutedWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.incidentMutedWebhookUrl, incidentMutedWebhookUrl));
        this.incidentMutedWebhookUrl = incidentMutedWebhookUrl;
    }

    /**
     * Returns a notification address for an incident status.
     * @param incidentStatus Incident status.
     * @return Notification address for incident status.
     */
    public String getNotificationAddressForStatus(IncidentStatus incidentStatus) {
        switch (incidentStatus) {
            case open:
                return this.incidentOpenedWebhookUrl;

            case acknowledged:
                return this.incidentAcknowledgedWebhookUrl;

            case resolved:
                return this.incidentResolvedWebhookUrl;

            case muted:
                return this.incidentMutedWebhookUrl;

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
    public IncidentNotificationSpec deepClone() {
        IncidentNotificationSpec cloned = (IncidentNotificationSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Cloned and expanded copy of the object.
     */
    public IncidentNotificationSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        IncidentNotificationSpec cloned = this.deepClone();
        cloned.incidentOpenedWebhookUrl = secretValueProvider.expandValue(cloned.incidentOpenedWebhookUrl, lookupContext);
        cloned.incidentAcknowledgedWebhookUrl = secretValueProvider.expandValue(cloned.incidentAcknowledgedWebhookUrl, lookupContext);
        cloned.incidentResolvedWebhookUrl = secretValueProvider.expandValue(cloned.incidentResolvedWebhookUrl, lookupContext);
        cloned.incidentMutedWebhookUrl = secretValueProvider.expandValue(cloned.incidentMutedWebhookUrl, lookupContext);
        return cloned;
    }

    /**
     * Combines the notification webhooks spec with the default webhooks. If the incident status' webhook is null, the corresponding value from default is set.
     * @param defaultWebhooks Default incident webhook notification spec
     * @return Combined IncidentNotificationSpec object with default webhooks.
     */
    public IncidentNotificationSpec combineWithDefaults(IncidentNotificationSpec defaultWebhooks){
        IncidentNotificationSpec clonedWebhooks = this.deepClone();

        if(clonedWebhooks.getIncidentOpenedWebhookUrl() == null){
            clonedWebhooks.setIncidentOpenedWebhookUrl(defaultWebhooks.getIncidentOpenedWebhookUrl());
        }

        if(clonedWebhooks.getIncidentAcknowledgedWebhookUrl() == null){
            clonedWebhooks.setIncidentAcknowledgedWebhookUrl(defaultWebhooks.getIncidentAcknowledgedWebhookUrl());
        }

        if(clonedWebhooks.getIncidentResolvedWebhookUrl() == null){
            clonedWebhooks.setIncidentResolvedWebhookUrl(defaultWebhooks.getIncidentResolvedWebhookUrl());
        }

        if(clonedWebhooks.getIncidentMutedWebhookUrl() == null){
            clonedWebhooks.setIncidentMutedWebhookUrl(defaultWebhooks.getIncidentMutedWebhookUrl());
        }

        return clonedWebhooks;
    }

    public static class IncidentNotificationSpecSampleFactory implements SampleValueFactory<IncidentNotificationSpec> {
        @Override
        public IncidentNotificationSpec createSample() {
            return new IncidentNotificationSpec() {{
                setIncidentOpenedWebhookUrl(SampleStringsRegistry.getSampleUrl() + "/opened");
                setIncidentAcknowledgedWebhookUrl(SampleStringsRegistry.getSampleUrl() + "/acknowledged");
                setIncidentResolvedWebhookUrl(SampleStringsRegistry.getSampleUrl() + "/resolved");
                setIncidentMutedWebhookUrl(SampleStringsRegistry.getSampleUrl() + "/muted");
            }};
        }
    }
}
