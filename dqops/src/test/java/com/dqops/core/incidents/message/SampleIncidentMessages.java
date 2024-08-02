package com.dqops.core.incidents.message;

import com.dqops.data.incidents.factory.IncidentStatus;

import java.time.Instant;

public class SampleIncidentMessages {

    public static IncidentNotificationMessage createSampleIncidentMessage(Instant instant, IncidentStatus incidentStatus) {
        IncidentNotificationMessage notificationMessage = new IncidentNotificationMessage();
        notificationMessage.setIncidentId("1");
        notificationMessage.setConnection("connection_name");
        notificationMessage.setSchema("schema_here");
        notificationMessage.setTable("table_name_here");
        notificationMessage.setTablePriority(2);
        notificationMessage.setIncidentHash(3L);
        notificationMessage.setFirstSeen(instant);
        notificationMessage.setLastSeen(instant);
        notificationMessage.setIncidentUntil(instant);
        notificationMessage.setQualityDimension("Reasonableness");
        notificationMessage.setCheckCategory("volume");
        notificationMessage.setCheckType("");
        notificationMessage.setCheckName("");
        notificationMessage.setIssueUrl("");
        notificationMessage.setHighestSeverity(3);
        notificationMessage.setFailedChecksCount(10);
        notificationMessage.setStatus(incidentStatus);
        return notificationMessage;
    }

}
