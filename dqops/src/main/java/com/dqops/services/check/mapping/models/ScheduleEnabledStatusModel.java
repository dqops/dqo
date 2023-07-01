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
package com.dqops.services.check.mapping.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of possible ways a schedule can be configured.
 */
public enum ScheduleEnabledStatusModel {
    /**
     * An enumeration signaling that the schedule is enabled.
     */
    @JsonProperty("enabled")
    enabled,

    /**
     * An enumeration signaling that the schedule has been intentionally disabled.
     */
    @JsonProperty("disabled")
    disabled,

    /**
     * An enumeration signaling that the schedule is not configured.
     */
    @JsonProperty("not_configured")
    not_configured,

    /**
     * An enumeration signaling that the schedule won't execute because it is overridden by every inner model.
     */
    @JsonProperty("overridden_by_checks")
    overridden_by_checks,
}