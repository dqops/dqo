package com.dqops.metadata.incidents;

import com.dqops.data.incidents.factory.IncidentStatus;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.NoSuchElementException;

/**
 * Intermediate model that contains notification target addresses common to IncidentNotificationSpec and FilteredNotificationSpec.
 */
@Data
public class NotificationCommonModel {

    @JsonPropertyDescription("Notification target addresses for each of the status.")
    private IncidentNotificationTargetSpec targetSpec;

    @JsonPropertyDescription("Message with the details of the filtered notification such as purpose explanation, SLA note, etc.")
    private String message;

    /**
     * Returns a notification address for an incident status.
     * @param incidentStatus Incident status.
     * @return Notification address for incident status.
     */
    public String getNotificationAddressForStatus(IncidentStatus incidentStatus) {
        switch (incidentStatus) {
            case open:
                return targetSpec.getIncidentOpenedAddresses();

            case acknowledged:
                return targetSpec.getIncidentAcknowledgedAddresses();

            case resolved:
                return targetSpec.getIncidentResolvedAddresses();

            case muted:
                return targetSpec.getIncidentMutedAddresses();

            default:
                throw new NoSuchElementException("Unsupported incident status: " + incidentStatus);
        }
    }

    /**
     * Creates a IncidentNotificationTargetSpec from the corresponding incident status addresses from IncidentNotificationSpec object.
     * @param filteredNotificationSpec The filtered notification spec.
     * @return IncidentNotificationTargetSpec object.
     */
    public static NotificationCommonModel from(FilteredNotificationSpec filteredNotificationSpec){
        IncidentNotificationTargetSpec incidentNotificationTargetSpec = filteredNotificationSpec.getTarget();
        NotificationCommonModel notificationCommonModel = new NotificationCommonModel(){{
            setTargetSpec(incidentNotificationTargetSpec);
            setMessage(filteredNotificationSpec.getMessage());
        }};
        return notificationCommonModel;
    }

    /**
     * Creates a IncidentNotificationTargetSpec from the corresponding incident status addresses from IncidentNotificationSpec object.
     * @param incidentNotificationSpec The incident notification spec.
     * @return IncidentNotificationTargetSpec object.
     */
    public static NotificationCommonModel from(IncidentNotificationSpec incidentNotificationSpec){
        IncidentNotificationTargetSpec incidentNotificationTargetSpec = new IncidentNotificationTargetSpec(){{
            setIncidentOpenedAddresses(incidentNotificationSpec.getIncidentOpenedAddresses());
            setIncidentAcknowledgedAddresses(incidentNotificationSpec.getIncidentAcknowledgedAddresses());
            setIncidentResolvedAddresses(incidentNotificationSpec.getIncidentResolvedAddresses());
            setIncidentMutedAddresses(incidentNotificationSpec.getIncidentMutedAddresses());
        }};
        NotificationCommonModel notificationCommonModel = new NotificationCommonModel(){{
            setTargetSpec(incidentNotificationTargetSpec);
        }};
        return notificationCommonModel;
    }

}
