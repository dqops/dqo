/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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