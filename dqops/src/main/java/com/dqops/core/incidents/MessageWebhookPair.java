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
package com.dqops.core.incidents;

/**
 * A pair of a message and a webhook url that is sent as a notification.
 */
public class MessageWebhookPair {
    private final IncidentNotificationMessage incidentNotificationMessage;
    private final String webhookUrl;

    /**
     * Creates an incident notification info with the target webhook url.
     * @param incidentNotificationMessage Notification message.
     * @param webhookUrl Webhook url.
     */
    public MessageWebhookPair(IncidentNotificationMessage incidentNotificationMessage, String webhookUrl) {
        this.incidentNotificationMessage = incidentNotificationMessage;
        this.webhookUrl = webhookUrl;
    }

    /**
     * Returns the notification message object.
     * @return Notification message.
     */
    public IncidentNotificationMessage getIncidentNotificationMessage() {
        return incidentNotificationMessage;
    }

    /**
     * Target webhook url.
     * @return Webhook url.
     */
    public String getWebhookUrl() {
        return webhookUrl;
    }

    @Override
    public String toString() {
        return "MessageWebhookPair{" +
                "incidentNotificationMessage=" + incidentNotificationMessage +
                ", webhookUrl='" + webhookUrl + '\'' +
                '}';
    }
}
