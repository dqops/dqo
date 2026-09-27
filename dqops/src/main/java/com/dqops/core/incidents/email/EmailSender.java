/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.incidents.email;

/**
 * Email sender constants.
 */
public class EmailSender {

    /**
     * The default email address used in the From header of the notification emails, used when the dqo.smtp-server.from-email parameter is not configured.
     */
    public static final String EMAIL_SENDER_FROM_EMAIL = "dqops_noreply@dqops.com";

    /**
     * The default sender name used in the From header of the notification emails, used when the dqo.smtp-server.from-name parameter is not configured.
     */
    public static final String EMAIL_SENDER_FROM_NAME = "DQOps Incident Notification";
}
