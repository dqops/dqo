package com.dqops.core.incidents;

public interface IncidentNotificationMessageTextCreator {
    String prepareText(IncidentNotificationMessageParameters messageParameters);
}
