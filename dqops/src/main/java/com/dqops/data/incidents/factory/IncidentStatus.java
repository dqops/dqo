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
package com.dqops.data.incidents.factory;


import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of the statuses used in the "status" field of the "incidents" table.
 */
public enum IncidentStatus {
    /**
     * It is a new incident that was not reviewed.
     */
    @JsonProperty("open")
    open,

    /**
     * This is an incident that was reviewed and there is a work in progress to resolve the incident.
     * Additional incidents should not be raised until it is resolved.
     */
    @JsonProperty("acknowledged")
    acknowledged,

    /**
     * The incident was resolved. If a new data quality issue is detected, a new incident will be raised.
     */
    @JsonProperty("resolved")
    resolved,

    /**
     * The incident is muted and will not be reported.
     */
    @JsonProperty("muted")
    muted;

    public static class IncidentStatusSampleFactory implements SampleValueFactory<IncidentStatus> {
        @Override
        public IncidentStatus createSample() {
            return open;
        }
    }
}
