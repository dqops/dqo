package com.dqops.core.incidents;

/**
 * A creator for text field of incident notification message.
 */
public interface IncidentNotificationMessageMarkdownFormatter {

    /**
     * Prepares string for text field of notification message, which is built from multiple fields from it's parameters.
     * @param messageParameters A container with parameters that are used to build text field and make up links to application.
     * @return Markdown formatted string
     */
    String prepareText(IncidentNotificationMessageParameters messageParameters);

    /**
     * Prepares string for text field of notification message in html format, which is built from multiple fields from it's parameters.
     * @param notificationMessage A notification message.
     * @return Markdown formatted string
     */
    String prepareText(IncidentNotificationMessage notificationMessage);

}
