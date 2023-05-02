/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.incidents;

import ai.dqo.metadata.incidents.IncidentGroupingSpec;

import java.util.List;

/**
 * Incident notification service that sends notifications when new data quality incidents are detected.
 */
public interface IncidentNotificationService {
    /**
     * Sends new incident notifications to the notification targets (webhooks) specified in the incident grouping configuration.
     * @param newMessages List of new data quality incidents that will be sent as a payload.
     * @param incidentGrouping Incident grouping that identifies the notification target (where to send the notifications).
     */
    void sendNotifications(List<IncidentNotificationMessage> newMessages, IncidentGroupingSpec incidentGrouping);
}
