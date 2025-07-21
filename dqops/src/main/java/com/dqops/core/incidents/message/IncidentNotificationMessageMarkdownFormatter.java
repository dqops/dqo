/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

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
