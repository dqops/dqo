/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.incidents;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Configuration of addresses used for new or updated incident's notifications.
 * Specifies the webhook URLs or email addresses where the notification messages are sent.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class IncidentNotificationTargetSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<IncidentNotificationTargetSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
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

    /**
     * Returns the addresses where notifications of new incidents are sent.
     * @return Webhook URLs or email addresses.
     */
    public String getIncidentOpenedAddresses() {
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
        return incidentAcknowledgedAddresses;
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
        return incidentResolvedAddresses;
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
        return incidentMutedAddresses;
    }

    /**
     * Sets addresses where notifications of muted incidents are sent.
     * @param incidentMutedAddresses Webhook URLs or email addresses
     */
    public void setIncidentMutedAddresses(String incidentMutedAddresses) {
        this.setDirtyIf(!Objects.equals(this.incidentMutedAddresses, incidentMutedAddresses));
        this.incidentMutedAddresses = incidentMutedAddresses;
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
    public IncidentNotificationTargetSpec deepClone() {
        IncidentNotificationTargetSpec cloned = (IncidentNotificationTargetSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a cloned and expanded version of the objects. All parameters are changed to the values expanded from variables like ${ENV_VAR}.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Cloned and expanded copy of the object.
     */
    public IncidentNotificationTargetSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        IncidentNotificationTargetSpec cloned = this.deepClone();
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
    public IncidentNotificationTargetSpec combineWithDefaults(IncidentNotificationTargetSpec defaultIncidentNotification){
        IncidentNotificationTargetSpec clonedIncidentNotification = this.deepClone();

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

    public static class IncidentNotificationSpecSampleFactory implements SampleValueFactory<IncidentNotificationTargetSpec> {
        @Override
        public IncidentNotificationTargetSpec createSample() {
            return new IncidentNotificationTargetSpec() {{
                setIncidentOpenedAddresses(SampleStringsRegistry.getSampleUrl() + "/opened");
                setIncidentAcknowledgedAddresses(SampleStringsRegistry.getSampleUrl() + "/acknowledged");
                setIncidentResolvedAddresses(SampleStringsRegistry.getSampleUrl() + "/resolved");
                setIncidentMutedAddresses(SampleStringsRegistry.getSampleUrl() + "/muted");
            }};
        }
    }
}
