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
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Configuration of addresses used for new or updated incident's notifications.
 * Specifies the webhook URLs or email addresses where the notification messages are sent.
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
    private String incidentOpenedAddresses;

    @JsonPropertyDescription("Notification address(es) where the notification messages describing acknowledged messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email address). The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String incidentAcknowledgedAddresses;

    @JsonPropertyDescription("Notification address(es) where the notification messages describing resolved messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email address). The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String incidentResolvedAddresses;

    @JsonPropertyDescription("Notification address(es) where the notification messages describing muted messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email address). The format of the JSON message is documented in the IncidentNotificationMessage object.")
    private String incidentMutedAddresses;

    @JsonPropertyDescription("Obsolete, use the incidentOpenedAddresses instead.")
    @Getter
    private String incidentOpenedWebhookUrl;

    @JsonPropertyDescription("Obsolete, use the incidentAcknowledgedAddresses instead.")
    @Getter
    private String incidentAcknowledgedWebhookUrl;

    @JsonPropertyDescription("Obsolete, use the incidentResolvedAddresses instead.")
    @Getter
    private String incidentResolvedWebhookUrl;

    @JsonPropertyDescription("Obsolete, use the incidentMutedAddresses instead.")
    @Getter
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
     * Returns the addresses where notifications of new incidents are sent.
     * @return Webhook URLs or email addresses.
     */
    public String getIncidentOpenedAddresses() {
        if(incidentOpenedAddresses == null){
            return incidentOpenedWebhookUrl;
        }
        return incidentOpenedAddresses;
    }

    /**
     * Sets addresses where notifications of new incidents are sent.
     * @param incidentOpenedAddresses Webhook URLs or email addresses.
     */
    public void setIncidentOpenedAddresses(String incidentOpenedAddresses) {
        this.setDirtyIf(!Objects.equals(this.incidentOpenedAddresses, incidentOpenedAddresses));
        this.incidentOpenedAddresses = incidentOpenedAddresses;
    }

    /**
     * Returns addresses where notifications of acknowledged incidents are sent.
     * @return Webhook URLs or email addresses
     */
    public String getIncidentAcknowledgedAddresses() {
        if(incidentAcknowledgedAddresses != null){
            return incidentAcknowledgedAddresses;
        }
        return incidentAcknowledgedWebhookUrl;
    }

    /**
     * Sets addresses where notifications of acknowledged incidents are sent.
     * @param incidentAcknowledgedAddresses Webhook url.
     */
    public void setIncidentAcknowledgedAddresses(String incidentAcknowledgedAddresses) {
        this.setDirtyIf(!Objects.equals(this.incidentAcknowledgedAddresses, incidentAcknowledgedAddresses));
        this.incidentAcknowledgedAddresses = incidentAcknowledgedAddresses;
    }

    /**
     * Returns addresses where notifications of resolved incidents are pushed using a HTTP POST request.
     * @return Webhook URLs or email addresses
     */
    public String getIncidentResolvedAddresses() {
        if(incidentResolvedAddresses != null){
            return incidentResolvedAddresses;
        }
        return incidentResolvedWebhookUrl;
    }

    /**
     * Sets addresses where notifications of resolved incidents are sent.
     * @param incidentResolvedAddresses Webhook URLs or email addresses
     */
    public void setIncidentResolvedAddresses(String incidentResolvedAddresses) {
        this.setDirtyIf(!Objects.equals(this.incidentResolvedAddresses, incidentResolvedAddresses));
        this.incidentResolvedAddresses = incidentResolvedAddresses;
    }

    /**
     * Returns addresses where notifications of muted incidents are pushed using a HTTP POST request.
     * @return Webhook URLs or email addresses
     */
    public String getIncidentMutedAddresses() {
        if(incidentMutedAddresses != null){
            return incidentMutedAddresses;
        }
        return incidentMutedWebhookUrl;
    }

    /**
     * Sets addresses where notifications of muted incidents are sent.
     * @param incidentMutedAddresses Webhook URLs or email addresses
     */
    public void setIncidentMutedAddresses(String incidentMutedAddresses) {
        this.setDirtyIf(!Objects.equals(this.incidentMutedAddresses, incidentMutedAddresses));
        this.incidentMutedAddresses = incidentMutedAddresses;
    }

    public void setIncidentOpenedWebhookUrl(String incidentOpenedWebhookUrl) {
        this.incidentOpenedAddresses = incidentOpenedWebhookUrl;
    }

    public void setIncidentAcknowledgedWebhookUrl(String incidentAcknowledgedWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.incidentAcknowledgedAddresses, incidentAcknowledgedWebhookUrl));
        this.incidentAcknowledgedAddresses = incidentAcknowledgedWebhookUrl;
    }

    public void setIncidentResolvedWebhookUrl(String incidentResolvedWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.incidentResolvedAddresses, incidentResolvedWebhookUrl));
        this.incidentResolvedAddresses = incidentResolvedWebhookUrl;
    }

    public void setIncidentMutedWebhookUrl(String incidentMutedWebhookUrl) {
        this.setDirtyIf(!Objects.equals(this.incidentMutedAddresses, incidentMutedWebhookUrl));
        this.incidentMutedAddresses = incidentMutedWebhookUrl;
    }

    /**
     * Returns a notification address for an incident status.
     * @param incidentStatus Incident status.
     * @return Notification address for incident status.
     */
    public String getNotificationAddressForStatus(IncidentStatus incidentStatus) {
        switch (incidentStatus) {
            case open:
                return this.incidentOpenedAddresses;

            case acknowledged:
                return this.incidentAcknowledgedAddresses;

            case resolved:
                return this.incidentResolvedAddresses;

            case muted:
                return this.incidentMutedAddresses;

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
        cloned.incidentOpenedAddresses = secretValueProvider.expandValue(cloned.incidentOpenedAddresses, lookupContext);
        cloned.incidentAcknowledgedAddresses = secretValueProvider.expandValue(cloned.incidentAcknowledgedAddresses, lookupContext);
        cloned.incidentResolvedAddresses = secretValueProvider.expandValue(cloned.incidentResolvedAddresses, lookupContext);
        cloned.incidentMutedAddresses = secretValueProvider.expandValue(cloned.incidentMutedAddresses, lookupContext);
        return cloned;
    }

    /**
     * Combines the incident notification spec with the default addresses. If the incident status' address is null, the corresponding value from default is set.
     * @param defaultIncidentNotification Default incident notification spec.
     * @return Combined IncidentNotificationSpec object with default addresses.
     */
    public IncidentNotificationSpec combineWithDefaults(IncidentNotificationSpec defaultIncidentNotification){
        IncidentNotificationSpec clonedIncidentNotification = this.deepClone();

        if(clonedIncidentNotification.getIncidentOpenedAddresses() == null){
            clonedIncidentNotification.setIncidentOpenedAddresses(defaultIncidentNotification.getIncidentOpenedAddresses());
        }

        if(clonedIncidentNotification.getIncidentAcknowledgedAddresses() == null){
            clonedIncidentNotification.setIncidentAcknowledgedAddresses(defaultIncidentNotification.getIncidentAcknowledgedAddresses());
        }

        if(clonedIncidentNotification.getIncidentResolvedAddresses() == null){
            clonedIncidentNotification.setIncidentResolvedAddresses(defaultIncidentNotification.getIncidentResolvedAddresses());
        }

        if(clonedIncidentNotification.getIncidentMutedAddresses() == null){
            clonedIncidentNotification.setIncidentMutedAddresses(defaultIncidentNotification.getIncidentMutedAddresses());
        }

        return clonedIncidentNotification;
    }

    public static class IncidentNotificationSpecSampleFactory implements SampleValueFactory<IncidentNotificationSpec> {
        @Override
        public IncidentNotificationSpec createSample() {
            return new IncidentNotificationSpec() {{
                setIncidentOpenedAddresses(SampleStringsRegistry.getSampleUrl() + "/opened");
                setIncidentAcknowledgedAddresses(SampleStringsRegistry.getSampleUrl() + "/acknowledged");
                setIncidentResolvedAddresses(SampleStringsRegistry.getSampleUrl() + "/resolved");
                setIncidentMutedAddresses(SampleStringsRegistry.getSampleUrl() + "/muted");
            }};
        }
    }
}
