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

package com.dqops.data.incidents.models;

/**
 * Enumeration that says where a filtered notification for an incident is defined. Is it defined on a connection level, or on the global level.
 */
public enum IncidentFilteredNotificationLocation {
    /**
     * The notification filter is configured on a connection.
     */
    connection_notification,

    /**
     * The notification s configured on a global level.
     */
    global_notification
}
