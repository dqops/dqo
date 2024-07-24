package com.dqops.core.incidents.message;

/**
 * A creator for text field of incident notification message.
 */
public interface IncidentNotificationMessageMarkdownFormatter {

    /**
     * Prepares string for text field of notification message in html format, which is built from multiple fields from it's parameters.
     * @param notificationMessage A notification message.
     * @return Markdown formatted string
     */
    String prepareText(IncidentNotificationMessage notificationMessage);

}
